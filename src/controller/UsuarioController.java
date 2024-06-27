package controller;

import java.util.List;
import model.dao.UsuarioDAO;
import model.entity.Usuario;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario login(String email, String senha) {
        return usuarioDAO.getUsuarioByEmailAndPassword(email, senha);
    }

    public boolean cadastrarNovoUsuario(String email, String senha, String tipo) {
        return usuarioDAO.cadastrarNovoUsuario(email, senha, tipo);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public boolean alterarUsuario(int id, String email, String senha, String tipo) {
        return usuarioDAO.alterarUsuario(id, email, senha, tipo);
    }

    public boolean deletarUsuario(int id) {
        return usuarioDAO.deletarUsuario(id);
    }

    public Usuario getUsuarioById(int id) {
        return usuarioDAO.getUsuarioById(id);
    }
}