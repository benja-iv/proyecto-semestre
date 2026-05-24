package com.smartfood.ms_catalogo.config;

import com.smartfood.ms_catalogo.model.Categoria;
import com.smartfood.ms_catalogo.repository.CategoriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private final CategoriaRepository categoriaRepository;

    public DataInitializer(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) {
        if (categoriaRepository.count() == 0) {
            logger.info("Inicializando catalogo de categorias...");
            categoriaRepository.save(new Categoria(null, "Comida Rápida"));
            categoriaRepository.save(new Categoria(null, "Pizzas"));
            categoriaRepository.save(new Categoria(null, "Bebidas"));
            categoriaRepository.save(new Categoria(null, "Postres"));
            logger.info("Catalogo de categorias inicializado exitosamente.");
        }
    }
}