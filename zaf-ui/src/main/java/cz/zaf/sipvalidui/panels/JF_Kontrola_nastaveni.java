/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

import cz.zaf.sipvalidator.nsesss2017.profily.ZakladniProfilValidace;

/**
 *
 * @author m000xz006159
 */
public class JF_Kontrola_nastaveni extends javax.swing.JFrame {

    final JFmain mainFrame;

    /**
     * Creates new form JF_Kontrola_nastaveni
     */
    public JF_Kontrola_nastaveni(JFmain main) {
        this.mainFrame = main;
        initComponents();
        setFramePosition();
        setIconImage("/icons/lupa-512.png");
        getContentPane().setBackground(Color.white);
        jLabel_progresbar.setText("VYBRÁNO " + main.getNahraneSoubory().size() + " SIP SOUBORŮ KE ZPRACOVÁNÍ");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField_idkontroly = new javax.swing.JTextField();
        jButton_SPlny = new javax.swing.JButton();
        jButton_SPrazdny = new javax.swing.JButton();
        jButton_Prejimka = new javax.swing.JButton();
        jLabel_progresbar = new javax.swing.JLabel();
        jProgressBar_kontrola = new javax.swing.JProgressBar();
        jTextField_davka = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setTitle("NASTAVENÍ KONTROLY");
        setAlwaysOnTop(true);

        jTextField_idkontroly.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField_idkontroly.setText("ZADEJTE ID KONTROLY");

        jButton_SPlny.setText("SIP PLNÝ");
        jButton_SPlny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SPlnyActionPerformed(evt);
            }
        });

        jButton_SPrazdny.setText("SIP PRÁZDNÝ");
        jButton_SPrazdny.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_SPrazdnyActionPerformed(evt);
            }
        });

        jButton_Prejimka.setText("PŘEJÍMKA");
        jButton_Prejimka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_PrejimkaActionPerformed(evt);
            }
        });

        jLabel_progresbar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel_progresbar.setText("VYBRÁNO 3000 SIP SOUBORŮ KE ZPRACOVÁNÍ");

        jProgressBar_kontrola.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar_kontrola.setForeground(new java.awt.Color(0, 0, 0));

        jTextField_davka.setText("1000");

        jLabel1.setText("xml max. sip");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_davka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField_idkontroly)
                    .addComponent(jProgressBar_kontrola, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jButton_SPrazdny, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(jButton_SPlny, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jButton_Prejimka, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel_progresbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField_idkontroly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_SPrazdny)
                    .addComponent(jButton_Prejimka)
                    .addComponent(jButton_SPlny))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_davka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel_progresbar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jProgressBar_kontrola, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents    

    private void jButton_SPrazdnyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SPrazdnyActionPerformed
        // typ 1 
        zamkni();
        mainFrame.setVelikostDavky(get_davka());
        jLabel_progresbar.setText("ZAČÍNÁM ZPRACOVÁVAT");
        mainFrame.do_contoll(ZakladniProfilValidace.SKARTACE_METADATA, jTextField_idkontroly.getText());
    }//GEN-LAST:event_jButton_SPrazdnyActionPerformed

    private void jButton_PrejimkaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_PrejimkaActionPerformed
        // typ 3
        zamkni();
        mainFrame.setVelikostDavky(get_davka());
        jLabel_progresbar.setText("ZAČÍNÁM ZPRACOVÁVAT");
        mainFrame.do_contoll(ZakladniProfilValidace.PREJIMKA, jTextField_idkontroly.getText());
    }//GEN-LAST:event_jButton_PrejimkaActionPerformed

    private void jButton_SPlnyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_SPlnyActionPerformed
        // typ 2
        zamkni();
        mainFrame.setVelikostDavky(get_davka());
        jLabel_progresbar.setText("ZAČÍNÁM ZPRACOVÁVAT");
        mainFrame.do_contoll(ZakladniProfilValidace.SKARTACE_UPLNY, jTextField_idkontroly.getText());
    }//GEN-LAST:event_jButton_SPlnyActionPerformed
    private void zamkni(){
        jButton_Prejimka.setEnabled(false);
        jButton_SPlny.setEnabled(false);
        jButton_SPrazdny.setEnabled(false);
        jTextField_idkontroly.setEnabled(false);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            //                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
                
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); // odkomentováním podmínky if a zakomentováním tohoto, návrat java look
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFmain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        /*java.awt.EventQueue.invokeLater(() -> {
            new JF_Kontrola_nastaveni().setVisible(true);
        });*/
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton_Prejimka;
    public javax.swing.JButton jButton_SPlny;
    public javax.swing.JButton jButton_SPrazdny;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel_progresbar;
    public static javax.swing.JProgressBar jProgressBar_kontrola;
    private javax.swing.JTextField jTextField_davka;
    private javax.swing.JTextField jTextField_idkontroly;
    // End of variables declaration//GEN-END:variables

    // nastaví pozici Frame do středu obrazovky.
    private void setFramePosition(){        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 4);
        this.setLocation(x, y);       
    }
    
    // nastaví ikonu Frame
    private void setIconImage(String getResource) {
        // String musí být ve tvaru "/ikony/validIco.png", kde "ikony" je název balíku.
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(getResource)));      
    }
    
    private int get_davka(){
        String string = jTextField_davka.getText();
        if(string == null || string.equals("1000") || string.equals("")){
            return 1000;
        }
        else{
            try{
             int d = Integer.parseInt(string);
             return d;
            }catch(NumberFormatException e){
                return 1000;
            }
        }
    }

}