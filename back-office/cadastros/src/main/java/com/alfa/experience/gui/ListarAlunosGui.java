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
    private final TelaPrincipal telaPrincipal;
    private JComboBox<String> cbEventos; // Exibe nomes dos eventos
    private JButton btnAtualizar; // Botão para atualizar a tabela
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    private final AlunoService alunoService;
    private Map<Integer, String> eventosMap; // Mapa para mapear ID para nome

    public ListarAlunosGui(AlunoService alunoService, TelaPrincipal telaPrincipal) {
        this.alunoService = alunoService;
        this.telaPrincipal = telaPrincipal;
        inicializarComponentes();
        montarLayout();
        carregarEventos();
    }

    private void inicializarComponentes() {
        var guiUtils = new GuiUtils();
        setJMenuBar(guiUtils.criarMenuBar(this, telaPrincipal));
        setTitle("Listar Alunos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        cbEventos = new JComboBox<>();
        cbEventos.setFont(GuiUtils.FONTE_CAMPO);
        cbEventos.setBackground(GuiUtils.COR_CAMPO_TEXTO);
        cbEventos.setForeground(GuiUtils.COR_TEXTO_LABEL);
        carregarEventosCombo();

        btnAtualizar = guiUtils.criarBotao("Atualizar", "primario");
        btnAtualizar.addActionListener(e -> atualizarTabela());


        String[] colunas = {"ID", "Nome", "Sobrenome", "CPF", "Email", "Empresa", "Evento", "Presente?"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 7) return Boolean.class; // Coluna de checkbox
                return super.getColumnClass(columnIndex);
            }
        };

        tabelaAlunos = new JTable(modeloTabela);
        tabelaAlunos.getColumn("Presente?").setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkbox = new JCheckBox();
                checkbox.setSelected(value != null && (Boolean) value);
                checkbox.setEnabled(true);
                return checkbox;
            }
        });


        tabelaAlunos.getColumn("Presente?").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public boolean stopCellEditing() {
                int row = tabelaAlunos.getEditingRow();
                Boolean novaPresenca = (Boolean) getCellEditorValue();
                long idAluno = (Long) modeloTabela.getValueAt(row, 0);
                int idEvento = getSelectedEventId();
                int idInscricao = alunoService.getIdInscricao((int) idAluno, idEvento);
                if (idInscricao != -1) {
                    if (alunoService.atualizarPresenca(idInscricao, novaPresenca)) {
                        modeloTabela.setValueAt(novaPresenca, row, 7);
                    } else {
                        JOptionPane.showMessageDialog(ListarAlunosGui.this, "Falha ao atualizar presença.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(ListarAlunosGui.this, "Inscrição não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
                return super.stopCellEditing();
            }
        });
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);

        JPanel panel = new JPanel(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Selecione o Evento:"));
        topPanel.add(cbEventos);
        topPanel.add(btnAtualizar);
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);

    }

    private void montarLayout() {
    }

    private void carregarEventos() {
        carregarEventosCombo();
        if (cbEventos.getItemCount() > 0) {
            cbEventos.setSelectedIndex(0);
        }
    }

    private void carregarEventosCombo() {
        cbEventos.removeAllItems();
        eventosMap = alunoService.listarEventos();
        if (eventosMap == null) {
            System.err.println("Erro: eventosMap é null. Verifique a conexão ou a query.");
            JOptionPane.showMessageDialog(this, "Não foi possível carregar os eventos. Verifique a conexão.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (eventosMap.isEmpty()) {
            System.err.println("Nenhum evento encontrado no banco de dados.");
            JOptionPane.showMessageDialog(this, "Nenhum evento cadastrado no banco de dados.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (Map.Entry<Integer, String> entry : eventosMap.entrySet()) {
            String eventName = entry.getValue();
            if (eventName != null) {
                System.out.println("Adicionando evento ID: " + entry.getKey() + ", Nome: " + eventName);
                cbEventos.addItem(eventName);
            }
        }
    }

    private int getSelectedEventId() {
        String selectedEventName = (String) cbEventos.getSelectedItem();
        if (selectedEventName == null) return -1;
        for (Map.Entry<Integer, String> entry : eventosMap.entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(selectedEventName)) {
                return entry.getKey();
            }
        }
        return -1;
    }


    private void atualizarTabela() {
        int idEvento = getSelectedEventId();
        if (idEvento == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um evento válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        modeloTabela.setRowCount(0);
        List<Aluno> alunos = alunoService.listarAlunosPorEvento(idEvento);
        for (Aluno aluno : alunos) {
            Object[] row = {
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getSobrenome(),
                    aluno.getCpf(),
                    aluno.getEmail(),
                    aluno.getEmpresa(),
                    aluno.getNomeEvento() != null ? aluno.getNomeEvento() : "Não inscrito",
                    aluno.getPresenca() != null ? aluno.getPresenca() : false
            };
            modeloTabela.addRow(row);
        }
        if (alunos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum aluno inscrito neste evento.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

}