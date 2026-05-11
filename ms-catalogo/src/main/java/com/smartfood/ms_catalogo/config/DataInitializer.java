package com.smartfood.ms_catalogo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.smartfood.ms_catalogo.model.Categoria;
import com.smartfood.ms_catalogo.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    
    private final CategoriaRepository categoriaRepository;
    
    @Override
    public void run(String... args){
        
        if(categoriaRepository.count() > 0){
            log.info("Categorías ya cargadas, se omite este archivo");
            return; 
        }
        
        log.info("Cargando categorías...");
        categoriaRepository.save(new Categoria(null, "Hamburguesas"));
        categoriaRepository.save(new Categoria(null, "Bebidas"));
        categoriaRepository.save(new Categoria(null, "Postres"));
        log.info("Categorías cargadas exitosamente");
    }
}