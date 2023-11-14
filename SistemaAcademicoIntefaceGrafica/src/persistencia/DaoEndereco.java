package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import modelo.Endereco;

public class DaoEndereco extends DAO {

    public ArrayList<Endereco> buscarEnderecosBanco() {
        ArrayList<Endereco> enderecos = new ArrayList<>();
        try {
            String sql = """
                         SELECT * FROM endereco;
                         """;
            ResultSet rs = consultaSQL(sql);
            while (rs.next()) {
                Endereco end = new Endereco();
                end.setId(rs.getInt("id"));
                end.setCidade(rs.getString("cidade"));
                end.setRua(rs.getString("rua"));
                end.setNumero(rs.getString("numero"));

                enderecos.add(end);
            }
        } catch (SQLException ex) {
            System.out.println("Falha ao carregar endereços!\n" + ex.getMessage());
        }
        return enderecos;
    }

    public Endereco buscarEnderecosPorIDBanco(int idEndereco) {
        Endereco end = null;
        try {
            String sql = "SELECT * from ENDERECO where id = " + idEndereco;
            ResultSet rs = consultaSQL(sql);
            if (rs.next()) {
                end = new Endereco();
                end.setId(rs.getInt("idendereco"));
                end.setCidade(rs.getString("cidade"));
                end.setRua(rs.getString("rua"));
                end.setNumero(rs.getString("numero"));
            }
        } catch (SQLException ex) {
            System.out.println("Falha ao carregar endereço!\n" + ex.getMessage());
        }
        return end;
    }

    public boolean salvarEnderecoBanco(Endereco end) {
        try {
            String sql = """
                         INSERT INTO public.endereco(
                          id, cidade, rua, numero )
                          VALUES (?, ?, ?, ?);
                         """;

            PreparedStatement ps = criarPreparedStatement(sql);
            ps.setInt(1, end.getId());
            ps.setString(2, end.getCidade());
            ps.setString(3, end.getRua());
            ps.setString(4, end.getNumero());

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Falha ao salvar endereço\n" + ex.getMessage());
            return false;
        }
    }

    public boolean atualizarEnderecoBanco(Endereco end) {
        try {
            String sql = """
                         UPDATE public.endereco
                          SET cidade = ?, rua = ?, numero = ?
                          WHERE idendereco =""" + end.getId();

            PreparedStatement ps = criarPreparedStatement(sql);
            ps.setString(1, end.getCidade());
            ps.setString(2, end.getRua());
            ps.setString(3, end.getNumero());

            ps.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("Falha ao editar endereço\n" + ex.getMessage());
            return false;
        }
    }

    public String removerEnderecoBanco(Endereco end) {
        return "DELETE FROM public.endereco WHERE id = " + end.getId();
    }

    public boolean remover(Endereco end) {
        try {
            executeSql(removerEnderecoBanco(end));
            return true;
        } catch (SQLException e) {
            System.out.println("Falha ao remover endereço\n" + e.getMessage());
            return false;
        }
    }

}
