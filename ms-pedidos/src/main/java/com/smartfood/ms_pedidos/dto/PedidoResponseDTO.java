package com.smartfood.ms_pedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private Long clienteId;
    private Long productoId;
    private Integer cantidad;
    private Double total;
    private LocalDateTime fechaPedido;
}