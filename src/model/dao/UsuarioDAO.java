package model.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.entity.Administrador;
import model.entity.Cliente;
import model.entity.Usuario;

public class UsuarioDAO {

    private List<Usuario> usuarios;

    public UsuarioDAO() {
        this.usuarios = carregarUsuarios();
    }

    private List<Usuario> carregarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("data/usuarios.txt"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(", ");
                int id = Integer.parseInt(dados[0]);
                String email = dados[1];
                String senha = dados[2];
                String tipo = dados[3];

                if (tipo.equals("Administrador")) {
                    usuarios.add(new Administrador(id, email, senha));
                } else if (tipo.equals("Cliente")) {
                    usuarios.add(new Cliente(id, email, senha));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return usuarios;
    }

    public Usuario getUsuarioByEmailAndPassword(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean cadastrarNovoUsuario(String email, String senha, String tipo) {
        int id = usuarios.size() + 1;
        Usuario novoUsuario;
        if (tipo.equalsIgnoreCase("Administrador")) {
            novoUsuario = new Administrador(id, email, senha);
        } else if (tipo.equalsIgnoreCase("Cliente")) {
            novoUsuario = new Cliente(id, email, senha);
        } else {
            return false;  // Tipo inválido
        }

        usuarios.add(novoUsuario);
        return salvarUsuarios();
    }

    public List<Usuario> listarUsuarios() {
        return new ArrayList<>(usuarios);
    }

    public boolean alterarUsuario(int id, String email, String senha, String tipo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuario.setEmail(email);
                usuario.setSenha(senha);
                if (tipo.equalsIgnoreCase("Administrador") && !(usuario instanceof Administrador)) {
                    usuarios.set(usuarios.indexOf(usuario), new Administrador(id, email, senha));
                } else if (tipo.equalsIgnoreCase("Cliente") && !(usuario instanceof Cliente)) {
                    usuarios.set(usuarios.indexOf(usuario), new Cliente(id, email, senha));
                }
                return salvarUsuarios();
            }
        }
        return false;  // Usuário não encontrado
    }

    public boolean deletarUsuario(int id) {
        Usuario usuarioParaRemover = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                usuarioParaRemover = usuario;
                break;
            }
        }

        if (usuarioParaRemover != null) {
            usuarios.remove(usuarioParaRemover);
            return salvarUsuarios();
        }

        return false;  // Usuário não encontrado
    }

    public Usuario getUsuarioById(int id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    private boolean salvarUsuarios() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/usuarios.txt"))) {
            for (Usuario usuario : usuarios) {
                String tipo = (usuario instanceof Administrador) ? "Administrador" : "Cliente";
                bw.write(usuario.getId() + ", " + usuario.getEmail() + ", " + usuario.getSenha() + ", " + tipo);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}