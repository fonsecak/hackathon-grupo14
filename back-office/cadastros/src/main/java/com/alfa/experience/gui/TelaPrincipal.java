package com.alfa.experience.gui;

import com.alfa.experience.service.EventoService;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private final EventoService eventoService;
    private JButton btGerEventos, btListarAlunos;

    public TelaPrincipal(EventoService eventoService) {
        this.eventoService = eventoService;
        var guiUtils = new GuiUtils();
        guiUtils.montarTelaPadrao(this, "Menu Principal - AlfaExperience", 600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("Menu Principal - AlfaExperience", SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);
        add(montarTelaInicial(), BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel montarTelaInicial() {
        var jPanel = new JPanel(new GridBagLayout());
        var guiUtils = new GuiUtils();

        btGerEventos = new JButton("Gerenciar Eventos");
        btListarAlunos = new JButton("Listar Alunos por Evento");

        btGerEventos.addActionListener(e -> new GerEventoGui(eventoService));
        btListarAlunos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento."));

        jPanel.add(btGerEventos, guiUtils.montarConstraints(0, 0));
        jPanel.add(btListarAlunos, guiUtils.montarConstraints(0, 1));

        return jPanel;
    }
}

