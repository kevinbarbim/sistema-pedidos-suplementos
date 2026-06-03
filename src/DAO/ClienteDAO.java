package DAO;
import DTO.ClienteDTO;
import DTO.EnderecoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ClienteDAO {
    Connection conn;
    PreparedStatement pstm;
    ResultSet rs;
    ArrayList<ClienteDTO> lista = new ArrayList<>();

    public void cadastrarCliente(ClienteDTO objClienteDTO) {
        String sqlPessoa = "INSERT INTO Pessoa (cpf, nome, fone, email) VALUES (?, ?, ?, ?)";
        String sqlCliente = "INSERT INTO Cliente (codigo_pessoa, status) VALUES (?, ?)";
        String sqlEndereco = "INSERT INTO Endereco (cep, cidade, estado, status, codigo_cliente) VALUES (?, ?, ?, ?, ?)";

        conn = new ConexaoDAO().conectaBD();
        try {
            conn.setAutoCommit(false);
            
            pstm = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, objClienteDTO.getCpf());
            pstm.setString(2, objClienteDTO.getNome());
            pstm.setString(3, objClienteDTO.getFone());
            pstm.setString(4, objClienteDTO.getEmail());
            pstm.executeUpdate();
            
            rs = pstm.getGeneratedKeys();
            int idGerado = 0;
            if (rs.next()) {
                idGerado = rs.getInt(1);
            }
            
            pstm = conn.prepareStatement(sqlCliente);
            pstm.setInt(1, idGerado);
            pstm.setString(2, objClienteDTO.getStatus());
            pstm.executeUpdate();
            
            if (objClienteDTO.getEndereco() != null) {
                pstm = conn.prepareStatement(sqlEndereco);
                pstm.setString(1, objClienteDTO.getEndereco().getCep());
                pstm.setString(2, objClienteDTO.getEndereco().getCidade());
                pstm.setString(3, objClienteDTO.getEndereco().getEstado());
                pstm.setString(4, objClienteDTO.getEndereco().getStatus());
                pstm.setInt(5, idGerado);
                pstm.executeUpdate();
            }

            conn.commit();
        } catch (SQLException erro) {
            try {
                conn.rollback();
            } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "ClienteDAO cadastrarCliente" + erro);
        } finally {
            try {
                if (pstm != null) pstm.close();
            } catch (SQLException ex) {}
        }
    }

    public void alterarCliente(ClienteDTO objClienteDTO) {
        String sqlPessoa = "UPDATE Pessoa SET cpf=?, nome=?, fone=?, email=? WHERE codigo=?";
        String sqlCliente = "UPDATE Cliente SET status=? WHERE codigo_pessoa=?";
        String sqlEndereco = "UPDATE Endereco SET cep=?, cidade=?, estado=?, status=? WHERE codigo_cliente=?";
        
        conn = new ConexaoDAO().conectaBD();
        try {
            conn.setAutoCommit(false);
            
            pstm = conn.prepareStatement(sqlPessoa);
            pstm.setString(1, objClienteDTO.getCpf());
            pstm.setString(2, objClienteDTO.getNome());
            pstm.setString(3, objClienteDTO.getFone());
            pstm.setString(4, objClienteDTO.getEmail());
            pstm.setInt(5, objClienteDTO.getCodigo());
            pstm.executeUpdate();
            
            pstm = conn.prepareStatement(sqlCliente);
            pstm.setString(1, objClienteDTO.getStatus());
            pstm.setInt(2, objClienteDTO.getCodigo());
            pstm.executeUpdate();
            
            if (objClienteDTO.getEndereco() != null) {
                pstm = conn.prepareStatement(sqlEndereco);
                pstm.setString(1, objClienteDTO.getEndereco().getCep());
                pstm.setString(2, objClienteDTO.getEndereco().getCidade());
                pstm.setString(3, objClienteDTO.getEndereco().getEstado());
                pstm.setString(4, objClienteDTO.getEndereco().getStatus());
                pstm.setInt(5, objClienteDTO.getCodigo());
                pstm.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException erro) {
            try {
                conn.rollback();
            } catch (SQLException ex) {}
            JOptionPane.showMessageDialog(null, "ClienteDAO alterarCliente" + erro);
        }
    }

    public void excluirCliente(ClienteDTO objClienteDTO) {
        conn = new ConexaoDAO().conectaBD(); try { pstm = conn.prepareStatement("DELETE FROM Endereco WHERE codigo_cliente=?"); pstm.setInt(1, objClienteDTO.getCodigo()); pstm.execute(); pstm.close(); pstm = conn.prepareStatement("DELETE FROM Pessoa WHERE codigo=?"); pstm.setInt(1, objClienteDTO.getCodigo()); pstm.execute(); pstm.close(); } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ClienteDAO excluirCliente" + erro);
        }
    }

    public ArrayList<ClienteDTO> pesquisarCliente() {
        conn = new ConexaoDAO().conectaBD();
        try {
            String sql = "SELECT p.codigo, p.cpf, p.nome, p.fone, p.email, c.status, e.id as id_end, e.cep, e.cidade, e.estado, e.status as status_end " +
                         "FROM Cliente c " +
                         "INNER JOIN Pessoa p ON c.codigo_pessoa = p.codigo " +
                         "LEFT JOIN Endereco e ON c.codigo_pessoa = e.codigo_cliente";
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ClienteDTO objClienteDTO = new ClienteDTO();
                objClienteDTO.setCodigo(rs.getInt("codigo"));
                objClienteDTO.setCpf(rs.getString("cpf"));
                objClienteDTO.setNome(rs.getString("nome"));
                objClienteDTO.setFone(rs.getString("fone"));
                objClienteDTO.setEmail(rs.getString("email"));
                objClienteDTO.setStatus(rs.getString("status"));
                
                if (rs.getInt("id_end") > 0) {
                    EnderecoDTO end = new EnderecoDTO();
                    end.setId(rs.getInt("id_end"));
                    end.setCep(rs.getString("cep"));
                    end.setCidade(rs.getString("cidade"));
                    end.setEstado(rs.getString("estado"));
                    end.setStatus(rs.getString("status_end"));
                    end.setCodigoCliente(rs.getInt("codigo"));
                    objClienteDTO.setEndereco(end);
                }
                
                lista.add(objClienteDTO);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ClienteDAO pesquisarCliente" + e);
        }
        return lista;
    }
}

