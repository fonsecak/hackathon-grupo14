package com.alfa.experience.service;

import com.alfa.experience.dao.EventoDao;
import com.alfa.experience.model.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoService {
    public static Evento buscarPorId(long pk) {
        var dao = new EventoDao();
        return (Evento) dao.select(pk);
    }

    public boolean salvar (Evento evento){
        var dao = new EventoDao();
        return dao.insert(evento);
    }

   public List<Evento> listarTodos() {
       var dao = new EventoDao();
       List<Evento> eventos = new ArrayList<>();
       dao.selectAll().forEach(
               objetc -> eventos.add((Evento)objetc));
       return eventos;
   }
}
