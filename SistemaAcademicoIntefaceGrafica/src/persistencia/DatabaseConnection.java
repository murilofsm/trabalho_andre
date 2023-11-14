package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection con = null;

    private static void criarConexao() {
        String nomeBanco = "db_faculdadefinal";
        String url = "jdbc:mysql://localhost:3306/" + nomeBanco;
        String user = "root";
        String pass = "2344";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Falha ao criar conexao com o banco de dados\n" + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            if (con == null || con.isClosed()) {
                criarConexao();
            }
            return con;
        } catch (SQLException ex) {
            System.out.println("Falha ao obter conexao\n" + ex.getMessage());
            return null;
        }
    }
}
