package view;

import model.Cliente;
import services.ClienteServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewCliente extends JFrame {
    private ClienteServices clienteService = new ClienteServices();

    private JTextField txtNome = new JTextField(15);
    private JTextField txtCpf = new JTextField(15);
    private JTextField txtTelefone = new JTextField(15);
    private JTextField txtEmail = new JTextField(15);
    private JTextField txtPlaca = new JTextField(15);
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public ViewCliente() {
        setLayout(new BorderLayout());

        JPanel painelCadastro = new JPanel(new GridLayout(6, 2));
        painelCadastro.add(new JLabel("Nome:"));
        painelCadastro.add(txtNome);
        painelCadastro.add(new JLabel("CPF:"));
        painelCadastro.add(txtCpf);
        painelCadastro.add(new JLabel("Telefone:"));
        painelCadastro.add(txtTelefone);
        painelCadastro.add(new JLabel("E-mail:"));
        painelCadastro.add(txtEmail);
        painelCadastro.add(new JLabel("Placa do veículo:"));
        painelCadastro.add(txtPlaca);

        JButton btnCadastrar = new JButton("Cadastrar");
        painelCadastro.add(btnCadastrar);

        add(painelCadastro, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Telefone", "E-mail", "Placa"}, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnCadastrar.addActionListener((ActionEvent e) -> {
            try {
                Cliente cliente = new Cliente();
                cliente.setNome(txtNome.getText());
                cliente.setCpf(txtCpf.getText());
                cliente.setTelefone(txtTelefone.getText());
                cliente.setEmail(txtEmail.getText());
                cliente.setPlacaVeiculo(txtPlaca.getText());

                clienteService.cadastrarCliente(cliente);
                atualizarTabela();

                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");

                txtNome.setText("");
                txtCpf.setText("");
                txtTelefone.setText("");
                txtEmail.setText("");
                txtPlaca.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Cliente c : clienteService.listarTodosOsClientes()) {
            modeloTabela.addRow(new Object[]{
                    c.getId(), c.getNome(), c.getCpf(), c.getTelefone(), c.getEmail(), c.getPlacaVeiculo()
            });
        }
    }
}
