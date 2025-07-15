package menu;

import dao.FuncionarioDAO;
import dao.ProdutoDAO;
import dao.ProducaoDAO;
import model.Funcionario;
import model.Produto;
import model.Producao;

import java.util.ArrayList;
import java.util.Scanner;

public class ProducaoMenu {

    private static ProducaoDAO producaoDAO = new ProducaoDAO();
    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private static ProdutoDAO produtoDAO = new ProdutoDAO();
    private static Scanner scanner = new Scanner(System.in);

    // ATUALIZAÇÃO: O método agora é 'main' para ser chamado pela AppIndustria
    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- MENU PRODUÇÃO ---");
            System.out.println("[1] Listar produções");
            System.out.println("[2] Cadastrar nova produção");
            System.out.println("[3] Remover produção");
            System.out.println("[0] VOLTAR AO MENU PRINCIPAL");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        listarProducoes();
                        break;
                    case 2:
                        cadastrarProducao();
                        break;
                    case 3:
                        removerProducao();
                        break;
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                opcao = -1; // Garante que o loop continue
            }
        } while (opcao != 0);
    }

    // O resto da classe permanece igual...
    private static void listarProducoes() {
        ArrayList<Producao> producoes = producaoDAO.listar();
        if (producoes.isEmpty()) {
            System.out.println("Nenhum registro de produção encontrado.");
            return;
        }
        System.out.println("\n--- LISTA DE PRODUÇÕES ---");
        for (Producao p : producoes) {
            System.out.println(p);
        }
    }

    private static void cadastrarProducao() {
        System.out.println("\n--- Selecione o Funcionário ---");
        ArrayList<Funcionario> funcionarios = funcionarioDAO.listar();
        if (funcionarios.isEmpty()) {
            System.out.println("ERRO: Nenhum funcionário cadastrado.");
            return;
        }
        for (Funcionario f : funcionarios) {
            System.out.println("[" + f.getIdFuncionario() + "] - " + f.getNome() + " " + f.getSobrenome());
        }
        System.out.print("Digite o ID do funcionário: ");
        int idFuncionario = Integer.parseInt(scanner.nextLine());
        Funcionario funcionarioEscolhido = funcionarioDAO.buscarPorId(idFuncionario);
        if (funcionarioEscolhido == null) {
            System.out.println("ID de funcionário inválido. Operação cancelada.");
            return;
        }

        System.out.println("\n--- Selecione o Produto ---");
        ArrayList<Produto> produtos = produtoDAO.Listar();
        if (produtos.isEmpty()) {
            System.out.println("ERRO: Nenhum produto cadastrado.");
            return;
        }
        for (Produto p : produtos) {
            System.out.println("[" + p.getIdProduto() + "] - " + p.getNomeProduto());
        }
        System.out.print("Digite o ID do produto: ");
        int idProduto = Integer.parseInt(scanner.nextLine());
        Produto produtoEscolhido = produtoDAO.buscarPorId(idProduto);
        if (produtoEscolhido == null) {
            System.out.println("ID de produto inválido. Operação cancelada.");
            return;
        }

        System.out.print("Digite a data da produção (AAAA-MM-DD): ");
        String data = scanner.nextLine();
        System.out.print("Digite a quantidade produzida: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        Producao novaProducao = new Producao(null, data, quantidade, funcionarioEscolhido, produtoEscolhido);

        if (producaoDAO.cadastrar(novaProducao)) {
            System.out.println("Registro de produção cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar registro de produção.");
        }
    }

    private static void removerProducao() {
        System.out.print("Digite o ID do registro de produção a ser removido: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (producaoDAO.remover(id)) {
            System.out.println("Registro de produção removido com sucesso!");
        } else {
            System.out.println("Erro ao remover o registro. Verifique se o ID existe.");
        }
    }
}