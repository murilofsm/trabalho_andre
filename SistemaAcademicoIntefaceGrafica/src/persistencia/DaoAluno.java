package persistencia;

import modelo.Aluno;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Curso;
import modelo.Docente;
import modelo.Endereco;
import util.ConverterUtils;

public class DaoAluno<T> extends DAO {

    private static DaoEndereco daoEndereco = new DaoEndereco();

    public void inserirAlunoBanco(Aluno al) {
        int idEndereco = daoEndereco.inserirEnderecoBanco(al.getEndereco());

        try {
            String sql = """
                         
                         INSERT INTO aluno (nome, cpf, email, genero, datanascimento, ra, datamatricula, situacao, idcurso, idendereco)
                         VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                         
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, al.getNome());
            stmt.setString(2, al.getCpf());
            stmt.setString(3, al.getEmail());
            stmt.setString(4, al.getGenero());
            stmt.setDate(5, ConverterUtils.localDateParaDate(al.getDataNascimento()));
            stmt.setString(6, al.getRa());
            stmt.setDate(7, ConverterUtils.localDateParaDate(al.getDataMatricula()));
            stmt.setString(8, al.getSituacao());
            stmt.setInt(9, al.getCurso().getId());
            stmt.setInt(10, idEndereco);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno cadastrado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no inserirAlunoBanco()\n" + ex.getMessage());
        }
    }

    public void editarAlunoBanco(Aluno al) {
        try {
            String sql = """
                            UPDATE aluno a
                            INNER JOIN endereco e on a.idendereco = e.id
                            SET a.nome = ?, a.cpf = ?, a.email = ?, a.genero = ?, a.datanascimento = ?, a.ra = ?, a.datamatricula = ?, a.situacao = ?, e.cidade = ?, e.rua = ?, e.numero = ?
                            WHERE a.id = ?;
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, al.getNome());
            stmt.setString(2, al.getCpf());
            stmt.setString(3, al.getEmail());
            stmt.setString(4, al.getGenero());
            stmt.setDate(5, ConverterUtils.localDateParaDate(al.getDataNascimento()));
            stmt.setString(6, al.getRa());
            stmt.setDate(7, ConverterUtils.localDateParaDate(al.getDataMatricula()));
            stmt.setString(8, al.getSituacao());
            stmt.setString(9, al.getEndereco().getRua());
            stmt.setString(10, al.getEndereco().getNumero());
            stmt.setInt(11, al.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno editado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no editarAlunoBanco()\n" + ex.getMessage());
        }
    }

    public boolean removerAlunoBanco(int idAluno) {
        try {
            String sql = """
                         DELETE a, e
                         FROM aluno a
                         INNER JOIN endereco e on a.idendereco = e.id
                         WHERE a.id = ? and a.idendereco = e.id;                      
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, idAluno);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Aluno removido com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro no removerAlunoBanco()\n" + ex.getMessage());
            return false;
        }
    }

    public Aluno localizarAlunoUnicoBanco(int idAluno) {
        Aluno al = new Aluno();

        return al;
    }

    public List<Aluno> localizarTodosAlunosBanco() {

        List<Aluno> result = new ArrayList<>();

        try {
            String sql = """
                         
                        SELECT a.id, a.nome, a.cpf, a.email, a.genero, a.datanascimento, a.ra, a.datamatricula, a.situacao, a.idcurso, c.nome, c.cargahoraria, c.qtdsemestres, a.idendereco, e.cidade, e.rua, e.numero
                        FROM aluno a
                        inner join curso c on a.idcurso = c.id
                        inner join endereco e on a.idendereco = e.id;
                         
                         """;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Aluno aluno;
                aluno = new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("genero"),
                        rs.getDate("datanascimento").toLocalDate(),
                        rs.getString("ra"),
                        rs.getDate("datamatricula").toLocalDate(),
                        rs.getString("situacao"),
                        new Endereco(
                                rs.getInt("idendereco"),
                                rs.getString("cidade"),
                                rs.getString("rua"),
                                rs.getString("numero")
                        ),
                        new Curso(
                                rs.getInt("idcurso"),
                                rs.getString("nome"),
                                rs.getInt("cargahoraria"),
                                rs.getInt("qtdsemestres"),
                                new Docente(),
                                0
                        )
                        
                );
                result.add(aluno);
            }

            rs.close();

        } catch (SQLException ex) {
            System.out.println("Erro no localizarTodosAlunosBanco()\n" + ex.getMessage());
        }

        return result;
    }

}
