/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

/**
 *
 * @author m000xz006159
 */
public class JPanel_Kontrola_analyza extends javax.swing.JPanel {

    /**
     * Creates new form JPanel_Kontrola_analyza
     */
    public JPanel_Kontrola_analyza() {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea_kontrola = new javax.swing.JTextArea();

        jTextArea_kontrola.setEditable(false);
        jTextArea_kontrola.setColumns(20);
        jTextArea_kontrola.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        jTextArea_kontrola.setRows(5);
        jTextArea_kontrola.setToolTipText("");
        jTextArea_kontrola.setWrapStyleWord(true);
        jTextArea_kontrola.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane1.setViewportView(jTextArea_kontrola);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea jTextArea_kontrola;
    // End of variables declaration//GEN-END:variables
}