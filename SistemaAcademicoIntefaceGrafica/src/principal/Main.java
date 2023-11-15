package principal;

import controle.ControleLogin;
import controle.ControlePrincipal;
import java.time.LocalDate;
import modelo.Endereco;
import modelo.Funcionario;
import persistencia.DaoEndereco;
import persistencia.DaoFuncionario;

public class Main {

    private static ControlePrincipal controle;
    
    public static void main(String[] args) {
        controle = new ControlePrincipal();
        ControleLogin controleLogin = new ControleLogin(controle);

        controle.getControleLogin().abrirTelaLogin();
    }

    public static ControlePrincipal getControle() {
        return controle;
    }
     
/*
    static DaoEndereco daoEndereco = new DaoEndereco();
    static DaoFuncionario daoFuncionario = new DaoFuncionario();

    public static void main(String[] args) {

        Endereco e = new Endereco(3, "Cascavel", "Cipreste", "222");

        //int idEndereco = daoEndereco.inserirEnderecoBanco(e);
        //System.out.println(idEndereco);
        //daoEndereco.atualizarEnderecoBanco(e);
        //daoEndereco.removerEnderecoBanco(e);
        Funcionario f = new Funcionario(1, "Murilo", "2222", "sla@sla", "masculino", LocalDate.now(), e, "1111", 10.000);
        daoFuncionario.inserirFuncionarioBanco(f);
    }
*/
}
