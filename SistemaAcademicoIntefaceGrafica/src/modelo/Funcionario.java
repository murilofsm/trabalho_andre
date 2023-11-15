package modelo;

import java.time.LocalDate;

public class Funcionario extends Pessoa {

    protected String ctps;
    protected double salario;

    public Funcionario() {
    }

    public Funcionario(Integer id, String nome, String cpf, String email, String genero, LocalDate dataNascimento, String ctps, double salario, Endereco endereco) {
        super(id, nome, cpf, email, genero, dataNascimento, endereco);
        this.ctps = ctps;
        this.salario = salario;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    @Override
    public void exibirInformacoes() {
        System.out.println(nome + " | Cpf: " + cpf + " | Idade: " + calcularIdade() + " anos "
                + " | Cidade: " + endereco.getCidade() + " | Rua : " + endereco.getRua() + " | Número: " + endereco.getNumero() + " | "
                + "Ctps: " + ctps + " |  Salario: " + salario
        );

    }

    @Override
    public String toString() {
        return nome + " | Cpf: " + cpf + " | Idade: " + calcularIdade() + " anos "
                + " | Cidade: " + endereco.getCidade() + " | Rua : " + endereco.getRua() + " | Número: " + endereco.getNumero() + " | "
                + "Ctps: " + ctps + " |  Salario: " + salario;
    }
}
