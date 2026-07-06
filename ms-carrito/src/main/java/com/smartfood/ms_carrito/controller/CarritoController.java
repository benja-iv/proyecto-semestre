package com.smartfood.ms_carrito.controller;

import com.smartfood.ms_carrito.dto.ItemCarritoRequestDTO;
import com.smartfood.ms_carrito.model.ItemCarrito;
import com.smartfood.ms_carrito.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {
    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @Operation(
        summary = "Consulta el carrito de un cliente",
        description = "Retorna todos los items agregados al carrito de compras de un cliente específico."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito obtenido correctamente")
    })
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ItemCarrito>> obtenerPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.obtenerPorCliente(clienteId));
    }

    @Operation(
        summary = "Agrega un producto al carrito",
        description = "Añade un item al carrito de un cliente, validando previamente el producto y su stock disponible en ms-catalogo."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item agregado correctamente al carrito"),
        @ApiResponse(responseCode = "400", description = "Producto sin stock suficiente, no encontrado, o servicio de catálogo no disponible")
    })
    @PostMapping
    public ResponseEntity<ItemCarrito> agregarItem(@Valid @RequestBody ItemCarritoRequestDTO dto) {
        return ResponseEntity.ok(service.agregarItem(dto));
    }

    @Operation(
        summary = "Elimina un item del carrito",
        description = "Quita un item específico del carrito de compras según su identificador."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item eliminado correctamente, sin contenido de retorno"),
        @ApiResponse(responseCode = "404", description = "No existe un item de carrito con ese ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarItem(@PathVariable Long id) {
        service.eliminarItem(id);
        return ResponseEntity.noContent().build();
    }
}
