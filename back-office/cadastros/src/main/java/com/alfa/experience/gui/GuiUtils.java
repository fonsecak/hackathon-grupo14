package com.alfa.experience.gui;

import javax.swing.*;
import java.awt.*;

public class GuiUtils {

    public GridBagConstraints montarConstraints(int x, int y){
        var constraint = new GridBagConstraints();
        constraint.insets = new Insets(5,5,5,5);
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.fill = GridBagConstraints.HORIZONTAL; //Alinha o comprimento do botão como padrão
        return constraint;
    }

    public void montarTelaPadrao(JFrame frame,String titulo, Integer largura, Integer altura){
        frame.setTitle(titulo);
        frame.setSize(largura, altura);
        frame.setLocationRelativeTo(null);
    }
}