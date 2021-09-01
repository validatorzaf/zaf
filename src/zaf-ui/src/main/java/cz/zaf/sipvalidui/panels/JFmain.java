/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;

import static cz.zaf.sipvalidui.panels.JPanelValidace.jCheckBoxObsahovaNa;
import static cz.zaf.sipvalidui.panels.JPanelValidace.jListObsahovaNA;
import static cz.zaf.sipvalidui.panels.JPanelValidace.jTextAreaPopisPravidel;
import static cz.zaf.sipvalidui.panels.JPanelValidace.jTextAreaPredKontrola;
import static cz.zaf.sipvalidui.panels.JPanel_Kontrola_analyza.jTextArea_kontrola;
import static java.lang.Math.round;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import cz.zaf.sipvalidator.helper.Helper;
import cz.zaf.sipvalidator.nsesss2017.SipValidator;
import cz.zaf.sipvalidator.nsesss2017.profily.ProfilValidace;
import cz.zaf.sipvalidator.sip.SipInfo;
import cz.zaf.sipvalidator.sip.SipInfo.SipType;
import cz.zaf.sipvalidator.sip.SipLoader;
import cz.zaf.sipvalidator.sip.XmlProtokolWriter;
import cz.zaf.sipvalidui.analysis.Analys;
import cz.zaf.sipvalidui.openFiles.SIP_Opener;

/**
 *
 * @author m000xz006159
 */
public class JFmain extends javax.swing.JFrame {
    //zásobník pro nahrané soubory
    //    static SeznamOtevrenychSouboru seznamNahranychSouboru;
    public static String program_name = "SipValid", program_version = "20190918";
    EventListenerJFmainTableSeSipSoubory listenerTableSeSip;
    public static DefaultTableModel tabulkaSIPsouboru;

    NahraneSoubory nahraneSoubory = new NahraneSoubory();

    /**
     * Maximalni pocet SIPu v davce ke zpracovani
     */
    private int velikostDavky = 1000;
    private ProfilValidace profilValidace;
    static JF_Kontrola_nastaveni jk;
    public static ProgressWorker worker;
    public static int progresbar_index = 0, progresbar_pocet = 0, main_pocitadlo = 0;
    public static String progresbar_textforlabel = "";
    //public static int zvoleny_typ_kontroly = 1; // přejímka 3, prázdný 1, plný 2 - kvůli výpisu modelu obsahové

    static ListSelectionModel listSelection;
    public static String cesta_xml_web = "Soubor zatím nebyl vytvořen.";

    /**
     * Creates new form JFmain
     */
    public JFmain() {
        initComponents();
        // nastavení barvy pozadí Frame
        getContentPane().setBackground(Color.white);
        // nastavení velikosti Frame
        setExtendedState(JFmain.MAXIMIZED_BOTH);
        // nastavení pozice Frame
        setFramePosition();
        // nastaví ikonu Frame. Jako parametr zadej např. "/ikony/validIco.png", /balík/ikona.png
        setIconImage("/icons/lupa-512.png");
        setTitle(program_name + program_version);
        // nastavení velikosti jSplitPane
        Dimension d = new Dimension(400, 1000);
        Dimension d2 = new Dimension(260, 1000);
        jSplitPane1.getLeftComponent().setPreferredSize(d);
        jSplitPane1.getLeftComponent().setMinimumSize(d2);
        jSplitPane1.addPropertyChangeListener((PropertyChangeEvent pce) -> {
            nastavTabulku(tabulkaSIPsouboru, jSplitPane1.getLeftComponent().getWidth());
        });
        jMenuItem1.setEnabled(false);

        jTabbedPane.addTab("SIP validace", new JPanelValidace(nahraneSoubory));
        jTabbedPane.addTab("Analýza kontroly", new JPanel_Kontrola_analyza());
        //        jTabbedPane.addTab("SIP info", new JPanelSipInformation());
        //        jTabbedPane.addTab("Skartační návrh", new JPanelSkartacniNavrh());
        //        jTabbedPane.addTab("Dokumenty Entity", new JPanelSkartacniNavrhDokumentyEntit());

        tabulkaSIPsouboru = new DefaultTableModel() {
            // překrytí metody, abych nemohl editovat nějaké sloupce v tabulce
            @Override
            public boolean isCellEditable(int row, int column) {
                // editovat by šel pouze první (index)
                //                return column == 1;
                return false;
            }
        };

        jTable1.setModel(tabulkaSIPsouboru);

        nastavTabulku(tabulkaSIPsouboru, jSplitPane1.getLeftComponent().getMinimumSize().width);

        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelection = jTable1.getSelectionModel();
        listSelection.addListSelectionListener(new EventListenerJFmainTableSeSipSoubory(jTextAreaPredKontrola,
                jTextAreaPopisPravidel, jTable1, jListObsahovaNA, jCheckBoxObsahovaNa, this));

        //        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    }

    private void nastavTabulku(DefaultTableModel model, int width) {
        model.setColumnCount(5);

        // zarovnání textu do středu
        DefaultTableCellRenderer defTableCellRendererCenterAlighment = new DefaultTableCellRenderer();
        defTableCellRendererCenterAlighment.setHorizontalAlignment(SwingConstants.CENTER);
        // tohle pak k příslušnému sloupci: jTable1.getColumnModel().getColumn(1).setCellRenderer(defTableCellRendererCenterAlighment);

        //sloupec první z leva
        //hlavička
        jTable1.getColumnModel().getColumn(0).setHeaderValue("Poř.č.");
        jTable1.getColumnModel().getColumn(0).setMaxWidth(40);
        jTable1.getColumnModel().getColumn(0).setMinWidth(40);

        // sloupec druhý z leva
        //hlavička
        jTable1.getColumnModel().getColumn(1).setHeaderValue("Typ");
        jTable1.getColumnModel().getColumn(1).setMaxWidth(30);
        jTable1.getColumnModel().getColumn(1).setMinWidth(30);
        jTable1.getColumnModel().getColumn(1).setCellRenderer(defTableCellRendererCenterAlighment);
        // sloupec třetí z leva
        //hlavička
        jTable1.getColumnModel().getColumn(2).setHeaderValue("Sk. znak");
        jTable1.getColumnModel().getColumn(2).setMaxWidth(65);
        jTable1.getColumnModel().getColumn(2).setMinWidth(35);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(35);
        jTable1.getColumnModel().getColumn(2).setCellRenderer(defTableCellRendererCenterAlighment);

        // sloupec 4 z leva
        //hlavička
        jTable1.getColumnModel().getColumn(3).setHeaderValue("Název souboru");
        jTable1.getColumnModel().getColumn(3).setMinWidth(85);
        //šířka je 400-2 = 398 => 398 - ostatní sloupce = šířka názvu

        // sloupec 5 z leva
        //hlavička
        jTable1.getColumnModel().getColumn(4).setHeaderValue("Velikost");
        jTable1.getColumnModel().getColumn(4).setMaxWidth(70);
        jTable1.getColumnModel().getColumn(4).setMinWidth(70);

        int a, b, c, d, e;
        a = jTable1.getColumnModel().getColumn(0).getWidth();
        b = jTable1.getColumnModel().getColumn(1).getWidth();
        c = jTable1.getColumnModel().getColumn(2).getPreferredWidth();
        e = jTable1.getColumnModel().getColumn(4).getWidth();
        d = width - (a + b + c + e);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(d);

        // speciální nastavení pro jTable
        // dvojí kliknutí na tableHeader
        //        jTable1.getTableHeader().addMouseListener(new MouseAdapter() {
        //            @Override
        //            public void mouseClicked(MouseEvent e){
        //                if(e.getClickCount() == 2){
        //                    System.out.println("dvojí kliknutí");
        //                    int col = jTable1.columnAtPoint(e.getPoint()); // získá na jaký sloupec jsem klikl
        //                    System.err.println(col);
        //                }    
        //            }           
        //        });

    }

    // nastaví pozici Frame do středu obrazovky.
    private void setFramePosition() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);
    }

    // nastaví ikonu Frame
    private void setIconImage(String getResource) {
        // String musí být ve tvaru "/ikony/validIco.png", kde "ikony" je název balíku.
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(getResource)));
    }

    //    private void setJMenuZobrazeniCHeck(){
    //        jMenu1.
    //    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTabbedPane = new javax.swing.JTabbedPane();
        jMenuBar = new javax.swing.JMenuBar();
        jMenuBarSoubor = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuSouborSmazatSipSoubory = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuBarKontroly = new javax.swing.JMenu();
        jMenuKontrolyNovakontrola = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                                         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                 .addGap(0, 100, Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
                                       jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                               .addGap(0, 100, Short.MAX_VALUE));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                                         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                 .addGap(0, 100, Short.MAX_VALUE));
        jPanel2Layout.setVerticalGroup(
                                       jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                               .addGap(0, 100, Short.MAX_VALUE));

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
                                         jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                 .addGap(0, 400, Short.MAX_VALUE));
        jFrame1Layout.setVerticalGroup(
                                       jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                               .addGap(0, 300, Short.MAX_VALUE));

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
                                         jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                 .addGap(0, 400, Short.MAX_VALUE));
        jFrame2Layout.setVerticalGroup(
                                       jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                               .addGap(0, 300, Short.MAX_VALUE));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jSplitPane1.setMinimumSize(new java.awt.Dimension(400, 25));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {

                }));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(jTable1);

        jSplitPane1.setLeftComponent(jScrollPane2);
        jSplitPane1.setRightComponent(jTabbedPane);

        jMenuBar.setBackground(new java.awt.Color(255, 255, 0));
        jMenuBar.setForeground(new java.awt.Color(255, 102, 0));
        jMenuBar.setToolTipText("");

        jMenuBarSoubor.setText("Soubor");

        jMenuItem2.setText("Otevři SIP (mets.xml, sip(zip), sip(dir))");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuBarSoubor.add(jMenuItem2);

        jMenuSouborSmazatSipSoubory.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE,
                                                                                      0));
        jMenuSouborSmazatSipSoubory.setText("Smazat seznam SIP");
        jMenuSouborSmazatSipSoubory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuSouborSmazatSipSouboryActionPerformed(evt);
            }
        });
        jMenuBarSoubor.add(jMenuSouborSmazatSipSoubory);

        jMenuItem1.setText("Hide Seznam SIP");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuBarSoubor.add(jMenuItem1);

        jMenuBar.add(jMenuBarSoubor);

        jMenuBarKontroly.setText("Kontroly");
        jMenuBarKontroly.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuBarKontrolyMouseClicked(evt);
            }
        });

        jMenuKontrolyNovakontrola.setText("Nová kontrola");
        jMenuKontrolyNovakontrola.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuKontrolyNovakontrolaActionPerformed(evt);
            }
        });
        jMenuBarKontroly.add(jMenuKontrolyNovakontrola);

        jMenuBar.add(jMenuBarKontroly);

        setJMenuBar(jMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                          .addGroup(layout.createSequentialGroup()
                                                  .addContainerGap()
                                                  .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 973,
                                                                Short.MAX_VALUE)
                                                  .addContainerGap()));
        layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout
                                                .createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560,
                                                              Short.MAX_VALUE)
                                                .addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nactiSouboryDoTabulkySeSeznamem() {
        // důležité pro následné přidávání případných dalších souborů, 
        // aby se tabulka nemusela refreshovat celá znovu. Začnu prostě 
        // u posledního souboru, jež nebyl ještě přidán
        int pocetJizNahranychSouboru = nahraneSoubory.size();

        int pocetJizPridanychSIPvTabulce = tabulkaSIPsouboru.getRowCount();

        // jestliže už v tabulce mám vypsáno 20 souborů ze seznamNahranychSouboru, 
        // musím začít až od souboru 21, čili cyklus proběhne o 20 méně cyklů;
        int pocetProCyklus = pocetJizNahranychSouboru;

        for (int i = pocetJizPridanychSIPvTabulce; i < pocetProCyklus; i++) {
            SipLoader sipLoader = nahraneSoubory.get(i);
            pridejRadekDoTabulky(i, sipLoader);
        }
    }

    private Object[] pridejRadekDoTabulky(int index, SipLoader sipLoader) {
        SipInfo sipInfo = sipLoader.getSip();
        String g = sipInfo.getName();
        Object[] row = new Object[] { " " + (index + 1) + ".", "???", "?" + "????", " " + sipInfo.getName(),
                getVelikostSipuProTabulku(sipInfo.getLenght()) };
        tabulkaSIPsouboru.addRow(row);
        return row;
    }

    static String getVelikostSipuProTabulku(long sipSizeLong) {
        int sipSizeInt = (int) sipSizeLong;
        int sipSizeLenght = Integer.toString(sipSizeInt).length();
        String vysledek;

        if (sipSizeLenght < 4) {
            vysledek = " " + Integer.toString(sipSizeInt);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci vrátit pouze první zaokrouhlenou číslici (takže mě zajímají jen první dvě.
        if (sipSizeLenght == 4 || sipSizeLenght == 7 || sipSizeLenght == 10) {
            String s = Integer.toString(sipSizeInt).substring(0, 2);
            int v = round(Integer.parseInt(s) / 10);
            vysledek = " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci 2 (např 20 kB)
        if (sipSizeLenght == 5 || sipSizeLenght == 8) {
            String s = Integer.toString(sipSizeInt).substring(0, 3);
            int v = round(Integer.parseInt(s) / 10);
            vysledek = " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci např 200 kB
        if (sipSizeLenght == 6 || sipSizeLenght == 9) {
            String s = Integer.toString(sipSizeInt).substring(0, 4);
            int v = round(Integer.parseInt(s) / 10);
            vysledek = " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        return "nezjistěno";

    }

    static String pridejJednotkyProVelikostSipSouboru(long sipSizeLong) {
        String b = " B";
        String kb = " kB";
        String mb = " MB";
        String gb = " GB";
        if (sipSizeLong < 4)
            return b;
        if (sipSizeLong == 4 || sipSizeLong == 5 | sipSizeLong == 6)
            return kb;
        if (sipSizeLong > 6 && sipSizeLong != 10)
            return mb;
        return gb;
    }

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        if (jSplitPane1.getLeftComponent().isVisible()) {
            jSplitPane1.getLeftComponent().setVisible(false);
            jSplitPane1.setDividerLocation(0);
        } else {
            jSplitPane1.getLeftComponent().setVisible(true);
            jSplitPane1.setDividerLocation(401);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void resetAllRow() {
        int radky = tabulkaSIPsouboru.getRowCount();
        for (int i = 0; i < radky; i++) {
            tabulkaSIPsouboru.removeRow(0);
        }
        nahraneSoubory.clear();
    }


    public static String getSipTypeShortcut(SipType sipType) {

        if (sipType == SipType.DOKUMENT) {
            return "DOK";
        }
        if (sipType == SipType.SPIS) {
            return "SPI";
        }
        if (sipType == SipType.DIL) {
            return "DÍL";
        }

        return " - ";
    }

    static void resetRow(int i, SipInfo souborSIP) {
        //        if(!souborSIP.get_SIP_Parsed_Information().isEmpty()){
        tabulkaSIPsouboru.removeRow(i);
        Object[] o = new Object[] { " " + (i + 1) + ".", getSipTypeShortcut(souborSIP.getType()),
                souborSIP.getSKznak() + souborSIP.getSKlhuta(), " " + souborSIP.getName(), getVelikostSipuProTabulku(
                                                                                                                     souborSIP
                                                                                                                             .getLenght()) };
        tabulkaSIPsouboru.insertRow(i, o);
        //        }

    }

    private void jMenuBarKontrolyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBarKontrolyMouseClicked

    }//GEN-LAST:event_jMenuBarKontrolyMouseClicked

    private void jMenuSouborSmazatSipSouboryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSouborSmazatSipSouboryActionPerformed
        resetAllRow();
    }//GEN-LAST:event_jMenuSouborSmazatSipSouboryActionPerformed

    // OTEVŘI SIP
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        //        try {
        //            new Sip_Open_Web().getOpenFiles();
        SIP_Opener.getOpenFiles(this);
        nactiSouboryDoTabulkySeSeznamem();
        //        } catch (IOException | InterruptedException | ZipException ex) {
        //            Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex);
        //        }

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuKontrolyNovakontrolaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuKontrolyNovakontrolaActionPerformed
        jk = new JF_Kontrola_nastaveni(this);
        jk.setVisible(true);
    }//GEN-LAST:event_jMenuKontrolyNovakontrolaActionPerformed

    private void provedKontrolu(ProfilValidace profilValidace, String id_kontroly_zadane) {

        long start_k = 0, end_k = 0;
        JF_Kontrola_nastaveni.jProgressBar_kontrola.setStringPainted(true);
        worker = new ProgressWorker(JF_Kontrola_nastaveni.jProgressBar_kontrola,
                JF_Kontrola_nastaveni.jLabel_progresbar);

        progresbar_pocet = nahraneSoubory.size();
            start_k = System.currentTimeMillis();
        for (int i = 0; i < nahraneSoubory.size(); i++) {
            SipLoader sl = nahraneSoubory.get(i);
                SipInfo svf = sl.getSip();
                svf.reset_data_Kontroly();
                
                SipValidator spv = new SipValidator(profilValidace, Collections.emptyList());
                spv.validate(sl);

                resetRow(i, svf); // přepsání hodnoty v tabulce sipsouborů

                try {
                    progresbar_index = i + 1;
                    progresbar_textforlabel = svf.getName();
                    worker.doInBackground();
                } catch (Exception ex) {
                    Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            progresbar_textforlabel = "VYTVÁŘÍM XML S VÝSLEDKY KONTROLY...";

            String ads = id_kontroly_zadane;
            if (id_kontroly_zadane == null || id_kontroly_zadane.isEmpty()) {
                ads = Helper.getActualDateString();
            }
            ulozXml(ads, profilValidace, id_kontroly_zadane);
            listSelection.clearSelection();
            end_k = System.currentTimeMillis();
            jTextArea_kontrola.setCaretPosition(0);


        worker.done();
        worker.execute();
        jk.setVisible(false);
        //        Analys_kontrola analys = new Analys_kontrola(seznamNahranychSouboru, JPanel_Kontrola_analyza.jTextArea_kontrola, "id_test_01", "test Kontroly", start_k, end_k);
        Analys analys1 = new Analys(JPanel_Kontrola_analyza.jTextArea_kontrola, id_kontroly_zadane,
                profilValidace.getNazev(),
                start_k, end_k,
                nahraneSoubory);

    }

    public void do_contoll(ProfilValidace profilValidace, String id_kontroly_zadane) {
        this.profilValidace = profilValidace;
        Thread t1 = new Thread(() -> provedKontrolu(profilValidace, id_kontroly_zadane));
        t1.start();
    }

    public void addSip(SipLoader sipLoader) {
        SipInfo sip = sipLoader.getSip();
        String sipName = sip.getName();

        if (!nahraneSoubory.isLoaded(sipName)) {
            nahraneSoubory.add(sipLoader);
            pridejRadekDoTabulky(sip);
        }
    }

    private boolean is_in_list(String sipName, ArrayList<SipLoader> seznamSIP) {
        for (SipLoader sipLoader : seznamSIP) {
            if (sipName.equals(sipLoader.getSip().getName())) {
                return true;
            }
        }
        return false;
    }

    private Object[] pridejRadekDoTabulky(SipInfo souborSIP) {
        String g = souborSIP.getName();
        int index = JFmain.tabulkaSIPsouboru.getRowCount();
        Object[] row = new Object[] { " " + (index + 1) + ".", "???", "?" + "????", " " + souborSIP.getName(),
                getVelikostSipuProTabulku(souborSIP.getLenght()) };
        JFmain.tabulkaSIPsouboru.addRow(row);
        return row;
    }

    void ulozXml(String ads, ProfilValidace profilValidace, String id_kontroly_zadane) {
        int ind = 0;
        for (int start = 0; start < nahraneSoubory.size(); start += velikostDavky) {
            int end = Math.min(start + velikostDavky, nahraneSoubory.size());

            List<SipInfo> cola = nahraneSoubory.getSipy(start, end);

            cola.forEach(si -> {
                Path src = si.getCesta();
                if (src != null) {
                    Path outputPath;
                    if (Files.isDirectory(src)) {
                        outputPath = src.getParent().resolve(src.getFileName() + ".xml");
                    } else {
                        outputPath = src.resolveSibling(".xml");
                    }
                    try(XmlProtokolWriter pw = new XmlProtokolWriter(outputPath.toAbsolutePath().toString(), 
                                                                     id_kontroly_zadane, profilValidace);)
                    {
                        pw.writeVysledek(si);
                    } catch (Exception ex) {
                        Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            ind++;
        }
    }

    /**
     * @param args
     *            the command line arguments
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void main(String args[]) throws IOException, InterruptedException {
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFmain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //        boolean s_vypisem = false;
        //        String identifikator_kontroly = args[0];
        //        String path_to_sip = args[1];
        //        String path_for_xml = args[6];
        //        String path_for_unzip = args[5];
        //        String typ_kontroly = args[2];
        //        String eset_welldone = args[3];
        //        String eset_error = args[4];
        //        String prepinac_cetnosti = args[7];
        //        if(args.length > 8){
        //            davka = get_davka(args[8]);
        //        }
        //        if(args.length == 10){
        //            s_vypisem = args[9].equals("true");
        //        }
        //        
        //        
        //        seznamNahranychSouboru = new ArrayList<>();
        //        program_name = "SipValid_CLI";
        //        if(s_vypisem){
        //            SIP_Opener.getOpenFiles_web_vypis(new File(path_to_sip), prepinac_cetnosti, path_for_unzip);
        //        }
        //        else{
        //            SIP_Opener.getOpenFiles_web(new File(path_to_sip), prepinac_cetnosti, path_for_unzip);
        //        }
        //        
        //        if(typ_kontroly.equals("1")){
        //            zvoleny_typ_kontroly =1;
        //            if(s_vypisem){
        //                do_contoll_web_vypis("skartační řízení (jen metadata)", seznam_Prazdny, identifikator_kontroly, path_for_xml, eset_welldone, eset_error);
        //            }
        //            else{
        //                do_contoll_web("skartační řízení (jen metadata)", seznam_Prazdny, identifikator_kontroly, path_for_xml, eset_welldone, eset_error);
        //            }   
        //        }
        //        if(typ_kontroly.equals("3")){
        //            zvoleny_typ_kontroly =3;
        //            if(s_vypisem){
        //                do_contoll_web_vypis("přejímka", seznam_Prejimka, identifikator_kontroly, path_for_xml, eset_welldone, eset_error);
        //            }
        //            else{
        //                do_contoll_web("přejímka", seznam_Prejimka, identifikator_kontroly, path_for_xml, eset_welldone, eset_error);
        //            }
        //        }
        //        if(typ_kontroly.equals("2")){
        //            zvoleny_typ_kontroly =2;
        //            if(s_vypisem){
        //                do_contoll_web_vypis("skartační řízení (s komponentami)", seznam_Plny, identifikator_kontroly, path_for_xml, eset_welldone, eset_error);
        //            }
        //            else{
        //                do_contoll_web("skartační řízení (s komponentami)", seznam_Plny, identifikator_kontroly, path_for_xml, eset_welldone, eset_error);
        //            }
        //            
        //        }

        //        /* Create and display the form */

        java.awt.EventQueue.invokeLater(() -> {
            new JFmain().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JMenuBar jMenuBar;
    private javax.swing.JMenu jMenuBarKontroly;
    private javax.swing.JMenu jMenuBarSoubor;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuKontrolyNovakontrola;
    private javax.swing.JMenuItem jMenuSouborSmazatSipSoubory;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    public static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    public void setVelikostDavky(int velikostDavky) {
        this.velikostDavky = velikostDavky;
    }

    /**
     * Vrati posledni profil validace
     * 
     * @return
     */
    ProfilValidace getProfilValidace() {
        return profilValidace;
    }

    public NahraneSoubory getNahraneSoubory() {
        return nahraneSoubory;
    }
}
