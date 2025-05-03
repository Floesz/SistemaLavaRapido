package view;

import controller.TipoServicoController;
import model.TipoServico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ViewTipoServico extends JPanel {
    private JTextField txtNome = new JTextField(15);
    private JTextField txtDescricao = new JTextField(15);
    private JTextField txtPreco = new JTextField(10);

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private JButton btnCadastrar = new JButton("Cadastrar");
    private JButton btnEditar = new JButton("Editar");
    private JButton btnExcluir = new JButton("Excluir");

    private TipoServicoController tipoController = new TipoServicoController();
    private Integer idEditando = null;

    public ViewTipoServico() {
        setLayout(new BorderLayout());


        // Painel de botões (esquerda)
        JPanel painelBotoes = new JPanel();
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.Y_AXIS));
        painelBotoes.add(btnCadastrar);
        painelBotoes.add(Box.createVerticalStrut(10));
        painelBotoes.add(btnEditar);
        painelBotoes.add(Box.createVerticalStrut(10));
        painelBotoes.add(btnExcluir);
        add(painelBotoes, BorderLayout.WEST);

        // Painel de campos (direita)
        JPanel painelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painelCampos.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCampos.add(new JLabel("Descrição:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtDescricao, gbc);

        gbc.gridx = 0; gbc.gridy++;
        painelCampos.add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        painelCampos.add(txtPreco, gbc);

        add(painelCampos, BorderLayout.CENTER);

        // Tabela (baixo)
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Nome", "Descrição", "Preço"}, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.SOUTH);

        // Ações
        btnCadastrar.addActionListener((ActionEvent e) -> {
            try {
                TipoServico tipo = new TipoServico();
                tipo.setNomeServico(txtNome.getText());
                tipo.setDescricao(txtDescricao.getText());
                tipo.setPreco(Double.parseDouble(txtPreco.getText()));

                if (idEditando == null) {
                    tipoController.adicionarTipoDeServico(tipo);
                    JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");
                } else {
                    tipo.setId(idEditando);
                    tipoController.editarTipoDeServico(tipo);
                    JOptionPane.showMessageDialog(this, "Serviço atualizado com sucesso!");
                    idEditando = null;
                    btnCadastrar.setText("Cadastrar");
                }

                limparCampos();
                atualizarTabela();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnEditar.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                idEditando = (Integer) modeloTabela.getValueAt(linha, 0);
                txtNome.setText((String) modeloTabela.getValueAt(linha, 1));
                txtDescricao.setText((String) modeloTabela.getValueAt(linha, 2));
                txtPreco.setText(String.valueOf(modeloTabela.getValueAt(linha, 3)));

                btnCadastrar.setText("Salvar edição");
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um serviço para editar.");
            }
        });

        btnExcluir.addActionListener(e -> {
            int linha = tabela.getSelectedRow();
            if (linha != -1) {
                int id = (int) modeloTabela.getValueAt(linha, 0);
                tipoController.excluirTipoDeServico(id);
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um serviço para excluir.");
            }
        });

        atualizarTabela();
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (TipoServico s : tipoController.listarTodos()) {
            modeloTabela.addRow(new Object[]{
                    s.getId(), s.getNomeServico(), s.getDescricao(), s.getPreco()
            });
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
    }
}
