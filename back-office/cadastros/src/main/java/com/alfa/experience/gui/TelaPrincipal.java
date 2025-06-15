package com.alfa.experience.gui;

import com.alfa.experience.service.EventoService;
import com.alfa.experience.service.PalestranteService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaPrincipal extends JFrame {
    private final EventoService eventoService;
    private final PalestranteService palestranteService;
    //private final AlunoService alunoService;

    // Variáveis para controlar as instâncias das telas
    private GerEventoGui gerEventoGui;
    private GerPalestranteGui gerPalestranteGui;
    //private ListarAlunosGui listarAlunosGui;


    public TelaPrincipal(EventoService eventoService, PalestranteService palestranteService) {
        this.eventoService = eventoService;
        this.palestranteService = palestranteService;
        //this.alunoService = alunoService;
        montarTelaInicial();
    }

    private void montarTelaInicial() {
        var guiUtils = new GuiUtils();
        guiUtils.montarTelaPadrao(this, "Sistema AlfaExperience", 800, 600);

        // Adicionar MenuBar
        setJMenuBar(guiUtils.criarMenuBar(this, this));

        // Logo
        JLabel jlLogo = new JLabel();
        java.net.URL logoURL = getClass().getResource("/img/logo.png");
        ImageIcon logo = new ImageIcon(logoURL);
        Image imagem = logo.getImage().getScaledInstance(500, 350, Image.SCALE_SMOOTH);
        jlLogo.setIcon(new ImageIcon(imagem));

        // Painel principal com logo
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(GuiUtils.COR_FUNDO_TELA);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;

        painelPrincipal.add(jlLogo, gbc);
        add(painelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void abrirGerEventoGui() {
        if (gerEventoGui == null || !gerEventoGui.isVisible()) {
            gerEventoGui = new GerEventoGui(eventoService, palestranteService, this);
            configurarFechamento(gerEventoGui, () -> gerEventoGui = null);
            gerEventoGui.setVisible(true);
        } else {
            gerEventoGui.toFront();
            gerEventoGui.requestFocus();
        }
    }

    public void abrirGerPalestranteGui() {
        if (gerPalestranteGui == null || !gerPalestranteGui.isVisible()) {
            gerPalestranteGui = new GerPalestranteGui(palestranteService, this);
            configurarFechamento(gerPalestranteGui, () -> gerPalestranteGui = null);
            gerPalestranteGui.setVisible(true);
        } else {
            gerPalestranteGui.toFront();
            gerPalestranteGui.requestFocus();
        }
    }

//    private void abrirListarAlunosGui() {
//        if (listarAlunosGui == null || !listarAlunosGui.isVisible()) {
//            listarAlunosGui = new ListarAlunosGui(alunoService);
//            configurarFechamento(listarAlunosGui, () -> listarAlunosGui = null);
//            listarAlunosGui.setVisible(true);
//        } else {
//            listarAlunosGui.toFront();
//            listarAlunosGui.requestFocus();
//        }
//    }

    private void configurarFechamento(JFrame frame, Runnable onClose) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                onClose.run();
            }
        });
    }
}

