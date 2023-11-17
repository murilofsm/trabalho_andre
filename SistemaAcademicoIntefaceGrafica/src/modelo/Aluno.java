package modelo;

import java.time.LocalDate;

public class Aluno extends Pessoa{
    protected String ra;
    protected Curso curso;
    protected LocalDate dataMatricula;
    protected String situacao;
    
    public Aluno() {
        this.curso = new Curso();
    }

    public Aluno(Integer id, String nome, String cpf, String email, String genero, LocalDate dataNascimento, String ra,LocalDate dataMatricula,  String situacao, Endereco endereco, Curso curso) {
        super(id, nome, cpf, email, genero, dataNascimento, endereco);
        this.ra = ra;
        this.curso = curso;
        this.dataMatricula = dataMatricula;
        this.situacao = situacao;
    }




    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDate dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public int getAnoMatricula(){
        return getDataMatricula().getYear();
    }
    
    @Override
    public void exibirInformacoes(){
        System.out.println(nome +  " | Cpf: " + cpf + " | RA: " + ra + " | Idade: " + calcularIdade() + " anos | "
                            + "Curso: " + curso.getNome() + " | Ano matricula: " + getAnoMatricula() + " | Situação: " + situacao);
    }
    
    
}
