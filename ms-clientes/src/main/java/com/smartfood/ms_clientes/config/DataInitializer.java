package com.smartfood.ms_clientes.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartfood.ms_clientes.model.Cliente;
import com.smartfood.ms_clientes.repository.ClienteRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;

    @Override
    public void run(String... args) throws Exception {
        clienteRepository.saveAll(List.of(
            new Cliente(null, "Juan Artus", "juan@gmail.com", "+56912345678", "Av. Calera 123"),
            new Cliente(null, "Maria Gomez", "maria@gmail.com", "+56987654321", "Santiago 321")
        ));
    }
}