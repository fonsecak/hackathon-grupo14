package com.alfa.experience.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class TelaGerEventos extends JFrame {
    private JLabel jlId;
    private JTextField tfId;
    private JLabel jlNome;
    private JTextField tfNome;
    private JLabel jlDtInicio;
    private JFormattedTextField tfDtInicio;
    private JLabel jlDtFim;
    private JFormattedTextField tfDtFim;
    private JLabel jlLocal;
    private JTextField tfLocal;
    private JLabel jlValorIncricao;
    private JTextField tfValorIncricao;
    private JLabel jlPublicoAlvo;
    private JTextField tfPublicoAlvo;
    private JLabel jlObjetivo;
    private JTextField tfObjetivo;
    private JLabel jlBanner;
    private JTextField tfBanner;
    private JLabel jlPalestrante;
    private JTextField tfPalestrante;
    private JLabel jlEspecialidade;
    private JTextField tfEspecialidade;
    private JLabel jlVagasMaximas;
    private JTextField tfVagasMaximas;
    private JLabel jlCreated_at;
    private JTextField tfCreated_at;
    private JLabel jlUpdated_at;
    private JTextField tfUpdated_at;


    public void GerEventos(){
        var guiUtils = new GuiUtils();
        guiUtils.montarTelaPadrao(this, "Gerenciamento de Eventos", 600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        //
        add(montarCampos());
    }

    private JPanel montarCampos() {
        var jPanel = new JPanel(new GridBagLayout());
        var guiUtils = new GuiUtils();

        jlId = new JLabel("ID");
        tfId = new JTextField(20);
        tfId.setEditable(false);
        jlNome = new JLabel("Nome");
        tfNome = new JTextField(20);
        jlDtInicio = new JLabel("Data de inicio");
        tfDtInicio = criarCampoData();
        jlDtFim = new JLabel("Data Fim");
        tfDtFim = criarCampoData();
        jlLocal = new JLabel("Local");
        tfLocal = new JTextField(20);
        jlValorIncricao = new JLabel("Valor Incrição");
        tfValorIncricao = new JTextField(20);
        jlPublicoAlvo = new JLabel("Publico Alvo");
        tfPublicoAlvo = new JTextField(20);
        jlObjetivo = new JLabel("Objetivo");
        tfObjetivo = new JTextField(20);
        jlBanner = new JLabel("Banner");
        tfBanner = new JTextField(20);
        jlPalestrante = new JLabel("Palestrante");
        tfPalestrante = new JTextField(20);
        jlEspecialidade = new JLabel("Especialidade");
        tfEspecialidade = new JTextField(20);
        jlVagasMaximas = new JLabel("Vagas Maximas");
        tfVagasMaximas = new JTextField(20);
        jlCreated_at = new JLabel("Created_at");
        tfCreated_at = new JTextField(20);
        tfCreated_at.setEditable(false);
        jlUpdated_at = new JLabel("Updated_at");
        tfUpdated_at = new JTextField(20);
        tfUpdated_at.setEditable(false);

        jPanel.add(jlId, guiUtils.montarConstraints(0, 0));
        jPanel.add(tfId, guiUtils.montarConstraints(1, 0));

        jPanel.add(jlNome, guiUtils.montarConstraints(0, 1));
        jPanel.add(tfNome, guiUtils.montarConstraints(1, 1));


        return jPanel;
    }

    public static JFormattedTextField criarCampoData() {
        try {
            MaskFormatter dataFormatter = new MaskFormatter("##/##/####");
            dataFormatter.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(dataFormatter);
            return campo;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }
}
