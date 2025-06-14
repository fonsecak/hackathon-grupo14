package com.alfa.experience.gui;

import com.alfa.experience.model.Palestrante;
import com.alfa.experience.service.PalestranteService;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GerPalestranteGui extends JFrame {

    private final PalestranteService palestranteService;

    private JLabel jlId;
    private JTextField tfId;
    private JLabel jlNome;
    private JTextField tfNome;
    private JLabel jlSobre;
    private JTextField tfSobre;
    private JLabel jlFoto;
    private JTextField tfFoto;
    private JButton btSelecionarFoto;

    private JButton btConfirmar;
    private JButton btExcluir;
    private JButton btAtualizar;
    private JButton btLimpar;
    private JTable tbPalestrantes;

    private File imagemSelecionada;

    private static final String FOTOS_DIR = "../../resources/img/";

    public GerPalestranteGui(PalestranteService palestranteService) {
        this.palestranteService = palestranteService;
        criarDiretorioFotos();
        mostrarTela();
    }

    private void criarDiretorioFotos() {
        File dir = new File(FOTOS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void mostrarTela() {
        var guiUtils = new GuiUtils();
        guiUtils.montarTelaPadrao(this, "Gerenciamento de Palestrantes - AlfaExperience", 600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel titulo = new JLabel("Gerenciamento de Palestrantes", SwingConstants.CENTER);
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

        jlId = guiUtils.criarLabel("ID");
        tfId = guiUtils.criarTextField(20);
        tfId.setEditable(false);
        jlNome = guiUtils.criarLabel("Nome");
        tfNome = guiUtils.criarTextField(20);
        jlSobre = guiUtils.criarLabel("Sobre");
        tfSobre = guiUtils.criarTextField(20);
        jlFoto = guiUtils.criarLabel("Foto");
        tfFoto = guiUtils.criarTextField(20);
        tfFoto.setEditable(false);
        btSelecionarFoto = guiUtils.criarBotao("Selecionar");
        btSelecionarFoto.addActionListener(this::selecionarImagem);

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
        jPanel.add(jlNome, guiUtils.montarConstraints(0, 1));
        jPanel.add(tfNome, guiUtils.montarConstraints(1, 1));
        jPanel.add(jlSobre, guiUtils.montarConstraints(0, 2));
        jPanel.add(tfSobre, guiUtils.montarConstraints(1, 2));
        jPanel.add(jlFoto, guiUtils.montarConstraints(0, 3));
        jPanel.add(tfFoto, guiUtils.montarConstraints(1, 3));
        jPanel.add(btSelecionarFoto, guiUtils.montarConstraints(2, 3));
        jPanel.add(btConfirmar, guiUtils.montarConstraints(0, 4));
        jPanel.add(btExcluir, guiUtils.montarConstraints(1, 4));
        jPanel.add(btAtualizar, guiUtils.montarConstraints(2, 4));
        jPanel.add(btLimpar, guiUtils.montarConstraints(3, 4));

        return jPanel;
    }

    private void selecionarImagem(ActionEvent e) {
        var guiUtils = new GuiUtils();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png"));
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagemSelecionada = chooser.getSelectedFile();
            String extension = imagemSelecionada.getName().substring(imagemSelecionada.getName().lastIndexOf("."));
            String newFileName = "foto_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm_ss")) + extension;
            tfFoto.setText(newFileName);
            guiUtils.exibirMensagem(this, "Imagem selecionada! Será salva ao confirmar o palestrante.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void confirmar(ActionEvent e) {
        salvarPalestrante();
    }

    private void atualizar(ActionEvent e) {
        if (tfId.getText().isEmpty()) {
            var guiUtils = new GuiUtils();
            guiUtils.exibirMensagem(this, "Selecione um palestrante para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        salvarPalestrante();
    }

    private void excluir(ActionEvent e) {
        var guiUtils = new GuiUtils();
        int row = tbPalestrantes.getSelectedRow();
        if (row != -1) {
            Long pk = (Long) tbPalestrantes.getValueAt(row, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Deseja excluir o palestrante '" + tbPalestrantes.getValueAt(row, 1) + "'?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Palestrante palestrante = palestranteService.buscarPorId(pk);
                    if (palestrante.getFoto() != null) {
                        Files.deleteIfExists(Paths.get(FOTOS_DIR, palestrante.getFoto()));
                    }
                    palestranteService.excluir(pk);
                    guiUtils.exibirMensagem(this, "Palestrante excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limparCampos();
                    tbPalestrantes.setModel(carregarPalestrantes());
                } catch (IOException ex) {
                    guiUtils.exibirMensagem(this, "Erro ao excluir palestrante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            guiUtils.exibirMensagem(this, "Selecione um palestrante para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarPalestrante() {
        var guiUtils = new GuiUtils();

        String fotoName = tfFoto.getText().trim();
        if (imagemSelecionada != null && !fotoName.isEmpty()) {
            try {
                Path destPath = Paths.get(FOTOS_DIR, fotoName);
                Files.copy(imagemSelecionada.toPath(), destPath);
                imagemSelecionada = null;
            } catch (Exception ex) {
                guiUtils.exibirMensagem(this, "Erro ao salvar imagem: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        var palestrante = new Palestrante();
        try {
            palestrante.setId(tfId.getText().isEmpty() ? null : Long.parseLong(tfId.getText()));
            palestrante.setNome(tfNome.getText().trim());
            palestrante.setSobre(tfSobre.getText().trim());
            palestrante.setFoto(tfFoto.getText().trim());
        } catch (IllegalArgumentException ex) {
            guiUtils.exibirMensagem(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            palestranteService.salvar(palestrante);
            guiUtils.exibirMensagem(this, "Palestrante salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
            tbPalestrantes.setModel(carregarPalestrantes());
        } catch (Exception ex) {
            guiUtils.exibirMensagem(this, "Erro ao salvar palestrante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        JTextField[] campos = {tfId, tfNome, tfSobre, tfFoto};
        for (var campo : campos) {
            campo.setText("");
        }
        imagemSelecionada = null;
    }

    private JScrollPane montarTabelaDados() {
        tbPalestrantes = new JTable();
        tbPalestrantes.setDefaultEditor(Object.class, null);
        tbPalestrantes.getSelectionModel().addListSelectionListener(this::selecionar);
        tbPalestrantes.setModel(carregarPalestrantes());
        return new JScrollPane(tbPalestrantes);
    }

    private DefaultTableModel carregarPalestrantes() {
        var guiUtils = new GuiUtils();
        var tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Nome");
        try {
            palestranteService.listarTodos().forEach(p ->
                    tableModel.addRow(new Object[]{p.getId(), p.getNome()}));
        } catch (Exception e) {
            guiUtils.exibirMensagem(this, "Erro ao carregar palestrantes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return tableModel;
    }

    private void selecionar(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int row = tbPalestrantes.getSelectedRow();
            if (row != -1) {
                var guiUtils = new GuiUtils();
                try {
                    Long id = (Long) tbPalestrantes.getValueAt(row, 0);
                    Palestrante palestrante = palestranteService.buscarPorId(id);
                    tfId.setText(String.valueOf(palestrante.getId()));
                    tfNome.setText(palestrante.getNome());
                    tfSobre.setText(palestrante.getSobre() != null ? palestrante.getSobre() : "");
                    tfFoto.setText(palestrante.getFoto() != null ? palestrante.getFoto() : "");
                    imagemSelecionada = null;
                } catch (Exception ex) {
                    guiUtils.exibirMensagem(this, "Erro ao carregar palestrante: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}