package com.smartfood.ms_despacho.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "despachos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Despacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotNull(message = "El ID del pedido es obligatorio")
    private Long pedidoId;

    @Column(nullable = false)
    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @Column(nullable = false)
    @NotBlank(message = "La direccion es obligatoria")
    private String direccionEntrega;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;
}