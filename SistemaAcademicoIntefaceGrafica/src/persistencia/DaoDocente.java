package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Docente;
import modelo.Endereco;
import modelo.Docente;
import util.ConverterUtils;

public class DaoDocente<T> extends DAO {

    private static DaoEndereco daoEndereco = new DaoEndereco();

    public void inserirDocenteBanco(Docente doc) {

        int idEndereco = daoEndereco.inserirEnderecoBanco(doc.getEndereco());

        try {
            String sql = """
                         
                         INSERT INTO docente(nome, cpf, email, genero, datanascimento, ctps, formacao, salario, idendereco)
                         VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);
                         
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, doc.getNome());
            stmt.setString(2, doc.getCpf());
            stmt.setString(3, doc.getEmail());
            stmt.setString(4, doc.getGenero());
            stmt.setDate(5, ConverterUtils.localDateParaDate(doc.getDataNascimento()));
            stmt.setString(6, doc.getCtps());
            stmt.setString(7, doc.getFormacao());
            stmt.setDouble(8, doc.getSalario());
            stmt.setInt(9, idEndereco);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Docente cadastrado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no inserirDocenteBanco()\n" + ex.getMessage());
        }
    }

    public void editarDocenteBanco(Docente doc) {
        try {
            String sql = """
                            UPDATE docente d
                            INNER JOIN endereco e on d.idendereco = e.id
                            SET d.nome = ?, d.cpf = ?, d.email = ?, d.genero = ?, d.datanascimento = ?, d.ctps = ?, d.salario = ?,
                            e.cidade = ?, e.rua = ?, e.numero = ?
                            WHERE d.id = ?;
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, doc.getNome());
            stmt.setString(2, doc.getCpf());
            stmt.setString(3, doc.getEmail());
            stmt.setString(4, doc.getGenero());
            stmt.setDate(5, ConverterUtils.localDateParaDate(doc.getDataNascimento()));
            stmt.setString(6, doc.getCtps());
            stmt.setDouble(7, doc.getSalario());
            stmt.setString(8, doc.getEndereco().getCidade());
            stmt.setString(9, doc.getEndereco().getRua());
            stmt.setString(10, doc.getEndereco().getNumero());
            stmt.setInt(11, doc.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Docente editado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no editarDocenteBanco()\n" + ex.getMessage());
        }
    }

    public boolean removerDocenteBanco(int idDocente) {
        try {
            String sql = """
                         DELETE d, e
                         FROM docente d
                         INNER JOIN endereco e on d.idendereco = e.id
                         WHERE d.id = 2 and d.idendereco = e.id;                    
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, idDocente);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Docente removido com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro no removerDocenteBanco()\n" + ex.getMessage());
            return false;
        }
    }

    public Docente localizarDocenteUnicoBanco(int idDocente) {

        Docente doc = new Docente();

        try {
            String sql = """
                         
                        SELECT d.id, d.nome, d.cpf, d.email, d.genero, d.datanascimento, d.ctps, d.formacao, d.salario, d.idendereco,
                        e.cidade, e.rua, e.numero
                        FROM docente d
                        INNER JOIN endereco e on d.idendereco = e.id
                        WHERE d.id = ?;
                         
                        """;

            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, idDocente);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                doc.setId(rs.getInt("d.id"));
                doc.setNome(rs.getString("d.nome"));
                doc.setCpf(rs.getString("d.cpf"));
                doc.setEmail(rs.getString("d.email"));
                doc.setGenero(rs.getString("d.genero"));
                doc.setDataNascimento(rs.getDate("d.datanascimento").toLocalDate());
                doc.setCtps(rs.getString("d.ctps"));
                doc.setFormacao(rs.getString("d.formacao"));
                doc.setSalario(rs.getDouble("d.salario"));
                doc.setEndereco(new Endereco(
                        rs.getInt("d.idendereco"),
                        rs.getString("e.cidade"),
                        rs.getString("e.rua"),
                        rs.getString("e.numero"))
                );
            }

            rs.close();

        } catch (SQLException ex) {
            System.out.println("Erro no localizarDocenteUnicoBanco()\n" + ex.getMessage());
        }
        return doc;
    }

    public List<Docente> localizarTodosDocentesBanco() {

        List<Docente> result = new ArrayList<>();

        try {
            String sql = """
                         
                        SELECT d.id, d.nome, d.cpf, d.email, d.genero, d.datanascimento, d.ctps, d.formacao, d.salario, d.idendereco,
                        e.cidade, e.rua, e.numero
                        FROM docente d
                        INNER JOIN endereco e on d.idendereco = e.id;
                         
                         """;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Docente doc = new Docente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("genero"),
                        rs.getDate("datanascimento").toLocalDate(),
                        rs.getString("ctps"),
                        rs.getString("formacao"),
                        rs.getDouble("salario"),
                        new Endereco(
                                rs.getInt("idendereco"),
                                rs.getString("cidade"),
                                rs.getString("rua"),
                                rs.getString("numero")
                        )
                );
                result.add(doc);
            }

            rs.close();

        } catch (SQLException ex) {
            System.out.println("Erro no localizarTodosDocentesBanco()\n" + ex.getMessage());
        }

        return result;
    }

}
