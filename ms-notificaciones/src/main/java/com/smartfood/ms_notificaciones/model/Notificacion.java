package com.smartfood.ms_notificaciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "El cliente ID es obligatorio")
    private Long clienteId;

    @Column(nullable = false)
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fechaEnvio;
}