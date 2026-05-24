package com.smartfood.ms_carrito.service;

import com.smartfood.ms_carrito.client.ProductoClient;
import com.smartfood.ms_carrito.dto.ItemCarritoRequestDTO;
import com.smartfood.ms_carrito.dto.ProductoResponseDTO;
import com.smartfood.ms_carrito.exception.ItemCarritoNotFoundException;
import com.smartfood.ms_carrito.model.ItemCarrito;
import com.smartfood.ms_carrito.repository.ItemCarritoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarritoService {
    private static final Logger logger = LoggerFactory.getLogger(CarritoService.class);
    private final ItemCarritoRepository repository;
    private final ProductoClient productoClient;

    public CarritoService(ItemCarritoRepository repository, ProductoClient productoClient) {
        this.repository = repository;
        this.productoClient = productoClient;
    }

    @Transactional(readOnly = true)
    public List<ItemCarrito> obtenerPorCliente(Long clienteId) {
        logger.info("Consultando carrito del cliente ID: {}", clienteId);
        return repository.findByClienteId(clienteId);
    }

    @Transactional
    public ItemCarrito agregarItem(ItemCarritoRequestDTO dto) {
        logger.info("Validando producto ID {} en ms-catalogo antes de agregar al carrito", dto.getProductoId());
        try {
            ProductoResponseDTO producto = productoClient.obtenerProductoPorId(dto.getProductoId());
            if (producto == null || producto.getStock() < dto.getCantidad()) {
                throw new RuntimeException("Producto sin stock suficiente o no encontrado");
            }
        } catch (Exception e) {
            logger.error("Error al comunicar con ms-catalogo: {}", e.getMessage());
            throw new RuntimeException("No se pudo validar el producto. Intente mas tarde.");
        }

        logger.info("Agregando producto ID {} al carrito del cliente ID {}", dto.getProductoId(), dto.getClienteId());
        ItemCarrito item = new ItemCarrito(null, dto.getClienteId(), dto.getProductoId(), dto.getCantidad());
        return repository.save(item);
    }

    @Transactional
    public void eliminarItem(Long id) {
        logger.info("Eliminando item de carrito ID: {}", id);
        if (!repository.existsById(id)) {
            throw new ItemCarritoNotFoundException(id);
        }
        repository.deleteById(id);
    }
}