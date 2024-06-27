package view;

import java.util.List;
import java.util.Scanner;
import controller.AmbienteController;
import model.entity.Ambiente;
import model.entity.Cliente;
import model.entity.Reserva;
import util.AppLogger;

public class UsuarioView {

    private Cliente usuarioLogado;

    public UsuarioView(Cliente usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void menuCliente(Scanner scanner) {
        while (true) {
            System.out.println("\n=========================================");
            System.out.println("                MENU CLIENTE              ");
            System.out.println("=========================================");
            System.out.println("1 - VER AMBIENTES DISPONÍVEIS");
            System.out.println("2 - RESERVAR AMBIENTE");
            System.out.println("3 - LISTAR MINHAS RESERVAS");
            System.out.println("4 - SAIR");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            switch (opcao) {
                case 1:
                    listarAmbientesDisponiveis();
                    break;
                case 2:
                    ReservaView reservaView = new ReservaView(usuarioLogado);
                    reservaView.reservarAmbiente();
                    break;
                case 3:
                    listarMinhasReservas();
                    break;
                case 4:
                    AppLogger.info(usuarioLogado.getEmail(), "Logout realizado.");
                    System.out.println("\nVocê saiu com sucesso. Até a próxima!");
                    return;
                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
            }
        }
    }

    private void listarAmbientesDisponiveis() {
        AmbienteController ambienteController = new AmbienteController();
        List<Ambiente> ambientes = ambienteController.listarAmbientes();

        System.out.println("\n=========================================");
        System.out.println("        AMBIENTES DISPONÍVEIS            ");
        System.out.println("=========================================");

        for (Ambiente ambiente : ambientes) {
            List<String> horariosDisponiveis = ambienteController.listarHorariosDisponiveis(ambiente.getId());
            if (!horariosDisponiveis.isEmpty()) {
                System.out.println("ID: " + ambiente.getId() + ", Nome: " + ambiente.getNome() + ", Horários disponíveis: " + horariosDisponiveis);
            }
        }

        AppLogger.info(usuarioLogado.getEmail(), "Listou ambientes disponíveis.");
        System.out.println("=========================================\n");
    }

    private void listarMinhasReservas() {
        List<Reserva> reservas = usuarioLogado.getMinhasReservasFromDAO();

        System.out.println("\n=========================================");
        System.out.println("           MINHAS RESERVAS               ");
        System.out.println("=========================================");

        if (reservas.isEmpty()) {
            System.out.println("Você não possui reservas cadastradas.");
        } else {
            for (Reserva reserva : reservas) {
                System.out.println(reserva);
            }
        }

        AppLogger.info(usuarioLogado.getEmail(), "Listou suas reservas.");
        System.out.println("=========================================\n");
    }
}
