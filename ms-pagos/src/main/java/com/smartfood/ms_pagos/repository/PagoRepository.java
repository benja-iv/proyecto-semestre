package com.smartfood.ms_pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartfood.ms_pagos.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}