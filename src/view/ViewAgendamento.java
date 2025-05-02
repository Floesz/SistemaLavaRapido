package view;

import exceptions.AgendamentoException;
import exceptions.ClienteException;
import model.Agendamento;
import model.Cliente;
import model.TipoServico;
import services.AgendamentoServices;
import services.ClienteServices;
import services.TipoServices;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewAgendamento extends JPanel {
    // Serviços usados para acessar os dados
    private AgendamentoServices agendamentoService = new AgendamentoServices();
    private ClienteServices clienteService = new ClienteServices();
    private TipoServices tipoService = new TipoServices();

    // Componentes da tela
    private JTextField txtNomeCliente = new JTextField(20);
    private JComboBox<TipoServico> comboServicos = new JComboBox<>();
    private JTextField txtDataHora = new JTextField(20);
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public ViewAgendamento() {
        setLayout(new BorderLayout());

        // Painel superior com os campos de entrada
        JPanel painelCadastro = new JPanel(new GridLayout(4, 2));
        painelCadastro.add(new JLabel("Cliente:"));
        painelCadastro.add(txtNomeCliente);
        painelCadastro.add(new JLabel("Serviço:"));
        painelCadastro.add(comboServicos);
        painelCadastro.add(new JLabel("Data e Hora (dd/MM/yyyy HH:mm):"));
        painelCadastro.add(txtDataHora);

        JButton btnAgendar = new JButton("Agendar");
        painelCadastro.add(btnAgendar);

        add(painelCadastro, BorderLayout.NORTH);

        // Tabela para exibir agendamentos
        modeloTabela = new DefaultTableModel(new Object[]{"ID", "Cliente", "Serviço", "Data/Hora", "Status"}, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);



        // Ação do botão para cadastar o agendamento
        btnAgendar.addActionListener((ActionEvent e) -> {
            try {
                // Pega o nome do cliente que foi digitado no campo de texto
                String nomeCliente = txtNomeCliente.getText().trim();
                if (nomeCliente.isEmpty()) {
                    throw new ClienteException("Informe o nome do cliente!");
                }

                // Busca o cliente na lista de clientes pelo nome
                Cliente clienteSelecionado = null;
                for (Cliente c : ClienteServices.listarTodosOsClientes()) {
                    if (c.getNome().equalsIgnoreCase(nomeCliente)) {
                        clienteSelecionado = c;
                        break;
                    }
                }

                // Verifica se o cliente foi encontrado
                if (clienteSelecionado == null) {
                    throw new ClienteException("Cliente não encontrado: " + nomeCliente);
                }

                // Pega o serviço selecionado do ComboBox
                TipoServico servicoSelecionado = (TipoServico) comboServicos.getSelectedItem();

                // Pega a data e hora do agendamento
                LocalDateTime dataHora = LocalDateTime.parse(txtDataHora.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                // Cria o agendamento
                Agendamento agendamento = new Agendamento();
                agendamento.setCliente(clienteSelecionado);
                agendamento.setTipo(servicoSelecionado);
                agendamento.setDataHora(dataHora);
                agendamento.setStatus("Agendado");

                // Cadastra o agendamento
                AgendamentoServices.agendarLavagem(agendamento);
                atualizarTabela();

                JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!");
                // Limpa os campos após o agendamento
                txtNomeCliente.setText("");
                txtDataHora.setText("");

            } catch (AgendamentoException ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }


        });

        carregarDados();
        atualizarTabela();
    }

    public void carregarDados() {
        comboServicos.removeAllItems();
        // Busca os serviços na tabela
        for (TipoServico servico : TipoServices.listarTodosOsServicos()) {
           if(servico != null) {
               comboServicos.addItem(servico);
           }
        }
    }


    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Agendamento ag : AgendamentoServices.listarTodosOsAgendamentos()) {
            modeloTabela.addRow(new Object[]{
                    ag.getIdAgendamento(),
                    ag.getCliente().getNome(),
                    ag.getTipo().getNomeServico(),
                    ag.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
                    ag.getStatus()
            });
        }


    }
    }
