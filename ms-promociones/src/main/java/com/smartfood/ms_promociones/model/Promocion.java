package com.smartfood.ms_promociones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "promociones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Promocion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "El codigo es obligatorio")
    private String codigo;

    @Column(nullable = false)
    @NotNull(message = "El porcentaje es obligatorio")
    @Min(value = 1, message = "Minimo 1% de descuento")
    private Integer porcentajeDescuento;

    @Column(nullable = false)
    private Boolean activa;
}