package application;

import view.ViewAgendamento;
import view.ViewCliente;
import view.ViewTipoServico;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        super("Sistema Lava Rápido");


        JTabbedPane abas = new JTabbedPane();

        ViewAgendamento viewAgendamento = new ViewAgendamento();
        ViewTipoServico viewTipoServico = new ViewTipoServico();

        abas.addTab("Clientes", new ViewCliente());
        abas.addTab("Tipos de Serviço",viewTipoServico);
        abas.addTab("Agendamentos",viewAgendamento);
        add(abas);

        abas.addChangeListener(e -> {
            int selectedIndex = abas.getSelectedIndex();
            String tituloAba = abas.getTitleAt(selectedIndex);

            if (tituloAba.equals("Agendamentos")) {
                viewAgendamento.carregarDados(); // Atualiza o ComboBox de serviços
            }
        });


        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }

}
