/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.senac.drogarara;

import com.senac.drogarara.conexao.Conexao;
import com.senac.drogarara.view.LoginFrame;

/**
 *
 * @author david
 */
public class Drogarara {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Conexao c = new Conexao();
        c.getConexao();
        
        LoginFrame lf = new LoginFrame();
        lf.setVisible(true);
    }
    
}
