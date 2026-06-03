package DAO;

import DTO.ItemPedidoDTO;
import DTO.PedidoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PedidoDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<PedidoDTO> lista = new ArrayList<>();

    public void cadastrarPedido(PedidoDTO objPedidoDTO, ArrayList<ItemPedidoDTO> itens) {
        String sqlPedido = "INSERT INTO Pedido (data_hora, status, total, codigo_cliente, codigo_funcionario) VALUES (?, ?, ?, ?, ?)";
        String sqlItem = "INSERT INTO Item_Pedido (nr_pedido, codigo_produto, preco_unitario, qtd_vendida) VALUES (?, ?, ?, ?)";
        
        conn = new ConexaoDAO().conectaBD();

        try {
            conn.setAutoCommit(false);
            
            pstm = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            pstm.setTimestamp(1, Timestamp.valueOf(objPedidoDTO.getDataHora()));
            pstm.setString(2, objPedidoDTO.getStatus());
            pstm.setBigDecimal(3, objPedidoDTO.getTotal());
            pstm.setInt(4, objPedidoDTO.getCodigoCliente());
            pstm.setInt(5, objPedidoDTO.getCodigoFuncionario());
            pstm.executeUpdate();
            
            rs = pstm.getGeneratedKeys();
            int nrPedido = 0;
            if (rs.next()) {
                nrPedido = rs.getInt(1);
            }
            
            if (itens != null && !itens.isEmpty()) {
                pstm = conn.prepareStatement(sqlItem);
                for (ItemPedidoDTO item : itens) {
                    pstm.setInt(1, nrPedido);
                    pstm.setInt(2, item.getCodigoProduto());
                    pstm.setBigDecimal(3, item.getPrecoUnitario());
                    pstm.setInt(4, item.getQtdVendida());
                    pstm.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException erro) {
            try { conn.rollback(); } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "PedidoDAO cadastrarPedido" + erro);
        } finally {
            try { if (pstm != null) pstm.close(); } catch (SQLException ex) {}
        }
    }

    public void alterarPedido(PedidoDTO objPedidoDTO) {
        String sql = "UPDATE Pedido SET data_hora=?, status=?, total=?, codigo_cliente=?, codigo_funcionario=? WHERE nr=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setTimestamp(1, Timestamp.valueOf(objPedidoDTO.getDataHora()));
            pstm.setString(2, objPedidoDTO.getStatus());
            pstm.setBigDecimal(3, objPedidoDTO.getTotal());
            pstm.setInt(4, objPedidoDTO.getCodigoCliente());
            pstm.setInt(5, objPedidoDTO.getCodigoFuncionario());
            pstm.setInt(6, objPedidoDTO.getNr());

            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "PedidoDAO alterarPedido" + erro);
        }
    }

    public void excluirPedido(PedidoDTO objPedidoDTO) {
        String sql = "DELETE FROM Pedido WHERE nr=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objPedidoDTO.getNr());

            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "PedidoDAO excluirPedido" + erro);
        }
    }

    public ArrayList<PedidoDTO> pesquisarPedido() {
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "SELECT * FROM Pedido";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                PedidoDTO objPedidoDTO = new PedidoDTO();
                objPedidoDTO.setNr(rs.getInt("nr"));
                objPedidoDTO.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                objPedidoDTO.setStatus(rs.getString("status"));
                objPedidoDTO.setTotal(rs.getBigDecimal("total"));
                objPedidoDTO.setCodigoCliente(rs.getInt("codigo_cliente"));
                objPedidoDTO.setCodigoFuncionario(rs.getInt("codigo_funcionario"));

                lista.add(objPedidoDTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "PedidoDAO pesquisarPedido" + e);
        }
        return lista;
    }
}
