package DAO;
import DTO.CategoriaDTO;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;

public class CategoriaDAO {
    Connection conn;
    PreparedStatement pstm;
    ArrayList<CategoriaDTO> lista = new ArrayList<>();
    ResultSet rs;

    public void cadastrarCategoria(CategoriaDTO objCategoriaDTO) {
        String sql = "INSERT INTO Categoria (nome) VALUES (?)";
        conn = new ConexaoDAO().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objCategoriaDTO.getNome());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CategoriaDAO cadastrarCategoria" + erro);
        }
    }

    public void alterarCategoria(CategoriaDTO objCategoriaDTO) {
        String sql = "UPDATE Categoria SET nome=? WHERE codigo=?";
        conn = new ConexaoDAO().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objCategoriaDTO.getNome());
            pstm.setInt(2, objCategoriaDTO.getCodigo());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CategoriaDAO alterarCategoria" + erro);
        }
    }

    public void excluirCategoria(CategoriaDTO objCategoriaDTO) {
        String sql = "DELETE FROM Categoria WHERE codigo=?";
        conn = new ConexaoDAO().conectaBD();
        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objCategoriaDTO.getCodigo());
            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "CategoriaDAO excluirCategoria" + erro);
        }
    }

    public ArrayList<CategoriaDTO> pesquisarCategoria() {
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "SELECT * FROM Categoria";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                CategoriaDTO objCategoriaDTO = new CategoriaDTO();
                objCategoriaDTO.setCodigo(rs.getInt("codigo"));
                objCategoriaDTO.setNome(rs.getString("nome"));
                lista.add(objCategoriaDTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CategoriaDAO pesquisarCategoria" + e);
        }
        return lista;
    }
}
