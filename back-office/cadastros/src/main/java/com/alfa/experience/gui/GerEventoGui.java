package com.alfa.experience.gui;

import com.alfa.experience.model.Evento;
import com.alfa.experience.service.EventoService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GerEventoGui extends JFrame {

    private final EventoService eventoService;

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
    private JComboBox<String> cbPublicoAlvo;
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
    private JButton btExcluir;
    private JButton btAtualizar;
    private JButton btLimpar;
    private JTable tbEventos;

    private static final String[] PUBLICOS_ALVO = {"Todos","Administração", "Ciências Contábeis", "Direito", "Sistemas p/ Internet", "Pedagogia", "Psicologia"};

    public GerEventoGui(EventoService eventoService) {
        this.eventoService = eventoService;
        mostrarTela();
    }

    private void mostrarTela() {
        var guiUtils = new GuiUtils();
        guiUtils.montarTelaPadrao(this, "Gerenciamento de Eventos - AlfaExperience", 800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titulo = new JLabel("Gerenciamento de Eventos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        JPanel painelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelTitulo.add(titulo);
        add(painelTitulo, BorderLayout.NORTH);
        add(montarCampos(), BorderLayout.CENTER);
        add(montarTabelaDados(), BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel montarCampos() {
        var jPanel = new JPanel(new GridBagLayout());
        var guiUtils = new GuiUtils();

        jlId = new JLabel("ID");
        tfId = new JTextField(20);
        tfId.setEditable(false);
        jlNome = new JLabel("Titulo");
        tfNome = new JTextField(20);
        jlDtInicio = new JLabel("Data de inicio");
        tfDtInicio = criarCampoData();
        jlDtFim = new JLabel("Data Fim");
        tfDtFim = criarCampoData();
        jlLocal = new JLabel("Local");
        tfLocal = new JTextField(20);
        jlValorIncricao = new JLabel("Valor Incrição");
        tfValorIncricao = new JTextField(20);
        jlPublicoAlvo = guiUtils.criarLabel("Público Alvo");
        cbPublicoAlvo = new JComboBox<>(PUBLICOS_ALVO);
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

        btConfirmar = guiUtils.criarBotao("Confirmar");
        btConfirmar.addActionListener(this::confirmar);
        btExcluir = guiUtils.criarBotao("Excluir");
        btExcluir.addActionListener(this::excluir);
        btAtualizar = guiUtils.criarBotao("Atualizar");
        btAtualizar.addActionListener(this::atualizar);
        btLimpar = guiUtils.criarBotao("Limpar");
        btLimpar.addActionListener(e -> limparCampos());

        jPanel.add(jlId, guiUtils.montarConstraints(0, 0));
        jPanel.add(tfId, guiUtils.montarConstraints(1, 0));

        jPanel.add(jlNome, guiUtils.montarConstraints(2, 0));
        jPanel.add(tfNome, guiUtils.montarConstraints(3, 0));

        jPanel.add(jlDtInicio, guiUtils.montarConstraints(0, 1));
        jPanel.add(tfDtInicio, guiUtils.montarConstraints(1, 1));

        jPanel.add(jlDtFim, guiUtils.montarConstraints(2, 1));
        jPanel.add(tfDtFim, guiUtils.montarConstraints(3, 1));

        jPanel.add(jlLocal, guiUtils.montarConstraints(0, 2));
        jPanel.add(tfLocal, guiUtils.montarConstraints(1, 2));

        jPanel.add(jlValorIncricao, guiUtils.montarConstraints(2, 2));
        jPanel.add(tfValorIncricao, guiUtils.montarConstraints(3, 2));

        jPanel.add(jlPublicoAlvo, guiUtils.montarConstraints(0, 3));
        jPanel.add(cbPublicoAlvo, guiUtils.montarConstraints(1, 3));

        jPanel.add(jlObjetivo, guiUtils.montarConstraints(2, 3));
        jPanel.add(tfObjetivo, guiUtils.montarConstraints(3, 3));

        jPanel.add(jlBanner, guiUtils.montarConstraints(0, 4));
        jPanel.add(tfBanner, guiUtils.montarConstraints(1, 4));

        jPanel.add(jlPalestrante, guiUtils.montarConstraints(2, 4));
        jPanel.add(tfPalestrante, guiUtils.montarConstraints(3, 4));

        jPanel.add(jlEspecialidade, guiUtils.montarConstraints(0, 5));
        jPanel.add(tfEspecialidade, guiUtils.montarConstraints(1, 5));

        jPanel.add(jlVagasMaximas, guiUtils.montarConstraints(2, 5));
        jPanel.add(tfVagasMaximas, guiUtils.montarConstraints(3, 5));

        jPanel.add(btConfirmar, guiUtils.montarConstraints(0, 6));
        jPanel.add(btExcluir, guiUtils.montarConstraints(1, 6));
        jPanel.add(btAtualizar, guiUtils.montarConstraints(2, 6));
        jPanel.add(btLimpar, guiUtils.montarConstraints(3, 6));

        return jPanel;
    }

    private void confirmar(ActionEvent e) {
        salvarEvento();
    }

    private void salvarEvento() {
        var guiUtils = new GuiUtils();

        if (tfNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome do evento é obrigatório.");
            return;
        }

        Timestamp inicio = obterTimestampValido(tfDtInicio);
        Timestamp fim = obterTimestampValido(tfDtFim);
        if (inicio == null || fim == null) {
            JOptionPane.showMessageDialog(this, "Datas inválidas.");
            return;
        }
        Integer vagasMaximas = null;
        try {
            if (!tfVagasMaximas.getText().isEmpty()) {
                vagasMaximas = Integer.valueOf(tfVagasMaximas.getText());
                if (vagasMaximas <= 0) {
                    JOptionPane.showMessageDialog(this, "Vagas máximas deve ser maior que zero.");
                    return;
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Vagas máximas deve ser um número válido.");
            return;
        }

        var evento = new Evento();
        try {
            evento.setId(tfId.getText().isEmpty() ? null : Long.parseLong(tfId.getText()));
            evento.setNome(tfNome.getText().trim());
            evento.setDtInicio(inicio);
            evento.setDtFim(fim);
            evento.setLocal(tfLocal.getText().trim());
            evento.setValorInscricao(tfValorIncricao.getText());
            evento.setPublicoAlvo((String) cbPublicoAlvo.getSelectedItem());
            evento.setObjetivo(tfObjetivo.getText().trim());
            evento.setBanner(tfBanner.getText().trim());
            evento.setPalestrante(tfPalestrante.getText().trim());
            evento.setEspecialidade(tfEspecialidade.getText().trim());
            evento.setVagasMaximas(vagasMaximas);
        } catch (IllegalArgumentException ex) {
            guiUtils.exibirMensagem(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            eventoService.salvar(evento);
            guiUtils.exibirMensagem(this, "Evento salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            tbEventos.setModel(carregarEventos());
        } catch (Exception e) {
            guiUtils.exibirMensagem(this, "Erro ao salvar evento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizar(ActionEvent e) {
        if (tfId.getText().isEmpty()) {
            GuiUtils.exibirMensagem(this, "Selecione um evento para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        salvarEvento();
    }

    private void excluir(ActionEvent e) {
        int row = tbEventos.getSelectedRow();
        if (row != -1) {
            Long pk = (Long) tbEventos.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir este evento?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    eventoService.excluir(pk);
                    GuiUtils.exibirMensagem(this, "Evento excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    tbEventos.setModel(carregarEventos());
                } catch (Exception ex) {
                    GuiUtils.exibirMensagem(this, "Erro ao excluir evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            GuiUtils.exibirMensagem(this, "Selecione um evento para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        tfId.setText(null);
        tfNome.setText(null);
        tfDtInicio.setText(null);
        tfDtFim.setText(null);
        tfLocal.setText(null);
        tfValorIncricao.setText(null);
        cbPublicoAlvo.setSelectedIndex(0);
        tfObjetivo.setText(null);
        tfBanner.setText(null);
        tfPalestrante.setText(null);
        tfEspecialidade.setText(null);
        tfVagasMaximas.setText(null);
    }

    private JScrollPane montarTabelaDados(){
        tbEventos = new JTable();
        tbEventos.setDefaultEditor(Object.class, null);
        tbEventos.getSelectionModel().addListSelectionListener(this::selecionar);
        tbEventos.setModel(carregarEventos());
        return new JScrollPane(tbEventos);
    }

    private DefaultTableModel carregarEventos() {
        var tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        tableModel.addColumn("Dt_Inicio");
        tableModel.addColumn("Dt_Fim");
        tableModel.addColumn("Palestrante");
        tableModel.addColumn("Max Incrições");
        try {
            eventoService.listarTodos().forEach(a ->
                    tableModel.addRow(new Object[]{a.getId(), a.getNome(), a.getDtInicio(), a.getDtFim(), a.getPalestrante(), a.getVagasMaximas()})
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar eventos: " + e.getMessage());
        }
        return tableModel;
    }

    private void selecionar(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int linha = tbEventos.getSelectedRow();
            if (linha != -1) {
                try {
                    Long id = (Long) tbEventos.getValueAt(linha, 0);
                    Evento evento = EventoService.buscarPorId(id);
                    tfId.setText(String.valueOf(evento.getId()));
                    tfNome.setText(evento.getNome());
                    tfDtInicio.setText(formatarTimestamp(evento.getDtInicio()));
                    tfDtFim.setText(formatarTimestamp(evento.getDtFim()));
                    tfLocal.setText(evento.getLocal());
                    tfValorIncricao.setText(evento.getValorInscricao());
                    cbPublicoAlvo.setSelectedItem(evento.getPublicoAlvo() != null ? evento.getPublicoAlvo() : PUBLICOS_ALVO[0]);
                    tfObjetivo.setText(evento.getObjetivo());
                    tfBanner.setText(evento.getBanner());
                    tfPalestrante.setText(evento.getPalestrante());
                    tfEspecialidade.setText(evento.getEspecialidade());
                    tfVagasMaximas.setText(String.valueOf(evento.getVagasMaximas()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao carregar evento: " + ex.getMessage());
                }
            }
        }
    }

    private String formatarTimestamp(Timestamp timestamp) {
        if (timestamp == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return timestamp.toLocalDateTime().format(formatter);
    }

    public static JFormattedTextField criarCampoData() {
        // Esse método serve para criar um campo formatado para o usuário digitar a data e a hora.
        // Eu uso o MaskFormatter para definir o formato "dd/MM/yyyy HH:mm", que obriga a digitação correta.
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

    public static Timestamp obterTimestampValido(JFormattedTextField campo) {
        // Esse método é responsável por pegar o valor digitado no campo formatado e converter para Timestamp.
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
