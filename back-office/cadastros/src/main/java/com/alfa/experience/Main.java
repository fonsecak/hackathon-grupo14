package com.alfa.experience;

import com.alfa.experience.dao.AlunoDao;
import com.alfa.experience.dao.EventoDao;
import com.alfa.experience.dao.PalestranteDao;
import com.alfa.experience.gui.TelaPrincipal;
import com.alfa.experience.service.AlunoService;
import com.alfa.experience.service.EventoService;
import com.alfa.experience.service.PalestranteService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::iniciar);
    }

    private static void iniciar() {
        var telaPrincipal = new TelaPrincipal(new EventoService(new EventoDao()), new PalestranteService(new PalestranteDao()), new AlunoService (new AlunoDao()));
        telaPrincipal.setVisible(true);
    }
}