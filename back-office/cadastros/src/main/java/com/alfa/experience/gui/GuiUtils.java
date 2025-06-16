package com.alfa.experience.gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class GuiUtils {

    public static final Color COR_PRIMARIA = new Color(11, 62, 134);
    public static final Color COR_TERCIARIA = new Color(63, 63, 63);
    public static final Color COR_SECUNDARIA = new Color(54, 136, 201);
    public static final Color COR_PERIGO = new Color(162, 36, 36);
    public static final Color COR_FUNDO_TELA = new Color(241, 241, 241, 255);
    public static final Color COR_CAMPO_TEXTO = Color.WHITE;
    public static final Color COR_BORDA_CAMPO = new Color(192, 192, 192);
    public static final Color COR_TEXTO_LABEL = new Color(33, 33, 33);
    public static final Color COR_CAMPO_DESABILITADO = new Color(157, 157, 157);
    public static final Color COR_MENU_FUNDO = new Color(13, 52, 80);
    public static final Color COR_MENU_TEXTO = Color.WHITE;
    public static final Color COR_BOTAO_SELECIONAR = new Color(119, 119, 119);

    // Fontes
    public static final Font FONTE_LABEL = new Font("Ubuntu", Font.PLAIN, 14);
    public static final Font FONTE_CAMPO = new Font("Ubuntu", Font.PLAIN, 16);
    public static final Font FONTE_BOTAO = new Font("Ubuntu", Font.BOLD, 16);
    public static final Font FONTE_MENU = new Font("Ubuntu", Font.PLAIN, 16);

    // Bordas
    private static final Border BORDA_CAMPO = BorderFactory.createLineBorder(COR_BORDA_CAMPO, 1, true);
    private static final Border BORDA_CAMPO_ATENCAO = BorderFactory.createLineBorder(COR_PERIGO, 1, true);
    private static final Border MARGEM_CAMPO = new EmptyBorder(8, 8, 8, 8);
    private static final Border BORDA_COMPOSTA = new CompoundBorder(BORDA_CAMPO, MARGEM_CAMPO);
    private static final Border BORDA_ATENCAO = new CompoundBorder(BORDA_CAMPO_ATENCAO, MARGEM_CAMPO);

    public void montarTelaPadrao(JFrame frame, String titulo, Integer largura, Integer altura) {
        frame.setTitle(titulo);
        frame.setSize(largura, altura);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(COR_FUNDO_TELA);
        aplicarIcone(frame);
        aplicarTema();
    }

    public GridBagConstraints montarConstraints(int x, int y) {
        return montarConstraints(x, y, 1, 0.0, GridBagConstraints.WEST);
    }

    public GridBagConstraints montarConstraints(int x, int y, int gridwidth, double weightx, int anchor) {
        var constraint = new GridBagConstraints();
        constraint.insets = new Insets(8, 8, 8, 8);
        constraint.gridx = x;
        constraint.gridy = y;
        constraint.gridwidth = gridwidth;
        constraint.weightx = weightx;
        constraint.anchor = anchor;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        return constraint;
    }

    public void aplicarIcone(JFrame frame) {
        java.net.URL iconeURL = getClass().getResource("/img/logojava.png");
        ImageIcon icone = new ImageIcon(iconeURL);
        frame.setIconImage(icone.getImage());
    }

    public JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_LABEL);
        label.setForeground(COR_TEXTO_LABEL);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(new EmptyBorder(0, 0, 0, 8));
        return label;
    }

    public JTextField criarTextField(int colunas) {
        JTextField textField = new JTextField(colunas);
        textField.setFont(FONTE_CAMPO);
        textField.setBackground(COR_CAMPO_TEXTO);
        textField.setForeground(COR_TEXTO_LABEL);
        textField.setBorder(BORDA_COMPOSTA);
        textField.setDisabledTextColor(COR_TEXTO_LABEL);
        textField.setCaretColor(COR_PRIMARIA);
        return textField;
    }

    public JFormattedTextField criarCampoData() {
        try {
            MaskFormatter dataFormatter = new MaskFormatter("##/##/#### ##:##");
            dataFormatter.setPlaceholderCharacter('_');
            JFormattedTextField campo = new JFormattedTextField(dataFormatter);
            campo.setFont(FONTE_CAMPO);
            campo.setBackground(COR_CAMPO_TEXTO);
            campo.setForeground(COR_TEXTO_LABEL);
            campo.setBorder(BORDA_COMPOSTA);
            campo.setCaretColor(COR_PRIMARIA);
            return campo;
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    public JButton criarBotao(String texto, String tipo) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setBorder(new EmptyBorder(8, 16, 8, 16));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));

        switch (tipo.toLowerCase()) {
            case "primario":
                botao.setBackground(COR_PRIMARIA);
                botao.setOpaque(true);
                break;
            case "secundario":
                botao.setBackground(COR_SECUNDARIA);
                botao.setOpaque(true);
                break;
            case "perigo":
                botao.setBackground(COR_PERIGO);
                botao.setOpaque(true);
                break;
            default:
                botao.setBackground(COR_PRIMARIA);
                botao.setOpaque(true);
        }

        botao.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botao.setBackground(escurecerCor(botao.getBackground(), 0.9f));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botao.setBackground(restaurarCorOriginal(botao));
            }
        });

        return botao;
    }

    public JButton criarBotaoSelecionar(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE_BOTAO);
        botao.setForeground(Color.WHITE);
        botao.setBorder(new EmptyBorder(12, 24, 12, 24));
        botao.setFocusPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setBackground(COR_BOTAO_SELECIONAR);
        botao.setOpaque(true);
        return botao;
    }

    private Color escurecerCor(Color cor, float fator) {
        int r = Math.max(0, (int) (cor.getRed() * fator));
        int g = Math.max(0, (int) (cor.getGreen() * fator));
        int b = Math.max(0, (int) (cor.getBlue() * fator));
        return new Color(r, g, b);
    }

    private Color restaurarCorOriginal(JButton botao) {
        String text = botao.getText().toLowerCase();
        if (text.contains("excluir")) return COR_PERIGO;
        if (text.contains("limpar") || text.contains("cancelar")) return COR_SECUNDARIA;
        return COR_PRIMARIA;
    }

    public void aplicarTema() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.put("control", COR_FUNDO_TELA);
            UIManager.put("text", COR_TEXTO_LABEL);
            UIManager.put("nimbusBase", COR_TERCIARIA);
            UIManager.put("nimbusSelectionBackground", COR_TERCIARIA);
            UIManager.put("textField.disabledBackground", COR_CAMPO_DESABILITADO);
            UIManager.put("MenuBar.background", COR_MENU_FUNDO);
            UIManager.put("Menu.foreground", COR_MENU_TEXTO);
            UIManager.put("MenuItem.foreground", COR_MENU_TEXTO);
        } catch (Exception e) {
            System.err.println("Erro ao aplicar tema: " + e.getMessage());
        }
    }

    public static void exibirMensagem(JFrame parent, String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(parent, mensagem, titulo, tipo);
    }

    public boolean validarCamposObrigatorios(JTextField... campos) {
        for (JTextField campo : campos) {
            if (campo.getText().trim().isEmpty()) {
                campo.setBorder(BORDA_ATENCAO);
                return false;
            } else {
                campo.setBorder(BORDA_COMPOSTA);
            }
        }
        return true;
    }

    public JMenuBar criarMenuBar(JFrame telaAtual, TelaPrincipal telaPrincipal) {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(COR_MENU_FUNDO);
        menuBar.setOpaque(false);
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Menu Arquivo
        JMenu arquivoMenu = new JMenu("Arquivo");
        arquivoMenu.setFont(FONTE_MENU);
        arquivoMenu.setForeground(COR_MENU_TEXTO);

        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.setFont(FONTE_MENU);
        sairItem.setForeground(COR_MENU_TEXTO);
        sairItem.addActionListener(e -> {
            if (telaAtual instanceof TelaPrincipal) {
                System.exit(0);
            } else {
                telaAtual.dispose();
            }
        });
        arquivoMenu.add(sairItem);

        // Menu Navegação
        JMenu navegacaoMenu = new JMenu("Navegação");
        navegacaoMenu.setFont(FONTE_MENU);
        navegacaoMenu.setForeground(COR_MENU_TEXTO);

        JMenuItem eventosItem = new JMenuItem("Gerenciar Eventos");
        eventosItem.setFont(FONTE_MENU);
        eventosItem.setForeground(COR_MENU_TEXTO);
        eventosItem.addActionListener(e -> {
            if (!(telaAtual instanceof GerEventoGui)) {
                telaPrincipal.abrirGerEventoGui();
                if (!(telaAtual instanceof TelaPrincipal)) {
                    telaAtual.dispose();
                }
            }
        });

        JMenuItem palestrantesItem = new JMenuItem("Gerenciar Palestrantes");
        palestrantesItem.setFont(FONTE_MENU);
        palestrantesItem.setForeground(COR_MENU_TEXTO);
        palestrantesItem.addActionListener(e -> {
            if (!(telaAtual instanceof GerPalestranteGui)) {
                telaPrincipal.abrirGerPalestranteGui();
                if (!(telaAtual instanceof TelaPrincipal)) {
                    telaAtual.dispose();
                }
            }
        });

        JMenuItem alunosItem = new JMenuItem("Listar Alunos");
        alunosItem.setFont(FONTE_MENU);
        alunosItem.setForeground(COR_MENU_TEXTO);
        alunosItem.addActionListener(e -> {
            if (!(telaAtual instanceof ListarAlunosGui)) {
                telaPrincipal.abrirListarAlunosGui();
                if (!(telaAtual instanceof TelaPrincipal)) {
                    telaAtual.dispose();
                }
            }
        });

        navegacaoMenu.add(eventosItem);
        navegacaoMenu.add(palestrantesItem);
        navegacaoMenu.add(alunosItem);

        // Menu Ajuda
        JMenu ajudaMenu = new JMenu("Ajuda");
        ajudaMenu.setFont(FONTE_MENU);
        ajudaMenu.setForeground(COR_MENU_TEXTO);

        JMenuItem sobreItem = new JMenuItem("Sobre");
        sobreItem.setFont(FONTE_MENU);
        sobreItem.setForeground(COR_MENU_TEXTO);
        sobreItem.addActionListener(e -> exibirMensagem(telaAtual, "AlfaExperience v1.0\nDesenvolvido para Hackathon\nEquipe de 5 membros", "Sobre", JOptionPane.INFORMATION_MESSAGE));
        ajudaMenu.add(sobreItem);

        menuBar.add(arquivoMenu);
        menuBar.add(navegacaoMenu);
        menuBar.add(ajudaMenu);

        return menuBar;
    }
}