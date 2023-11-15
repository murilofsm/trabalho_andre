package persistencia;

import java.sql.SQLException;
import controle.ControleAluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import modelo.Aluno;
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
                System.out.println("Funcionaro cadastrado com sucesso!! ");
            }
            stmt.close();

        } catch (SQLException ex) {
            System.out.println("Erro no inserirFuncionarioBanco()\n" + ex.getMessage());
        }
    }

    public List<T> localizarTodosFuncionariosBanco() {

        List<T> result = new ArrayList<>();

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
                result.add((T) funcionario);
            }
            
            rs.close();

        } catch (SQLException ex) {
            System.out.println("Erro no localizarTodosFuncionariosBanco()\n" + ex.getMessage());
        }
        
        return result;
    }

}
