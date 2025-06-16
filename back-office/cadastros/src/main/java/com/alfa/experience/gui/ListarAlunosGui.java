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

    private JComboBox<Integer> cbEventos;
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    private final AlunoService alunoService;
    private Map<Integer, String> eventosMap;

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
        carregarEventosCombo(); // Preenche o JComboBox com IDs dos eventos
        // Define o evento inicial (ex.: primeiro evento ou um específico)
        if (cbEventos.getItemCount() > 0) {
            cbEventos.setSelectedIndex(0); // Seleciona o primeiro evento por padrão
        }
        cbEventos.addActionListener(e -> atualizarTabela());


        // Renderer para exibir nomes em vez de IDs
        cbEventos.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Integer) {
                    Integer eventId = (Integer) value;
                    String eventName = eventosMap.get(eventId);
                    setText(eventName != null ? eventName : "ID: " + eventId);
                }
                return this;
            }
        });

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
                        modeloTabela.setValueAt(novaPresenca, row, 7);
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
        carregarEventosCombo();
        if (cbEventos.getItemCount() > 0) {
            cbEventos.setSelectedIndex(0);
            atualizarTabela();
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
            Integer eventId = entry.getKey();
            String eventName = entry.getValue();
            System.out.println("Adicionando evento ID: " + eventId + ", Nome: " + eventName); // Depuração
            cbEventos.addItem(eventId); // Adiciona o ID do evento
        }
    }

    private int getSelectedEventId() {
        return cbEventos.getSelectedItem() != null ? (Integer) cbEventos.getSelectedItem() : -1;
    }

    private void atualizarTabela() {
        int idEvento = getSelectedEventId();
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
                    aluno.getNomeEvento() != null ? aluno.getNomeEvento() : "Não inscrito",
                    aluno.getPresenca() != null ? aluno.getPresenca() : false
            };
            modeloTabela.addRow(row);
        }
    }

}