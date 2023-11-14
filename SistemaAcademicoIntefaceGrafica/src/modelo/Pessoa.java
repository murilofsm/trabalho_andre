package modelo;

import java.time.LocalDate;
import util.DateUtils;

/**
 *
 * @author Andre
 */
public abstract class Pessoa implements Comparable<Pessoa>, IDescricao {

    protected Integer id;
    protected String nome;
    protected String cpf;
    protected String email;
    protected String genero;
    protected LocalDate dataNascimento;
    protected Endereco endereco;

    public Pessoa() {
        endereco = new Endereco();
    }

    public Pessoa(Integer id, String nome, String cpf, String email, String genero, LocalDate dataNascimento, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.genero = genero;
        this.dataNascimento = dataNascimento;
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public long calcularIdade() {
        return DateUtils.quantidadeAnosEntreDatas(dataNascimento, LocalDate.now());
    }

    @Override
    public int compareTo(Pessoa o) {
        return this.nome.compareToIgnoreCase(o.getNome());
    }

    public String getInformacoes() {
        return nome + " | Cpf: " + cpf + " | Idade: " + calcularIdade() + " anos "
                + " | Cidade: " + endereco.getCidade() + "| Rua : " + endereco.getRua() + ", " + endereco.getNumero();
    }

    public void exibirInformacoes() {
        System.out.println(getInformacoes());
    }

    @Override
    /**
     * Retorna a propriedade que melhor descreve/representa o objeto
     */
    public String getDescricao() {
        return nome;
    }

}
