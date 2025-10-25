import java.util.Scanner;
import model.Conta;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite seu nome: ");
        String nomeUsuario = sc.nextLine();

        // Aqui mostra o local onde é criado o nome.
        Conta conta = new Conta(1200.00, nomeUsuario);

        System.out.println("Olá " + conta.nomeUsuario + ", seja bem-vindo ao nosso primeiro protótipo de banco em Java!!");

        do {
            System.out.println("\nQual será sua escolha hoje?");
            System.out.println("1 - Ver saldo.");
            System.out.println("2 - Transferência (PIX/ETC).");
            System.out.println("3 - Deseja fazer um depósito?");
            System.out.println("4 - Sair.");
            System.out.print("Digite a opção: ");
            conta.opcao = sc.nextInt();

            switch (conta.opcao) {
                case 1 -> System.out.println("Seu saldo é: R$ " + conta.saldo);

                case 2 -> {
                    System.out.print("Digite o valor da transferência: ");
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Digite o nome da conta de destino: ");
                    String escolha = sc.nextLine();

                    if (valor <= conta.saldo) {
                        System.out.println("Transferência de R$ " + valor + " para conta " + escolha + " realizada com sucesso!");
                    } else {
                        System.out.println("Saldo insuficiente ou valor inválido.");
                    }
                }

                case 3 -> {
                    sc.nextLine();
                    System.out.print("Digite o nome da conta de destino: ");
                    String escolha = sc.nextLine();

                    System.out.print("Digite o valor: ");
                    double valor = sc.nextDouble();

                    if (valor >= 1200.00) {
                        System.out.println("O valor foi transferido para a conta " + escolha + " com sucesso!");
                        System.out.println("Valor da transferência: R$ " + valor);
                    } else {
                        System.out.println("Você não tem saldo suficiente para essa operação!");
                    }
                }

                default -> System.out.println("Opção inválida!");
            }

        } while (conta.opcao != 4);

        sc.close();
    }
}
