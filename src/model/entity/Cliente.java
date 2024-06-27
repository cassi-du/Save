package model.entity;

import java.util.ArrayList;
import java.util.List;

import controller.ReservaController;
import model.dao.ReservaDAO;


public class Cliente extends Usuario {
    private List<Reserva> minhasReservas;

    public Cliente(int id, String email, String senha) {
        super(id, email, senha);
        this.minhasReservas = new ArrayList<>();
    }

    public boolean reservarAmbiente(Ambiente ambiente, String horario) {
        ReservaController reservaController = new ReservaController();
        if (reservaController.isAmbienteReservado(ambiente.getId(), horario)) {
            return false; // Ambiente já está reservado neste horário
        } else {
            ReservaDAO reservaDAO = new ReservaDAO();
            boolean sucesso = reservaDAO.criar(this.getId(), ambiente.getId(), horario);
            if (sucesso) {
                Reserva reserva = new Reserva(0, this.getId(), ambiente.getId(), horario, this.getEmail(), ambiente.getNome());
                minhasReservas.add(reserva);
                return true;
            }
            return false;
        }
    }

    public List<Reserva> getMinhasReservasFromDAO() {
        ReservaDAO reservaDAO = new ReservaDAO();
        return reservaDAO.listarPorCliente(this.getId());
    }
}
