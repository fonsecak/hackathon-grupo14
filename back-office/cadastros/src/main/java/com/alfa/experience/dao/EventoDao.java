package com.alfa.experience.dao;

import com.alfa.experience.model.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoDao extends Dao implements DaoInterface {

    @Override
    public boolean insert(Object entity) {
        try{
            var evento = (Evento) entity;
            var insertSql = "insert into eventos(nome, data_hora_inicio, data_hora_fim, local, valor_inscricao, publico_alvo, objetivo, banner, palestrante, especialidade, vagas_maxima) values(?,?,?,?,?,?,?,?,?,?,?)";
            var ps = getConnection().prepareStatement(insertSql);
            ps.setString(1, evento.getNome());
            ps.setString(2, evento.getDtInicio());
            ps.setString(3, evento.getDtFim());
            ps.setString(4, evento.getLocal());
            ps.setString(5, evento.getValorIncricao());
            ps.setString(6, evento.getPublicoAlvo());
            ps.setString(7, evento.getObjetivo());
            ps.setString(8, evento.getBanner());
            ps.setString(9, evento.getPalestrante());
            ps.setString(10, evento.getEspecialidade());
            ps.setString(11, evento.getVagasMax());

            return ps.execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Object entity) {
        return false;
    }

    @Override
    public boolean delete(Long pk) {
        return false;
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
                ev.setDtInicio(rs.getString("data_hora_inicio"));
                ev.setDtFim(rs.getString("data_hora_fim"));
                ev.setLocal(rs.getString("local"));
                ev.setValorIncricao(rs.getString("valor_inscricao"));
                ev.setPublicoAlvo(rs.getString("publico_alvo"));
                ev.setObjetivo(rs.getString("objetivo"));
                ev.setBanner(rs.getString("banner"));
                ev.setPalestrante(rs.getString("palestrante"));
                ev.setEspecialidade(rs.getString("especialidade"));
                ev.setVagasMax(rs.getString("vagas_maxima"));
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
                e.setDtInicio(rs.getString("data_hora_inicio"));
                e.setDtFim(rs.getString("data_hora_fim"));
                e.setLocal(rs.getString("local"));
                e.setValorIncricao(rs.getString("valor_inscricao"));
                e.setPublicoAlvo(rs.getString("publico_alvo"));
                e.setObjetivo(rs.getString("objetivo"));
                e.setBanner(rs.getString("banner"));
                e.setPalestrante(rs.getString("palestrante"));
                e.setEspecialidade(rs.getString("especialidade"));
                e.setVagasMax(rs.getString("vagas_maxima"));
                eventos.add(e);
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(eventos);
    }
}



