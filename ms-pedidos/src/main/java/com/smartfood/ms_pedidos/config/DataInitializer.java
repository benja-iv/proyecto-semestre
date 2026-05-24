package com.smartfood.ms_pedidos.config;

import com.smartfood.ms_pedidos.model.Pedido;
import com.smartfood.ms_pedidos.repository.PedidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final PedidoRepository pedidoRepository;

    public DataInitializer(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public void run(String... args) {
        if (pedidoRepository.count() > 0) {
            logger.info("DataInitializer: BD ya tiene {} pedido(s). Omitiendo carga inicial.",
                    pedidoRepository.count());
            return;
        }

        logger.info("DataInitializer: cargando datos de prueba...");

        pedidoRepository.save(new Pedido(null, "Juan Perez", "2 Hamburguesas, 1 Refresco", 15000.0, "PENDIENTE", LocalDateTime.now().minusHours(2)));
        pedidoRepository.save(new Pedido(null, "Maria Gomez", "1 Pizza Familiar, 2 Refrescos", 25000.0, "EN_PREPARACION", LocalDateTime.now().minusHours(1)));
        pedidoRepository.save(new Pedido(null, "Carlos Lopez", "Sushi variado 30 piezas", 35000.0, "PAGADO", LocalDateTime.now()));

        logger.info("DataInitializer: {} pedidos cargados exitosamente.",
                pedidoRepository.count());
    }
}