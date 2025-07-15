package dao;

import config.ConexaoMySQL;
import model.Funcionario;
import model.Produto;
import model.Producao;
import model.Setor; // Necessário para montar o objeto Funcionario

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProducaoDAO {

    private Connection conn;

    public ProducaoDAO() {
        this.conn = ConexaoMySQL.getConnection();
    }

    public ArrayList<Producao> listar() {
        ArrayList<Producao> producoes = new ArrayList<>();
        // Query com múltiplos JOINs para buscar todos os dados relacionados
        String sql = "SELECT pr.id_producao, pr.data_producao, pr.quantidade, " +
                "p.id_produtos, p.nome_produto, p.descricao, " +
                "f.id_funcionario, f.nome, f.sobrenome, " +
                "s.id_setor, s.nome_setor, s.responsavel " +
                "FROM producao pr " +
                "INNER JOIN produtos p ON pr.id_produtos = p.id_produtos " +
                "INNER JOIN funcionario f ON pr.id_funcionario = f.id_funcionario " +
                "INNER JOIN setor s ON f.id_setor = s.id_setor;";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Monta o objeto Produto
                Produto produto = new Produto(rs.getInt("id_produtos"), rs.getString("nome_produto"), rs.getString("descricao"));

                // Monta o objeto Setor
                Setor setor = new Setor(rs.getInt("id_setor"), rs.getString("nome_setor"), rs.getString("responsavel"));

                // Monta o objeto Funcionario (que depende do Setor)
                Funcionario funcionario = new Funcionario(rs.getInt("id_funcionario"), rs.getString("nome"), rs.getString("sobrenome"), setor);

                // Monta o objeto Producao
                Producao producao = new Producao(rs.getInt("id_producao"), rs.getString("data_producao"), rs.getInt("quantidade"), funcionario, produto);

                producoes.add(producao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar as produções: " + e.getMessage());
        }
        return producoes;
    }

    public boolean cadastrar(Producao producao) {
        String sql = "INSERT INTO producao (id_produtos, id_funcionario, data_producao, quantidade) VALUES (?, ?, ?, ?);";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Pega os IDs dos objetos para as chaves estrangeiras
            ps.setInt(1, producao.getProduto().getIdProduto());
            ps.setInt(2, producao.getFuncionario().getIdFuncionario());
            ps.setString(3, producao.getDataProducao());
            ps.setInt(4, producao.getQuantidade());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produção: " + e.getMessage());
        }
        return false;
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM producao WHERE id_producao = ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover produção: " + e.getMessage());
        }
        return false;
    }

    // Métodos como buscarPorId e atualizar seguiriam a mesma lógica,
    // fazendo os JOINs necessários para montar os objetos completos.
}
