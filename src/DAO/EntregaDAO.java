package DAO;

import DTO.EntregaDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EntregaDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<EntregaDTO> lista = new ArrayList<>();

    public void cadastrarEntrega(EntregaDTO objEntregaDTO) {
        String sql = "INSERT INTO Entrega (valor_entrega, data_saida, status, nr_pedido, codigo_entregador) VALUES (?, ?, ?, ?, ?)";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setBigDecimal(1, objEntregaDTO.getValorEntrega());
            if (objEntregaDTO.getDataSaida() != null) {
                pstm.setTimestamp(2, Timestamp.valueOf(objEntregaDTO.getDataSaida()));
            } else {
                pstm.setNull(2, Types.TIMESTAMP);
            }
            pstm.setString(3, objEntregaDTO.getStatus());
            pstm.setInt(4, objEntregaDTO.getNrPedido());
            pstm.setInt(5, objEntregaDTO.getCodigoEntregador());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "EntregaDAO cadastrarEntrega" + erro);
        }
    }

    public void alterarEntrega(EntregaDTO objEntregaDTO) {
        String sql = "UPDATE Entrega SET valor_entrega=?, data_saida=?, status=?, nr_pedido=?, codigo_entregador=? WHERE nr=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setBigDecimal(1, objEntregaDTO.getValorEntrega());
            if (objEntregaDTO.getDataSaida() != null) {
                pstm.setTimestamp(2, Timestamp.valueOf(objEntregaDTO.getDataSaida()));
            } else {
                pstm.setNull(2, Types.TIMESTAMP);
            }
            pstm.setString(3, objEntregaDTO.getStatus());
            pstm.setInt(4, objEntregaDTO.getNrPedido());
            pstm.setInt(5, objEntregaDTO.getCodigoEntregador());
            pstm.setInt(6, objEntregaDTO.getNr());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "EntregaDAO alterarEntrega" + erro);
        }
    }

    public void excluirEntrega(EntregaDTO objEntregaDTO) {
        String sql = "DELETE FROM Entrega WHERE nr=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objEntregaDTO.getNr());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "EntregaDAO excluirEntrega" + erro);
        }
    }

    public ArrayList<EntregaDTO> pesquisarEntrega() {
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "SELECT * FROM Entrega";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                EntregaDTO objEntregaDTO = new EntregaDTO();
                objEntregaDTO.setNr(rs.getInt("nr"));
                objEntregaDTO.setValorEntrega(rs.getBigDecimal("valor_entrega"));
                Timestamp ts = rs.getTimestamp("data_saida");
                if (ts != null) {
                    objEntregaDTO.setDataSaida(ts.toLocalDateTime());
                }
                objEntregaDTO.setStatus(rs.getString("status"));
                objEntregaDTO.setNrPedido(rs.getInt("nr_pedido"));
                objEntregaDTO.setCodigoEntregador(rs.getInt("codigo_entregador"));
                lista.add(objEntregaDTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "EntregaDAO pesquisarEntrega" + e);
        }
        return lista;
    }
}
