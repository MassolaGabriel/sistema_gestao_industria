package menu;

import dao.ProdutoDAO;
import model.Produto;

import java.util.ArrayList;
import java.util.Scanner;

public class ProdutoMenu {
    // O DAO e o Scanner agora são membros estáticos da classe
    private static ProdutoDAO dao = new ProdutoDAO();
    private static Scanner scanner = new Scanner(System.in);

    // O método main agora é responsável apenas por controlar o loop do menu
    public static void main(String[] args) {
        int opcao;

        do {
            System.out.println("\n--- MENU PRODUTO ---");
            System.out.println("[1] Listar produtos");
            System.out.println("[2] Buscar produto por ID");
            System.out.println("[3] Cadastrar produto");
            System.out.println("[4] Atualizar produto");
            System.out.println("[5] Remover produto");
            System.out.println("[0] SAIR");
            System.out.print("Escolha uma opção: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Por favor, digite um número.");
                scanner.next();
                System.out.print("Escolha uma opção: ");
            }
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    listarProdutos();
                    break;
                case 2:
                    buscarProdutoPorId();
                    break;
                case 3:
                    cadastrarProduto();
                    break;
                case 4:
                    atualizarProduto();
                    break;
                case 5:
                    removerProduto();
                    break;
                case 0:
                    System.out.println("Saindo do menu de produtos...");
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 0);
    }

    // --- MÉTODOS DE MENU (AGORA FORA DO MAIN) ---

    private static void listarProdutos() {
        ArrayList<Produto> produtos = dao.Listar();
        if (produtos != null && !produtos.isEmpty()) {
            System.out.println("\n--- LISTA DE PRODUTOS ---");
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        } else {
            System.out.println("Nenhum produto encontrado.");
        }
    }

    private static void buscarProdutoPorId() {
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Produto produto = dao.buscarPorId(id);
        if (produto != null) {
            System.out.println("Produto encontrado: " + produto);
        } else {
            System.out.println("Produto com ID " + id + " não encontrado.");
        }
    }

    private static void cadastrarProduto() {
        System.out.print("Nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição do produto: ");
        String descricao = scanner.nextLine();

        // O ID é gerado pelo banco, então passamos null
        Produto produto = new Produto(null, nome, descricao);

        if (dao.cadastrarProduto(produto)) {
            System.out.println("Produto cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar produto.");
        }
    }

    private static void atualizarProduto() {
        System.out.print("ID do produto a atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        // Verifica se o produto existe antes de tentar atualizar
        Produto produto = dao.buscarPorId(id);
        if (produto == null) {
            System.out.println("Produto com ID " + id + " não encontrado.");
            return;
        }

        System.out.print("Novo nome do produto (Deixe em branco para não alterar): ");
        String nome = scanner.nextLine();
        System.out.print("Nova descrição do produto (Deixe em branco para não alterar): ");
        String descricao = scanner.nextLine();

        // Atualiza os campos apenas se o usuário digitou algo novo
        if (nome != null && !nome.trim().isEmpty()) {
            produto.setNomeProduto(nome);
        }
        if (descricao != null && !descricao.trim().isEmpty()) {
            produto.setDescricao(descricao);
        }

        if (dao.atualizarProduto(produto)) {
            System.out.println("Produto atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar produto.");
        }
    }

    private static void removerProduto() {
        System.out.print("ID do produto a remover: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (dao.deletarProduto(id)) {
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Erro ao remover produto. Verifique se o ID existe.");
        }
    }
}