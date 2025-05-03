package view;

import controller.AgendamentoController;
import controller.ClienteController;
import controller.TipoServicoController;
import exceptions.ClienteException;
import model.Agendamento;
import model.Cliente;
import model.TipoServico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewAgendamento extends JPanel {
    private JTextField txtIdentificacaoCliente = new JTextField(20);
    private JComboBox<TipoServico> comboServicos = new JComboBox<>();
    private JTextField txtDataHora = new JTextField(20);

    private JButton btnAgendar = new JButton("Agendar");
    private JButton btnEditar = new JButton("Editar");
    private JButton btnCancelar = new JButton("Cancelar");

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private AgendamentoController agendamentoController = new AgendamentoController();
    private ClienteController clienteController = new ClienteController();
    private TipoServicoController tipoController = new TipoServicoController();

    private Integer idEditando = null;

    public ViewAgendamento() {
        setLayout(new BorderLayout());




        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.add(btnAgendar);
        painelBotoes.add(Box.createVerticalStrut(10));
        painelBotoes.add(btnEditar);
        painelBotoes.add(Box.createVerticalStrut(10));
        painelBotoes.add(btnCancelar);
        add(painelBotoes, BorderLayout.WEST);

        // Painel de campos
        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(new JLabel("ID ou CPF do Cliente:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtIdentificacaoCliente, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCampos.add(new JLabel("Serviço:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(comboServicos, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCampos.add(new JLabel("Data e Hora (dd/MM/yyyy HH:mm):"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtDataHora, gbc);

        add(painelCampos, BorderLayout.CENTER);

        // Tabela
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Cliente", "Serviço", "Data/Hora", "Status"}, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.SOUTH);

        carregarServicos();
        atualizarTabela();

        btnAgendar.addActionListener((ActionEvent e) -> {
            try {
                String identificacao = txtIdentificacaoCliente.getText().trim();
                if (identificacao.isEmpty()) {
                    throw new ClienteException("Informe o ID ou CPF do cliente!");
                }

                Cliente cliente;
                if (identificacao.length() == 11) {
                    cliente = clienteController.buscarPorCpf(identificacao);
                } else {
                    cliente = clienteController.buscarPorId(Integer.parseInt(identificacao));
                }

                if (cliente == null) {
                    throw new ClienteException("Cliente não encontrado: " + identificacao);
                }

                TipoServico servico = (TipoServico) comboServicos.getSelectedItem();
                LocalDateTime dataHora = LocalDateTime.parse(txtDataHora.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                Agendamento ag = new Agendamento();
                ag.setCliente(cliente);
                ag.setTipo(servico);
                ag.setDataHora(dataHora);
                ag.setStatus("Agendado");

                if (idEditando == null) {
                    agendamentoController.adicionarAgendamento(ag);
                    JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!");
                } else {
                    ag.setIdAgendamento(idEditando);
                    agendamentoController.editarAgendamento(ag);
                    JOptionPane.showMessageDialog(this, "Agendamento atualizado com sucesso!");
                    idEditando = null;
                    btnAgendar.setText("Agendar");
                }

                limparCampos();
                atualizarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                idEditando = (Integer) modeloTabela.getValueAt(linha, 0);
                txtIdentificacaoCliente.setText((String) modeloTabela.getValueAt(linha, 1));

                String nomeServico = (String) modeloTabela.getValueAt(linha, 2);
                for (int i = 0; i < comboServicos.getItemCount(); i++) {
                    if (comboServicos.getItemAt(i).getNomeServico().equalsIgnoreCase(nomeServico)) {
                        comboServicos.setSelectedIndex(i);
                        break;
                    }
                }

                txtDataHora.setText((String) modeloTabela.getValueAt(linha, 3));
                btnAgendar.setText("Salvar edição");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um agendamento para editar.");
            }
        });

        btnCancelar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                agendamentoController.cancelarAgendamento(id);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um agendamento para excluir.");
            }
        });
    }

    public void carregarServicos() {
        comboServicos.removeAllItems();
        for (TipoServico servico : tipoController.listarTodos()) {
            if (servico != null) {
                comboServicos.addItem(servico);
            }
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Agendamento ag : agendamentoController.listarTodos()) {
            modeloTabela.addRow(new Object[]{
                    ag.getIdAgendamento(),
                    ag.getCliente().getNome(),
                    ag.getTipo().getNomeServico(),
                    ag.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    ag.getStatus()
            });
        }
    }

    private void limparCampos() {
        txtIdentificacaoCliente.setText("");
        txtDataHora.setText("");
        comboServicos.setSelectedIndex(0);
    }
}
