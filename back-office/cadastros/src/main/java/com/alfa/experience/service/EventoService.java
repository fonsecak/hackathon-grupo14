package com.alfa.experience.service;

import com.alfa.experience.dao.EventoDao;
import com.alfa.experience.model.Evento;

import java.util.ArrayList;
import java.util.List;

public class EventoService {
    public boolean salvar (Evento evento){
        var dao = new EventoDao();
        return dao.insert(evento);
    }

//    public List<Evento> listarTodos() {
//        var dao = new AlunoDao();
//        List<Evento> alunos = new ArrayList<>();
//        dao.selectAll().forEach(
//                objetc -> Evento.add((Evento)objetc));
//        return alunos;
//    }
}
