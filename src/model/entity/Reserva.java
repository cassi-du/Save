package model.entity;

public class Reserva {
    private int id;
    private int idCliente;
    private int idAmbiente;
    private String horario;
    private String nomeCliente;
    private String nomeAmbiente;

    public Reserva(int id, int idCliente, int idAmbiente, String horario, String nomeCliente, String nomeAmbiente) {
        this.id = id;
        this.idCliente = idCliente;
        this.idAmbiente = idAmbiente;
        this.horario = horario;
        this.nomeCliente = nomeCliente;
        this.nomeAmbiente = nomeAmbiente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdAmbiente() {
        return idAmbiente;
    }

    public void setIdAmbiente(int idAmbiente) {
        this.idAmbiente = idAmbiente;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getNomeAmbiente() {
        return nomeAmbiente;
    }

    @Override
    public String toString() {
        return String.format("Reserva [Cliente: %s, Ambiente: %s, Horário: %s]", nomeCliente, nomeAmbiente, horario);
    }

}
