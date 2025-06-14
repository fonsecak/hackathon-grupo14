package com.alfa.experience.gui;

import com.alfa.experience.service.EventoService;
import com.alfa.experience.service.PalestranteService;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private final EventoService eventoService;
    private final PalestranteService palestranteService;

    private JButton btGerEventos, btListarAlunos, btGerPalestrantes;

    public TelaPrincipal(EventoService eventoService, PalestranteService palestranteService) {
        this.eventoService = eventoService;
        this.palestranteService = palestranteService;

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
        jPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margens

        btGerEventos = guiUtils.criarBotao("Gerenciar Eventos");
        btGerPalestrantes = guiUtils.criarBotao("Gerenciar Palestrantes");
        btListarAlunos = guiUtils.criarBotao("Listar Alunos por Evento");

        btGerEventos.addActionListener(e -> new GerEventoGui(eventoService, palestranteService));
        btGerPalestrantes.addActionListener(e -> new GerPalestranteGui(palestranteService));
        btListarAlunos.addActionListener(e -> JOptionPane.showMessageDialog(this, "Funcionalidade em desenvolvimento."));

        GridBagConstraints gbc = guiUtils.montarConstraints(0, 0);
        gbc.insets = new Insets(10, 0, 10, 0); // Espaçamento vertical
        jPanel.add(btGerPalestrantes, gbc);

        gbc = guiUtils.montarConstraints(0, 1);
        gbc.insets = new Insets(10, 0, 10, 0);
        jPanel.add(btGerEventos, gbc);

        gbc = guiUtils.montarConstraints(0, 2);
        gbc.insets = new Insets(10, 0, 10, 0);
        jPanel.add(btListarAlunos, gbc);

        return jPanel;
    }
}

