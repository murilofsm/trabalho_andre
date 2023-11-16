package modelo;

import java.time.LocalDate;

public class Docente extends Funcionario {

    protected String formacao;

    public Docente() {
    }

    public Docente(Integer id, String nome, String cpf, String email, String genero, LocalDate dataNascimento, String ctps, String formacao, double salario, Endereco endereco) {
        super(id, nome, cpf, email, genero, dataNascimento, ctps, salario, endereco);
        this.formacao = formacao;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println(nome + " | Cpf: " + cpf + " | Idade: " + calcularIdade() + " anos "
                + " | Cidade: " + endereco.getCidade() + " | Rua : " + endereco.getRua() + " | Número: " + endereco.getNumero() + " | "
                + "Ctps: " + ctps + " |  Salario: " + salario + " | Formação: " + formacao
        );
    }
}
