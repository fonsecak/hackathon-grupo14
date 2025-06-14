package com.alfa.experience.gui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class GuiUtils {

    public GridBagConstraints montarConstraints(int x, int y) {
        return montarConstraints(x, y, 1, 0.0, GridBagConstraints.WEST);
    }

    public GridBagConstraints montarConstraints(int x, int y, int gridwidth, double weightx, int anchor) {
        var constraint = new GridBagConstraints();
        constraint.insets = new Insets(5, 5, 5, 5);
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = gridwidth;
        constraint.weightx = weightx;
        constraint.anchor = anchor;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        return constraint;
    }

    public void montarTelaPadrao(JFrame frame, String titulo, Integer largura, Integer altura) {
        frame.setTitle(titulo);
        frame.setSize(largura, altura);
        frame.setLocationRelativeTo(null);
        frame.setBackground(Color.WHITE);
    }

    public JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    public JTextField criarTextField(int colunas) {
        JTextField textField = new JTextField(colunas);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return textField;
    }

    public JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 14));
        botao.setBackground(new Color(50, 150, 50));
        botao.setForeground(Color.WHITE);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        return botao;
    }

    public JFormattedTextField criarCampoData() {
        try {
            MaskFormatter dataFormatter = new MaskFormatter("##/##/#### ##:##");
            dataFormatter.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(dataFormatter);
            campo.setFont(new Font("Arial", Font.PLAIN, 14));
            return campo;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    public static void exibirMensagem(JFrame parent, String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(parent, mensagem, titulo, tipo);
    }

    public void aplicarTema() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}