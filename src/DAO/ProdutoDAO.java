package DAO;

import DTO.ProdutoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ProdutoDAO {

    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<ProdutoDTO> lista = new ArrayList<>();

    public void cadastrarProduto(ProdutoDTO objProdutoDTO) {
        String sql = "INSERT INTO Produto (descricao, estoque, status, preco, codigo_categoria) VALUES (?, ?, ?, ?, ?)";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objProdutoDTO.getDescricao());
            pstm.setInt(2, objProdutoDTO.getEstoque());
            pstm.setString(3, objProdutoDTO.getStatus());
            pstm.setBigDecimal(4, objProdutoDTO.getPreco());
            pstm.setInt(5, objProdutoDTO.getCodigoCategoria());

            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ProdutoDAO cadastrarProduto" + erro);
        }
    }

    public void alterarProduto(ProdutoDTO objProdutoDTO) {
        String sql = "UPDATE Produto SET descricao=?, estoque=?, status=?, preco=?, codigo_categoria=? WHERE codigo=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, objProdutoDTO.getDescricao());
            pstm.setInt(2, objProdutoDTO.getEstoque());
            pstm.setString(3, objProdutoDTO.getStatus());
            pstm.setBigDecimal(4, objProdutoDTO.getPreco());
            pstm.setInt(5, objProdutoDTO.getCodigoCategoria());
            pstm.setInt(6, objProdutoDTO.getCodigo());

            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ProdutoDAO alterarProduto" + erro);
        }
    }

    public void excluirProduto(ProdutoDTO objProdutoDTO) {
        String sql = "DELETE FROM Produto WHERE codigo=?";
        conn = new ConexaoDAO().conectaBD();

        try {
            pstm = conn.prepareStatement(sql);
            pstm.setInt(1, objProdutoDTO.getCodigo());

            pstm.execute();
            pstm.close();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ProdutoDAO excluirProduto" + erro);
        }
    }

    public ArrayList<ProdutoDTO> pesquisarProduto() {
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "SELECT * FROM Produto";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ProdutoDTO objProdutoDTO = new ProdutoDTO();
                objProdutoDTO.setCodigo(rs.getInt("codigo"));
                objProdutoDTO.setDescricao(rs.getString("descricao"));
                objProdutoDTO.setEstoque(rs.getInt("estoque"));
                objProdutoDTO.setStatus(rs.getString("status"));
                objProdutoDTO.setPreco(rs.getBigDecimal("preco"));
                objProdutoDTO.setCodigoCategoria(rs.getInt("codigo_categoria"));

                lista.add(objProdutoDTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ProdutoDAO pesquisarProduto" + e);
        }
        return lista;
    }
}
