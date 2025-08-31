package com.delivery.controller;

import com.delivery.model.Pedido;
import com.delivery.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "API para gerenciar pedidos de delivery")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    @Operation(
            summary = "Criar novo pedido",
            description = "Cria um novo pedido de delivery com os dados fornecidos"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Pedido> criarPedido(
            @Parameter(description = "Dados do novo pedido")
            @Valid @RequestBody Pedido pedido) {
        try {
            Pedido novoPedido = pedidoService.criarPedido(pedido);
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(
            summary = "Listar todos os pedidos",
            description = "Retorna uma lista com todos os pedidos ordenados por data de criação (mais recentes primeiro)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de pedidos retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    public ResponseEntity<List<Pedido>> listarTodosPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodosPedidos();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar pedido por ID",
            description = "Retorna um pedido específico baseado no seu ID único"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))
            ),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Pedido> buscarPorId(
            @Parameter(description = "ID único do pedido", example = "1")
            @PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(pedido -> new ResponseEntity<>(pedido, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar pedido",
            description = "Atualiza completamente os dados de um pedido existente"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido atualizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))
            ),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    public ResponseEntity<Pedido> atualizarPedido(
            @Parameter(description = "ID do pedido a ser atualizado", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do pedido")
            @Valid @RequestBody Pedido pedido) {
        try {
            Pedido pedidoAtualizado = pedidoService.atualizarPedido(id, pedido);
            return new ResponseEntity<>(pedidoAtualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}/entregar")
    @Operation(
            summary = "Marcar pedido como entregue",
            description = "Altera o status de um pedido para 'entregue'"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Pedido marcado como entregue com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Pedido.class))
            ),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Pedido> marcarComoEntregue(
            @Parameter(description = "ID do pedido a ser marcado como entregue", example = "1")
            @PathVariable Long id) {
        try {
            Pedido pedidoEntregue = pedidoService.marcarComoEntregue(id);
            return new ResponseEntity<>(pedidoEntregue, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Deletar pedido",
            description = "Remove permanentemente um pedido do sistema"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public ResponseEntity<Map<String, String>> deletarPedido(
            @Parameter(description = "ID do pedido a ser deletado", example = "1")
            @PathVariable Long id) {
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

    @GetMapping("/nao-entregues")
    @Operation(
            summary = "Listar pedidos pendentes",
            description = "Retorna apenas os pedidos que ainda não foram entregues"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de pedidos pendentes",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    public ResponseEntity<List<Pedido>> listarPedidosNaoEntregues() {
        List<Pedido> pedidos = pedidoService.listarPedidosNaoEntregues();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/entregues")
    @Operation(
            summary = "Listar pedidos entregues",
            description = "Retorna apenas os pedidos que já foram entregues"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de pedidos entregues",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    public ResponseEntity<List<Pedido>> listarPedidosEntregues() {
        List<Pedido> pedidos = pedidoService.listarPedidosEntregues();
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/buscar")
    @Operation(
            summary = "Buscar pedidos por nome",
            description = "Busca pedidos pelo nome da pessoa (busca parcial, case insensitive)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de pedidos encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))
    )
    public ResponseEntity<List<Pedido>> buscarPorNome(
            @Parameter(description = "Nome ou parte do nome da pessoa", example = "João")
            @RequestParam String nome) {
        List<Pedido> pedidos = pedidoService.buscarPorNomePessoa(nome);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @GetMapping("/estatisticas")
    @Operation(
            summary = "Obter estatísticas",
            description = "Retorna estatísticas básicas dos pedidos (total, pendentes, entregues)"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Estatísticas dos pedidos",
            content = @Content(mediaType = "application/json")
    )
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