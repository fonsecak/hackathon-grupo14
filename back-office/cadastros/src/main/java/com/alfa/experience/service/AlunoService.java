package com.alfa.experience.service;

import com.alfa.experience.dao.AlunoDao;
import com.alfa.experience.model.Aluno;
import java.util.List;
import java.util.Map;

public class AlunoService {

    private final AlunoDao dao;

    public AlunoService(AlunoDao dao) {
        this.dao = dao;
    }

    public List<Aluno> listarAlunosPorEvento(int idEvento) {
        return dao.listarAlunosPorEvento(idEvento);
    }

    public boolean atualizarPresenca(int idInscricao, Boolean presenca) {
        return dao.atualizarPresencaInscricao(idInscricao, presenca);
    }

    public int getIdInscricao(int idAluno, int idEvento) {
        return dao.getIdInscricao(idAluno, idEvento);
    }

    public Map<Integer, String> listarEventos() {
        return dao.listarEventos();
    }
}