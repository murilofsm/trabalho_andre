package util;

import java.util.ArrayList;

public class MenusUtils {

    public static void cadastroRealizadoComSucesso() {
        System.out.println("\nCadastro realizado com sucesso !!\n");
    }

    public static void cadastroNaoRealizadoComSucesso() {
        System.out.println("\nCadastro não realizado com sucesso !! Tente novamente !!\n");
    }

    public static void opcaoInvalida() {
        System.out.println("\nOpção inválida! Tente novamente!!\n");
    }

    public static void usuarioNaoEncontrado() {
        System.out.println("\nUsuário não encontrado !!\n");
    }

    public static void remocaoSucesso() {
        System.out.println("\nRemoção ocorrida com sucesso !!\n");
    }

    public static void alteracaoSucesso() {
        System.out.println("\nAlteração ocorrida com sucesso !!\n");
    }

    public static void operacaoCanceladaComSucesso() {
        System.out.println("\nOperação cancelada com sucesso !!\n");
    }

    public static void erroRemocao() {
        System.out.println("\nOcorreu um erro na remoção !!\n");
    }

    public static void erroAlteracao() {
        System.out.println("\nOcorreu um erro na alteracao !!\n");
    }

    public static void pularLinha() {
        System.out.println();
    }

    public static boolean confirmacaoSim(String resp) {
        return resp.equalsIgnoreCase("SIM")
                || resp.equalsIgnoreCase("SIm")
                || resp.equalsIgnoreCase("sIM")
                || resp.equalsIgnoreCase("sIm")
                || resp.equalsIgnoreCase("Sim")
                || resp.equalsIgnoreCase("siM")
                || resp.equalsIgnoreCase("S")
                || resp.equalsIgnoreCase("s");
    }

}
