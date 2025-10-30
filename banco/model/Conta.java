package model;

import java.util.Scanner;

public class Conta {
    public int opcao;
    public double saldo;
    public String nomeUsuario;
    public int senha; // Adicionado para persistência e login

    // Construtores

    public Conta(double saldo, String nomeUsuario, int senha) {
        this.saldo = saldo;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
    }

    public Conta() {
    }



    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public int getSenha() {
        return senha;
    }

    public double getSaldo() {
        return saldo;
    }



    public void verSaldo() {
        System.out.printf("Seu saldo atual é: R$ %.2f%n", saldo);
    }

    public void transferir(Scanner sc) {

        System.out.print("Digite o valor da transferência: R$ ");
        double valor = sc.nextDouble();
        sc.nextLine();

        if (valor <= 0) {
            System.out.println("Valor inválido!");
            return;
        }

        if (valor > saldo) {
            System.out.println("Saldo insuficiente!");
            return;
        }

        System.out.print("Digite o nome da conta de destino: ");
        String destino = sc.nextLine();

        saldo -= valor;
        System.out.printf("Transferência de R$ %.2f para %s realizada com sucesso!%n", valor, destino);
    }

    public void depositar(Scanner sc) {
        // ... (Seu código original de depósito aqui)
        System.out.print("Digite o valor do depósito: R$ ");
        double valor = sc.nextDouble();
        sc.nextLine(); // Consome a linha após o double

        if (valor <= 0) {
            System.out.println("Valor inválido!");
            return;
        }

        saldo += valor;
        System.out.printf("Depósito de R$ %.2f realizado com sucesso!%n", valor);

    }
}