package DAO;

import DTO.ItemPedidoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ItemPedidoDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<ItemPedidoDTO> lista = new ArrayList<>();

    public void cadastrarItemPedido(ItemPedidoDTO objItemPedidoDTO) {
        String sql = "INSERT INTO Item_Pedido (nr_pedido, codigo_produto, preco_unitario, qtd_vendida) VALUES (?, ?, ?, ?)";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objItemPedidoDTO.getNrPedido());
            pstm.setInt(2, objItemPedidoDTO.getCodigoProduto());
            pstm.setBigDecimal(3, objItemPedidoDTO.getPrecoUnitario());
            pstm.setInt(4, objItemPedidoDTO.getQtdVendida());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ItemPedidoDAO cadastrarItemPedido" + erro);
        }
    }

    public void alterarItemPedido(ItemPedidoDTO objItemPedidoDTO) {
        String sql = "UPDATE Item_Pedido SET preco_unitario=?, qtd_vendida=? WHERE nr_pedido=? AND codigo_produto=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setBigDecimal(1, objItemPedidoDTO.getPrecoUnitario());
            pstm.setInt(2, objItemPedidoDTO.getQtdVendida());
            pstm.setInt(3, objItemPedidoDTO.getNrPedido());
            pstm.setInt(4, objItemPedidoDTO.getCodigoProduto());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ItemPedidoDAO alterarItemPedido" + erro);
        }
    }

    public void excluirItemPedido(ItemPedidoDTO objItemPedidoDTO) {
        String sql = "DELETE FROM Item_Pedido WHERE nr_pedido=? AND codigo_produto=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objItemPedidoDTO.getNrPedido());
            pstm.setInt(2, objItemPedidoDTO.getCodigoProduto());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ItemPedidoDAO excluirItemPedido" + erro);
        }
    }

    public ArrayList<ItemPedidoDTO> pesquisarItemPedido() {
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "SELECT * FROM Item_Pedido";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ItemPedidoDTO objItemPedidoDTO = new ItemPedidoDTO();
                objItemPedidoDTO.setNrPedido(rs.getInt("nr_pedido"));
                objItemPedidoDTO.setCodigoProduto(rs.getInt("codigo_produto"));
                objItemPedidoDTO.setPrecoUnitario(rs.getBigDecimal("preco_unitario"));
                objItemPedidoDTO.setQtdVendida(rs.getInt("qtd_vendida"));
                lista.add(objItemPedidoDTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ItemPedidoDAO pesquisarItemPedido" + e);
        }
        return lista;
    }
}
