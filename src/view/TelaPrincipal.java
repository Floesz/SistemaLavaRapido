package view;

import javax.swing.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        super("Sistema Lava Rápido - Abas");

        // Cria o JTabbedPane
        JTabbedPane abas = new JTabbedPane();

        // Adiciona as abas (cada uma com um JPanel diferente)
        abas.addTab("Clientes", new ViewCliente());  // JPanel específico


        // Adiciona o tabbed pane ao JFrame
        add(abas);

        // Configurações básicas da janela
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TelaPrincipal::new);
    }

}
