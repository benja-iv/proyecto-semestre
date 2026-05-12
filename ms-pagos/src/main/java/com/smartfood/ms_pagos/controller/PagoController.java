package com.smartfood.ms_pagos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.smartfood.ms_pagos.dto.PagoRequestDTO;
import com.smartfood.ms_pagos.dto.PagoResponseDTO;
import com.smartfood.ms_pagos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponseDTO> crear(@Valid @RequestBody PagoRequestDTO dto) {
        return new ResponseEntity<>(pagoService.procesarPago(dto), HttpStatus.CREATED);
    }
}