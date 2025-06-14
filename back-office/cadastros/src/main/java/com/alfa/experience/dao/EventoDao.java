package com.alfa.experience.dao;

import com.alfa.experience.model.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoDao extends Dao implements DaoInterface {

    @Override
    public boolean insert(Object entity) {
        try{
            var evento = (Evento) entity;
            var insertSql = "INSERT INTO eventos (nome, descricao, data_hora_inicio, data_hora_fim, local, valor_inscricao, publico_alvo, objetivo, banner, vagas_maxima, id_palestrantes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            var ps = getConnection().prepareStatement(insertSql);
            ps.setString(1, evento.getNome());
            ps.setString(2, evento.getDescricao());
            ps.setTimestamp(3, evento.getDtInicio());
            ps.setTimestamp(4, evento.getDtFim());
            ps.setString(5, evento.getLocal());
            ps.setString(6, evento.getValorInscricao());
            ps.setString(7, evento.getPublicoAlvo());
            ps.setString(8, evento.getObjetivo());
            ps.setString(9, evento.getBanner());
            ps.setInt(10, evento.getVagasMaximas());
            if (evento.getIdPalestrantes() != null) {
                ps.setLong(11, evento.getIdPalestrantes());
            } else {
                ps.setNull(11, java.sql.Types.BIGINT);
            }
            return ps.execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Object entity) {
        try {
            var evento = (Evento) entity;
            var updateSql = "UPDATE eventos SET nome=?, descricao=?, data_hora_inicio=?, data_hora_fim=?, local=?, valor_inscricao=?, publico_alvo=?, objetivo=?, banner=?, vagas_maxima=?, id_palestrantes=? WHERE id=?";
            var ps = getConnection().prepareStatement(updateSql);
            ps.setString(1, evento.getNome());
            ps.setString(2, evento.getDescricao());
            ps.setTimestamp(3, evento.getDtInicio());
            ps.setTimestamp(4, evento.getDtFim());
            ps.setString(5, evento.getLocal());
            ps.setString(6, evento.getValorInscricao());
            ps.setString(7, evento.getPublicoAlvo());
            ps.setString(8, evento.getObjetivo());
            ps.setString(9, evento.getBanner());
            ps.setInt(10, evento.getVagasMaximas());
            ps.setLong(11, evento.getIdPalestrantes());
            ps.setLong(12, evento.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long pk) {
        try {
            var deleteSql = "DELETE FROM eventos WHERE id=?";
            var ps = getConnection().prepareStatement(deleteSql);
            ps.setLong(1, pk);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Object select(Long pk) {
        var ev = new Evento();
        try {
            var selectSql = "select * from eventos where id=?";

            var ps = getConnection().prepareStatement(selectSql);
            ps.setLong(1, pk);

            var rs = ps.executeQuery();

            while (rs.next()){
                ev.setId(rs.getLong("id"));
                ev.setNome(rs.getString("nome"));
                ev.setDescricao(rs.getString("descricao"));
                ev.setDtInicio(rs.getTimestamp("data_hora_inicio"));
                ev.setDtFim(rs.getTimestamp("data_hora_fim"));
                ev.setLocal(rs.getString("local"));
                ev.setValorInscricao(rs.getString("valor_inscricao"));
                ev.setPublicoAlvo(rs.getString("publico_alvo"));
                ev.setObjetivo(rs.getString("objetivo"));
                ev.setBanner(rs.getString("banner"));
                ev.setVagasMaximas(rs.getInt("vagas_maxima"));
                ev.setIdPalestrantes(rs.getLong("id_palestrantes"));
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ev;
    }

    @Override
    public List<Object> selectAll() {
        List<Evento> eventos = new ArrayList<>();
        try {
            var selectSql = "select * from eventos";
            var rs = getConnection().prepareStatement(selectSql).executeQuery();
            while (rs.next()){
                var e = new Evento();
                e.setId(rs.getLong("id"));
                e.setNome(rs.getString("nome"));
                e.setDescricao(rs.getString("descricao"));
                e.setDtInicio(rs.getTimestamp("data_hora_inicio"));
                e.setDtFim(rs.getTimestamp("data_hora_fim"));
                e.setLocal(rs.getString("local"));
                e.setValorInscricao(rs.getString("valor_inscricao"));
                e.setPublicoAlvo(rs.getString("publico_alvo"));
                e.setObjetivo(rs.getString("objetivo"));
                e.setBanner(rs.getString("banner"));
                e.setVagasMaximas(rs.getInt("vagas_maxima"));
                e.setIdPalestrantes(rs.getLong("id_palestrantes"));
                eventos.add(e);
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(eventos);
    }
}



