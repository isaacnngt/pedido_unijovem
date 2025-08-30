package com.delivery.repository;

import com.delivery.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos por status de entrega
    List<Pedido> findByEntregue(Boolean entregue);

    // Buscar pedidos por nome da pessoa (ignora case)
    List<Pedido> findByNomePessoaContainingIgnoreCase(String nomePessoa);

    // Buscar pedidos ordenados por data (mais recentes primeiro)
    @Query("SELECT p FROM Pedido p ORDER BY p.dataPedido DESC")
    List<Pedido> findAllOrderByDataPedidoDesc();

    // Contar pedidos não entregues
    Long countByEntregue(Boolean entregue);
}
