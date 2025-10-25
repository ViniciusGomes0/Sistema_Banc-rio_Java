package model;

import java.util.Scanner;

public class Conta {
    public int opcao;
    public double saldo;
    public String nomeUsuario;

    public Conta(double saldo, String nomeUsuario) {
        this.saldo = saldo;
        this.nomeUsuario = nomeUsuario;
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
        System.out.print("Digite o valor do depósito: R$ ");
        double valor = sc.nextDouble();

        if (valor <= 0) {
            System.out.println("Valor inválido!");
            return;
        }

        saldo += valor;
        System.out.printf("Depósito de R$ %.2f realizado com sucesso!%n", valor);
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }
}
