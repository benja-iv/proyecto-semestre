package com.smartfood.ms_pedidos.controller;

import com.smartfood.ms_pedidos.dto.PedidoRequestDTO;
import com.smartfood.ms_pedidos.dto.PedidoResponseDTO;
import com.smartfood.ms_pedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Validated
public class PedidoController {

    private static final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(
        summary = "Lista todos los pedidos",
        description = "Retorna la lista completa de pedidos registrados en el sistema."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> obtenerTodos() {
        logger.debug("GET /api/pedidos recibido");
        return ResponseEntity.ok(pedidoService.obtenerTodos());
    }

    @Operation(
        summary = "Busca un pedido por ID",
        description = "Retorna los datos de un pedido específico según su identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado correctamente"),
        @ApiResponse(responseCode = "404", description = "No existe un pedido con ese ID")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPorId(@PathVariable Long id) {
        logger.debug("GET /api/pedidos/{} recibido", id);
        return ResponseEntity.ok(pedidoService.obtenerPorId(id));
    }

    @Operation(
        summary = "Crea un nuevo pedido",
        description = "Registra un nuevo pedido para un cliente, validando disponibilidad de productos con ms-catalogo e ms-inventario."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos o producto sin stock suficiente")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> crearPedido(@Valid @RequestBody PedidoRequestDTO dto) {
        logger.debug("POST /api/pedidos recibido para cliente: '{}'", dto.getCliente());
        PedidoResponseDTO creado = pedidoService.crearPedido(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
        summary = "Actualiza el estado de un pedido",
        description = "Cambia el estado de un pedido existente (ej. PENDIENTE, PAGADO, DESPACHADO)."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Estado actualizado correctamente, sin contenido de retorno"),
        @ApiResponse(responseCode = "404", description = "No existe un pedido con ese ID")
    })
    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Long id,
            @Parameter(description = "Nuevo estado del pedido (ej. PENDIENTE, PAGADO, DESPACHADO)")
            @RequestParam String estado) {
        logger.debug("PUT /api/pedidos/{}/estado recibido con estado: '{}'", id, estado);
        pedidoService.actualizarEstado(id, estado);
        return ResponseEntity.noContent().build();
    }
}
