
package main;

import frames.MainFrame;

public class Main {

    public static void main(String[] args) {
        
        java.awt.EventQueue.invokeLater(() -> {
            LookAndFeel.setLookAndFeel(LookAndFeel.LAF.SYSTEM);
            new MainFrame().setVisible(true);
        });
        
    }
    
}
