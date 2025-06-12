package com.alfa.experience.gui;

import com.alfa.experience.model.Evento;
import com.alfa.experience.service.EventoService;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GerEventoGui extends JFrame {
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

    private JButton btConfirmar;


    public void GerEventos(){
        var guiUtils = new GuiUtils();
        guiUtils.montarTelaPadrao(this, "Gerenciamento de Eventos", 600, 600);
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

        btConfirmar = new JButton("Confirmar");
        btConfirmar.addActionListener(this::confirmar);

        jPanel.add(jlId, guiUtils.montarConstraints(0, 0));
        jPanel.add(tfId, guiUtils.montarConstraints(1, 0));

        jPanel.add(jlNome, guiUtils.montarConstraints(0, 1));
        jPanel.add(tfNome, guiUtils.montarConstraints(1, 1));

        jPanel.add(jlDtInicio, guiUtils.montarConstraints(0, 2));
        jPanel.add(tfDtInicio, guiUtils.montarConstraints(1, 2));

        jPanel.add(jlDtFim, guiUtils.montarConstraints(0, 3));
        jPanel.add(tfDtFim, guiUtils.montarConstraints(1, 3));

        jPanel.add(jlLocal, guiUtils.montarConstraints(0, 4));
        jPanel.add(tfLocal, guiUtils.montarConstraints(1, 4));

        jPanel.add(jlValorIncricao, guiUtils.montarConstraints(0, 5));
        jPanel.add(tfValorIncricao, guiUtils.montarConstraints(1, 5));

        jPanel.add(jlPublicoAlvo, guiUtils.montarConstraints(0, 6));
        jPanel.add(tfPublicoAlvo, guiUtils.montarConstraints(1, 6));

        jPanel.add(jlObjetivo, guiUtils.montarConstraints(0, 7));
        jPanel.add(tfObjetivo, guiUtils.montarConstraints(1, 7));

        jPanel.add(jlBanner, guiUtils.montarConstraints(0, 8));
        jPanel.add(tfBanner, guiUtils.montarConstraints(1, 8));

        jPanel.add(jlPalestrante, guiUtils.montarConstraints(0, 9));
        jPanel.add(tfPalestrante, guiUtils.montarConstraints(1, 9));

        jPanel.add(jlEspecialidade, guiUtils.montarConstraints(0, 10));
        jPanel.add(tfEspecialidade, guiUtils.montarConstraints(1, 10));

        jPanel.add(jlPalestrante, guiUtils.montarConstraints(0, 10));
        jPanel.add(tfPalestrante, guiUtils.montarConstraints(1, 10));

        jPanel.add(jlVagasMaximas, guiUtils.montarConstraints(0, 11));
        jPanel.add(tfVagasMaximas, guiUtils.montarConstraints(1, 11));

        jPanel.add(jlPalestrante, guiUtils.montarConstraints(0, 12));
        jPanel.add(tfPalestrante, guiUtils.montarConstraints(1, 12));

        jPanel.add(btConfirmar, guiUtils.montarConstraints(1,15));

        return jPanel;
    }

    private void confirmar(ActionEvent event) {

        var servico = new EventoService();
        var evento = new Evento();


        evento.setId(tfId.getText().isEmpty() ? null : Long.valueOf(tfId.getText()));
        evento.setNome(tfNome.getText());
        Timestamp inicio = obterTimestampValido(tfDtInicio);
        evento.setDtInicio(String.valueOf(inicio));
        Timestamp fim = obterTimestampValido(tfDtFim);
        evento.setDtFim(String.valueOf(fim));
        evento.setLocal(tfLocal.getText());
        evento.setValorIncricao(tfValorIncricao.getText());
        evento.setPublicoAlvo(tfPublicoAlvo.getText());
        evento.setObjetivo(tfObjetivo.getText());
        evento.setBanner(tfBanner.getText());
        evento.setPalestrante(tfPalestrante.getText());
        evento.setEspecialidade(tfEspecialidade.getText());
        evento.setVagasMax(tfVagasMaximas.getText());

        servico.salvar(evento);
    }

    // Esse método serve para criar um campo formatado para o usuário digitar a data e a hora.
    // Eu uso o MaskFormatter para definir o formato "dd/MM/yyyy HH:mm", que obriga a digitação correta.
    public static JFormattedTextField criarCampoData() {
        try {
            MaskFormatter dataFormatter = new MaskFormatter("##/##/#### ##:##");
            dataFormatter.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(dataFormatter);
            return campo;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    // Esse método é responsável por pegar o valor digitado no campo formatado e converter para Timestamp.
    public static Timestamp obterTimestampValido(JFormattedTextField campo) {
        String texto = campo.getText().trim();
        // Aqui eu verifico se o campo ainda está com espaços em branco ou preenchido com valor inválido.
        if (texto.contains("_") || texto.equals("00/00/0000 00:00")) {
            return null;
        }

        try {
            // Eu uso o DateTimeFormatter para converter o texto do campo para um LocalDateTime,
            // usando o padrão que inclui data e hora.
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dataHora = LocalDateTime.parse(texto, formatter);

            // Por fim, converto o LocalDateTime para Timestamp e retorno.
            return Timestamp.valueOf(dataHora);
        } catch (DateTimeParseException e) {
            return null; // se a data for inválida, retorna null
        }
    }

}
