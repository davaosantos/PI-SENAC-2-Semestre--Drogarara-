/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.drogarara.conexao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author david
 */
public class Conexao {
    
    public static Connection getConexao(){
        try {
            //Tenta estabelecer conexao
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/drogarara?serverTimezone=UTC", // url
                    "davaosantos", // usuario
                    "repolho123" // senha
            );
            return conn;
        } catch (Exception e) {
            //Erro no momento de conectar
            System.out.println("Erro ao conectar " + e.getMessage());
            return null;
        }
    }
    
}
