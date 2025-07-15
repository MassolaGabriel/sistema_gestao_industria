package menu;

import dao.FuncionarioDAO;
import dao.SetorDAO;
import model.Funcionario;
import model.Setor;

import java.util.ArrayList;
import java.util.Scanner;

public class FuncionarioMenu {

    private static FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
    private static SetorDAO setorDAO = new SetorDAO();
    private static Scanner scanner = new Scanner(System.in);

    // ATUALIZAÇÃO: O método agora é 'main' para ser chamado pela AppIndustria
    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n--- MENU FUNCIONÁRIO ---");
            System.out.println("[1] Listar funcionários");
            System.out.println("[2] Buscar funcionário por ID");
            System.out.println("[3] Cadastrar funcionário");
            System.out.println("[4] Atualizar funcionário");
            System.out.println("[5] Remover funcionário");
            System.out.println("[0] VOLTAR AO MENU PRINCIPAL");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        listarFuncionarios();
                        break;
                    case 2:
                        buscarFuncionarioPorId();
                        break;
                    case 3:
                        cadastrarFuncionario();
                        break;
                    case 4:
                        atualizarFuncionario();
                        break;
                    case 5:
                        removerFuncionario();
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
    private static void listarFuncionarios() {
        ArrayList<Funcionario> funcionarios = funcionarioDAO.listar();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado.");
            return;
        }
        System.out.println("\n--- LISTA DE FUNCIONÁRIOS ---");
        for (Funcionario f : funcionarios) {
            System.out.println(f);
        }
    }

    private static void buscarFuncionarioPorId() {
        System.out.print("Digite o ID do funcionário: ");
        int id = Integer.parseInt(scanner.nextLine());
        Funcionario f = funcionarioDAO.buscarPorId(id);
        if (f != null) {
            System.out.println("Funcionário encontrado: " + f);
        } else {
            System.out.println("Funcionário com ID " + id + " não encontrado.");
        }
    }

    private static void cadastrarFuncionario() {
        System.out.print("Nome do funcionário: ");
        String nome = scanner.nextLine();
        System.out.print("Sobrenome do funcionário: ");
        String sobrenome = scanner.nextLine();

        System.out.println("\nSelecione um Setor para o funcionário:");
        ArrayList<Setor> setores = setorDAO.listar();
        if (setores.isEmpty()) {
            System.out.println("ERRO: Nenhum setor cadastrado. Cadastre um setor antes de adicionar um funcionário.");
            return;
        }
        for (Setor s : setores) {
            System.out.println("[" + s.getIdSetor() + "] - " + s.getNome());
        }
        System.out.print("Digite o ID do setor escolhido: ");
        int idSetor = Integer.parseInt(scanner.nextLine());

        Setor setorEscolhido = setorDAO.buscarPorId(idSetor);
        if (setorEscolhido == null) {
            System.out.println("ID de setor inválido. Operação cancelada.");
            return;
        }

        Funcionario novoFuncionario = new Funcionario(null, nome, sobrenome, setorEscolhido);

        if (funcionarioDAO.cadastrar(novoFuncionario)) {
            System.out.println("Funcionário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar funcionário.");
        }
    }

    private static void atualizarFuncionario() {
        System.out.print("Digite o ID do funcionário a ser atualizado: ");
        int id = Integer.parseInt(scanner.nextLine());
        Funcionario funcionario = funcionarioDAO.buscarPorId(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.print("Novo nome (Atual: " + funcionario.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo sobrenome (Atual: " + funcionario.getSobrenome() + "): ");
        String sobrenome = scanner.nextLine();

        if (!nome.trim().isEmpty()) {
            funcionario.setNome(nome);
        }
        if (!sobrenome.trim().isEmpty()) {
            funcionario.setSobrenome(sobrenome);
        }

        System.out.println("\nDeseja alterar o setor do funcionário? (S/N) (Atual: " + funcionario.getSetor().getNome() + ")");
        String alterarSetor = scanner.nextLine();
        if(alterarSetor.equalsIgnoreCase("S")){
            ArrayList<Setor> setores = setorDAO.listar();
            for (Setor s : setores) {
                System.out.println("[" + s.getIdSetor() + "] - " + s.getNome());
            }
            System.out.print("Digite o NOVO ID do setor: ");
            int idNovoSetor = Integer.parseInt(scanner.nextLine());
            Setor novoSetor = setorDAO.buscarPorId(idNovoSetor);
            if(novoSetor != null){
                funcionario.setSetor(novoSetor);
            } else {
                System.out.println("ID do novo setor inválido. O setor original será mantido.");
            }
        }

        if (funcionarioDAO.atualizar(funcionario)) {
            System.out.println("Funcionário atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar funcionário.");
        }
    }

    private static void removerFuncionario() {
        System.out.print("Digite o ID do funcionário a ser removido: ");
        int id = Integer.parseInt(scanner.nextLine());
        if (funcionarioDAO.remover(id)) {
            System.out.println("Funcionário removido com sucesso!");
        } else {
            System.out.println("Erro ao remover funcionário. Verifique se o ID existe.");
        }
    }
}