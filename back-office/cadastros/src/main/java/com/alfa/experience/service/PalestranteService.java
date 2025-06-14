package com.alfa.experience.service;
import com.alfa.experience.dao.PalestranteDao;
import com.alfa.experience.model.Palestrante;

import java.util.ArrayList;
import java.util.List;

public class PalestranteService {
    private final PalestranteDao dao;

    public PalestranteService(PalestranteDao dao) {
        this.dao = dao;
    }

    public static Palestrante buscarPorId(long pk) {
        var dao = new PalestranteDao();
        return (Palestrante) dao.select(pk);
    }

    public void salvar (Palestrante palestrante){
        if (palestrante.getId() == null) {
            dao.insert(palestrante);
        } else {
            dao.update(palestrante);
        }
    }

    public void excluir(long pk){
        dao.delete(pk);
    }

    public List<Palestrante> listarTodos() {
        var dao = new PalestranteDao();
        List<Palestrante> palestrante = new ArrayList<>();
        dao.selectAll().forEach(
                objetc -> palestrante.add((Palestrante)objetc));
        return palestrante;
    }
}
