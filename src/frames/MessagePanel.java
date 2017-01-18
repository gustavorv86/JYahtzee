package frames;

import javax.swing.JOptionPane;

public class MessagePanel {
    public static final int YES = JOptionPane.YES_OPTION;
    public static final int NO = JOptionPane.NO_OPTION;
    public static final int CANCEL = JOptionPane.CANCEL_OPTION;
    
    public static void showInfo(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje,"Message",JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void showWarning(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje,"Warning",JOptionPane.WARNING_MESSAGE);
    }
    
    public static void showError(String mensaje){
        JOptionPane.showMessageDialog(null,mensaje,"Error",JOptionPane.ERROR_MESSAGE);
    }
    
    public static boolean confirmYesNo(String message){
        return JOptionPane.showConfirmDialog(null,message,"Message",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE)==JOptionPane.YES_OPTION;
    }
        
    public static int confirmYesNoCancel(String title){
        return JOptionPane.showConfirmDialog(null,"Message",title,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
    }
}
