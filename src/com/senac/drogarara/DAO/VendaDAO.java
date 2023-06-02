/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.drogarara.DAO;

import com.senac.drogarara.conexao.Conexao;
import com.senac.drogarara.gui.LoginFrame;
import com.senac.drogarara.model.Venda;
import com.senac.drogarara.model.VendaProduto;
import java.math.BigDecimal;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 * dados relacionados a vendas. Ela permite gerar vendas, buscar vendas por
 * período e inserir itens de venda.
 * @author david Esta classe é responsável por fornecer métodos de acesso aos
 */
public class VendaDAO {

    /**
     * Gera uma venda, inserindo os dados da venda e dos itens de venda no banco
     * de dados.
     *
     * @param v O objeto Venda contendo as informações da venda.
     * @throws Exception Se ocorrer um erro durante a geração da venda.
     */
    public static void gerarVenda(Venda v) throws Exception {
        try (Connection con = Conexao.getConexao()) {
            String queryVenda = "INSERT INTO Venda (id_cliente, valor_total, data_venda) VALUES (?, ?, ?)";
            String queryVendaProduto = "INSERT INTO Venda_Produto (id_venda, id_produto, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";
            String queryUpdateProduto = "UPDATE Produto SET quantidade_estoque = quantidade_estoque - ? WHERE id_produto = ?";

            // Inserir dados da venda na tabela Venda
            PreparedStatement statementVenda = con.prepareStatement(queryVenda, Statement.RETURN_GENERATED_KEYS);
            statementVenda.setInt(1, v.getId_cliente());
            statementVenda.setBigDecimal(2, v.getValor_total());
            statementVenda.setDate(3, new java.sql.Date(v.getData_venda().getTime()));
            int affectedRows = statementVenda.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Falha ao inserir venda, nenhuma linha afetada.");
            }

            ResultSet generatedKeys = statementVenda.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idVenda = generatedKeys.getInt(1);
                v.setId_venda(idVenda);

                // Inserir os itens de venda na tabela Venda_Produto
                List<VendaProduto> itens = v.getItens();
                PreparedStatement statementVendaProduto = con.prepareStatement(queryVendaProduto);

                for (VendaProduto item : itens) {
                    boolean prodDisponivel = ProdutoDAO.consultaDisponibilidadeProd(item.getId_produto(), item.getQuantidade());

                    if (prodDisponivel) {
                        statementVendaProduto.setInt(1, idVenda);
                        statementVendaProduto.setInt(2, item.getId_produto());
                        statementVendaProduto.setInt(3, item.getQuantidade());
                        statementVendaProduto.setBigDecimal(4, item.getValor_unitario());
                        statementVendaProduto.executeUpdate();

                        // Decrementar a quantidade de produto na tabela Produto
                        PreparedStatement statementUpdateProduto = con.prepareStatement(queryUpdateProduto);
                        statementUpdateProduto.setInt(1, item.getQuantidade());
                        statementUpdateProduto.setInt(2, item.getId_produto());
                        statementUpdateProduto.executeUpdate();
                    } else {
                        JOptionPane.showMessageDialog(null, "Não há quantidade em estoque suficiente");
                        throw new Exception("Não há produtos disponíveis");
                    }
                }

                JOptionPane.showMessageDialog(null, "Venda cadastrada");
            } else {
                throw new SQLException("Falha ao inserir venda, nenhum ID gerado.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco de dados!");
        }
    }

    /**
     * Insere os itens de venda no banco de dados.
     *
     * @param idVenda O ID da venda.
     * @param itens A lista de objetos VendaProduto contendo os itens de venda.
     */
    private static void inserirItensVenda(int idVenda, List<VendaProduto> itens) {
        try (Connection con = Conexao.getConexao()) {
            String query = "INSERT INTO VendaProduto (id_venda, id_produto, valor_unitario, quantidade) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(query);

            for (VendaProduto item : itens) {
                statement.setInt(1, idVenda);
                statement.setInt(2, item.getId_produto());
                statement.setBigDecimal(3, item.getValor_unitario());
                statement.setInt(4, item.getQuantidade());
                statement.addBatch();
            }

            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratar a exceção adequadamente de acordo com a sua lógica de negócio
        }
    }

    /**
     * Busca as vendas realizadas em um período específico.
     *
     * @param dataInicio A data de início do período.
     * @param dataFim A data de fim do período.
     * @return Uma lista de objetos Venda correspondentes ao período informado.
     */
    public static List<Venda> buscarVendasPorPeriodo(Date dataInicio, Date dataFim) {
        List<Venda> vendas = new ArrayList<>();

        try (Connection con = Conexao.getConexao()) {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM Venda WHERE data_venda BETWEEN ? AND ?");

            // Define os parâmetros da consulta
            statement.setDate(1, new java.sql.Date(dataInicio.getTime()));
            statement.setDate(2, new java.sql.Date(dataFim.getTime()));

            ResultSet resultSet = statement.executeQuery();

            // Percorre os resultados da consulta e cria objetos Venda
            while (resultSet.next()) {
                int idVenda = resultSet.getInt("id_venda");
                int idCliente = resultSet.getInt("id_cliente");
                double valorTotal = resultSet.getDouble("valor_total");
                Date dataVenda = resultSet.getDate("data_venda");

                Venda venda = new Venda(idVenda, idCliente, BigDecimal.valueOf(valorTotal), dataVenda);
                vendas.add(venda);
            }

            resultSet.close();
            statement.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vendas;
    }

}
