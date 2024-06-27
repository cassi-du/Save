package controller;

import java.util.List;
import model.dao.ReservaDAO;
import model.entity.Reserva;

public class ReservaController {
    private ReservaDAO reservaDAO;

    public ReservaController() {
        this.reservaDAO = new ReservaDAO();
    }

    public boolean criarReserva(int idCliente, int idAmbiente, String horario) {
        return reservaDAO.criar(idCliente, idAmbiente, horario);
    }

    public List<Reserva> getReservasPorCliente(int idCliente) {
        return reservaDAO.listarPorCliente(idCliente);
    }

    public List<Reserva> getReservasPorAmbiente(int idAmbiente) {
        return reservaDAO.listarPorAmbiente(idAmbiente);
    }

    public boolean isAmbienteReservado(int idAmbiente, String horario) {
        List<Reserva> reservas = reservaDAO.listarPorAmbiente(idAmbiente);
        for (Reserva reserva : reservas) {
            if (reserva.getHorario().equals(horario)) {
                return true;
            }
        }
        return false;
    }
}
