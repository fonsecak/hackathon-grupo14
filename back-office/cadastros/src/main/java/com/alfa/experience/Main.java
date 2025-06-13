package com.alfa.experience;

import com.alfa.experience.gui.TelaPrincipal;
import com.alfa.experience.service.EventoService;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::iniciar);
    }

    private static void iniciar() {
        var telaPrincipal = new TelaPrincipal(new EventoService());
        telaPrincipal.setVisible(true);
    }
}