package com.alfa.experience.dao;

import com.alfa.experience.model.Palestrante;

import java.util.ArrayList;
import java.util.List;

public class PalestranteDao extends Dao implements DaoInterface {

    @Override
    public boolean insert(Object entity) {
        try{
            var palestrante = (Palestrante) entity;
            var insertSql = "insert into palestrantes (nome, sobre, foto) values(?,?,?)";
            var ps = getConnection().prepareStatement(insertSql);
            ps.setString(1, palestrante.getNome());
            ps.setString(2, palestrante.getSobre());
            ps.setString(3, palestrante.getFoto());

            return ps.execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Object entity) {
        try {
            var palestrante = (Palestrante) entity;
            var updateSql = "UPDATE palestrantes SET nome=?, sobre=?, foto=? WHERE id=?";
            var ps = getConnection().prepareStatement(updateSql);
            ps.setString(1, palestrante.getNome());
            ps.setString(2, palestrante.getSobre());
            ps.setString(3, palestrante.getFoto());
            ps.setLong(4, palestrante.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(Long pk) {
        try {
            var deleteSql = "DELETE FROM palestrantes WHERE id=?";
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
        var ev = new Palestrante();
        try {
            var selectSql = "select * from palestrantes where id=?";

            var ps = getConnection().prepareStatement(selectSql);
            ps.setLong(1, pk);

            var rs = ps.executeQuery();

            while (rs.next()){
                ev.setId(rs.getLong("id"));
                ev.setNome(rs.getString("nome"));
                ev.setSobre(rs.getString("sobre"));
                ev.setFoto(rs.getString("foto"));
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return ev;
    }

    @Override
    public List<Object> selectAll() {
        List<Palestrante> palestrantes = new ArrayList<>();
        try {
            var selectSql = "select * from palestrantes";
            var rs = getConnection().prepareStatement(selectSql).executeQuery();
            while (rs.next()){
                var e = new Palestrante();
                e.setId(rs.getLong("id"));
                e.setNome(rs.getString("nome"));
                e.setSobre(rs.getString("sobre"));
                e.setFoto(rs.getString("foto"));
                palestrantes.add(e);
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return new ArrayList<>(palestrantes);
    }
}



