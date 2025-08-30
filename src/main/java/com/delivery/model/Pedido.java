package com.delivery.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da pessoa é obrigatório")
    @Column(name = "nome_pessoa", nullable = false)
    private String nomePessoa;

    @NotNull(message = "Quantidade é obrigatória")
    @Positive(message = "Quantidade deve ser maior que zero")
    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "forma_pagamento")
    private String formaPagamento;

    @Column(name = "entregue", nullable = false)
    private Boolean entregue = false;

    @Column(name = "data_pedido", nullable = false)
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
