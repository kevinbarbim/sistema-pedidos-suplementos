package DAO;

import DTO.EntregadorDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EntregadorDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<EntregadorDTO> lista = new ArrayList<>();

    public void cadastrarEntregador(EntregadorDTO objEntregadorDTO) {
        String sqlPessoa = "INSERT INTO Pessoa (cpf, nome, fone, email) VALUES (?, ?, ?, ?)";
        String sqlEntregador = "INSERT INTO Entregador (codigo_pessoa, obs) VALUES (?, ?)";

        conn = new ConexaoDAO().conectaBD();

        try {
            conn.setAutoCommit(false);

            pstm = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, objEntregadorDTO.getCpf());
            pstm.setString(2, objEntregadorDTO.getNome());
            pstm.setString(3, objEntregadorDTO.getFone());
            pstm.setString(4, objEntregadorDTO.getEmail());
            pstm.executeUpdate();

            rs = pstm.getGeneratedKeys();
            int idGerado = 0;
            if (rs.next()) {
                idGerado = rs.getInt(1);
            }

            pstm = conn.prepareStatement(sqlEntregador);
            pstm.setInt(1, idGerado);
            pstm.setString(2, objEntregadorDTO.getObs());
            pstm.executeUpdate();

            conn.commit();
        } catch (SQLException erro) {
            try { conn.rollback(); } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "EntregadorDAO cadastrarEntregador" + erro);
        } finally {
            try { if (pstm != null) pstm.close(); } catch (SQLException ex) {}
        }
    }

    public void alterarEntregador(EntregadorDTO objEntregadorDTO) {
        String sqlPessoa = "UPDATE Pessoa SET cpf=?, nome=?, fone=?, email=? WHERE codigo=?";
        String sqlEntregador = "UPDATE Entregador SET obs=? WHERE codigo_pessoa=?";

        conn = new ConexaoDAO().conectaBD();

        try {
            conn.setAutoCommit(false);

            pstm = conn.prepareStatement(sqlPessoa);
            pstm.setString(1, objEntregadorDTO.getCpf());
            pstm.setString(2, objEntregadorDTO.getNome());
            pstm.setString(3, objEntregadorDTO.getFone());
            pstm.setString(4, objEntregadorDTO.getEmail());
            pstm.setInt(5, objEntregadorDTO.getCodigo());
            pstm.executeUpdate();

            pstm = conn.prepareStatement(sqlEntregador);
            pstm.setString(1, objEntregadorDTO.getObs());
            pstm.setInt(2, objEntregadorDTO.getCodigo());
            pstm.executeUpdate();

            conn.commit();
        } catch (SQLException erro) {
            try { conn.rollback(); } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "EntregadorDAO alterarEntregador" + erro);
        }
    }

    public void excluirEntregador(EntregadorDTO objEntregadorDTO) {
        String sql = "DELETE FROM Pessoa WHERE codigo=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objEntregadorDTO.getCodigo());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "EntregadorDAO excluirEntregador" + erro);
        }
    }

    public ArrayList<EntregadorDTO> pesquisarEntregador() {
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "SELECT p.codigo, p.cpf, p.nome, p.fone, p.email, e.obs " +
                         "FROM Entregador e " +
                         "INNER JOIN Pessoa p ON e.codigo_pessoa = p.codigo";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                EntregadorDTO objEntregadorDTO = new EntregadorDTO();
                objEntregadorDTO.setCodigo(rs.getInt("codigo"));
                objEntregadorDTO.setCpf(rs.getString("cpf"));
                objEntregadorDTO.setNome(rs.getString("nome"));
                objEntregadorDTO.setFone(rs.getString("fone"));
                objEntregadorDTO.setEmail(rs.getString("email"));
                objEntregadorDTO.setObs(rs.getString("obs"));
                lista.add(objEntregadorDTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "EntregadorDAO pesquisarEntregador" + e);
        }
        return lista;
    }
}
