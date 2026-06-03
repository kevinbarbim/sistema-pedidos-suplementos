
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexaoDAO {

    private static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String JDBC_URL =
            "jdbc:sqlserver://localhost:1433;"
            + "databaseName=sistema_pedidos;"
            + "encrypt=true;trustServerCertificate=true;"
            + "user=sa;password=SuaSenhaAqui";

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Driver JDBC do SQL Server não encontrado. "
                    + "Baixe mssql-jdbc-12.6.1.jre11.jar e adicione ao classpath do projeto.\n"
                    + e.getMessage());
        }
    }

    public Connection conectaBD() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(JDBC_URL);
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "ConexaoDAO: " + erro.getMessage());
        }
        return conn;
    }
}
