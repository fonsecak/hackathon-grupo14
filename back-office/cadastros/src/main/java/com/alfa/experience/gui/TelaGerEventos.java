package com.alfa.experience.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaGerEventos extends JFrame {
    private JFrame frame;
    private JTable table;

    private DefaultTableModel tableModel;
    private GuiUtils guiUtils;

    public void GerEventos(){
        guiUtils = new GuiUtils();
        guiUtils.montarTelaPadrao(this, "Gerenciamento de Eventos", 600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setVisible(true);
    }
}
