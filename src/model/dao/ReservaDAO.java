package model.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Reserva;

public class ReservaDAO {
    private static final String FILE_PATH = "data/reservas.txt";
    private List<Reserva> listaReservas;
    private int proximoId;

    public ReservaDAO() {
        this.listaReservas = new ArrayList<>();
        this.proximoId = 1;
        carregarReservas(); // Carregar reservas do arquivo ao inicializar
    }

    public boolean criar(int idCliente, int idAmbiente, String horario) {
        Reserva reserva = new Reserva(proximoId++, idCliente, idAmbiente, horario, horario, horario);
        listaReservas.add(reserva);
        salvarDados();
        return true;
    }

    public List<Reserva> listar() {
        return listaReservas;
    }

    public List<Reserva> listarPorCliente(int idCliente) {
        List<Reserva> reservasCliente = new ArrayList<>();
        for (Reserva reserva : listaReservas) {
            if (reserva.getIdCliente() == idCliente) {
                reservasCliente.add(reserva);
            }
        }
        return reservasCliente;
    }

    public List<Reserva> listarPorAmbiente(int idAmbiente) {
        List<Reserva> reservasAmbiente = new ArrayList<>();
        for (Reserva reserva : listaReservas) {
            if (reserva.getIdAmbiente() == idAmbiente) {
                reservasAmbiente.add(reserva);
            }
        }
        return reservasAmbiente;
    }

    public boolean deletar(int id) {
        Reserva reservaParaDeletar = getById(id);
        if (reservaParaDeletar != null) {
            listaReservas.remove(reservaParaDeletar);
            salvarDados();
            return true;
        }
        return false;
    }

    private Reserva getById(int id) {
        for (Reserva reserva : listaReservas) {
            if (reserva.getId() == id) {
                return reserva;
            }
        }
        return null;
    }

    private void carregarReservas() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dados = line.split(",");
                int id = Integer.parseInt(dados[0]);
                int idCliente = Integer.parseInt(dados[1]);
                int idAmbiente = Integer.parseInt(dados[2]);
                String horario = dados[3];
                Reserva reserva = new Reserva(id, idCliente, idAmbiente, horario, horario, horario );
                listaReservas.add(reserva);
                proximoId = Math.max(proximoId, id + 1); // Atualiza o próximo ID
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados do arquivo de reservas: " + e.getMessage());
        }
    }

    private void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Reserva reserva : listaReservas) {
                bw.write(String.format("%d,%d,%d,%s\n", reserva.getId(), reserva.getIdCliente(), reserva.getIdAmbiente(), reserva.getHorario()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo de reservas: " + e.getMessage());
        }
    }
}
