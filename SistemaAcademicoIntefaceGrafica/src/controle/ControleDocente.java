package controle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import modelo.Docente;
import persistencia.DaoDocente;
import visao.TelaCadastroPessoa;

/**
 *
 * @author Andre
 */
public class ControleDocente extends ControleFuncionario {

    private static DaoDocente daoDocente = new DaoDocente();

    public ControleDocente() {
        super(Docente.class);
        setTelaCadastro(new TelaCadastroPessoa(this));
    }

    public List<Docente> getListaDocentes() {
        return daoDocente.localizarTodosDocentesBanco();
    }

    public List<String> getNomesDocentes() {
        registros.clear();

        registros = daoDocente.localizarTodosDocentesBanco();

        return registros.stream().map(x -> x.getNome()).collect(Collectors.toList());

    }

    public Docente getDocenteSelecionado(int index) {
        List<Docente> docentes = getListaDocentes();
        if (index >= 0 && index < docentes.size()) {
            return docentes.get(index);
        }
        return null;
    }

    public void setarDadosObjeto(Docente docente, HashMap<String, Object> dados) {
        try {
            super.setarDadosObjeto(docente, dados);
            docente.setFormacao((String) dados.getOrDefault("formacao", ""));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao Setar Dados do Docente!\n" + e.getMessage(), "Falha ao Setar Dados", JOptionPane.ERROR_MESSAGE);
        }
    }

    public HashMap<String, Object> getDadosObjeto(Docente docente) {
        HashMap<String, Object> dados = super.getDadosObjeto(docente);
        dados.put("formacao", docente.getFormacao());

        return dados;
    }

    @Override
    public void abrirTelaCadastroParaEdicao(int index) {
        registroSelecionado = daoDocente.localizarDocenteUnicoBanco(index);
        if (registroSelecionado == null) {
            JOptionPane.showMessageDialog(null, "Falha ao Editar \nRegistro não encontrado!", "Falha ao Editar", JOptionPane.ERROR_MESSAGE);
            return;
        }

        telaCadastro.setarDadosTela(getDadosObjeto(registroSelecionado));
        telaCadastro.setEditarDados(true);
        telaCadastro.setVisible(true);
    }

    @Override
    public void editar(HashMap<String, Object> dados) {
        if (registroSelecionado != null) {
            setarDadosObjeto(registroSelecionado, dados);
            daoDocente.editarDocenteBanco((Docente) registroSelecionado);
        }
    }

    @Override
    public void salvar(HashMap<String, Object> dados) {
        Docente docente = new Docente();
        setarDadosObjeto(docente, dados);
        daoDocente.inserirDocenteBanco(docente);
    }

    @Override
    public boolean removerCadastro(int index) {
        if (daoDocente.removerDocenteBanco(index)) {
            return true;
        };
        return false;
    }

}
