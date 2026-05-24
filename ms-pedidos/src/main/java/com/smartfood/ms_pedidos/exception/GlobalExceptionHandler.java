package com.smartfood.ms_pedidos.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> manejarPedidoNoEncontrado(
            PedidoNotFoundException ex,
            HttpServletRequest request) {

        logger.warn("Recurso no encontrado - ID: {} | Path: {} | Mensaje: {}",
                ex.getPedidoId(), request.getRequestURI(), ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(construirError(HttpStatus.NOT_FOUND, ex.getMessage(),
                        request.getRequestURI(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> manejarValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<String> erroresCampos = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> String.format("Campo '%s': %s (valor recibido: '%s')",
                        error.getField(),
                        error.getDefaultMessage(),
                        error.getRejectedValue()))
                .collect(Collectors.toList());

        logger.warn("Validacion fallida en {} {} - Errores: {}",
                request.getMethod(), request.getRequestURI(), erroresCampos);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(construirError(HttpStatus.BAD_REQUEST,
                        "Los datos enviados contienen errores de validacion",
                        request.getRequestURI(),
                        erroresCampos));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> manejarJsonInvalido(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        logger.warn("JSON invalido en la peticion - Path: {} | Detalle: {}",
                request.getRequestURI(), ex.getMostSpecificCause().getMessage());

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(construirError(HttpStatus.BAD_REQUEST,
                        "El cuerpo de la peticion tiene un formato JSON invalido o tipos de datos incorrectos",
                        request.getRequestURI(), null));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> manejarTipoIncorrecto(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        String mensaje = String.format(
            "El parametro '%s' con valor '%s' no puede convertirse al tipo esperado '%s'",
            ex.getName(), ex.getValue(),
            ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconocido"
        );

        logger.warn("Tipo de parametro incorrecto en {} - {}", request.getRequestURI(), mensaje);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(construirError(HttpStatus.BAD_REQUEST, mensaje,
                        request.getRequestURI(), null));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDTO> manejarMetodoNoPermitido(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request) {

        logger.warn("Metodo HTTP '{}' no permitido en '{}'. Metodos permitidos: {}",
                ex.getMethod(), request.getRequestURI(),
                ex.getSupportedHttpMethods());

        String mensaje = String.format(
            "El metodo HTTP '%s' no esta permitido para este endpoint. Metodos validos: %s",
            ex.getMethod(), ex.getSupportedHttpMethods()
        );

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(construirError(HttpStatus.METHOD_NOT_ALLOWED, mensaje,
                        request.getRequestURI(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> manejarExcepcionGeneral(
            Exception ex,
            HttpServletRequest request) {

        logger.error("Error interno no controlado - Tipo: {} | Path: {} | Mensaje: {}",
                ex.getClass().getSimpleName(), request.getRequestURI(), ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(construirError(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Ocurrio un error interno del servidor. El equipo tecnico ha sido notificado.",
                        request.getRequestURI(), null));
    }

    private ErrorResponseDTO construirError(HttpStatus status, String mensaje,
                                             String path, List<String> detalles) {
        return new ErrorResponseDTO(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                mensaje,
                path,
                detalles
        );
    }
}