package com.alfa.experience.gui;

import com.alfa.experience.model.Evento;
import com.alfa.experience.model.Palestrante;
import com.alfa.experience.service.EventoService;
import com.alfa.experience.service.PalestranteService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GerEventoGui extends JFrame {

    private final EventoService eventoService;
    private final PalestranteService palestranteService;

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
    private JLabel jlValorInscricao;
    private JTextField tfValorInscricao;
    private JLabel jlPublicoAlvo;
    private JComboBox<String> cbPublicoAlvo;
    private JLabel jlObjetivo;
    private JTextField tfObjetivo;
    private JLabel jlBanner;
    private JTextField tfBanner;
    private JLabel jlPalestrante;
    private JComboBox<Palestrante> cbPalestrante;
    private JLabel jlVagasMaximas;
    private JTextField tfVagasMaximas;
    private JLabel jlDescricao;
    private JTextField tfDescricao;
    private JButton btSelecionarBanner;
    private JButton btConfirmar;
    private JButton btExcluir;
    private JButton btAtualizar;
    private JButton btLimpar;
    private JTable tbEventos;

    private File imagemSelecionada; // Armazena o arquivo temporariamente

    private static final String[] PUBLICOS_ALVO = {"Todos","Administração", "Ciências Contábeis", "Direito", "Sistemas p/ Internet", "Pedagogia", "Psicologia"};
    private static final String BANNERS_DIR = "../../resources/img";  //Pasta onde é salva as imagens

    public GerEventoGui(EventoService eventoService, PalestranteService palestranteService) {
        if (eventoService == null || palestranteService == null) {
            JOptionPane.showMessageDialog(null, "Erro: Serviços não inicializados.", "Erro", JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("EventoService ou PalestranteService não podem ser nulos.");
        }
        this.eventoService = eventoService;
        this.palestranteService = palestranteService;
        criarDiretorioBanners();
        mostrarTela();
    }

    private void criarDiretorioBanners() {
        File dir = new File(BANNERS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
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

        // Carregar palestrantes após construir a interface
        try {
            carregarPalestrantesComboBox();
        } catch (Exception e) {
            var guiUtils2 = new GuiUtils();
            guiUtils2.exibirMensagem(this, "Erro ao carregar palestrantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }


        setVisible(true);
    }

    private JPanel montarCampos() {
        var jPanel = new JPanel(new GridBagLayout());
        var guiUtils = new GuiUtils();

        jlId = guiUtils.criarLabel("ID");
        tfId = new JTextField(20);
        tfId.setEditable(false);
        jlNome = guiUtils.criarLabel("Título");
        tfNome = new JTextField(20);
        jlDtInicio = guiUtils.criarLabel("Data de Início");
        tfDtInicio = criarCampoData();
        jlDtFim = guiUtils.criarLabel("Data Fim");
        tfDtFim = criarCampoData();
        jlLocal = guiUtils.criarLabel("Local");
        tfLocal = new JTextField(20);
        jlValorInscricao = guiUtils.criarLabel("Valor Inscrição");
        tfValorInscricao = new JTextField(20);
        jlPublicoAlvo = guiUtils.criarLabel("Público Alvo");
        cbPublicoAlvo = new JComboBox<>(PUBLICOS_ALVO);
        jlObjetivo = guiUtils.criarLabel("Objetivo");
        tfObjetivo = new JTextField(20);
        jlBanner = guiUtils.criarLabel("Banner");
        tfBanner = new JTextField(20);
        tfBanner.setEditable(false);
        btSelecionarBanner = guiUtils.criarBotao("Selecionar");
        btSelecionarBanner.addActionListener(this::selecionarImagem);
        jlPalestrante = guiUtils.criarLabel("Palestrante");
        cbPalestrante = new JComboBox<>();
        jlVagasMaximas = guiUtils.criarLabel("Vagas Máximas");
        tfVagasMaximas = new JTextField(20);
        jlDescricao = guiUtils.criarLabel("Descrição"); // Inicializado
        tfDescricao = new JTextField(20);

        btConfirmar = guiUtils.criarBotao("Confirmar");
        btConfirmar.addActionListener(this::confirmar);
        btExcluir = guiUtils.criarBotao("Excluir");
        btExcluir.addActionListener(this::excluir);
        btAtualizar = guiUtils.criarBotao("Atualizar");
        btAtualizar.addActionListener(this::atualizar);
        btLimpar = guiUtils.criarBotao("Limpar");
        btLimpar.addActionListener(e -> limparCampos());

        try {
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
            jPanel.add(jlValorInscricao, guiUtils.montarConstraints(2, 2));
            jPanel.add(tfValorInscricao, guiUtils.montarConstraints(3, 2));
            jPanel.add(jlPublicoAlvo, guiUtils.montarConstraints(0, 3));
            jPanel.add(cbPublicoAlvo, guiUtils.montarConstraints(1, 3));
            jPanel.add(jlDescricao, guiUtils.montarConstraints(2, 3));
            jPanel.add(tfDescricao, guiUtils.montarConstraints(3, 3));
            jPanel.add(jlBanner, guiUtils.montarConstraints(0, 4));
            jPanel.add(tfBanner, guiUtils.montarConstraints(1, 4));
            jPanel.add(btSelecionarBanner, guiUtils.montarConstraints(2, 4));
            jPanel.add(jlPalestrante, guiUtils.montarConstraints(0, 5));
            jPanel.add(cbPalestrante, guiUtils.montarConstraints(1, 5));
            jPanel.add(jlVagasMaximas, guiUtils.montarConstraints(2, 5));
            jPanel.add(tfVagasMaximas, guiUtils.montarConstraints(3, 5));
            jPanel.add(btConfirmar, guiUtils.montarConstraints(0, 6));
            jPanel.add(btExcluir, guiUtils.montarConstraints(1, 6));
            jPanel.add(btAtualizar, guiUtils.montarConstraints(2, 6));
            jPanel.add(btLimpar, guiUtils.montarConstraints(3, 6));

        } catch (Exception e) {
            System.err.println("Erro ao adicionar componentes em montarCampos: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Falha ao montar campos: " + e.getMessage(), e);
        }
        return jPanel;
    }

    private void carregarPalestrantesComboBox() {
        cbPalestrante.removeAllItems();
        cbPalestrante.addItem(null); // Opção para "Nenhum palestrante"
        try {
            for (Palestrante p : palestranteService.listarTodos()) {
                cbPalestrante.addItem(p);
            }
            cbPalestrante.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value == null) {
                        setText("Selecione um palestrante");
                    } else {
                        setText(((Palestrante) value).getNome());
                    }
                    return this;
                }
            });
        } catch (Exception e) {
            var guiUtils = new GuiUtils();
            guiUtils.exibirMensagem(this, "Erro ao carregar palestrantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void selecionarImagem(ActionEvent e) {
        var guiUtils = new GuiUtils();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagemSelecionada = chooser.getSelectedFile();
            // Gerar nome previsto
            String extension = imagemSelecionada.getName().substring(imagemSelecionada.getName().lastIndexOf("."));
            String newFileName = "banner_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm_ss")) + extension;
            tfBanner.setText(newFileName); // Apenas atualiza o campo
            guiUtils.exibirMensagem(this, "Imagem selecionada! Será salva ao confirmar o evento.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
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

        // Copiar imagem, se selecionada
        String bannerName = tfBanner.getText().trim();
        if (imagemSelecionada != null && !bannerName.isEmpty()) {
            try {
                Path destPath = Paths.get(BANNERS_DIR, bannerName);
                Files.copy(imagemSelecionada.toPath(), destPath);
                imagemSelecionada = null;
            } catch (Exception ex) {
                guiUtils.exibirMensagem(this, "Erro ao salvar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        var evento = new Evento();
        try {
            evento.setId(tfId.getText().isEmpty() ? null : Long.parseLong(tfId.getText()));
            evento.setNome(tfNome.getText().trim());
            evento.setDescricao(tfDescricao.getText().trim());//trim() remove espaços em branco no começo e no final da string
            evento.setDtInicio(inicio);
            evento.setDtFim(fim);
            evento.setLocal(tfLocal.getText().trim());
            evento.setValorInscricao(tfValorInscricao.getText().trim());
            evento.setPublicoAlvo((String) cbPublicoAlvo.getSelectedItem());
            evento.setObjetivo(tfObjetivo.getText().trim());
            evento.setBanner(tfBanner.getText().trim());
            Palestrante palestrante = (Palestrante) cbPalestrante.getSelectedItem();
            evento.setIdPalestrantes(palestrante != null ? palestrante.getId() : null);
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
                    Evento evento = eventoService.buscarPorId(pk);
                    if (evento.getBanner() != null) {
                        Files.deleteIfExists(Paths.get(BANNERS_DIR, evento.getBanner()));
                    }
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
        tfValorInscricao.setText(null);
        tfObjetivo.setText(null);
        tfBanner.setText(null);
        tfDescricao.setText(null);
        tfVagasMaximas.setText(null);
        imagemSelecionada = null;
        cbPalestrante.setSelectedIndex(0);
        cbPublicoAlvo.setSelectedIndex(0);
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
            for (Evento e : eventoService.listarTodos()) {
                String palestranteNome = "";
                if (e.getIdPalestrantes() != null) {
                    Palestrante p = palestranteService.buscarPorId(e.getIdPalestrantes());
                    palestranteNome = p != null ? p.getNome() : "";
                }
                tableModel.addRow(new Object[]{e.getId(), e.getNome(), formatarTimestamp(e.getDtInicio()), formatarTimestamp(e.getDtFim()), palestranteNome, e.getVagasMaximas()});
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar eventos: " + e.getMessage());
        }
        return tableModel;
    }

    private void selecionar(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int linha = tbEventos.getSelectedRow();
            if (linha != -1) {
                var guiUtils = new GuiUtils();
                try {
                    Long id = (Long) tbEventos.getValueAt(linha, 0);
                    Evento evento = EventoService.buscarPorId(id);
                    tfId.setText(String.valueOf(evento.getId()));
                    tfNome.setText(evento.getNome());
                    tfDtInicio.setText(formatarTimestamp(evento.getDtInicio()));
                    tfDtFim.setText(formatarTimestamp(evento.getDtFim()));
                    tfLocal.setText(evento.getLocal());
                    tfValorInscricao.setText(evento.getValorInscricao());
                    cbPublicoAlvo.setSelectedItem(evento.getPublicoAlvo() != null ? evento.getPublicoAlvo() : PUBLICOS_ALVO[0]);
                    tfDescricao.setText(evento.getDescricao());
                    tfObjetivo.setText(evento.getObjetivo());
                    tfBanner.setText(evento.getBanner());
                    tfVagasMaximas.setText(String.valueOf(evento.getVagasMaximas()));

                    if (evento.getIdPalestrantes() != null) {
                        Palestrante palestrante = palestranteService.buscarPorId(evento.getIdPalestrantes());
                        System.out.println("Palestrante encontrado: " + (palestrante != null ? palestrante.getNome() : "null"));
                        if (palestrante != null) {
                            cbPalestrante.setSelectedItem(palestrante);
                            if (cbPalestrante.getSelectedItem() == null) {
                                System.out.println("Palestrante não encontrado no JComboBox para ID: " + evento.getIdPalestrantes());
                            }
                        } else {
                            cbPalestrante.setSelectedIndex(0);
                            System.out.println("Palestrante com ID " + evento.getIdPalestrantes() + " não existe.");
                        }
                    } else {
                        cbPalestrante.setSelectedIndex(0);
                        System.out.println("Evento sem palestrante associado.");
                    }
                    imagemSelecionada = null;
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
