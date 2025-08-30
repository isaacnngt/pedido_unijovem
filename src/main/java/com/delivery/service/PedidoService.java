package com.delivery.service;

import com.delivery.model.Pedido;
import com.delivery.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // Criar novo pedido
    public Pedido criarPedido(Pedido pedido) {
        pedido.setDataPedido(LocalDateTime.now());
        if (pedido.getEntregue() == null) {
            pedido.setEntregue(false);
        }
        return pedidoRepository.save(pedido);
    }

    // Listar todos os pedidos ordenados por data
    public List<Pedido> listarTodosPedidos() {
        return pedidoRepository.findAllOrderByDataPedidoDesc();
    }

    // Buscar pedido por ID
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidoRepository.findById(id);
    }

    // Atualizar pedido
    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setNomePessoa(pedidoAtualizado.getNomePessoa());
                    pedido.setQuantidade(pedidoAtualizado.getQuantidade());
                    pedido.setFormaPagamento(pedidoAtualizado.getFormaPagamento());
                    pedido.setEntregue(pedidoAtualizado.getEntregue());
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + id));
    }

    // Marcar como entregue
    public Pedido marcarComoEntregue(Long id) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setEntregue(true);
                    return pedidoRepository.save(pedido);
                })
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com id: " + id));
    }

    // Deletar pedido
    public void deletarPedido(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
        } else {
            throw new RuntimeException("Pedido não encontrado com id: " + id);
        }
    }

    // Listar pedidos não entregues
    public List<Pedido> listarPedidosNaoEntregues() {
        return pedidoRepository.findByEntregue(false);
    }

    // Listar pedidos entregues
    public List<Pedido> listarPedidosEntregues() {
        return pedidoRepository.findByEntregue(true);
    }

    // Buscar pedidos por nome da pessoa
    public List<Pedido> buscarPorNomePessoa(String nomePessoa) {
        return pedidoRepository.findByNomePessoaContainingIgnoreCase(nomePessoa);
    }

    // Contar pedidos pendentes
    public Long contarPedidosPendentes() {
        return pedidoRepository.countByEntregue(false);
    }
}
