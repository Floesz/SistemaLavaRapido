package model;

public class TipoServico {
    private int idServico;
    private String nomeServico;
    private String descricao;
    private double preco;

    public TipoServico() {
    }

    // Constructor com argumentos
    public TipoServico(int idServico, String nomeServico, double preco, String descricao) {
        this.idServico = idServico;
        this.nomeServico = nomeServico;
        this.preco = preco;
        this.descricao = descricao;
    }

    //Getters e Setters

    public int getId() {
        return idServico;
    }

    public void setId(int id) {
        this.idServico = id;
    }

    public String getNomeServico() {
        return nomeServico;
    }

    public void setNomeServico(String nomeServico) {
        this.nomeServico = nomeServico;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return nomeServico;
    }
}
