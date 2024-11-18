package com.mycompany.t15tresenraya;

import Ventanas.Ventana1;
import java.awt.EventQueue;
import javax.swing.JFrame;

public class T15TresEnRaya {

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->{
                Ventana1 v1= new Ventana1();
                v1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                v1.setVisible(true);
            });
    }
}
