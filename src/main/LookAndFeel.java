
package main;

import javax.swing.UIManager;

public class LookAndFeel {
    
    public static enum LAF {
        CROSS,
        SYSTEM,
        METAL,
        NIMBUS,
        MOTIF,
        WINDOWS,
        WINDOWS_CLASSIC
    };
    
    public static void setLookAndFeel(LAF laf) {
        switch (laf) {
        
            case CROSS:
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch(Exception ex) {
                    System.err.println("ERROR: "+ex.toString());
                }
                break;
            
            case SYSTEM:
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch(Exception ex) {
                    System.err.println("ERROR: "+ex.toString());
                }
                break;
        
            case METAL:
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch(Exception ex) {
                    System.err.println("ERROR: "+ex.toString());
                }
                break;
                
            case NIMBUS:
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch(Exception ex) {
                    System.err.println("ERROR: "+ex.toString());
                }
                break;
                
                
            case MOTIF:
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                } catch(Exception ex) {
                    System.err.println("ERROR: "+ex.toString());
                }
                break;
                
            case WINDOWS:
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch(Exception ex) {
                    System.err.println("ERROR: "+ex.toString());
                }
                break;   
               
            case WINDOWS_CLASSIC:
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
                } catch(Exception ex) {
                    System.err.println("ERROR: "+ex.toString());
                }
                break;                  
        }
    }
}
