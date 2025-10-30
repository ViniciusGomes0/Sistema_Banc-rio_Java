import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Conta;

public class Program {

    private static final String NOME_ARQUIVO = "contas.json";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println("Gson configurado para persistência de dados!");

        List<Conta> listaContas = lerContasDoJson(gson);
        Conta contaLogada = null;

        // Loop para garantir que o login ou cadastro seja efetuado
        while (contaLogada == null) {

            System.out.println("Você possui cadastro? 1[S] 2[N]:  ");

            if (!sc.hasNextInt()) {
                System.out.println("Opção inválida.");
                sc.next();
                continue;
            }
            int opcao = sc.nextInt();
            sc.nextLine();

            if (opcao == 2){
                System.out.println("Aqui será feito seu login e seu cadastro!: ");

                System.out.print("Digite seu nome: ");
                String nomeUsuarioNovo = sc.nextLine();

                // Verifica se o nome já existe antes de cadastrar
                if (listaContas.stream().anyMatch(c -> c.nomeUsuario.equalsIgnoreCase(nomeUsuarioNovo))) {
                    System.out.println("Nome de usuário já existe. Por favor, faça login (Opção 1).");
                    continue;
                }

                System.out.println("Digite sua senha (Apenas números inteiros): ");
                if (!sc.hasNextInt()) {
                    System.out.println("Senha inválida. O cadastro falhou.");
                    sc.next();
                    continue;
                }
                int senhaNova = sc.nextInt();
                sc.nextLine();

                // Cria a nova conta com saldo inicial de R$1200.00
                Conta novaConta = new Conta(1200.00, nomeUsuarioNovo, senhaNova);
                listaContas.add(novaConta);
                salvarContasNoJson(listaContas, gson); // Salva o novo cadastro

                System.out.println("Cadastro feito com sucesso! Efetive seu login agora!\n");

                // Login logo em seguida (mantendo a frase original)
                System.out.print("Digite seu nome: ");
                String loginUsuario = sc.nextLine();

                System.out.println("Digite sua senha (Apenas números inteiros): ");
                if (!sc.hasNextInt()) {
                    System.out.println("Senha inválida.");
                    sc.next();
                    continue;
                }
                int senhaUsuario = sc.nextInt();
                sc.nextLine();

                // Tenta logar com a conta recém-criada
                contaLogada = buscarConta(loginUsuario, senhaUsuario, listaContas);

                if(contaLogada == null){
                    // Mensagem de erro unificada (usuário não cadastrado)
                    System.out.println("As credenciais não são iguais (Usuário não cadastrado ou senha incorreta)");
                    continue;
                }
            }
            else if (opcao == 1){ // Opção [S]
                // Confirmar o login
                System.out.print("Digite seu nome: ");
                String loginUsuario = sc.nextLine();

                System.out.println("Digite sua senha (Apenas números inteiros): ");
                if (!sc.hasNextInt()) {
                    System.out.println("Senha inválida.");
                    sc.next();
                    continue;
                }
                int senhaUsuario = sc.nextInt();
                sc.nextLine();

                // Tenta logar buscando a conta salva no JSON
                contaLogada = buscarConta(loginUsuario, senhaUsuario, listaContas);

                if (contaLogada != null) {
                    System.out.println("Login efetuado com sucesso!\n");
                } else {
                    // Mantém a frase, mas a lógica agora verifica o JSON
                    System.out.println("As credenciais não são iguais (Usuário não cadastrado ou senha incorreta).");
                }
            } else {
                System.out.println("Opção de menu inválida. Tente novamente.");
            }
        }



        System.out.println("Olá " + contaLogada.nomeUsuario + ", seja bem-vindo ao nosso primeiro protótipo de banco em Java!!");

        // laços dos menus principais e efetivações do banco:
        do {
            System.out.println("\nQual será sua escolha hoje?");
            System.out.println("1 - Ver saldo.");
            System.out.println("2 - Transferência (PIX/ETC).");
            System.out.println("3 - Deseja fazer um depósito?");
            System.out.println("4 - Sair.");
            System.out.print("Digite a opção: ");

            if (!sc.hasNextInt()) {
                System.out.println("Opção inválida.");
                sc.next();
                continue;
            }
            contaLogada.opcao = sc.nextInt();
            sc.nextLine();

            switch (contaLogada.opcao) {
                case 1 -> System.out.println("Seu saldo é: R$ " + contaLogada.saldo);

                case 2 -> {
                    System.out.print("Digite o valor da transferência: ");
                    if (!sc.hasNextDouble()) {
                        System.out.println("Valor inválido.");
                        sc.next();
                        break;
                    }
                    double valor = sc.nextDouble();
                    sc.nextLine();
                    System.out.print("Digite o nome da conta de destino: ");
                    String escolha = sc.nextLine();

                    if (valor > 0 && valor <= contaLogada.saldo) {
                        contaLogada.saldo -= valor; // Atualiza o saldo
                        salvarContasNoJson(listaContas, gson); // Salva a mudança
                        System.out.println("Transferência de R$ " + valor + " para conta " + escolha + " realizada com sucesso!");
                    } else {
                        System.out.println("Saldo insuficiente ou valor inválido.");
                    }
                }

                case 3 -> {
                    System.out.print("Digite o valor: ");
                    if (!sc.hasNextDouble()) {
                        System.out.println("Valor inválido.");
                        sc.next();
                        break;
                    }
                    double valor = sc.nextDouble();
                    sc.nextLine();

                    // Lógica de depósito corrigida
                    if (valor > 0) {
                        contaLogada.saldo += valor; // Adiciona o saldo
                        salvarContasNoJson(listaContas, gson); // Salva a mudança
                        System.out.println("Depósito de R$ " + valor + " realizado com sucesso!");
                    } else {
                        System.out.println("Valor de depósito inválido!");
                    }
                }

                default -> System.out.println("Opção inválida!");
            }

        } while (contaLogada.opcao != 4);

        sc.close();
    }


    private static List<Conta> lerContasDoJson(Gson gson) {
        File arquivo = new File(NOME_ARQUIVO);
        if (!arquivo.exists()) {
            return new ArrayList<>();
        }
        try (Reader reader = new FileReader(arquivo)) {

            java.lang.reflect.Type tipoListaConta = new TypeToken<List<Conta>>() {}.getType();
            List<Conta> contas = gson.fromJson(reader, tipoListaConta);
            return contas != null ? contas : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados do JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void salvarContasNoJson(List<Conta> contas, Gson gson) {
        try (Writer writer = new FileWriter(NOME_ARQUIVO)) {
            gson.toJson(contas, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no JSON: " + e.getMessage());
        }
    }

    //Método de Busca para o Login

    private static Conta buscarConta(String nome, int senha, List<Conta> listaContas) {
        for (Conta conta : listaContas) {
            if (conta.nomeUsuario.equalsIgnoreCase(nome) && conta.senha == senha) {
                return conta;
            }
        }
        return null;
    }
}