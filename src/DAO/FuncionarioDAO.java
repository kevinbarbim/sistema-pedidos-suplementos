package DAO;

import DTO.FuncionarioDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FuncionarioDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<FuncionarioDTO> lista = new ArrayList<>();

    public void cadastrarFuncionario(FuncionarioDTO objFuncionarioDTO) {
        String sqlPessoa = "INSERT INTO Pessoa (cpf, nome, fone, email) VALUES (?, ?, ?, ?)";
        String sqlFuncionario = "INSERT INTO Funcionario (codigo_pessoa, salario) VALUES (?, ?)";

        conn = new ConexaoDAO().conectaBD();

        try {
            conn.setAutoCommit(false);
            
            pstm = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, objFuncionarioDTO.getCpf());
            pstm.setString(2, objFuncionarioDTO.getNome());
            pstm.setString(3, objFuncionarioDTO.getFone());
            pstm.setString(4, objFuncionarioDTO.getEmail());
            pstm.executeUpdate();
            
            rs = pstm.getGeneratedKeys();
            int idGerado = 0;
            if (rs.next()) {
                idGerado = rs.getInt(1);
            }
            
            pstm = conn.prepareStatement(sqlFuncionario);
            pstm.setInt(1, idGerado);
            pstm.setBigDecimal(2, objFuncionarioDTO.getSalario());
            pstm.executeUpdate();

            conn.commit();
        } catch (SQLException erro) {
            try { conn.rollback(); } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "FuncionarioDAO cadastrarFuncionario" + erro);
        } finally {
            try { if (pstm != null) pstm.close(); } catch (SQLException ex) {}
        }
    }

    public void alterarFuncionario(FuncionarioDTO objFuncionarioDTO) {
        String sqlPessoa = "UPDATE Pessoa SET cpf=?, nome=?, fone=?, email=? WHERE codigo=?";
        String sqlFuncionario = "UPDATE Funcionario SET salario=? WHERE codigo_pessoa=?";

        conn = new ConexaoDAO().conectaBD();

        try {
            conn.setAutoCommit(false);
            
            pstm = conn.prepareStatement(sqlPessoa);
            pstm.setString(1, objFuncionarioDTO.getCpf());
            pstm.setString(2, objFuncionarioDTO.getNome());
            pstm.setString(3, objFuncionarioDTO.getFone());
            pstm.setString(4, objFuncionarioDTO.getEmail());
            pstm.setInt(5, objFuncionarioDTO.getCodigo());
            pstm.executeUpdate();
            
            pstm = conn.prepareStatement(sqlFuncionario);
            pstm.setBigDecimal(1, objFuncionarioDTO.getSalario());
            pstm.setInt(2, objFuncionarioDTO.getCodigo());
            pstm.executeUpdate();
            
            conn.commit();
        } catch (SQLException erro) {
            try { conn.rollback(); } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "FuncionarioDAO alterarFuncionario" + erro);
        }
    }

    public void excluirFuncionario(FuncionarioDTO objFuncionarioDTO) {
        String sql = "DELETE FROM Pessoa WHERE codigo=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objFuncionarioDTO.getCodigo());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "FuncionarioDAO excluirFuncionario" + erro);
        }
    }

    public ArrayList<FuncionarioDTO> pesquisarFuncionario() {
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "SELECT p.codigo, p.cpf, p.nome, p.fone, p.email, f.salario " +
                         "FROM Funcionario f " +
                         "INNER JOIN Pessoa p ON f.codigo_pessoa = p.codigo";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                FuncionarioDTO objFuncionarioDTO = new FuncionarioDTO();
                objFuncionarioDTO.setCodigo(rs.getInt("codigo"));
                objFuncionarioDTO.setCpf(rs.getString("cpf"));
                objFuncionarioDTO.setNome(rs.getString("nome"));
                objFuncionarioDTO.setFone(rs.getString("fone"));
                objFuncionarioDTO.setEmail(rs.getString("email"));
                objFuncionarioDTO.setSalario(rs.getBigDecimal("salario"));
                lista.add(objFuncionarioDTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "FuncionarioDAO pesquisarFuncionario" + e);
        }
        return lista;
    }
}
