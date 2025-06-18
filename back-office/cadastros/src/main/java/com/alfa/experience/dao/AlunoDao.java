package com.alfa.experience.dao;

import com.alfa.experience.model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlunoDao extends Dao implements DaoInterface {

    @Override
    public boolean insert(Object entity) {
        return false;
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }

    //Atualiza o status de presença de uma inscrição específica com base no seu ID.

    //Este método é mais específico, usando o id da inscrição em vez de combinar
    // id_participante e id_evento, oferecendo maior flexibilidade para atualizações individuais.
    public boolean atualizarPresencaInscricao(int idInscricao, Boolean presenca) {
        try {
            var updateSql = "UPDATE inscricoes SET status = ? WHERE id = ?";
            var ps = getConnection().prepareStatement(updateSql);
            ps.setBoolean(1, presenca != null ? presenca : false);
            ps.setInt(2, idInscricao);
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

    //Lista todos os alunos inscritos em um evento específico.
    public List<Aluno> listarAlunosPorEvento(int idEvento) {
        List<Aluno> alunos = new ArrayList<>();
        Connection conn = getConnection();
        if (conn == null) return alunos;

        //Usa uma query SQL com INNER JOIN para combinar as tabelas participantes, inscricoes, e eventos, filtrando pelo idEvento.
        //Para cada linha do ResultSet, cria um objeto Aluno com os campos correspondentes (ID, nome, sobrenome, etc.),
        //incluindo o status da inscrição (inscricao_status) como presença e o nome do evento.

        String sql = "SELECT p.id, p.nome, p.sobrenome, p.cpf, p.email, p.senha, p.empresa, p.status AS participante_status, i.status AS inscricao_status, e.nome AS nome_evento " +
                "FROM participantes p " +
                "INNER JOIN inscricoes i ON p.id = i.id_participante " +
                "INNER JOIN eventos e ON i.id_evento = e.id " +
                "WHERE i.id_evento = ? " +
                "ORDER BY p.nome ASC";
        //O uso de INNER JOIN garante que apenas alunos inscritos sejam retornados. Se um aluno não estiver inscrito, ele não aparecerá.
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
                        rs.getString("participante_status"),
                        rs.getObject("inscricao_status") != null ? rs.getBoolean("inscricao_status") : null,
                        rs.getString("nome_evento")
                );
                alunos.add(aluno);
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar alunos: " + e.getMessage());
        }
        return alunos;
    }

    //Busca o ID de uma inscrição com base no ID do aluno e do evento.
    //para identificar a inscrição específica antes de atualizá-la (ex.: em atualizarPresencaInscricao).
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

    public Map<Integer, String> listarEventos() {
        Map<Integer, String> eventos = new HashMap<>();
        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("Conexão nula ao listar eventos.");
            return eventos;
        }

        //Usa SELECT DISTINCT id, nome FROM eventos para evitar duplicatas, garantindo que cada evento apareça apenas uma vez.
        String sql = "SELECT DISTINCT id, nome FROM eventos";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                if (!eventos.containsKey(id)) {
                    eventos.put(id, nome != null ? nome : "Sem nome");
                } else {
                    System.err.println("Evento duplicado detectado: ID " + id + ", Nome: " + nome);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao listar eventos: " + e.getMessage());
        }
        return eventos;
    }

}
