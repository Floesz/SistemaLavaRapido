package view;

import controller.ClienteController;
import model.Cliente;
import services.ClienteServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewCliente extends JPanel {
    private ClienteServices clienteService = new ClienteServices();

    private JTextField txtNome = new JTextField(15);
    private JTextField txtCpf = new JTextField(15);
    private JTextField txtTelefone = new JTextField(15);
    private JTextField txtEmail = new JTextField(15);
    private JTextField txtPlaca = new JTextField(15);
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ClienteController clienteController = new ClienteController();

    private JButton btnCadastrar = new JButton("Cadastrar");
    private JButton btnEditar = new JButton("Editar");
    private JButton btnExcluir = new JButton("Excluir");

    private Integer idClienteEditando = null;

    public ViewCliente() {
        setLayout(new BorderLayout());


        // Painel principal para campos e botões lado a lado
        JPanel painelTopo = new JPanel(new BorderLayout());

        // Painel de botões na esquerda
        JPanel painelBotoes = new JPanel(new GridLayout(3, 1, 10, 10));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);

        // Painel de campos na direita
        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCampos.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtCpf, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCampos.add(new JLabel("Telefone:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtTelefone, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCampos.add(new JLabel("E-mail:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtEmail, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCampos.add(new JLabel("Placa do veículo:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtPlaca, gbc);

        painelTopo.add(painelBotoes, BorderLayout.WEST);
        painelTopo.add(painelCampos, BorderLayout.CENTER);
        add(painelTopo, BorderLayout.NORTH);

        // Tabela permanece onde estava
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Telefone", "E-mail", "Placa"}, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Ações dos botões
        btnCadastrar.addActionListener((ActionEvent e) -> {
            try {
                Cliente cliente = new Cliente();
                cliente.setNome(txtNome.getText());
                cliente.setCpf(txtCpf.getText());
                cliente.setTelefone(txtTelefone.getText());
                cliente.setEmail(txtEmail.getText());
                cliente.setPlacaVeiculo(txtPlaca.getText());

                if (idClienteEditando == null) {
                    clienteController.adicionarCliente(cliente);
                    JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                } else {
                    cliente.setId(idClienteEditando);
                    clienteController.editarCliente(cliente);
                    JOptionPane.showMessageDialog(this, "Cliente atualizado com sucesso!");
                    idClienteEditando = null;
                    btnCadastrar.setText("Cadastrar");
                }

                atualizarTabela();
                limparCampos();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                clienteController.excluirCliente(id);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.");
            }
        });

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                idClienteEditando = (int) modeloTabela.getValueAt(linha, 0);
                txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
                txtCpf.setText((String) modeloTabela.getValueAt(linha, 2));
                txtTelefone.setText((String) modeloTabela.getValueAt(linha, 3));
                txtEmail.setText((String) modeloTabela.getValueAt(linha, 4));
                txtPlaca.setText((String) modeloTabela.getValueAt(linha, 5));

                btnCadastrar.setText("Salvar edição");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.");
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Cliente c : clienteController.listarTodos()) {
            System.out.println("Cliente: " + c.getNome());
            modeloTabela.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail(), c.getPlacaVeiculo()
            });
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtPlaca.setText("");
    }
}
