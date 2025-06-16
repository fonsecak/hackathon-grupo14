package com.alfa.experience.gui;

import com.alfa.experience.model.Aluno;
import com.alfa.experience.service.AlunoService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarAlunosGui extends JFrame {
    private JComboBox<Integer> cbEventos;
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

        cbEventos = new JComboBox<>();
        List<Integer> eventos = alunoService.listarEventos();
        for (Integer idEvento : eventos) {
            cbEventos.addItem(idEvento);
        }
        cbEventos.addActionListener(e -> atualizarTabela());

        String[] colunas = {"ID", "Nome", "Sobrenome", "CPF", "Email", "Empresa", "Status", "Evento", "Presente?"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 8) return Boolean.class; // Coluna de checkbox
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
                Boolean novaPresenca = (Boolean) getCellEditorValue();
                long idAluno = (long) modeloTabela.getValueAt(row, 0);
                int idEvento = (int) cbEventos.getSelectedItem();
                int idInscricao = alunoService.getIdInscricao((int) idAluno, idEvento);
                if (idInscricao != -1) {
                    if (alunoService.atualizarPresenca(idInscricao, novaPresenca)) {
                        modeloTabela.setValueAt(novaPresenca, row, 8);
                    }else {
                            JOptionPane.showMessageDialog(ListarAlunosGui.this, "Falha ao atualizar presença.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                }
                return super.stopCellEditing();
            }
        });
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Selecione o Evento:"), BorderLayout.NORTH);
        panel.add(cbEventos, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);

    }

    private void montarLayout() {

    }

    private void carregarEventos() {
        if (cbEventos.getItemCount() == 0) {
            cbEventos.setSelectedIndex(-1); // Nenhum evento selecionado inicialmente
        } else {
            cbEventos.setSelectedIndex(0);
        }
        atualizarTabela();
    }

    private void atualizarTabela() {
        int idEvento = (int) cbEventos.getSelectedItem();
        if (idEvento == 0) return; // Evita processamento se nenhum evento válido
        modeloTabela.setRowCount(0);
        List<Aluno> alunos = alunoService.listarTodosAlunos(idEvento);
        for (Aluno aluno : alunos) {
            Object[] row = {
                    aluno.getId(),
                    aluno.getNome(),
                    aluno.getSobrenome(),
                    aluno.getCpf(),
                    aluno.getEmail(),
                    aluno.getEmpresa(),
                    aluno.getStatus() != null ? (aluno.getStatus().equals("1") ? "Ativo" : "Inativo") : "Não definido", // Mapeia status
                    aluno.getNomeEvento() != null ? aluno.getNomeEvento() : "Não inscrito",
                    aluno.getPresenca() != null ? aluno.getPresenca() : false
            };
            modeloTabela.addRow(row);
        }
    }

}