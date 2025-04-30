package model;

import java.time.LocalDateTime;

public class Agendamento {
    private int idAgendamento;
    private Cliente cliente;
    private TipoServico tipo;
    private LocalDateTime dataHora;
    private String status;

    public Agendamento() {
    }

    // Constructor com argumentos
    public Agendamento(int idAgendamento, Cliente cliente, TipoServico tipo, LocalDateTime dataHora, String status) {
        this.idAgendamento = idAgendamento;
        this.cliente = cliente;
        this.tipo = tipo;
        this.dataHora = dataHora;
        this.status = status;
    }

    //Getters e Setters

    public int getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(int idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoServico getTipo() {
        return tipo;
    }

    public void setTipo(TipoServico tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
