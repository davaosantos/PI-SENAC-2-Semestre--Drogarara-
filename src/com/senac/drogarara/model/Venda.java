/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.drogarara.model;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author david
 */
public class Venda {
    
    private int id_venda;
    private int id_cliente;
    private BigDecimal valor_total;
    private Date data_venda;
    
    private List<VendaProduto> itens;

    public Venda() {
    }
    
    

    public Venda(int id_venda, int id_cliente, BigDecimal valor_total, Date data_venda, List<VendaProduto> itens) {
        this.id_venda = id_venda;
        this.id_cliente = id_cliente;
        this.valor_total = valor_total;
        this.data_venda = data_venda;
        this.itens = itens;
    }
    
    //Construtor para o relatorio 
    public Venda(int id_venda, int id_cliente, BigDecimal valor_total, Date data_venda) {
        this.id_venda = id_venda;
        this.id_cliente = id_cliente;
        this.valor_total = valor_total;
        this.data_venda = data_venda;
    }
    
    public int getId_venda() {
        return id_venda;
    }

    public void setId_venda(int id_venda) {
        this.id_venda = id_venda;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }


    public BigDecimal getValor_total() {
        return valor_total;
    }

    public void setValor_total(BigDecimal valor_total) {
        this.valor_total = valor_total;
    }

    public Date getData_venda() {
        return data_venda;
    }

    public void setData_venda(Date data_venda) {
        this.data_venda = data_venda;
    }

    public List<VendaProduto> getItens() {
        return itens;
    }

    public void setItens(List<VendaProduto> itens) {
        this.itens = itens;
    }
    
    
}
