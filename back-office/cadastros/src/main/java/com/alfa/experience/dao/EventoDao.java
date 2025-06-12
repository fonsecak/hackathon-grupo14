package com.alfa.experience.dao;

import com.alfa.experience.model.Evento;

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
    public List<Object> select(Long pk) {
        return List.of();
    }

    @Override
    public List<Object> selectAll() {
        return List.of();
    }

}
