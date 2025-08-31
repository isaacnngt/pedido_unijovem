package com.delivery.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Schema(description = "Entidade que representa um pedido de delivery")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do pedido", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Nome da pessoa é obrigatório")
    @Column(name = "nome_pessoa", nullable = false)
    @Schema(description = "Nome da pessoa que fez o pedido", example = "João Silva")
    private String nomePessoa;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    @Column(name = "quantidade", nullable = false)
    @Schema(description = "Quantidade de itens no pedido", example = "2", minimum = "1")
    private Integer quantidade;

    @Column(name = "forma_pagamento")
    @Schema(description = "Forma de pagamento escolhida", example = "PIX")
    private String formaPagamento;

    @Column(name = "entregue", nullable = false)
    @Schema(description = "Status de entrega do pedido", example = "false")
    private Boolean entregue = false;

    @Column(name = "data_pedido", nullable = false)
    @Schema(description = "Data e hora de criação do pedido", example = "2025-08-31T10:30:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime dataPedido;

    // Construtor padrão
    public Pedido() {
        this.dataPedido = LocalDateTime.now();
        this.entregue = false;
    }

    // Construtor com parâmetros
    public Pedido(String nomePessoa, Integer quantidade, String formaPagamento) {
        this();
        this.nomePessoa = nomePessoa;
        this.quantidade = quantidade;
        this.formaPagamento = formaPagamento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Boolean getEntregue() {
        return entregue;
    }

    public void setEntregue(Boolean entregue) {
        this.entregue = entregue;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", nomePessoa='" + nomePessoa + '\'' +
                ", quantidade=" + quantidade +
                ", formaPagamento='" + formaPagamento + '\'' +
                ", entregue=" + entregue +
                ", dataPedido=" + dataPedido +
                '}';
    }
}