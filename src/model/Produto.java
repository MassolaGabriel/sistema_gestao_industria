package model;

public class Produto {

    private Integer idProduto;
    private String nomeProduto;
    private String descricao;

    public Produto(Integer idProduto, String nomeProduto, String descricao) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.descricao = descricao;
    }

    public Produto() {
    }

    public Integer getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * MÉTODO toString() MELHORADO PARA RELATÓRIOS
     */
    @Override
    public String toString() {
        return String.format("ID: %-3d | Produto: %-20s | Descrição: %s",
                this.idProduto,
                this.nomeProduto,
                this.descricao);
    }
}