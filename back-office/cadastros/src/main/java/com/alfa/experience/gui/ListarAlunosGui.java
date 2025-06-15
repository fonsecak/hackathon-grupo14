package com.alfa.experience.gui;

import com.alfa.experience.model.Aluno;
import com.alfa.experience.service.AlunoService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarAlunosGui extends JFrame {
    private JComboBox<Integer> cbEventos; // Simula escolha de evento
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    private final AlunoService alunoService;

    public ListarAlunosGui(AlunoService alunoService) {
        this.alunoService = alunoService;
        inicializarComponentes();
        montarLayout();
        carregarEventos();
    }

    private void inicializarComponentes() {
        setTitle("Listar Alunos");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        GuiUtils guiUtils = new GuiUtils();


        cbEventos = new JComboBox<>();
        cbEventos.addItem(1);
        cbEventos.addItem(2);
        cbEventos.addActionListener(e -> atualizarTabela());

        // Tabela
        String[] colunas = {"ID", "Nome", "Sobrenome", "CPF", "Email", "Empresa", "Status", "Presente?"};
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
                checkbox.setSelected((value != null && (Boolean) value));
                checkbox.setEnabled(true);
                return checkbox;
            }
        });
        tabelaAlunos.getColumn("Presente?").setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public boolean stopCellEditing() {
                int row = tabelaAlunos.getEditingRow();
                boolean novoStatus = (Boolean) getCellEditorValue();
                int idAluno = (int) modeloTabela.getValueAt(row, 0);
                int idEvento = (int) cbEventos.getSelectedItem();
                int idInscricao = alunoService.getIdInscricao(idAluno, idEvento);
                if (idInscricao != -1) {
                    String status = novoStatus ? "Presente" : "Ausente";
                    if (alunoService.atualizarStatusInscricao(idInscricao)) {
                        modeloTabela.setValueAt(status, row, 6);
                    }
                }
                return super.stopCellEditing();
            }
        });
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);

        // Layout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Selecione o Evento:"), BorderLayout.NORTH);
        panel.add(cbEventos, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
    }

    private void montarLayout() {

    }

    private void carregarEventos() {
        cbEventos.setSelectedIndex(0);
        atualizarTabela();
    }

    private void atualizarTabela() {
        int idEvento = (int) cbEventos.getSelectedItem();
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
            };
            modeloTabela.addRow(row);
        }
    }

}