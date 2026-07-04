package com.smartfood.ms_pedidos.repository;

import com.smartfood.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

  public Pedido findByList<Pedido> findByEstado(String estado){
    return estado;
  };

}

