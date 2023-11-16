package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Endereco;
import modelo.Funcionario;
import util.ConverterUtils;

public class DaoFuncionario<T> extends DAO {

    private static DaoEndereco daoEndereco = new DaoEndereco();

    public void inserirFuncionarioBanco(Funcionario fun) {

        int idEndereco = daoEndereco.inserirEnderecoBanco(fun.getEndereco());

        try {
            String sql = """
                         
                         INSERT INTO funcionario (nome, cpf, email, genero, datanascimento, ctps, salario, idendereco)
                         VALUES (?, ?, ?, ?, ?, ?, ?, ?);
                         
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, fun.getNome());
            stmt.setString(2, fun.getCpf());
            stmt.setString(3, fun.getEmail());
            stmt.setString(4, fun.getGenero());
            stmt.setDate(5, ConverterUtils.localDateParaDate(fun.getDataNascimento()));
            stmt.setString(6, fun.getCtps());
            stmt.setDouble(7, fun.getSalario());
            stmt.setInt(8, idEndereco);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Funcionario cadastrado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no inserirFuncionarioBanco()\n" + ex.getMessage());
        }
    }

    public void editarFuncionarioBanco(Funcionario fun) {
        try {
            String sql = """
                            UPDATE funcionario f
                            INNER JOIN endereco e on f.idendereco = e.id
                            SET f.nome = ?, f.cpf = ?, f.email = ?, f.genero = ?, f.datanascimento = ?, f.ctps = ?, f.salario = ?, e.cidade = ?, e.rua = ?, e.numero = ?
                            WHERE f.id = ?;
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setString(1, fun.getNome());
            stmt.setString(2, fun.getCpf());
            stmt.setString(3, fun.getEmail());
            stmt.setString(4, fun.getGenero());
            stmt.setDate(5, ConverterUtils.localDateParaDate(fun.getDataNascimento()));
            stmt.setString(6, fun.getCtps());
            stmt.setDouble(7, fun.getSalario());
            stmt.setString(8, fun.getEndereco().getCidade());
            stmt.setString(9, fun.getEndereco().getRua());
            stmt.setString(10, fun.getEndereco().getNumero());
            stmt.setInt(11, fun.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Funcionario editado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no inserirFuncionarioBanco()\n" + ex.getMessage());
        }
    }
    
    
    

    public boolean removerFuncionarioBanco(int idFuncionario) {
        try {
            String sql = """
                         DELETE f, e
                         FROM funcionario f
                         INNER JOIN endereco e on f.idendereco = e.id
                         WHERE f.id = ? and f.idendereco = e.id;                      
                         """;
            PreparedStatement stmt = criarPreparedStatement(sql);
            
            stmt.setInt(1, idFuncionario);
         
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Funcionaro removido com sucesso!! ");
            }
            stmt.close();
            return true;
        } catch (SQLException ex) {
            System.out.println("Erro no removerFuncionarioBanco()\n" + ex.getMessage());
            return false;
        }
    }

    public Funcionario localizarFuncionarioUnicoBanco(int idFuncionario) {

        Funcionario fun = new Funcionario();

        try {
            String sql = """
                         
                        SELECT f.id, f.nome, f.cpf, f.email, f.genero, f.datanascimento, f.ctps, f.salario, f.idendereco, e.cidade, e.rua, e.numero
                        FROM funcionario f
                        INNER JOIN endereco e on f.idendereco = e.id
                        WHERE f.id = ?;
                         
                        """;

            PreparedStatement stmt = criarPreparedStatement(sql);

            stmt.setInt(1, idFuncionario);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                fun.setId(rs.getInt("id"));
                fun.setNome(rs.getString("nome"));
                fun.setCpf(rs.getString("cpf"));

                fun.setEmail(rs.getString("email"));
                fun.setGenero(rs.getString("genero"));
                fun.setDataNascimento(rs.getDate("datanascimento").toLocalDate());
                fun.setCtps(rs.getString("ctps"));
                fun.setSalario(rs.getDouble("salario"));
                fun.setEndereco(new Endereco(
                        rs.getInt("idendereco"),
                        rs.getString("cidade"),
                        rs.getString("rua"),
                        rs.getString("numero"))
                );
            }

            rs.close();

        } catch (SQLException ex) {
            System.out.println("Erro no localizarFuncionarioUnicoBanco()\n" + ex.getMessage());
        }
        return fun;
    }

    public List<Funcionario> localizarTodosFuncionariosBanco() {

        List<Funcionario> result = new ArrayList<>();

        try {
            String sql = """
                         
                        SELECT f.id, f.nome, f.cpf, f.email, f.genero, f.datanascimento, f.ctps, f.salario, f.idendereco, e.cidade, e.rua, e.numero
                        FROM funcionario f
                        INNER JOIN endereco e on f.idendereco = e.id;
                         
                         """;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Funcionario funcionario = new Funcionario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("genero"),
                        rs.getDate("datanascimento").toLocalDate(),
                        rs.getString("ctps"),
                        rs.getDouble("salario"),
                        new Endereco(
                                rs.getInt("idendereco"),
                                rs.getString("cidade"),
                                rs.getString("rua"),
                                rs.getString("numero")
                        )
                );
                result.add(funcionario);
            }

            rs.close();

        } catch (SQLException ex) {
            System.out.println("Erro no localizarTodosFuncionariosBanco()\n" + ex.getMessage());
        }

        return result;
    }

}
