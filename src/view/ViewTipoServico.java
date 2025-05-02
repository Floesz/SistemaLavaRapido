package view;

import model.TipoServico;
import services.TipoServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ViewTipoServico extends JPanel {
    private TipoServices servicoService = new TipoServices();

    private JTextField txtNome = new JTextField(15);
    private JTextField txtDescricao = new JTextField(15);
    private JTextField txtPreco = new JTextField(10);

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public ViewTipoServico() {
        setLayout(new BorderLayout());

        // Montagem do painel para cadastro de um novo tipo de serviço

        JPanel painelCadastro = new JPanel(new GridLayout(4, 2));
        painelCadastro.add(new JLabel("Nome:"));
        painelCadastro.add(txtNome);
        painelCadastro.add(new JLabel("Descrição:"));
        painelCadastro.add(txtDescricao);
        painelCadastro.add(new JLabel("Preço:"));
        painelCadastro.add(txtPreco);

        JButton btnCadastrar = new JButton("Cadastrar");
        painelCadastro.add(btnCadastrar);

        add(painelCadastro, BorderLayout.NORTH);

        // Tabela para listar todos os tipos de serviço

        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Preço"}, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botão que envia os dados preenchidos para a tabela que armazena os tipos de serviço disponiveis

        btnCadastrar.addActionListener((ActionEvent e) -> {
            try {
                TipoServico tipo = new TipoServico();
                tipo.setNomeServico(txtNome.getText());
                tipo.setDescricao(txtDescricao.getText());
                tipo.setPreco(Double.parseDouble(txtPreco.getText()));

                servicoService.cadastrarServico(tipo);
                atualizarTabela();

                JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");

                txtNome.setText("");
                txtDescricao.setText("");
                txtPreco.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (TipoServico s : servicoService.listarTodosOsServicos()) {
            modeloTabela.addRow(new Object[]{
                    s.getId(), s.getNomeServico(), s.getDescricao(), s.getPreco()
            });
        }
    }
}
