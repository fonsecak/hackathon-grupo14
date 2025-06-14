package com.alfa.experience.service;

import com.alfa.experience.dao.EventoDao;
import com.alfa.experience.model.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoService {
    private final EventoDao dao;

    public EventoService(EventoDao dao) {
        this.dao = dao;
    }

    public static Evento buscarPorId(long pk) {
        var dao = new EventoDao();
        return (Evento) dao.select(pk);
    }

    public void salvar (Evento evento){
        if (evento.getId() == null) {
            dao.insert(evento);
        } else {
            dao.update(evento);
        }
    }

    public void excluir(long pk){
        dao.delete(pk);
    }

   public List<Evento> listarTodos() {
       var dao = new EventoDao();
       List<Evento> eventos = new ArrayList<>();
       dao.selectAll().forEach(
               objetc -> eventos.add((Evento)objetc));
       return eventos;
   }
}
