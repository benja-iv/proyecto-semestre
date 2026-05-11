package com.smartfood.ms_catalogo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriaRequestDTO {
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
    
    private String descripcion;
}