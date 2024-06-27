package model.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import model.entity.Ambiente;

public class AmbienteDAO {
    private static final String FILE_PATH = "data/ambientes.txt";
    private List<Ambiente> listaAmbientes;
    private int proximoId;

    public AmbienteDAO() {
        this.listaAmbientes = new ArrayList<>();
        this.proximoId = 1;
        carregarDados(); // Carregar os ambientes ao inicializar o DAO
    }

    public boolean cadastrar(Ambiente ambiente) {
        ambiente.setId(proximoId++);
        listaAmbientes.add(ambiente);
        salvarDados();
        return true;
    }

    public List<Ambiente> listar() {
        return listaAmbientes;
    }

    public Ambiente getById(int id) {
        for (Ambiente ambiente : listaAmbientes) {
            if (ambiente.getId() == id) {
                return ambiente;
            }
        }
        return null;
    }

    public boolean alterar(Ambiente ambienteAlterado) {
        for (Ambiente ambiente : listaAmbientes) {
            if (ambiente.getId() == ambienteAlterado.getId()) {
                ambiente.setNome(ambienteAlterado.getNome());
                ambiente.setHorarios(ambienteAlterado.getHorarios());
                salvarDados();
                return true;
            }
        }
        return false;
    }

    public boolean deletar(int id) {
        Ambiente ambienteParaDeletar = getById(id);
        if (ambienteParaDeletar != null) {
            listaAmbientes.remove(ambienteParaDeletar);
            salvarDados();
            return true;
        }
        return false;
    }

    private void carregarDados() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] dados = line.split(",");
                if (dados.length == 3) {
                    int id = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    String horarios = dados[2].trim();
                    Ambiente ambiente = new Ambiente(nome, horarios);
                    ambiente.setId(id);
                    listaAmbientes.add(ambiente);
                    proximoId = Math.max(proximoId, id + 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de ambientes: " + e.getMessage());
        }
    }

    private void salvarDados() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Ambiente ambiente : listaAmbientes) {
                bw.write(String.format("%d,%s,%s\n", ambiente.getId(), ambiente.getNome(), ambiente.getHorarios()));
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo de ambientes: " + e.getMessage());
        }
    }
}
