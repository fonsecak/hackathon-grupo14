package com.alfa.experience.gui;

import com.alfa.experience.model.Aluno;
import com.alfa.experience.service.AlunoService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ListarAlunosGui extends JFrame {
    private final TelaPrincipal telaPrincipal; // Referência à tela principal para navegação e fechamento
    private JComboBox<String> cbEventos; // Caixa de seleção para exibir os nomes dos eventos
    private JButton btnAtualizar; // Botão para atualizar a tabela com os alunos do evento selecionado
    private JTable tabelaAlunos; // Tabela que exibe os dados dos alunos
    private DefaultTableModel modeloTabela; // Modelo da tabela para gerenciar linhas e colunas
    private final AlunoService alunoService; // Serviço para acessar dados de alunos
    private Map<Integer, String> eventosMap; // Mapa que associa IDs de eventos aos seus nomes

    // Construtor que inicializa o serviço de alunos e a tela principal, montando a interface
    public ListarAlunosGui(AlunoService alunoService, TelaPrincipal telaPrincipal) {
        this.alunoService = alunoService; // Armazena o serviço de alunos
        this.telaPrincipal = telaPrincipal; // Armazena a referência à tela principal
        mostrarTela(); // Chama o método para criar a interface gráfica
        carregarEventos(); // Carrega os eventos disponíveis ao iniciar
    }

    // Método para montar e exibir a interface gráfica da tela
    private void mostrarTela() {
        var guiUtils = new GuiUtils(); // Cria uma instância da classe utilitária para GUI
        guiUtils.montarTelaPadrao(this, "Listar Alunos - AlfaExperience", 800, 600); // Configura a janela com título e tamanho (800x600)
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Fecha apenas esta janela ao clicar no "X"

        setJMenuBar(guiUtils.criarMenuBar(this, telaPrincipal)); // Adiciona a barra de menu com opções de navegação

        cbEventos = new JComboBox<>(); // Inicializa a caixa de seleção para eventos
        cbEventos.setFont(GuiUtils.FONTE_CAMPO); // Define a fonte do texto
        cbEventos.setBackground(GuiUtils.COR_CAMPO_TEXTO); // Define a cor de fundo
        cbEventos.setForeground(GuiUtils.COR_TEXTO_LABEL); // Define a cor do texto
        carregarEventosCombo(); // Preenche a caixa com os nomes dos eventos

        btnAtualizar = guiUtils.criarBotao("Atualizar", "primario"); // Cria um botão "Atualizar" com estilo primário
        btnAtualizar.addActionListener(e -> atualizarTabela()); // Adiciona ação para atualizar a tabela ao clicar

        // Define as colunas da tabela
        String[] colunas = {"ID", "Nome", "Sobrenome", "CPF", "Email", "Empresa", "Evento", "Presente?"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return Boolean.class; // Define a coluna "Presente?" como checkbox (tipo Boolean)
                return super.getColumnClass(columnIndex); // Usa o tipo padrão para outras colunas
            }
        };

        tabelaAlunos = new JTable(modeloTabela); // Cria a tabela com o modelo definido
        // Configura o renderizador da coluna "Presente?" para exibir checkboxes
        tabelaAlunos.getColumn("Presente?").setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkbox = new JCheckBox(); // Cria um checkbox para renderizar
                checkbox.setSelected(value != null && (Boolean) value); // Marca o checkbox com base no valor (true/false)
                checkbox.setEnabled(true); // Habilita o checkbox para visualização
                return checkbox; // Retorna o checkbox como componente visual
            }
        });

        // Configura o editor da coluna "Presente?" para permitir edição com checkbox
        tabelaAlunos.getColumn("Presente?").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public boolean stopCellEditing() {
                int row = tabelaAlunos.getEditingRow(); // Pega a linha sendo editada
                Boolean novaPresenca = (Boolean) getCellEditorValue(); // Pega o novo valor do checkbox
                long idAluno = (Long) modeloTabela.getValueAt(row, 0); // Pega o ID do aluno na linha
                int idEvento = selecionarEventoId(); // Pega o ID do evento selecionado
                int idInscricao = alunoService.getIdInscricao((int) idAluno, idEvento); // Busca o ID da inscrição
                if (idInscricao != -1) { // Verifica se a inscrição foi encontrada
                    if (alunoService.atualizarPresenca(idInscricao, novaPresenca)) { // Tenta atualizar a presença
                        modeloTabela.setValueAt(novaPresenca, row, 7); // Atualiza o valor na tabela
                    } else {
                        JOptionPane.showMessageDialog(ListarAlunosGui.this, "Falha ao atualizar presença.", "Erro", JOptionPane.ERROR_MESSAGE); // Mostra erro se falhar
                    }
                } else {
                    JOptionPane.showMessageDialog(ListarAlunosGui.this, "Inscrição não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE); // Mostra erro se inscrição não existir
                }
                return super.stopCellEditing(); // Finaliza a edição
            }
        });
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos); // Adiciona a tabela a um painel com barra de rolagem

        JPanel panel = new JPanel(new BorderLayout()); // Cria um painel principal com layout BorderLayout
        JPanel topPanel = new JPanel(new FlowLayout()); // Cria um painel superior com layout FlowLayout
        topPanel.add(new JLabel("Selecione o Evento:")); // Adiciona um rótulo
        topPanel.add(cbEventos); // Adiciona a caixa de seleção de eventos
        topPanel.add(btnAtualizar); // Adiciona o botão "Atualizar"
        panel.add(topPanel, BorderLayout.NORTH); // Posiciona o painel superior no topo
        panel.add(scrollPane, BorderLayout.CENTER); // Posiciona a tabela no centro
        add(panel); // Adiciona o painel principal à janela
    }

    // Método para carregar os eventos e selecionar o primeiro, se disponível
    private void carregarEventos() {
        carregarEventosCombo(); // Carrega os eventos na caixa de seleção
        if (cbEventos.getItemCount() > 0) { // Verifica se há eventos
            cbEventos.setSelectedIndex(0); // Seleciona o primeiro evento
        }
    }

    // Método para carregar os eventos na caixa de seleção
    private void carregarEventosCombo() {
        cbEventos.removeAllItems(); // Limpa os itens anteriores da caixa
        eventosMap = alunoService.listarEventos(); // Obtém o mapa de eventos do serviço
        if (eventosMap == null) { // Verifica se o mapa é nulo (erro de conexão ou query)
            System.err.println("Erro: eventosMap é null. Verifique a conexão ou a query.");
            JOptionPane.showMessageDialog(this, "Não foi possível carregar os eventos. Verifique a conexão.", "Erro", JOptionPane.ERROR_MESSAGE);
            return; // Sai do método se houver erro
        }
        if (eventosMap.isEmpty()) { // Verifica se não há eventos
            System.err.println("Nenhum evento encontrado no banco de dados.");
            JOptionPane.showMessageDialog(this, "Nenhum evento cadastrado no banco de dados.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Sai do método se não houver eventos
        }
        for (Map.Entry<Integer, String> entry : eventosMap.entrySet()) { // Itera sobre o mapa de eventos
            String eventName = entry.getValue(); // Pega o nome do evento
            if (eventName != null) { // Verifica se o nome não é nulo
                cbEventos.addItem(eventName); // Adiciona o nome à caixa de seleção
            }
        }
    }

    // Método para obter o ID do evento selecionado com base no nome
    private int selecionarEventoId() {
        String selecionarEventoNome = (String) cbEventos.getSelectedItem(); // Pega o nome do evento selecionado
        if (selecionarEventoNome == null) return -1; // Retorna -1 se nada estiver selecionado
        for (Map.Entry<Integer, String> entry : eventosMap.entrySet()) { // Itera sobre o mapa
            if (entry.getValue() != null && entry.getValue().equals(selecionarEventoNome)) { // Compara o nome
                return entry.getKey(); // Retorna o ID correspondente
            }
        }
        return -1; // Retorna -1 se não encontrar o ID
    }

    // Método para atualizar a tabela com os alunos do evento selecionado
    private void atualizarTabela() {
        int idEvento = selecionarEventoId(); // Obtém o ID do evento selecionado
        if (idEvento == -1) { // Verifica se o ID é válido
            JOptionPane.showMessageDialog(this, "Selecione um evento válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return; // Sai do método se inválido
        }
        modeloTabela.setRowCount(0); // Limpa todas as linhas da tabela
        List<Aluno> alunos = alunoService.listarAlunosPorEvento(idEvento); // Busca os alunos do evento
        for (Aluno aluno : alunos) { // Itera sobre a lista de alunos
            Object[] row = {
                    aluno.getId(), // Adiciona o ID
                    aluno.getNome(), // Adiciona o nome
                    aluno.getSobrenome(), // Adiciona o sobrenome
                    aluno.getCpf(), // Adiciona o CPF
                    aluno.getEmail(), // Adiciona o email
                    aluno.getEmpresa(), // Adiciona a empresa
                    aluno.getNomeEvento() != null ? aluno.getNomeEvento() : "Não inscrito", // Adiciona o nome do evento ou "Não inscrito"
                    aluno.getPresenca() != null ? aluno.getPresenca() : false // Adiciona a presença (true/false)
            };
            modeloTabela.addRow(row); // Adiciona a linha à tabela
        }
        if (alunos.isEmpty()) { // Verifica se não há alunos
            JOptionPane.showMessageDialog(this, "Nenhum aluno inscrito neste evento.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
