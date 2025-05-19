import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.List;

public class Interfaz extends JFrame {

    private CardLayout cardLayout;
    private JPanel panelContenedor;
    private final String archivoCSV = "gps_data.csv";

    // Variables para que se pueda actualizar texto
    private JTextArea textAreaAnalisis;

    public Interfaz() {
        setTitle("Tussam 2.0");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        panelContenedor.add(crearPanelMenuPrincipal(), "menu");
        panelContenedor.add(crearPanelAnalisis(), "analisis");

        add(panelContenedor);
        setVisible(true);
    }

    private JPanel crearPanelMenuPrincipal() {
        JPanel panelMenu = new JPanel(new GridBagLayout());
        panelMenu.setBackground(Color.decode("#E6E6FA"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15, 0, 15, 0);

        String[] nombres = {
                "Generar Datos",
                "Actualizar Datos",
                "Analizar Datos",
                "Exportar JSON",
                "Salir"
        };

        JButton[] botones = new JButton[nombres.length];

        for (int i = 0; i < nombres.length; i++) {
            botones[i] = new JButton(nombres[i]);
            botones[i].setPreferredSize(new Dimension(250, 45));
            botones[i].setFont(new Font("SansSerif", Font.BOLD, 15));
            botones[i].setBackground(new Color(153, 102, 204));
            botones[i].setForeground(Color.WHITE);
            panelMenu.add(botones[i], gbc);
        }

        botones[0].addActionListener((ActionEvent e) -> {
            GenerarDatosGPS.generarCSV(archivoCSV);
            mostrarMensaje("Datos generados correctamente.");
        });

        botones[1].addActionListener(e -> {
            List<GPSData> datos = LectorDatos.leerCSV(archivoCSV);
            ActualizarDatos.actualizarVelocidad(datos);
            ArchivoUtil.guardarCSV(archivoCSV, datos);
            mostrarMensaje("Datos actualizados correctamente.");
        });

        botones[2].addActionListener(e -> {
            refrescarAnalisis();
            cardLayout.show(panelContenedor, "analisis");
        });

        botones[3].addActionListener(e -> {
            List<GPSData> datos = LectorDatos.leerCSV(archivoCSV);
            LectorDatos.exportarJSONUltimaPosicion(datos);
            mostrarMensaje("Archivos JSON exportados.");
        });

        botones[4].addActionListener(e -> System.exit(0));

        return panelMenu;
    }

    private JPanel crearPanelAnalisis() {
        JPanel panelAnalisis = new JPanel(new BorderLayout());
        panelAnalisis.setBackground(Color.decode("#F0F8FF"));

        textAreaAnalisis = new JTextArea();
        textAreaAnalisis.setFont(new Font("Monospaced", Font.PLAIN, 14));
        textAreaAnalisis.setEditable(false);
        JScrollPane scroll = new JScrollPane(textAreaAnalisis);

        JButton btnVolver = new JButton("Volver al menú");
        btnVolver.addActionListener(e -> cardLayout.show(panelContenedor, "menu"));

        panelAnalisis.add(scroll, BorderLayout.CENTER);
        panelAnalisis.add(btnVolver, BorderLayout.SOUTH);

        return panelAnalisis;
    }

    private void refrescarAnalisis() {
        List<GPSData> datos = LectorDatos.leerCSV(archivoCSV);
        String texto = LectorDatos.analizarDatosTexto(datos);
        textAreaAnalisis.setText(texto);
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interfaz());
    }
}
