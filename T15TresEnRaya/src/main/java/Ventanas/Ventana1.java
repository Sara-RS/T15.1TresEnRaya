package Ventanas;

import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Ventana1 extends JFrame{
    private JPanel tableroPanel;
    private JLabel estadoLabel;
    private JButton reiniciarBtn, salirBtn;
    private char[][] tablero; 
    private boolean turnoUsuario; 
    private boolean juegoTerminado;
    
    public Ventana1() throws HeadlessException {
        setSize(400, 500);
        setTitle("Tres en Raya");
        setLocation(200,100);
        setLayout(new BorderLayout());

        tablero = new char[3][3];
        turnoUsuario = new Random().nextBoolean();
        juegoTerminado = false;
        
        estadoLabel = new JLabel("Turno de: " + (turnoUsuario ? "Usuario (O)" : "Máquina (X)"), SwingConstants.CENTER);
        estadoLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(estadoLabel, BorderLayout.NORTH);

        tableroPanel = new TableroComponent();
        tableroPanel.setBackground(Color.WHITE);
        add(tableroPanel, BorderLayout.CENTER);

        JPanel botonesPanel = new JPanel(new GridLayout(1, 2));
        reiniciarBtn = new JButton("Reiniciar");
        salirBtn = new JButton("Salir");

        botonesPanel.add(reiniciarBtn);
        botonesPanel.add(salirBtn);
        add(botonesPanel, BorderLayout.SOUTH);

        reiniciarBtn.addActionListener(e -> reiniciarJuego());
        salirBtn.addActionListener(e -> System.exit(0));

        if (!turnoUsuario) {
            turnoMaquina();
        }
    }

    private class TableroComponent extends JPanel {
        public TableroComponent() {
            addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (juegoTerminado || !turnoUsuario) return;

                    int fila = e.getY() / (getHeight() / 3);
                    int columna = e.getX() / (getWidth() / 3);

                    if (fila < 3 && columna < 3 && tablero[fila][columna] == '\0') {
                        tablero[fila][columna] = 'O';
                        turnoUsuario = false;
                        repaint();
                        verificarEstado();

                        if (!juegoTerminado) {
                            turnoMaquina();
                        }
                    }
                }
            });
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.BLACK);
            for (int i = 1; i < 3; i++) {
                g.drawLine(0, i * getHeight() / 3, getWidth(), i * getHeight() / 3);
                g.drawLine(i * getWidth() / 3, 0, i * getWidth() / 3, getHeight());
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (tablero[i][j] == 'O') {
                        g.setColor(Color.RED);
                        g.drawOval(j * getWidth() / 3 + 10, i * getHeight() / 3 + 10,
                                   getWidth() / 3 - 20, getHeight() / 3 - 20);
                    }else if (tablero[i][j] == 'X') {
                        g.setColor(Color.BLUE);
                        g.drawLine(j * getWidth() / 3 + 10, i * getHeight() / 3 + 10,
                                   (j + 1) * getWidth() / 3 - 10, (i + 1) * getHeight() / 3 - 10);
                        g.drawLine((j + 1) * getWidth() / 3 - 10, i * getHeight() / 3 + 10,
                                   j * getWidth() / 3 + 10, (i + 1) * getHeight() / 3 - 10);
                    }
                }
            }
        }
    }

    private void turnoMaquina() {
        Random random = new Random();
        int fila, columna;

        do {
            fila = random.nextInt(3);
            columna = random.nextInt(3);
        }while (tablero[fila][columna] != '\0');

        tablero[fila][columna] = 'X';
        turnoUsuario = true;
        repaint();
        verificarEstado();
    }

    private void verificarEstado() {
        if (verGanador('O')) {
            juegoTerminado = true;
            estadoLabel.setText("¡Felicidades, ganaste el juego!");
        }else if (verGanador('X')) {
            juegoTerminado = true;
            estadoLabel.setText("Perdiste el juego.");
        }else if (tableroLleno()) {
            juegoTerminado = true;
            estadoLabel.setText("¡Empate!");
        }else {
            estadoLabel.setText("Turno de: " + (turnoUsuario ? "Usuario (O)" : "Máquina (X)"));
        }
    }

    private boolean verGanador(char jugador) {
        for (int i = 0; i < 3; i++) {
            if (tablero[i][0] == jugador && tablero[i][1] == jugador && tablero[i][2] == jugador) return true;
            if (tablero[0][i] == jugador && tablero[1][i] == jugador && tablero[2][i] == jugador) return true;
        }
        return (tablero[0][0] == jugador && tablero[1][1] == jugador && tablero[2][2] == jugador) ||
               (tablero[0][2] == jugador && tablero[1][1] == jugador && tablero[2][0] == jugador);
    }

    private boolean tableroLleno() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == '\0') return false;
            }
        }
        return true;
    }

    private void reiniciarJuego() {
        tablero = new char[3][3];
        turnoUsuario = new Random().nextBoolean();
        juegoTerminado = false;
        estadoLabel.setText("Turno de: " + (turnoUsuario ? "Usuario (O)" : "Máquina (X)"));
        repaint();
        if (!turnoUsuario) {
            turnoMaquina();
        }
    
    }
}