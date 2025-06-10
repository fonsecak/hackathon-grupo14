package com.alfa.experience.gui;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {
    private JButton btGerEventos, btListarAlunos;

    public TelaPrincipal(){
        var guiUtils = new  GuiUtils();
        // Configura a própria TelaPrincipal como o JFrame utilizando THIS
        guiUtils.montarTelaPadrao(this,"Gerenciar Eventos",600,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(montarTelaInicial(),BorderLayout.NORTH);
    }

    public JPanel montarTelaInicial(){
        var jPanel = new JPanel(new GridBagLayout());
        var guiUtils = new  GuiUtils();

        btGerEventos = new JButton("Gerenciar Eventos");
        btListarAlunos = new JButton("Listar Alunos por Evento");

        jPanel.add(btGerEventos, guiUtils.montarConstraints(0,1));
        jPanel.add(btListarAlunos, guiUtils.montarConstraints(0,2));

        btGerEventos.addActionListener(e -> new TelaGerEventos().GerEventos());
        return jPanel;
    }

}
