package com.alfa.experience.service;

import com.alfa.experience.model.Aluno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoService {
    private static final String URL = "jdbc:mysql://localhost:3306/seu_banco?serverTimezone=America/Sao_Paulo";
    private static final String USER = "seu_usuario";
    private static final String PASSWORD = "sua_senha";

    public List<Aluno> listarAlunosPorEvento(int idEvento) {
        List<Aluno> alunos = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.sobrenome, p.cpf, p.email, p.senha, p.empresa, i.status " +
                "FROM participantes p JOIN inscricoes i ON p.id = i.id_participante WHERE i.id_evento = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idEvento);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("sobrenome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("empresa"),
                        rs.getString("status")
                );
                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    public boolean atualizarStatusInscricao(int idInscricao, String novoStatus) {
        String sql = "UPDATE inscricoes SET status = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idInscricao);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status: " + e.getMessage());
            return false;
        }
    }

    public int getIdInscricao(int idAluno, int idEvento) {
        String sql = "SELECT id FROM inscricoes WHERE id_participante = ? AND id_evento = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idEvento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar ID da inscrição: " + e.getMessage());
        }
        return -1; // Retorna -1 se não encontrar
    }
}