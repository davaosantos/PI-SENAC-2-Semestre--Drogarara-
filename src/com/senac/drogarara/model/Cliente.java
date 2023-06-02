/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.drogarara.model;

import java.util.Date;

/**
 *
 * @author david
 */
public class Cliente {
    
    Integer idCliente;
    
    String nome;
    
    Long cpf;
    
    String endereco;
    
    String email;
    
    String estadoCivil;
    
    Long telefone;
    
    String sexo;
    
    String dtNascimento;

    public Cliente() {
    }

    public Cliente(Integer idCliente, String nome, Long cpf, String endereco, String email, String estadoCivil, Long telefone, String sexo, String dtNascimento) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.cpf = cpf;
        this.endereco = endereco;
        this.email = email;
        this.estadoCivil = estadoCivil;
        this.telefone = telefone;
        this.sexo = sexo;
        this.dtNascimento = dtNascimento;
    }
   
   
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public String getDtNascimento() {
        return dtNascimento;
    }

    public void setDtNascimento(String dtNascimento) {
        this.dtNascimento = dtNascimento;
    }
    
    
    
    
    
    
}
