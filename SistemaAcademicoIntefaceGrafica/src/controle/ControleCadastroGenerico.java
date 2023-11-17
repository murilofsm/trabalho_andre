package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.*;
import persistencia.DaoAluno;
import persistencia.DaoCurso;
import persistencia.DaoDocente;
import persistencia.DaoFuncionario;
import util.DialogBoxUtils;
import visao.TelaCadastro;
import visao.TelaListaCadastro;

/**
 *
 * @author Andre
 * @param <T>
 */
public abstract class ControleCadastroGenerico<T> implements IControleCadastro {

    private Class classeModelo;
    protected TelaCadastro telaCadastro;
    protected TelaListaCadastro telaListaCadastro;
    protected List<T> registros = new ArrayList<>();
    protected T registroSelecionado;
    private DaoFuncionario daoFuncionario = new DaoFuncionario();
    private DaoDocente daoDocente = new DaoDocente();
    private DaoCurso daoCurso = new DaoCurso();
    private DaoAluno daoAluno = new DaoAluno();

    public ControleCadastroGenerico(Class classeModelo) {
        this.classeModelo = classeModelo;
    }

    public ControleCadastroGenerico(Class classeModelo, TelaCadastro telaCadastro) {
        this.classeModelo = classeModelo;
        this.telaCadastro = telaCadastro;
    }

    public TelaCadastro getTelaCadastro() {
        return telaCadastro;
    }

    public void setTelaCadastro(TelaCadastro telaCadastro) {
        this.telaCadastro = telaCadastro;
    }

    public TelaListaCadastro getTelaListaCadastro() {
        return telaListaCadastro;
    }

    public List<T> getRegistros() {
        return registros;
    }

    public T getRegistroSelecionado() {
        return registroSelecionado;
    }

    @Override
    public String[] gerarColunasTabela() {
        if (classeModelo.equals(Aluno.class)) {
            return new String[]{"ID", "Nome", "CPF", "E-mail", "Curso"};
        } else if (classeModelo.getSuperclass() == Pessoa.class || classeModelo.equals(Docente.class) || classeModelo.equals(Funcionario.class)) {
            return new String[]{"ID", "Nome", "CPF", "E-mail"};
        } else if (classeModelo.equals(Curso.class)) {
            return new String[]{"ID", "Curso", "Semestres"};
        } else {
            DialogBoxUtils.exibirMensagemDeErro("Erro", "Erro ao gerar colunas para Tabela");
            return null;
        }
    }

    @Override
    public String[][] gerarDadosTabela(int qtdColunas, IControleCadastro controle) {

        List<T> lista = new ArrayList<>();
        
        if (controle instanceof ControleAluno) {
            lista.addAll(daoAluno.localizarTodosAlunosBanco());
        } else if (controle instanceof ControleFuncionario) {
            if (controle instanceof ControleDocente) {
                lista.addAll(daoDocente.localizarTodosDocentesBanco());
            } else {
                lista.addAll(daoFuncionario.localizarTodosFuncionariosBanco()); // adicionar todos os dados
            }
        } else if (controle instanceof ControleCurso) {
            lista.addAll(daoCurso.localizarTodosCursosBanco());

        }

        String[][] dados = new String[lista.size()][qtdColunas];

        int linha = 0;

        for (T reg : lista) {
            dados[linha] = getDadosEntidadeModeloParaTabela(reg);
            linha++;
        }

        return dados;
    }

    public String[] getDadosEntidadeModeloParaTabela(T entidade) {

        if (entidade instanceof Aluno) {
            Aluno al = (Aluno) entidade;
            return new String[]{"" + al.getId(), al.getNome(), al.getCpf(), al.getEmail(), al.getCurso() != null ? al.getCurso().getNome() : ""};
        } else if (entidade.getClass().getSuperclass() == Pessoa.class || entidade instanceof Docente || entidade instanceof Funcionario) {
            Pessoa p = (Pessoa) entidade;
            return new String[]{"" + p.getId(), p.getNome(), p.getCpf(), p.getEmail()};
        } else if (entidade instanceof Curso) {
            Curso cur = (Curso) entidade;
            return new String[]{"" + cur.getId(), cur.getNome(), "" + cur.getQtdSemestres()};
        } else {
            DialogBoxUtils.exibirMensagemDeErro("Erro", "Erro ao gerar Dados para Tabela");
            return new String[10];
        }
    }

    public List<String> getDescricaoRegistros() {
        if (classeModelo.isAnnotationPresent(IDescricao.class)) {
            return registros.stream().map(x -> ((IDescricao) x).getDescricao()).collect(Collectors.toList());
        } else {
            return registros.stream().map(x -> x.toString()).collect(Collectors.toList());
        }

    }

    @Override
    public void atualizarTabelaTelaListagem() {
        telaListaCadastro.atualizarTabela();
    }

    @Override
    public void abrirTelaListagem() {
        telaListaCadastro = new TelaListaCadastro(this);
        telaListaCadastro.setVisible(true);
    }

    @Override
    public void abrirTelaCadastro() {
        telaCadastro.inicializarComponentesTela();
        telaCadastro.setVisible(true);
        telaCadastro.setEditarDados(false);
    }

    @Override
    public abstract void abrirTelaCadastroParaEdicao(int index);

    @Override
    public abstract boolean removerCadastro(int index);

    @Override
    public abstract void editar(HashMap<String, Object> dados);

    @Override
    public abstract void salvar(HashMap<String, Object> dados);
}
