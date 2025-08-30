package com.delivery.controller;

import com.delivery.model.Pedido;
import com.delivery.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin(origins = "http://localhost:3000") // Para permitir requisições do React
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Criar novo pedido
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@Valid @RequestBody Pedido pedido) {
        try {
            Pedido novoPedido = pedidoService.criarPedido(pedido);
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodosPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodosPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(pedido -> new ResponseEntity<>(pedido, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar pedido
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id,
                                                  @Valid @RequestBody Pedido pedido) {
        try {
            Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedido);
            return new ResponseEntity<>(pedidoAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Marcar pedido como entregue
    @PatchMapping("/{id}/entregar")
    public ResponseEntity<Pedido> marcarComoEntregue(@PathVariable Long id) {
        try {
            Pedido pedidoEntregue = pedidoService.marcarComoEntregue(id);
            return new ResponseEntity<>(pedidoEntregue, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Deletar pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarPedido(@PathVariable Long id) {
        try {
            pedidoService.deletarPedido(id);
            return new ResponseEntity<>(
                    Map.of("message", "Pedido deletado com sucesso"),
                    HttpStatus.OK
            );
        } catch (RuntimeException e) {
            return new ResponseEntity<>(
                    Map.of("error", "Pedido não encontrado"),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    // Listar pedidos não entregues
    @GetMapping("/nao-entregues")
    public ResponseEntity<List<Pedido>> listarPedidosNaoEntregues() {
        List<Pedido> pedidos = pedidoService.listarPedidosNaoEntregues();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Listar pedidos entregues
    @GetMapping("/entregues")
    public ResponseEntity<List<Pedido>> listarPedidosEntregues() {
        List<Pedido> pedidos = pedidoService.listarPedidosEntregues();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Buscar pedidos por nome da pessoa
    @GetMapping("/buscar")
    public ResponseEntity<List<Pedido>> buscarPorNome(@RequestParam String nome) {
        List<Pedido> pedidos = pedidoService.buscarPorNomePessoa(nome);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    // Estatísticas básicas
    @GetMapping("/estatisticas")
    public ResponseEntity<Map<String, Object>> obterEstatisticas() {
        Long pedidosPendentes = pedidoService.contarPedidosPendentes();
        List<Pedido> todosPedidos = pedidoService.listarTodosPedidos();

        Map<String, Object> estatisticas = Map.of(
                "totalPedidos", todosPedidos.size(),
                "pedidosPendentes", pedidosPendentes,
                "pedidosEntregues", todosPedidos.size() - pedidosPendentes
        );

        return new ResponseEntity<>(estatisticas, HttpStatus.OK);
    }
}