package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Endereco;

public class DaoEndereco extends DAO {

    public int inserirEnderecoBanco(Endereco e) {
        Connection con = getConexao();

        Integer idEndereco = 0;
        try {
            String sql = """
                         INSERT into endereco(cidade, rua, numero) VALUES(?, ?, ?);
                         """;

            PreparedStatement stmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setString(1, e.getCidade());
            stmt.setString(2, e.getRua());
            stmt.setString(3, e.getNumero());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {

                System.out.println("Endereco inserido com sucesso!! ");

                ResultSet generateKeys = stmt.getGeneratedKeys();

                while (generateKeys.next()) {
                    idEndereco = generateKeys.getInt(1);
                }
            }

            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro no inserirEnderecoBanco():" + ex.getMessage());
        }

        return idEndereco;
    }

    public void removerEnderecoBanco(Endereco e) {
        try {
            String sql = """
                         DELETE FROM endereco
                         WHERE id = ?;
                         """;

            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, e.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Endereco removido com sucesso!! ");
            }

            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro no inserirEnderecoBanco():" + ex.getMessage());
        }
    }

    public void atualizarEnderecoBanco(Endereco e) {
        try {
            String sql = """
                         UPDATE endereco
                         SET cidade = ?, rua = ?, numero = ?
                         WHERE id = ?;
                         """;

            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, e.getCidade());
            stmt.setString(2, e.getRua());
            stmt.setString(3, e.getNumero());
            stmt.setInt(4, e.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Endereco atualizado com sucesso!! ");
            }

            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro no inserirEnderecoBanco():" + ex.getMessage());
        }
    }
}
