/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.senac.drogarara.DAO;

/**
 *
 * @author david
 */
import com.senac.drogarara.conexao.Conexao;
import com.senac.drogarara.model.Venda_Produto;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Esta classe é responsável por fornecer métodos de acesso aos dados relacionados aos produtos de uma venda.
 * @author david
 */
public class VendaProdutosDAO {
    
    
     /**
     * Busca os produtos de uma venda específica.
     * @param id_venda O ID da venda.
     * @return Uma lista de objetos Venda_Produto correspondentes à venda.
     */
    public static List<Venda_Produto> buscarProdutosDaVenda(int id_venda) {
        List<Venda_Produto> vendaProdutos = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = Conexao.getConexao();
            String query = "SELECT * FROM Venda_Produto WHERE id_venda = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id_venda);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int id_produto = rs.getInt("id_produto");
                int quantidade = rs.getInt("quantidade");
                BigDecimal valor_unitario = rs.getBigDecimal("valor_unitario");

                Venda_Produto vendaProduto = new Venda_Produto(id_venda, id_produto, quantidade, valor_unitario);
                vendaProdutos.add(vendaProduto);
               
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            
        }

        return vendaProdutos;
    }
}
