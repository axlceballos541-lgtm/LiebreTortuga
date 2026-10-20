package com.axl.liebretortuga;

import javax.swing.*;
import java.awt.*;

public class LiebreYTortuga extends JFrame {

    private JLabel lblTitulo;
    private JButton btnIniciar;
    private JLabel lblLiebre;
    private JProgressBar barLiebre;
    private JLabel lblTortuga;
    private JProgressBar barTortuga;

    public LiebreYTortuga() {
        // Ventana de interfaz
        setTitle("Fábulas gráficas de Esopo");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 12));

        // Componentes de la interfaz
        lblTitulo = new JLabel("La Liebre y la Tortuga");
        btnIniciar = new JButton("¡Iniciar Carrera!");

        lblLiebre = new JLabel("La Liebre: En espera");
        barLiebre = new JProgressBar(0, 100);
        barLiebre.setPreferredSize(new Dimension(320, 25));
        barLiebre.setStringPainted(true);

        lblTortuga = new JLabel("La Tortuga: En espera");
        barTortuga = new JProgressBar(0, 100);
        barTortuga.setPreferredSize(new Dimension(320, 25));
        barTortuga.setStringPainted(true);

        // Evento del botón para arrancar los hilos
        btnIniciar.addActionListener(e -> {
            btnIniciar.setEnabled(false); // Desactivar botón para que no se presione dos veces
            iniciarCarrera();
        });

        // Agregar todo a la ventana
        add(lblTitulo);
        add(btnIniciar);
        add(lblLiebre);
        add(barLiebre);
        add(lblTortuga);
        add(barTortuga);
    }

    private void iniciarCarrera() {
        
        // HILO DE LA LIEBRE
        Thread hiloLiebre = new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    final int avance = i;
                    Thread.sleep(40); // La liebre corre rápido

                    // La liebre se duerme a mitad de camino
                    if (i == 45) {
                        SwingUtilities.invokeLater(() -> lblLiebre.setText("La Liebre: ¡Se durmió!"));
                        Thread.sleep(4500); // Pausa larga
                    } else if (i == 46) {
                        SwingUtilities.invokeLater(() -> lblLiebre.setText("La Liebre: ¡Se despertó!"));
                    } else if (i < 45) {
                        SwingUtilities.invokeLater(() -> lblLiebre.setText("La Liebre: ¡Está corriendo!"));
                    }

                    // Actualizar barra de la liebre de forma segura
                    SwingUtilities.invokeLater(() -> barLiebre.setValue(avance));
                }
                SwingUtilities.invokeLater(() -> lblLiebre.setText("La Liebre: ¡Llegó a la meta!"));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        // HILO DE LA TORTUGA
        Thread hiloTortuga = new Thread(() -> {
            try {
                for (int i = 1; i <= 100; i++) {
                    final int avance = i;
                    Thread.sleep(80); // La tortuga es constante pero más lenta

                    if (i == 50) {
                        SwingUtilities.invokeLater(() -> lblTortuga.setText("La Tortuga: ¡Le lleva ventaja!"));
                    } else if (i < 50) {
                        SwingUtilities.invokeLater(() -> lblTortuga.setText("La Tortuga: ¡Está corriendo!"));
                    }

                    // Actualizar barra de la tortuga
                    SwingUtilities.invokeLater(() -> barTortuga.setValue(avance));
                }
                SwingUtilities.invokeLater(() -> lblTortuga.setText("La Tortuga: ¡Cruzó la meta!"));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        });

        // Arrancar hilos
        hiloLiebre.start();
        hiloTortuga.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LiebreYTortuga().setVisible(true);
        });
    }
}