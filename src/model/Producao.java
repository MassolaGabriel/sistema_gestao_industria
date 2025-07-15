package model;

public class Producao {
    private Integer idProducao;
    private String dataProducao;
    private Integer quantidade;
    private Funcionario funcionario;
    private Produto produto;

    public Producao(Integer idProducao, String dataProducao, Integer quantidade, Funcionario funcionario, Produto produto) {
        this.idProducao = idProducao;
        this.dataProducao = dataProducao;
        this.quantidade = quantidade;
        this.funcionario = funcionario;
        this.produto = produto;
    }

    public Producao() {}

    // --- Getters e Setters ---
    public Integer getIdProducao() {
        return idProducao;
    }

    public void setIdProducao(Integer idProducao) {
        this.idProducao = idProducao;
    }

    public String getDataProducao() {
        return dataProducao;
    }

    public void setDataProducao(String dataProducao) {
        this.dataProducao = dataProducao;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public String toString() {
        // toString mais informativo
        String nomeProduto = (this.produto != null) ? this.produto.getNomeProduto() : "N/A";
        String nomeFuncionario = (this.funcionario != null) ? this.funcionario.getNome() + " " + this.funcionario.getSobrenome() : "N/A";

        return "Produção [ID=" + idProducao +
                ", Data=" + dataProducao +
                ", Quantidade=" + quantidade +
                ", Produto=" + nomeProduto +
                ", Funcionário=" + nomeFuncionario + "]";
    }
}