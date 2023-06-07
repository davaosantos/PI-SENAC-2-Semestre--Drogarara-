/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.drogarara.DAO;

import com.senac.drogarara.conexao.Conexao;
import com.senac.drogarara.view.LoginFrame;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.senac.drogarara.model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/**
 * Classe responsável por realizar operações de acesso a dados relacionadas ao cliente.
 * Essa classe possui métodos para cadastrar, consultar, atualizar e excluir clientes.
  * @author david
 */
public class ClienteDAO {
    
    /**
     * Realiza o cadastro de um novo cliente no banco de dados.
     *
     * @param c O objeto Cliente a ser cadastrado.
     */
    public static void cadastroCliente(Cliente c) {
        //ESTRUTURA COM O BANCO
        try (Connection con = Conexao.getConexao(); PreparedStatement stm = con.prepareStatement("INSERT INTO Cliente(nome, cpf, endereco, email, estado_civil, telefone, sexo, data_nascimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            stm.setString(1, c.getNome());
            stm.setLong(2, c.getCpf());
            stm.setString(3, c.getEndereco());
            stm.setString(4, c.getEmail());
            stm.setString(5, c.getEstadoCivil());
            stm.setLong(6, c.getTelefone());
            stm.setString(7, c.getSexo());
            stm.setString(8, c.getDtNascimento());

            int rowsAffected = stm.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao cadastrar cliente");
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
        }
    }
    
    
    /**
     * Realiza a consulta de clientes com base no CPF.
     *
     * @param consCpfCliente O CPF do cliente a ser consultado.
     * @return Uma lista de clientes que correspondem ao CPF informado.
     */
    public static List<Cliente> consultarCliente(Long consCpfCliente) {

        List<Cliente> clienteList = new ArrayList<>();

        try (Connection con = Conexao.getConexao(); PreparedStatement stm = con.prepareStatement("SELECT id_cliente, nome, cpf, endereco, telefone, email, sexo , data_nascimento, estado_civil FROM Cliente WHERE cpf LIKE ?")) {
            stm.setString(1, String.valueOf(consCpfCliente) + "%");

            // Executar a consulta SQL e obter os resultados
            ResultSet rs = stm.executeQuery();

            // Criar um objeto DefaultTableModel para armazenar os dados do JTable
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Endereço", "Telefone", "Email", "Sexo", "Dt Nascimento", "Estado civil"}, 0);

            if (rs != null) {
                // Preencher o modelo com os dados da consulta
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getLong("cpf"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setTelefone(Long.parseLong(rs.getString("telefone")));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setSexo(rs.getString("sexo"));
                    cliente.setDtNascimento(rs.getDate("data_nascimento").toString());
                    cliente.setEstadoCivil(rs.getString("estado_civil").toString());

                    clienteList.add(cliente);

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

        return clienteList;
    }
    
    /**
     * Exclui um cliente do banco de dados com base no ID do cliente.
     *
     * @param idCliente O ID do cliente a ser excluído.
     * @return `true` se a exclusão foi bem-sucedida, caso contrário `false`.
     */
    public static boolean excluirCliente(Integer idCliente) {
        
        boolean booleanExclusao = false;

        try {
            // Criar uma conexão com o banco de dados
            Connection con = Conexao.getConexao();

            
            // Preparar a consulta SQL
            PreparedStatement stm = con.prepareStatement("DELETE FROM Cliente WHERE id_cliente = ?");

            // Preencher o valor do parâmetro da consulta SQL com o ID do cliente selecionado
            stm.setInt(1, idCliente);

            // Executar a consulta SQL
            int numLinhas = stm.executeUpdate();

            //Se a query obtiver sucesso
            if (numLinhas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso!");
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
     * Atualiza os dados de um cliente no banco de dados.
     *
     * @param c O objeto Cliente com os dados atualizados.
     * @throws SQLException Se ocorrer um erro ao executar a atualização no banco de dados.
     */
    public static void atualizarCliente(Cliente c) throws SQLException {
        
        // Criar uma conexão com o banco de dados
            Connection con = Conexao.getConexao();

            // Preparar a consulta SQL
            PreparedStatement stm = con.prepareStatement("UPDATE Cliente "
                    + "SET nome = ?,"
                    + "cpf = ?,"
                    + "endereco = ?,"
                    + "email = ?,"
                    + "estado_civil = ?,"
                    + "telefone = ?,"
                    + "sexo = ?,"
                    + "data_nascimento = ?"
                    + "WHERE id_cliente = ?"  
            );
                             
            //instrucaoSQL.setInt(0, c.getId());
            stm.setString(1, c.getNome());
            stm.setString(2, String.valueOf(c.getCpf()));
            stm.setString(3,c.getEndereco());
            stm.setString(4, c.getEmail());
            stm.setString(5, c.getEstadoCivil());
            stm.setLong(6, c.getTelefone());
            stm.setString(7, c.getSexo());
            stm.setString(8, c.getDtNascimento());
            stm.setString(9, String.valueOf(c.getIdCliente()));

            int numLinhas = stm.executeUpdate();

            //Se a query obtiver sucesso
            if (numLinhas > 0) {
                JOptionPane.showMessageDialog(null, "Cliente atualizado com sucesso!");
            }
        
    }
    
    /**
     * Consulta um cliente pelo ID.
     *
     * @param idCliente O ID do cliente a ser consultado.
     * @return O objeto Cliente correspondente ao ID informado.
     */
     public static Cliente consClienteId(Integer idCliente) {

        Cliente c = new Cliente();

        try (Connection con = Conexao.getConexao(); 
                
            PreparedStatement stm = con.prepareStatement("SELECT id_cliente, nome FROM Cliente WHERE id_cliente = ?")) {
            stm.setInt(1, idCliente);
            

            // Executar a consulta SQL e obter os resultados
            ResultSet rs = stm.executeQuery();

            if (rs.next()) { // Verificar se há resultados
                // Preencher o modelo com os dados da consulta
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNome(rs.getString("nome"));
            }

            // Fechar os objetos ResultSet, PreparedStatement e Connection
            rs.close();
            stm.close();
            con.close();

        } catch (SQLException ex) {
            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
        }

        return c;
    }
    
    /**
     * Consulta clientes pelo nome.
     *
     * @param nomeCliente O nome do cliente a ser consultado.
     * @return Uma lista de objetos Cliente correspondentes ao nome informado.
     */
    public static List<Cliente> consClienteNome(String nomeCliente) {
        
        List<Cliente> clienteList = new ArrayList<>();

        try (Connection con = Conexao.getConexao(); PreparedStatement stm = con.prepareStatement("SELECT id_cliente, nome, cpf, endereco, telefone FROM Cliente WHERE nome LIKE ?")) {
            stm.setString(1, nomeCliente + "%");

            // Executar a consulta SQL e obter os resultados
            ResultSet rs = stm.executeQuery();

            // Criar um objeto DefaultTableModel para armazenar os dados do JTable
            DefaultTableModel model = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Endereço"}, 0);

            if (rs != null) {
                // Preencher o modelo com os dados da consulta
                while (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("id_cliente"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getLong("cpf"));
                    cliente.setEndereco(rs.getString("endereco"));
                    cliente.setTelefone(Long.parseLong(rs.getString("telefone")));

                    clienteList.add(cliente);

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

        return clienteList;
        
    }

}
