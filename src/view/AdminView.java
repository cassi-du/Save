package view;

import java.util.List;
import java.util.Scanner;
import controller.UsuarioController;
import model.entity.Administrador;
import model.entity.Usuario;
import util.AppLogger;

public class AdminView {

    private Usuario usuarioLogado;
    public AdminView(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void menuAdministrador(Scanner scanner) {
        while (true) {
            System.out.println("\n===================================");
            System.out.println("          MENU ADMINISTRADOR       ");
            System.out.println("===================================");
            System.out.println("1- GERIR AMBIENTE");
            System.out.println("2- GERIR USUÁRIOS");
            System.out.println("3- SAIR");
            System.out.print("\nDigite a opção desejada: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            if (opcao == 1) {
                AmbienteView ambienteView = new AmbienteView(usuarioLogado);
                ambienteView.gerirAmbientes(scanner);
            } else if (opcao == 2) {
                gerirUsuarios(scanner);
            } else if (opcao == 3) {
                AppLogger.info(usuarioLogado.getEmail(), "Logout realizado.");
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void gerirUsuarios(Scanner scanner) {
        UsuarioController usuarioController = new UsuarioController();

        while (true) {
            System.out.println("\n===================================");
            System.out.println("            GERIR USUÁRIOS         ");
            System.out.println("===================================");
            System.out.println("1- Cadastrar novo usuário");
            System.out.println("2- Listar usuários");
            System.out.println("3- Alterar usuário");
            System.out.println("4- Deletar usuário");
            System.out.println("5- Voltar");
            System.out.print("\nDigite a opção desejada: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            if (opcao == 1) {
                System.out.println("\n===================================");
                System.out.println("      CADASTRAR NOVO USUÁRIO       ");
                System.out.println("===================================");
                System.out.print("\nDigite o email do novo usuário: ");
                String novoEmail = scanner.nextLine();
                System.out.print("\nDigite a senha do novo usuário: ");
                String novaSenha = scanner.nextLine();
                System.out.print("\nDigite o tipo do novo usuário (Administrador/Cliente): ");
                String tipo = scanner.nextLine();

                boolean sucesso = usuarioController.cadastrarNovoUsuario(novoEmail, novaSenha, tipo);
                if (sucesso) {
                    AppLogger.info(usuarioLogado.getEmail(), "Cadastrou novo usuário: " + novoEmail);
                    System.out.println("Usuário cadastrado com sucesso!");
                } else {
                    AppLogger.error(usuarioLogado.getEmail(), "Erro ao cadastrar novo usuário: " + novoEmail);
                    System.out.println("Erro ao cadastrar usuário. Tente novamente.");
                }
            } else if (opcao == 2) {
                System.out.println("\n===================================");
                System.out.println("           LISTAR USUÁRIOS         ");
                System.out.println("===================================");
                List<Usuario> usuarios = usuarioController.listarUsuarios();
                for (Usuario u : usuarios) {
                    String tipo = (u instanceof Administrador) ? "Administrador" : "Cliente";
                    System.out.println("ID " + u.getId() + ", Email: " + u.getEmail() + ", Tipo: " + tipo);
                }
                AppLogger.info(usuarioLogado.getEmail(), "Listou usuários.");
            } else if (opcao == 3) {
                System.out.println("\n===================================");
                System.out.println("           ALTERAR USUÁRIO         ");
                System.out.println("===================================");
                System.out.print("Digite o ID do usuário a ser alterado: ");
                int id = scanner.nextInt();
                scanner.nextLine();  // Limpar o buffer
                System.out.print("Digite o novo email do usuário: ");
                String novoEmail = scanner.nextLine();
                System.out.print("Digite a nova senha do usuário: ");
                String novaSenha = scanner.nextLine();
                System.out.print("Digite o novo tipo do usuário (Administrador/Cliente): ");
                String tipo = scanner.nextLine();

                boolean sucesso = usuarioController.alterarUsuario(id, novoEmail, novaSenha, tipo);
                if (sucesso) {
                    AppLogger.info(usuarioLogado.getEmail(), "Alterou usuário ID: " + id);
                    System.out.println("Usuário alterado com sucesso!");
                } else {
                    AppLogger.error(usuarioLogado.getEmail(), "Erro ao alterar usuário ID: " + id);
                    System.out.println("Erro ao alterar usuário. Tente novamente.");
                }
            } else if (opcao == 4) {
                System.out.println("\n===================================");
                System.out.println("          DELETAR USUÁRIO          ");
                System.out.println("===================================");
                System.out.print("Digite o ID do usuário a ser deletado: ");
                int id = scanner.nextInt();
                scanner.nextLine();  // Limpar o buffer

                Usuario usuarioParaDeletar = usuarioController.getUsuarioById(id);
                if (usuarioParaDeletar != null) {
                    System.out.println("Você deseja realmente deletar o usuário com ID: " + id + ", Email: " + usuarioParaDeletar.getEmail() + "?");
                    System.out.println("1 - SIM");
                    System.out.println("2 - NÃO");
                    System.out.print("\nDigite a opção desejada: ");
                    int confirmacao = scanner.nextInt();
                    scanner.nextLine();  // Limpar o buffer

                    if (confirmacao == 1) {
                        boolean sucesso = usuarioController.deletarUsuario(id);
                        if (sucesso) {
                            AppLogger.info(usuarioLogado.getEmail(), "Deletou usuário ID: " + id);
                            System.out.println("Usuário deletado com sucesso!");
                        } else {
                            AppLogger.error(usuarioLogado.getEmail(), "Erro ao deletar usuário ID: " + id);
                            System.out.println("Erro ao deletar usuário. Tente novamente.");
                        }
                    } else {
                        System.out.println("Operação cancelada.");
                        AppLogger.info(usuarioLogado.getEmail(), "Cancelou a deleção do usuário ID: " + id);
                    }
                } else {
                    System.out.println("Usuário não encontrado.");
                    AppLogger.warn(usuarioLogado.getEmail(), "Tentativa de deletar usuário não encontrado com ID: " + id);
                }
            } else if (opcao == 5) {
                break;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
