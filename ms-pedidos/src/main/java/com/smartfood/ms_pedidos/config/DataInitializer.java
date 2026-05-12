package com.smartfood.ms_pedidos.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.smartfood.ms_pedidos.model.Pedido;
import com.smartfood.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        pedidoRepository.saveAll(List.of(
    new Pedido(null, 1L, 1L, 1, 0.0, LocalDateTime.now(), "PENDIENTE"),
    new Pedido(null, 2L, 2L, 2, 0.0, LocalDateTime.now(), "PENDIENTE")
    ));
    }
}