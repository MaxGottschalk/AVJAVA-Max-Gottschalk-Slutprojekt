package controller;

import javax.swing.SwingUtilities;

import view.GUI;

public class Controller {

	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }
}
