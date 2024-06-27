package controller;

import java.util.ArrayList;
import java.util.List;
import model.dao.AmbienteDAO;
import model.entity.Ambiente;
import util.FileHandler;

public class AmbienteController {
    private AmbienteDAO ambienteDAO;

    public AmbienteController() {
        this.ambienteDAO = new AmbienteDAO();
    }

    public boolean cadastrarNovoAmbiente(String nome, String horarios) {
        Ambiente ambiente = new Ambiente(nome, horarios);
        return ambienteDAO.cadastrar(ambiente);
    }

    public List<Ambiente> listarAmbientes() {
        return ambienteDAO.listar();
    }

    public Ambiente getAmbienteById(int id) {
        return ambienteDAO.getById(id);
    }

    public boolean alterarAmbiente(int id, String nome, String horarios) {
        Ambiente ambiente = new Ambiente(nome, horarios);
        return ambienteDAO.alterar(ambiente);
    }

    public boolean deletarAmbiente(int id) {
        return ambienteDAO.deletar(id);
    }

    public List<String> listarHorariosDisponiveis(int idAmbiente) {
        List<String> horariosDisponiveis = new ArrayList<>();
        Ambiente ambiente = ambienteDAO.getById(idAmbiente);
    
        if (ambiente != null) {
            // Ler os horários disponíveis do ambiente especificado
            List<String> linhasAmbientes = FileHandler.lerArquivo("data/ambientes.txt");
            for (String linha : linhasAmbientes) {
                String[] partes = linha.split(",");
                int id = Integer.parseInt(partes[0]);
                if (id == idAmbiente) {
                    horariosDisponiveis.add(partes[2]); // Adiciona apenas o horário disponível
                    break;
                }
            }
    
            // Remover os horários já reservados
            List<String> linhasReservas = FileHandler.lerArquivo("data/reservas.txt");
            for (String linha : linhasReservas) {
                String[] partes = linha.split(",");
                int id = Integer.parseInt(partes[2]);
                if (id == idAmbiente) {
                    horariosDisponiveis.remove(partes[3]); // Remove o horário reservado
                }
            }
        }
    
        return horariosDisponiveis;
    }
}