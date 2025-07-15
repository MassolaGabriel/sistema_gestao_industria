package model;

public class Setor {
    private Integer idSetor;
    private  String nome;
    private String responsavel;

    public Setor(Integer idSetor, String nome, String responsavel) {
        this.idSetor = idSetor;
        this.nome = nome;
        this.responsavel = responsavel;
    }

    public Setor() {
    }

    public Integer getIdSetor() {
        return idSetor;
    }

    public void setIdSetor(Integer idSetor) {
        this.idSetor = idSetor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Setor: %-15s | Responsável: %s",
                this.idSetor,
                this.nome,
                this.responsavel);
    }
}