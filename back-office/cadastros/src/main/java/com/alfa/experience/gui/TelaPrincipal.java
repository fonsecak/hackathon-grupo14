package com.alfa.experience.gui;

import com.alfa.experience.service.EventoService;
import com.alfa.experience.service.PalestranteService;
import com.alfa.experience.service.AlunoService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TelaPrincipal extends JFrame {
    private final EventoService eventoService; // Armazena o serviço para gerenciar eventos
    private final PalestranteService palestranteService; // Armazena o serviço para gerenciar palestrantes
    private final AlunoService alunoService; // Armazena o serviço para gerenciar alunos

    // Variáveis para controlar as instâncias das telas
    private GerEventoGui gerEventoGui; // Controle da tela de gerenciamento de eventos
    private GerPalestranteGui gerPalestranteGui; // Controle da tela de gerenciamento de palestrantes
    private ListarAlunosGui listarAlunosGui; // Controle da tela de listagem de alunos

    // Construtor que inicializa os serviços e monta a tela principal
    public TelaPrincipal(EventoService eventoService, PalestranteService palestranteService, AlunoService alunoService) {
        this.eventoService = eventoService; // Atribui o serviço de eventos
        this.palestranteService = palestranteService; // Atribui o serviço de palestrantes
        this.alunoService = alunoService; // Atribui o serviço de alunos
        montarTelaInicial(); // Chama o método para criar a interface inicial
    }

    // Método para montar a tela inicial do sistema
    private void montarTelaInicial() {
        var guiUtils = new GuiUtils(); // Cria uma instância da classe utilitária para GUI
        guiUtils.montarTelaPadrao(this, "Sistema AlfaExperience", 800, 600); // Configura a janela com título e tamanho (800x600)

        // Adicionar MenuBar
        setJMenuBar(guiUtils.criarMenuBar(this, this)); // Adiciona a barra de menu com opções como "Gerenciar Eventos" e "Sair"

        // Logo
        JLabel jlLogo = new JLabel(); // Cria um rótulo para exibir a logo
        java.net.URL logoURL = getClass().getResource("/img/logojava.png"); // Busca o arquivo da logo no diretório de recursos
        ImageIcon logo = new ImageIcon(logoURL); // Carrega a imagem da logo
        Image imagem = logo.getImage().getScaledInstance(500, 450, Image.SCALE_SMOOTH); // Redimensiona a imagem para 500x450 com suavização
        jlLogo.setIcon(new ImageIcon(imagem)); // Define a imagem redimensionada no rótulo

        // Painel principal com logo
        JPanel painelPrincipal = new JPanel(new GridBagLayout()); // Cria um painel com layout GridBag para organizar componentes
        painelPrincipal.setBackground(GuiUtils.COR_FUNDO_TELA); // Define a cor de fundo do painel (branco claro)
        GridBagConstraints gbc = new GridBagConstraints(); // Configurações de posicionamento no GridBagLayout
        gbc.insets = new Insets(20, 20, 20, 20); // Define margens de 20 pixels em todos os lados
        gbc.gridx = 0; // Posição horizontal na grade (coluna 0)
        gbc.gridy = 0; // Posição vertical na grade (linha 0)
        gbc.weightx = 1.0; // Permite que o componente ocupe espaço horizontal sobrando
        gbc.weighty = 1.0; // Permite que o componente ocupe espaço vertical sobrando
        gbc.anchor = GridBagConstraints.CENTER; // Centraliza o componente no painel

        painelPrincipal.add(jlLogo, gbc); // Adiciona o rótulo da logo ao painel com as configurações
        add(painelPrincipal); // Adiciona o painel à janela principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o programa ao fechar a janela
        setVisible(true); // Torna a janela visível
    }

    // Método para abrir a tela de gerenciamento de eventos
    public void abrirGerEventoGui() {
        if (gerEventoGui == null || !gerEventoGui.isVisible()) { // Verifica se a tela não existe ou não está visível
            gerEventoGui = new GerEventoGui(eventoService, palestranteService, this, alunoService); // Cria uma nova instância da tela
            configurarFechamento(gerEventoGui, () -> gerEventoGui = null); // Configura o fechamento da tela
            gerEventoGui.setVisible(true); // Mostra a tela
        } else { // Se a tela já existe e está visível
            gerEventoGui.toFront(); // Traz a tela para o primeiro plano
            gerEventoGui.requestFocus(); // Dá foco à tela
        }
    }

    // Método para abrir a tela de gerenciamento de palestrantes
    public void abrirGerPalestranteGui() {
        if (gerPalestranteGui == null || !gerPalestranteGui.isVisible()) { // Verifica se a tela não existe ou não está visível
            gerPalestranteGui = new GerPalestranteGui(palestranteService, this); // Cria uma nova instância da tela
            configurarFechamento(gerPalestranteGui, () -> gerPalestranteGui = null); // Configura o fechamento da tela
            gerPalestranteGui.setVisible(true); // Mostra a tela
        } else { // Se a tela já existe e está visível
            gerPalestranteGui.toFront(); // Traz a tela para o primeiro plano
            gerPalestranteGui.requestFocus(); // Dá foco à tela
        }
    }

    // Método para abrir a tela de listagem de alunos
    void abrirListarAlunosGui() {
        if (listarAlunosGui == null || !listarAlunosGui.isVisible()) { // Verifica se a tela não existe ou não está visível
            listarAlunosGui = new ListarAlunosGui(alunoService, this); // Cria uma nova instância da tela
            configurarFechamento(listarAlunosGui, () -> listarAlunosGui = null); // Configura o fechamento da tela
            listarAlunosGui.setVisible(true); // Mostra a tela
        } else { // Se a tela já existe e está visível
            listarAlunosGui.toFront(); // Traz a tela para o primeiro plano
            listarAlunosGui.requestFocus(); // Dá foco à tela
        }
    }

    // Método para configurar o comportamento ao fechar uma tela
    private void configurarFechamento(JFrame frame, Runnable onClose) {
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas a janela atual, não o programa
        frame.addWindowListener(new WindowAdapter() { // Adiciona um ouvinte para eventos de janela
            @Override
            public void windowClosed(WindowEvent e) { // Executa quando a janela é fechada
                onClose.run(); // Executa a ação de limpar a referência da tela (ex.: gerEventoGui = null)
            }
        });
    }
}
