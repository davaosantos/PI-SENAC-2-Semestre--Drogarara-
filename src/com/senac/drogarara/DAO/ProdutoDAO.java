/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.drogarara.DAO;

import com.senac.drogarara.conexao.Conexao;
import com.senac.drogarara.gui.LoginFrame;
import com.senac.drogarara.model.Produto;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
*Classe responsável por interagir com o banco de dados para realizar operações relacionadas aos produtos.
*@author david
*/
public class ProdutoDAO {
    
    /**

    Realiza o cadastro de um novo produto no banco de dados.
    @param p O objeto Produto a ser cadastrado.
    */
    public static void cadastroProduto(Produto p) {

        //ESTRUTURA COM O BANCO
        try (Connection con = Conexao.getConexao(); PreparedStatement stm = con.prepareStatement("INSERT INTO Produto(nome, quantidade_estoque, categoria, valor, descricao) VALUES (?, ?, ?, ?, ?)")) {

            stm.setString(1, p.getNome());
            stm.setInt(2, p.getQtdEstoque());
            stm.setString(3, p.getCategoria());
            stm.setDouble(4, p.getValor());
            stm.setString(5, p.getDescricao());

            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
        }

    }
    
    /**

    Consulta produtos pelo nome.
    @param consNmProduto O nome do produto a ser consultado.
    @return Uma lista de objetos Produto correspondentes ao nome informado.
    */
    public static List<Produto> consultaProdutoNome(String consNmProduto) {

        List<Produto> produtoList = new ArrayList<>();

        try (Connection con = Conexao.getConexao(); 
                
                PreparedStatement stm = con.prepareStatement("SELECT id_produto, nome, quantidade_estoque, categoria, valor,descricao  FROM Produto WHERE nome LIKE ?")) {

            stm.setString(1, consNmProduto + "%");

            // Executar a consulta SQL e obter os resultados
            ResultSet rs = stm.executeQuery();

            // Criar um objeto DefaultTableModel para armazenar os dados do JTable
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "QTD Estoque", "Categoria", "Valor", "Descrição"}, 0);

            if (rs != null) {
                // Preencher o modelo com os dados da consulta
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setIdProduto(rs.getInt("id_produto"));
                    p.setNome(rs.getString("nome"));
                    p.setQtdEstoque(rs.getInt("quantidade_estoque"));
                    p.setCategoria(rs.getString("categoria"));
                    p.setValor(Double.parseDouble(rs.getString("valor")));
                    p.setDescricao(rs.getString("descricao"));

                    produtoList.add(p);

                }
            }

            // Fechar os objetos ResultSet, PreparedStatement e Connection
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
        }

        return produtoList;
    }
    
    
    /**

    Exclui um produto do banco de dados com base no ID do produto.
    @param idProduto O ID do produto a ser excluído.
    @return true se a exclusão foi bem-sucedida, caso contrário false.
    */
    public static boolean excluirProduto(Integer idProduto) {
        
        boolean booleanExclusao = false;

        try {
            // Criar uma conexão com o banco de dados
            Connection con = Conexao.getConexao();

            
            // Preparar a consulta SQL
            PreparedStatement stm = con.prepareStatement("DELETE FROM Produto WHERE id_produto = ?");

            // Preencher o valor do parâmetro da consulta SQL com o ID do cliente selecionado
            stm.setInt(1, idProduto);

            // Executar a consulta SQL
            int numLinhas = stm.executeUpdate();

            //Se a query obtiver sucesso
            if (numLinhas > 0) {
                JOptionPane.showMessageDialog(null, "Produto excluido com sucesso!");
            } else {
                booleanExclusao = true;
                JOptionPane.showMessageDialog(null, "Erro ao processar exclusão");
            }

            // Fechar os objetos PreparedStatement e Connection
            stm.close();
            con.close();
          
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao excluir o produto: " + ex.getMessage());
        }
        
        return booleanExclusao;

    }
    
    /**

    Atualiza os dados de um produto no banco de dados.
    @param p O objeto Produto com os dados atualizados.
    @throws SQLException Se ocorrer um erro ao executar a atualização no banco de dados.
    */
    public static void atualizarProduto(Produto p) throws SQLException {
        
        // Criar uma conexão com o banco de dados
            Connection con = Conexao.getConexao();

            // Preparar a consulta SQL
            PreparedStatement stm = con.prepareStatement("UPDATE Produto "
                    + "SET nome = ?,"
                    + "quantidade_estoque = ?,"
                    + "categoria = ?,"
                    + "valor = ?,"
                    + "descricao = ?" 
                    + "WHERE id_produto = ?"
            );
                             
            //instrucaoSQL.setInt(0, c.getId());
            stm.setString(1, p.getNome());
            stm.setString(2, String.valueOf(p.getQtdEstoque()));
            stm.setString(3,p.getCategoria());
            stm.setDouble(4, p.getValor());
            stm.setString(5, p.getDescricao());
            stm.setString(6, String.valueOf(p.getIdProduto()));

            int numLinhas = stm.executeUpdate();

            //Se a query obtiver sucesso
            if (numLinhas > 0) {
                JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
            }
        
    }
    
    /**

    Consulta um produto pelo ID.
    @param idProduto O ID do produto a ser consultado.
    @return O objeto Produto correspondente ao ID informado.
    */
    public static Produto buscarProdutosPorId(Integer idProduto) {

        Produto p = new Produto();

        try (Connection con = Conexao.getConexao(); 
                
            PreparedStatement stm = con.prepareStatement("SELECT id_produto, nome, quantidade_estoque, categoria, valor,descricao  FROM Produto WHERE id_produto = ?")) {
            stm.setInt(1, idProduto);
            

            // Executar a consulta SQL e obter os resultados
            ResultSet rs = stm.executeQuery();

            if (rs.next()) { // Verificar se há resultados
                // Preencher o modelo com os dados da consulta
                p.setIdProduto(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                
            }

            // Fechar os objetos ResultSet, PreparedStatement e Connection
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
        }

        return p;
    }
    
    /**

    Consulta a disponibilidade de um produto com base no ID do produto e na quantidade desejada.
    @param id_produto O ID do produto a ser consultado.
    @param quantidadePedido A quantidade desejada do produto.
    @return true se o produto estiver disponível na quantidade desejada, caso contrário false.
    */
    public static boolean consultaDisponibilidadeProd(int id_produto, int quantidadePedido) {
        
        
        Produto p = new Produto();
        boolean produtoDisponivel = false;

        try (Connection con = Conexao.getConexao(); 
                
            PreparedStatement stm = con.prepareStatement("SELECT id_produto, nome, quantidade_estoque, categoria, valor,descricao  FROM Produto WHERE id_produto = ?")) {
            stm.setInt(1, id_produto);
            

            // Executar a consulta SQL e obter os resultados
            ResultSet rs = stm.executeQuery();

            if (rs.next()) { // Verificar se há resultados
                // Preencher o modelo com os dados da consulta
                p.setIdProduto(rs.getInt("id_produto"));
                p.setNome(rs.getString("nome"));
                p.setQtdEstoque(rs.getInt("quantidade_estoque"));
            }
            
            if(p.getQtdEstoque() >= quantidadePedido){
                produtoDisponivel = true;
            }

            // Fechar os objetos ResultSet, PreparedStatement e Connection
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
        }

        return produtoDisponivel;
        
    }
    
    
};

