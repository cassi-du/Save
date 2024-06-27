package model.abstracts;

public abstract class AbstractUsuario {
    protected int id;
    protected String email;
    protected String senha;

    public AbstractUsuario(int id, String email, String senha) {
        this.id = id;
        this.email = email;
        this.senha = senha;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
