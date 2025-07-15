package dao;

import config.ConexaoMySQL;
import model.Funcionario;
import model.Setor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FuncionarioDAO {
    private Connection conn;

    public FuncionarioDAO() {
        this.conn = ConexaoMySQL.getConnection(); // Supondo que você tenha uma classe de conexão
    }

    // --- MÉTODO LISTAR (JÁ EXISTENTE E CORRETO) ---
    public ArrayList<Funcionario> listar() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT f.id_funcionario, f.nome, f.sobrenome, s.id_setor, s.nome_setor, s.responsavel " +
                "FROM funcionario f " +
                "INNER JOIN setor s ON f.id_setor = s.id_setor;";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Setor setor = new Setor(rs.getInt("id_setor"), rs.getString("nome_setor"), rs.getString("responsavel"));
                Funcionario funcionario = new Funcionario(rs.getInt("id_funcionario"), rs.getString("nome"), rs.getString("sobrenome"), setor);
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os funcionários: " + e.getMessage());
        }
        return funcionarios;
    }

    // --- NOVOS MÉTODOS ---

    public Funcionario buscarPorId(int id) {
        String sql = "SELECT f.id_funcionario, f.nome, f.sobrenome, s.id_setor, s.nome_setor, s.responsavel " +
                "FROM funcionario f " +
                "INNER JOIN setor s ON f.id_setor = s.id_setor " +
                "WHERE f.id_funcionario = ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Setor setor = new Setor(rs.getInt("id_setor"), rs.getString("nome_setor"), rs.getString("responsavel"));
                return new Funcionario(rs.getInt("id_funcionario"), rs.getString("nome"), rs.getString("sobrenome"), setor);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar funcionário por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean cadastrar(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nome, sobrenome, id_setor) VALUES (?, ?, ?);";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getSobrenome());
            // Aqui usamos o ID do objeto Setor para a chave estrangeira
            ps.setInt(3, funcionario.getSetor().getIdSetor());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar funcionário: " + e.getMessage());
        }
        return false;
    }

    public boolean atualizar(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nome = ?, sobrenome = ?, id_setor = ? WHERE id_funcionario = ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getSobrenome());
            ps.setInt(3, funcionario.getSetor().getIdSetor());
            ps.setInt(4, funcionario.getIdFuncionario());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar funcionário: " + e.getMessage());
        }
        return false;
    }

    public boolean remover(int id) {
        String sql = "DELETE FROM funcionario WHERE id_funcionario = ?;";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao remover funcionário: " + e.getMessage());
        }
        return false;
    }
}