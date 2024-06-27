package view;

import java.util.Scanner;
import controller.AmbienteController;
import model.entity.Cliente;
import model.entity.Ambiente;
import util.AppLogger;

public class ReservaView {

    private Cliente usuarioLogado;

    public ReservaView(Cliente usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public void reservarAmbiente() {
        AmbienteController ambienteController = new AmbienteController();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n=========================================");
            System.out.println("      SISTEMA DE RESERVA DE AMBIENTES    ");
            System.out.println("=========================================");
            System.out.println();

            System.out.print("Digite o ID do ambiente que deseja reservar: ");
            int idAmbiente = scanner.nextInt();
            scanner.nextLine();  // Limpar o buffer

            Ambiente ambiente = ambienteController.getAmbienteById(idAmbiente);

            if (ambiente != null) {
                boolean horarioValido = false;
                String horario = "";

                while (!horarioValido) {
                    System.out.print("Digite o horário desejado (ex: 09:00-13:00): ");
                    horario = scanner.nextLine();
                    System.out.println();

                    // Validar o formato do horário usando o HorarioValidator (assumi que você quer usar HorarioValidator, não AmbienteView)
                    if (AmbienteView.validarHorario(horario)) {
                        horarioValido = true;
                    } else {
                        System.out.println("Formato de horário inválido. Tente novamente.");
                    }
                }

                boolean sucesso = usuarioLogado.reservarAmbiente(ambiente, horario);

                if (sucesso) {
                    AppLogger.info(usuarioLogado.getEmail(), "Reservou ambiente ID: " + idAmbiente + " no horário: " + horario);
                    System.out.println("\n========================================");
                    System.out.println("     Reserva realizada com sucesso!     ");
                    System.out.println("========================================");
                    System.out.println();
                } else {
                    AppLogger.error(usuarioLogado.getEmail(), "Erro ao reservar ambiente ID: " + idAmbiente + " no horário: " + horario);
                    System.out.println("\n=================================================");
                    System.out.println("    HORÁRIO RESERVADO. ESCOLHA OUTRO HORÁRIO.    ");
                    System.out.println("=================================================");
                    System.out.println();

                    // Repetir a validação de horário após o erro de reserva
                    horarioValido = false;
                    while (!horarioValido) {
                        System.out.print("Digite o horário desejado (ex: 09:00-13:00): ");
                        horario = scanner.nextLine();
                        System.out.println();

                        // Validar o formato do horário usando o HorarioValidator
                        if (AmbienteView.validarHorario(horario)) {
                            horarioValido = true;
                        } else {
                            System.out.println("Formato de horário inválido. Tente novamente.");
                        }
                    }
                }
            } else {
                AppLogger.warn(usuarioLogado.getEmail(), "Tentativa de reservar ambiente não encontrado com ID: " + idAmbiente);
                System.out.println("\n======================================");
                System.out.println("       Ambiente não encontrado.       ");
                System.out.println("======================================");
                System.out.println();
            }
        }
    }
}