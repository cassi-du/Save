package view;

import java.util.Scanner;
import controller.UsuarioController;
import model.entity.Administrador;
import model.entity.Cliente;
import model.entity.Usuario;
import util.AppLogger;

public class MainView {

    private Usuario usuarioLogado;

    public void iniciarSistema() {
        Scanner scanner = new Scanner(System.in);
        UsuarioController usuarioController = new UsuarioController();

        System.out.println("\n=======================================================");
        System.out.println("     BEM-VINDO AO SISTEMA DE RESERVA DE AMBIENTES    ");
        System.out.println("========================================================");

        boolean loginValido = false;
        while (!loginValido) {
            System.out.print("\n Digite seu email: ");
            String email = scanner.nextLine().trim().toLowerCase(); // Converte para minúsculas e remove espaços

            System.out.print("\n Digite sua senha: ");
            String senha = scanner.nextLine().trim(); // Remove espaços

            usuarioLogado = usuarioController.login(email, senha);

            if (usuarioLogado != null) {
                loginValido = true;
                AppLogger.info(usuarioLogado.getEmail(), "Login realizado com sucesso.");

                if (usuarioLogado instanceof Administrador) {
                    AdminView adminView = new AdminView(usuarioLogado);
                    adminView.menuAdministrador(scanner);
                } else if (usuarioLogado instanceof Cliente) {
                    UsuarioView usuarioView = new UsuarioView((Cliente) usuarioLogado);
                    usuarioView.menuCliente(scanner);
                }
            } else {
                AppLogger.error(email, "Login inválido.");

                System.out.println("\n=====================================");
                System.out.println("           LOGIN INVÁLIDO            ");
                System.out.println("=====================================");
                System.out.println("Login inválido. Tente novamente.");
            }
        }
    }
}