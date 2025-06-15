package com.alfa.experience.dao;

import com.alfa.experience.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AlunoDao extends Dao implements DaoInterface {

    @Override
    public boolean insert(Object entity) {
        return false;
    }

    @Override
    public boolean update(Object entity) {

        try {
            var aluno = (Aluno) entity;
            var updateSql = "UPDATE inscricoes i " +
                    "JOIN participantes p ON p.id = i.id_participante " +
                    "SET i.status = ? " +
                    "WHERE p.id = ? AND i.id_evento = ?";
            var ps = getConnection().prepareStatement(updateSql);
            ps.setString(1, aluno.getStatus());
            ps.setLong(2, aluno.getId());
            ps.setInt(3, 1); // Substitua por um parâmetro de evento dinâmico se necessário
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long pk) {
        return false;
    }

    @Override
    public Object select(Long pk) {
        return null;
    }

    @Override
    public List<Object> selectAll() {
        return List.of();
    }

    public List<Aluno> listarAlunosPorEvento(int idEvento) {
        List<Aluno> alunos = new ArrayList<>();
        Connection conn = getConnection();
        if (conn == null) return alunos;

        String sql = "SELECT p.id, p.nome, p.sobrenome, p.cpf, p.email, p.senha, p.empresa, i.status " +
                "FROM participantes p JOIN inscricoes i ON p.id = i.id_participante WHERE i.id_evento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
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
        } catch (Exception e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }


    public int getIdInscricao(int idAluno, int idEvento) {
        Connection conn = getConnection();
        if (conn == null) return -1;

        String sql = "SELECT id FROM inscricoes WHERE id_participante = ? AND id_evento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAluno);
            stmt.setInt(2, idEvento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.err.println("Erro ao buscar ID da inscrição: " + e.getMessage());
        }
        return -1;
    }
}