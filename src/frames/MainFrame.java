
package frames;

import java.awt.Toolkit;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends javax.swing.JFrame {

    private final static int N_ROWS = 20;
    private final static int U1 = 1;
    private final static int U2 = 2;
    private final static int U3 = 3;
    private final static int U4 = 4;
    private final static int U5 = 5;
    private final static int U6 = 6;
    private final static int UT = 7;
    
    private final static int K3 = 10;
    private final static int K4 = 11;
    private final static int FH = 12;
    private final static int S4 = 13;
    private final static int S5 = 14;
    private final static int K5 = 15;
    private final static int CH = 16;
    private final static int LT = 17;
    
    private final static int GT = 19;
    
    private final static String MSG_SELECT_DICES = "<html>Select dices to roll and press <strong>Roll</strong> button or select a row</html>";
    private final static String MSG_SCORE_USED = "<html>This score is used. Please, <strong>select another row</strong></html>";
    private final static String MSG_FINISH = "<html><strong>GAME FINISHED!!</strong></html>";
    
    private final JLabel[] jLabelDices;
    private int numPlayers = 1;
    private int indexPlayer = 0;
    
    public MainFrame() {
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/icon.png")));
        jLabelDices = getAlljLabelDices();
        resetGame();
    }

    private void resetGame(){
        indexPlayer = 0;
        jTable.setModel(getTableModel(numPlayers));
        jLabelStatus.setText(MSG_SELECT_DICES);
        resetAllDices();
        rollDices();
        resetRollButton();
    }
    
    private JLabel[] getAlljLabelDices() {
        return new JLabel[]{
            jLabelDice1, jLabelDice2, jLabelDice3, jLabelDice4, jLabelDice5
        };
    }
    
    private DefaultTableModel getTableModel(int numPlayers) {
        int numCols = numPlayers+2;
        // HEADER
        String[] header = new String[numCols];
        header[0] = "PLAYERS";
        header[1] = "SCORES";
        for(int i=0; i<numPlayers; i++) {
            header[i+2] = "Player "+(i+1);
        }

        String data[][] = new String[N_ROWS][numCols];
        for(int i=0; i<N_ROWS; i++) {
            for(int j=0; j<numCols; j++) {
                data[i][j] = "";
            }
        }
        // DATA
        data[0][0]  = bold("UPPER SECTION");
        data[U1][0] = "Number of Aces [total of 1s]";
        data[U2][0] = "Number of Twos [total of 2s]";
        data[U3][0] = "Number of Threes [total of 3s]";
        data[U4][0] = "Number of Fours [total of 4s]";
        data[U5][0] = "Number of Fives [total of 5s]";
        data[U6][0] = "Number of Sixes [total of 6s]";
        data[UT][0] = bold("UPPER TOTAL");
        // SPACE
        data[9][0]  = bold("LOWER SECTION");
        data[K3][0] = "3 of a Kind [total]";
        data[K4][0] = "4 of a Kind [total]";
        data[FH][0] = "Full House [25]";
        data[S4][0] = "Sequence of 4 [30]";
        data[S5][0] = "Sequence of 5 [40]";
        data[K5][0] = "5 of a Kind (Yahtzee) [50]";
        data[CH][0] = "Chance [total]";
        data[LT][0] = bold("LOWER TOTAL");
        // SPACE
        data[GT][0] = bold("GRAND TOTAL");

        DefaultTableModel model = new DefaultTableModel(data, header) {
            @Override
            public Class getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
                
        return model;
    }
    
    private String bold(Object obj) {
        return "<html><strong>"+obj.toString()+"</strong></html>";
    }

    private int getDiceValue(JLabel jLabelDice) {
        try {
            if(jLabelDice.getToolTipText().isEmpty()) {
                return 0;
            } else {
                return Integer.parseInt(jLabelDice.getToolTipText());
            }
        }catch(Exception ex) {
            System.err.println("EXCEPTION: "+ex.toString());
            return 0;
        }
    }
    
    private int[] getDiceValues() {
        int[] values = new int[jLabelDices.length];
        for(int i=0; i<jLabelDices.length; i++) {
            values[i] = getDiceValue(jLabelDices[i]);
        }
        return values;
    }
    
    private void setDiceValue(JLabel jLabelDice, int value) {
        if(value<=0 || 6<value) {
            jLabelDice.setToolTipText("");
        } else {
            jLabelDice.setToolTipText(value+"");
        }
    }
    
    private boolean isDiceEmptyIcon(JLabel jLabelDice){
        return jLabelDice.getIcon().toString().endsWith("dice-0.png");
    }
    
    private void setDiceIcon(JLabel jLabelDice, int value){
        if(value<0 || 6<value) {
            value = 0;
        }
        String path = String.format("/images/dices/dice-%d.png", value);
        jLabelDice.setIcon(new ImageIcon(getClass().getResource(path)));
    }
    
    private void resetAllDices(){
        for(JLabel jLabelDice : jLabelDices) {
            setDiceValue(jLabelDice, 0);
            setDiceIcon(jLabelDice, 0);
        }
    }
        
    private void rollDices(){
        for(JLabel jLabelDice : jLabelDices) {
            if(isDiceEmptyIcon(jLabelDice)) {
                int rand = ThreadLocalRandom.current().nextInt(1, 6+1);
                setDiceValue(jLabelDice, rand);
            }
        }
        updateDiceIcons();
        decrementRollButton();
        updateTableValues();
    }
    
    private void updateDiceIcons(){
        for(JLabel jLabelDice : jLabelDices) {
            setDiceIcon(jLabelDice, getDiceValue(jLabelDice));
        }
    }
    
    private void resetRollButton(){
        jButtonRoll.setText("Roll (3)");
        jButtonRoll.setEnabled(true);
    }
    
    private void decrementRollButton(){
        String buttonLabel = jButtonRoll.getText();
        int count = buttonLabel.charAt(buttonLabel.indexOf('(')+1)-48;
        jButtonRoll.setText("Roll ("+(--count)+")");
        jButtonRoll.setEnabled(count != 1);
    }
    
    private void jLabelDiceMouseClicked(JLabel jLabelDice) {
        if(jButtonRoll.isEnabled()) {
            if(jLabelDice.getIcon().toString().endsWith("dice-0.png")) {
                setDiceIcon(jLabelDice, getDiceValue(jLabelDice));
            } else {
                setDiceIcon(jLabelDice, 0);
            }
        }
    }
    
    private int getCellValue(int row, int col) {
        String strValue = jTable.getValueAt(row, col).toString();
        strValue = strValue.replace("<html>", "");
        strValue = strValue.replace("</html>", "");
        strValue = strValue.replace("<strong>", "");
        strValue = strValue.replace("</strong>", "");
        if(strValue.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(strValue);
        }catch(Exception ex) {
            System.err.println("EXCEPTION: "+ex.toString());
            return 0;
        }
    }
    
    private boolean isCellEmpty(int row, int col) {
        return jTable.getValueAt(row, col).toString().isEmpty();
    }
    
    private void updateTableValues() {
        
            
        jTable.setValueAt("", U1, 1);
        jTable.setValueAt("", U2, 1);
        jTable.setValueAt("", U3, 1);
        jTable.setValueAt("", U4, 1);
        jTable.setValueAt("", U5, 1);
        jTable.setValueAt("", U6, 1);
        jTable.setValueAt("", K3, 1);
        jTable.setValueAt("", K4, 1);
        jTable.setValueAt("", FH, 1);
        jTable.setValueAt("", S4, 1);
        jTable.setValueAt("", S5, 1);
        jTable.setValueAt("", K5, 1);
        jTable.setValueAt("", CH, 1);

        Scores scores = new Scores(getDiceValues());
        if(isCellEmpty(U1, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.numberOfAces()), U1, 1);
        }
        if(isCellEmpty(U2, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.numberOfTwos()), U2, 1);
        }
        if(isCellEmpty(U3, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.numberOfThrees()), U3, 1);
        }
        if(isCellEmpty(U4, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.numberOfFours()), U4, 1);
        }
        if(isCellEmpty(U5, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.numberOfFives()), U5, 1);
        }
        if(isCellEmpty(U6, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.numberOfSixes()), U6, 1);
        }

        if(isCellEmpty(K3, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.threeOfAKind()), K3, 1);
        }
        if(isCellEmpty(K4, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.fourOfAKind()), K4, 1);
        }
        if(isCellEmpty(FH, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.fullHouse()), FH, 1);
        }
        if(isCellEmpty(S4, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.sequenceOf4()), S4, 1);
        }
        if(isCellEmpty(S5, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.sequenceOf5()), S5, 1);
        }
        if(isCellEmpty(K5, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.yahtzee()), K5, 1);
        }
        if(isCellEmpty(CH, indexPlayer + 2)){
            jTable.setValueAt(bold(scores.chance()), CH, 1);
        }

        for(int i=0; i<numPlayers; i++) {
            int col = i + 2;
            
            String header = "Player "+(i+1);
            if(i == indexPlayer) {
                header = bold(header);
            }
            
            jTable.getColumnModel().getColumn(col).setHeaderValue(header);
        
            int upperTotal = 0;
            upperTotal += getCellValue(U1, col);
            upperTotal += getCellValue(U2, col);
            upperTotal += getCellValue(U3, col);
            upperTotal += getCellValue(U4, col);
            upperTotal += getCellValue(U5, col);
            upperTotal += getCellValue(U6, col);
            jTable.setValueAt(bold(upperTotal), UT, col);

            int lowerTotal = 0;
            lowerTotal += getCellValue(K3, col);
            lowerTotal += getCellValue(K4, col);
            lowerTotal += getCellValue(FH, col);
            lowerTotal += getCellValue(S4, col);
            lowerTotal += getCellValue(S5, col);
            lowerTotal += getCellValue(K5, col);
            lowerTotal += getCellValue(CH, col);
            jTable.setValueAt(bold(lowerTotal), LT, col);

            jTable.setValueAt(bold(upperTotal+lowerTotal), GT, col);
        }
        jTable.getTableHeader().repaint();
    }
    
    private void saveScore() {
        int selectedRow = jTable.getSelectedRow();
        int col =  indexPlayer + 2;
        if(jTable.getValueAt(selectedRow, col).toString().isEmpty()) {
            String scoreValue = jTable.getValueAt(selectedRow, 1).toString();
            jTable.setValueAt(scoreValue, selectedRow, indexPlayer+2);
            if(finishGame()) {
                updateTableValues();
                jLabelStatus.setText(MSG_FINISH);
                int bestScore = getBestScore();
                MessagePanel.showInfo("Game Over \n Best score: "+bestScore);
                resetGame();
            } else {
                indexPlayer = (indexPlayer + 1) % numPlayers;
                resetAllDices();
                rollDices();
                resetRollButton();
            }
        } else {
            jLabelStatus.setText(MSG_SCORE_USED);
        }
    }
    
    private boolean finishGame(){
        for(int i=0; i<numPlayers; i++) {
            int col = i + 2;
            if(
                    isCellEmpty(U1, col) ||
                    isCellEmpty(U2, col) ||
                    isCellEmpty(U3, col) ||
                    isCellEmpty(U4, col) ||
                    isCellEmpty(U5, col) ||
                    isCellEmpty(U6, col) ||
                    
                    isCellEmpty(K3, col) ||
                    isCellEmpty(K4, col) ||
                    isCellEmpty(FH, col) ||
                    isCellEmpty(S4, col) ||
                    isCellEmpty(S5, col) ||
                    isCellEmpty(K5, col) ||
                    isCellEmpty(CH, col)
              ){
                return false;
            }
        }
        return true;
    }
    
    private int getBestScore(){
        int max = -1;
        int bestPlayer = -1;
        for(int i=0; i<numPlayers; i++) {
            int col = i + 2;
            int aux = getCellValue(GT, col);
            if(aux > max) {
                bestPlayer = i;
                max = aux;
            }
        }
        return max;
    }
    
    private void jSpinnerPlayerChanged(){
        int spinner = (int) jSpinnerPlayers.getValue();
        if(spinner != numPlayers) {
            numPlayers = spinner;
            resetGame();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelDice = new javax.swing.JPanel();
        jLabelDice5 = new javax.swing.JLabel();
        jLabelDice1 = new javax.swing.JLabel();
        jLabelDice2 = new javax.swing.JLabel();
        jLabelDice3 = new javax.swing.JLabel();
        jLabelDice4 = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jButtonRoll = new javax.swing.JButton();
        jLabelStatus = new javax.swing.JLabel();
        jLabelPlayers = new javax.swing.JLabel();
        jSpinnerPlayers = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("JYahtzee");

        jPanelDice.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabelDice5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dices/dice-0.png"))); // NOI18N
        jLabelDice5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelDice5MousePressed(evt);
            }
        });

        jLabelDice1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dices/dice-0.png"))); // NOI18N
        jLabelDice1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelDice1MousePressed(evt);
            }
        });

        jLabelDice2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dices/dice-0.png"))); // NOI18N
        jLabelDice2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelDice2MousePressed(evt);
            }
        });

        jLabelDice3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dices/dice-0.png"))); // NOI18N
        jLabelDice3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelDice3MousePressed(evt);
            }
        });

        jLabelDice4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dices/dice-0.png"))); // NOI18N
        jLabelDice4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelDice4MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelDiceLayout = new javax.swing.GroupLayout(jPanelDice);
        jPanelDice.setLayout(jPanelDiceLayout);
        jPanelDiceLayout.setHorizontalGroup(
            jPanelDiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDiceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelDice1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDice2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDice3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDice4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelDice5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDiceLayout.setVerticalGroup(
            jPanelDiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelDice1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDice2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDice3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDice4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelDice5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jScrollPane.setPreferredSize(new java.awt.Dimension(460, 460));

        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(jTable);

        jButtonRoll.setText("Roll (3)");
        jButtonRoll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRollActionPerformed(evt);
            }
        });

        jLabelStatus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelStatus.setText("Status Label");

        jLabelPlayers.setText("Players:");

        jSpinnerPlayers.setModel(new javax.swing.SpinnerNumberModel(1, 1, 4, 1));
        jSpinnerPlayers.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerPlayersStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelDice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonRoll, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelPlayers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSpinnerPlayers)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
                    .addComponent(jLabelStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelStatus)
                    .addComponent(jLabelPlayers)
                    .addComponent(jSpinnerPlayers, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelDice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonRoll))
                    .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonRollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRollActionPerformed
        rollDices();
    }//GEN-LAST:event_jButtonRollActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        if(evt.getClickCount() == 2) {
            saveScore();
        }
    }//GEN-LAST:event_jTableMouseClicked

    private void jLabelDice1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDice1MousePressed
        jLabelDiceMouseClicked(jLabelDice1);
    }//GEN-LAST:event_jLabelDice1MousePressed

    private void jLabelDice2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDice2MousePressed
        jLabelDiceMouseClicked(jLabelDice2);
    }//GEN-LAST:event_jLabelDice2MousePressed

    private void jLabelDice3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDice3MousePressed
        jLabelDiceMouseClicked(jLabelDice3);
    }//GEN-LAST:event_jLabelDice3MousePressed

    private void jLabelDice4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDice4MousePressed
        jLabelDiceMouseClicked(jLabelDice4);
    }//GEN-LAST:event_jLabelDice4MousePressed

    private void jLabelDice5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDice5MousePressed
        jLabelDiceMouseClicked(jLabelDice5);
    }//GEN-LAST:event_jLabelDice5MousePressed

    private void jSpinnerPlayersStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerPlayersStateChanged
        jSpinnerPlayerChanged();
    }//GEN-LAST:event_jSpinnerPlayersStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonRoll;
    private javax.swing.JLabel jLabelDice1;
    private javax.swing.JLabel jLabelDice2;
    private javax.swing.JLabel jLabelDice3;
    private javax.swing.JLabel jLabelDice4;
    private javax.swing.JLabel jLabelDice5;
    private javax.swing.JLabel jLabelPlayers;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JPanel jPanelDice;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JSpinner jSpinnerPlayers;
    private javax.swing.JTable jTable;
    // End of variables declaration//GEN-END:variables
}
