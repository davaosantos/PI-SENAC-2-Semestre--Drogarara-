/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.drogarara.model;

/**
 *
 * @author david
 */
public class Produto {
    
    Integer idProduto;
    
    String nome;
    
    Integer qtdEstoque;
    
    String categoria;
    
    Double valor;
    
    String descricao;

    public Produto() {
    }

    public Produto(Integer idProduto, String nome, Integer qtdEstoque, String categoria, Double valor, String descricao) {
        this.idProduto = idProduto;
        this.nome = nome;
        this.qtdEstoque = qtdEstoque;
        this.categoria = categoria;
        this.valor = valor;
        this.descricao = descricao;
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    
    
}
