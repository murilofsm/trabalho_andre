package persistencia;

import modelo.Curso;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Docente;
import modelo.Endereco;

public class DaoCurso<T> extends DAO {

    public void inserirCursoBanco(Curso cur) {
        try {
            String sql = """
                         
                         INSERT INTO curso (nome, cargahoraria, qtdsemestres)
                         VALUES (?, ?, ?);
                         
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, cur.getNome());
            stmt.setInt(2, cur.getCargaHoraria());
            stmt.setInt(3, cur.getQtdSemestres());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Curso criado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no inserirCursoBanco()\n" + ex.getMessage());
        }
    }

    public void editarCursoBanco(Curso cur) {
        try {
            String sql = """
                            UPDATE curso c
                            SET c.nome = ?, c.cargahoraria = ?, c.qtdsemestres = ?, c.idcoordenador = ?
                            WHERE id = ?;
                         
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, cur.getNome());
            stmt.setInt(2, cur.getCargaHoraria());
            stmt.setInt(3, cur.getQtdSemestres());
            stmt.setInt(4, cur.getCoordenador().getId());
            stmt.setInt(5, cur.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Curso editado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no inserirFuncionarioBanco()\n" + ex.getMessage());
        }
    }

    public boolean removerCursoBanco(int idCurso) {
        try {
            String sql = """
                        DELETE curso
                        FROM curso
                        WHERE id = ?;
                         
                        """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, idCurso);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Curso removido com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro no inserirFuncionarioBanco()\n" + ex.getMessage());
            return false;
        }
    }

    public Curso localizarCursoUnicoBanco(int idCurso) {
        Curso cur = new Curso();
        try {
            String sql = """
                         
                         SELECT c.id, c.nome, c.cargahoraria, c.qtdsemestres, c.idcoordenador,
                         d.nome, d.cpf, d.email, d.genero, d.datanascimento, d.ctps, d.formacao, d.salario, d.idendereco,
                         e.cidade, e.rua, e.numero 
                         FROM curso c
                         INNER JOIN docente d on c.idcoordenador = d.id
                         INNER JOIN endereco e on d.idendereco = e.id
                         WHERE c.id = ?;
                         
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);
            stmt.setInt(1, idCurso);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cur.setId(rs.getInt("c.id"));
                cur.setNome(rs.getString("c.nome"));
                cur.setCargaHoraria(rs.getInt("c.cargahoraria"));
                cur.setQtdSemestres(rs.getInt("c.qtdsemestres"));
                cur.setCoordenador(
                        new Docente(
                                rs.getInt("c.idcoordenador"),
                                rs.getString("d.nome"),
                                rs.getString("d.cpf"),
                                rs.getString("d.email"),
                                rs.getString("d.genero"),
                                rs.getDate("d.datanascimento").toLocalDate(),
                                rs.getString("d.ctps"),
                                rs.getString("d.formacao"),
                                rs.getDouble("d.salario"),
                                new Endereco(
                                        rs.getInt("d.idendereco"),
                                        rs.getString("e.cidade"),
                                        rs.getString("e.rua"),
                                        rs.getString("e.numero")
                                )
                        )
                );

            }
            stmt.close();
            rs.close();

        } catch (SQLException ex) {
            System.out.println("Erro no localizarCursoUnicoBanco()\n" + ex.getMessage());
        }
        
        return cur;

    }

    public List<Curso> localizarTodosCursosBanco() {
        List<Curso> result = new ArrayList<>();
        try {
            String sql = """
                         
                         SELECT c.id, c.nome, c.cargahoraria, c.qtdsemestres, c.idcoordenador,
                         d.nome, d.cpf, d.email, d.genero, d.datanascimento, d.ctps, d.formacao, d.salario, d.idendereco,
                         e.cidade, e.rua, e.numero 
                         FROM curso c
                         INNER JOIN docente d on c.idcoordenador = d.id
                         INNER JOIN endereco e on d.idendereco = e.id;
                         
                         """;

            ResultSet rs = consultaSQL(sql);

            while (rs.next()) {

                Curso curso = new Curso(
                        rs.getInt("c.id"),
                        rs.getString("c.nome"),
                        rs.getInt("c.cargahoraria"),
                        rs.getInt("c.qtdsemestres"),
                        new Docente(
                                rs.getInt("c.idcoordenador"),
                                rs.getString("d.nome"),
                                rs.getString("d.cpf"),
                                rs.getString("d.email"),
                                rs.getString("d.genero"),
                                rs.getDate("d.datanascimento").toLocalDate(),
                                rs.getString("d.ctps"),
                                rs.getString("d.formacao"),
                                rs.getDouble("d.salario"),
                                new Endereco(
                                        rs.getInt("d.idendereco"),
                                        rs.getString("e.cidade"),
                                        rs.getString("e.rua"),
                                        rs.getString("e.numero")
                                )
                        ),
                        0
                );
                result.add(curso);
            }
            rs.close();

        } catch (SQLException ex) {
            System.out.println("Erro no localizarCursoUnicoBanco()\n" + ex.getMessage());
        }
        return result;
    }
}
