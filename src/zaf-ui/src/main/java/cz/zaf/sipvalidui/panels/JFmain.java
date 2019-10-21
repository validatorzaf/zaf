/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.zaf.sipvalidui.panels;


import static cz.zaf.sipvalidui.panels.JPanelValidace.jTextAreaPredKontrola;
import static java.lang.Math.round;
import static cz.zaf.sipvalidui.panels.JPanelValidace.jCheckBoxObsahovaNa;
import static cz.zaf.sipvalidui.panels.JPanelValidace.jListObsahovaNA;
import static cz.zaf.sipvalidui.panels.JPanelValidace.jTextAreaPopisPravidel;
import static cz.zaf.sipvalidui.panels.JPanel_Kontrola_analyza.jTextArea_kontrola;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import cz.zaf.sipvalid.helper.Helper;
import cz.zaf.sipvalid.helper.HelperTime;
import cz.zaf.sipvalid.sip.KontrolaContext;
import cz.zaf.sipvalid.sip.SIP_MAIN;
import cz.zaf.sipvalid.sip.SIP_MAIN_helper;
import cz.zaf.sipvalid.sip.UrovenKontroly;
import cz.zaf.sipvalid.sip.SipValidator;
import cz.zaf.sipvalid.validator.RozparsovaniSipSouboru;
import cz.zaf.sipvalidui.analysis.Analys;
import cz.zaf.sipvalidui.openFiles.SIP_Opener;
import cz.zaf.sipvalid.xml_creator.Create_xml;


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
    public static ArrayList<SIP_MAIN> seznamNahranychSouboru;
    static int davka = 1000;
    static JF_Kontrola_nastaveni jk;
    public static ProgressWorker worker;
    public static int progresbar_index = 0, progresbar_pocet = 0, main_pocitadlo = 0;
    public static String progresbar_textforlabel = "";
    public static int zvoleny_typ_kontroly = 1; // přejímka 3, prázdný 1, plný 2 - kvůli výpisu modelu obsahové
    
//    private int[] seznamVsechPravidelObsahovaNA2018 = IntStream.range(1, 99).toArray();
    

//    // 28
//    private int[] sipPlny2012ME = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43};
//    // 18, 19, 20
//    private int[] sipPlny2012NS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
//    
//    // 28
//    private int[] sipPlny2017ME = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58};
//    // 19, 20, 21
//    private int[] sipPlny2017NS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
//    
//    // 26-36, 43
//    private int[] sipPrazdny2012ME = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 37, 38, 39, 40, 41, 42};
//    // 1, 18-20
//    private int[] sipPrazdny2012NS = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
//    
//    // 26-36, 43, 44
//    private int[] sipPrazdny2017ME = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 37, 38, 39, 40, 41, 42, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58};
//    // 1, 19-21
//    private int[] sipPrazdny2017NS = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
//    
//    // 28
//    private int[] prejimka2012ME = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43};
//    // 28
//    private int[] prejimka2017ME = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58};
//    
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
  
        jTabbedPane.addTab("SIP validace", new JPanelValidace());
        jTabbedPane.addTab("Analýza kontroly", new JPanel_Kontrola_analyza());
//        jTabbedPane.addTab("SIP info", new JPanelSipInformation());
//        jTabbedPane.addTab("Skartační návrh", new JPanelSkartacniNavrh());
//        jTabbedPane.addTab("Dokumenty Entity", new JPanelSkartacniNavrhDokumentyEntit());
        
        //vytvoř místo pro očekávaný seznam souborů
        seznamNahranychSouboru = new ArrayList<>();

        tabulkaSIPsouboru = new DefaultTableModel(){
            // překrytí metody, abych nemohl editovat nějaké sloupce v tabulce
            @Override
            public boolean isCellEditable(int row, int column){
                // editovat by šel pouze první (index)
            //                return column == 1;
            return false;
            }    
        };
     
        jTable1.setModel(tabulkaSIPsouboru);
        
        nastavTabulku(tabulkaSIPsouboru, jSplitPane1.getLeftComponent().getMinimumSize().width);
        
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSelection = jTable1.getSelectionModel();
        listSelection.addListSelectionListener(new EventListenerJFmainTableSeSipSoubory(jTextAreaPredKontrola, jTextAreaPopisPravidel, jTable1, jListObsahovaNA, jCheckBoxObsahovaNa));
        
//        jScrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        
    }
    
    private void nastavTabulku(DefaultTableModel model, int width){
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
        
        int a,b,c,d,e;
        a = jTable1.getColumnModel().getColumn(0).getWidth();
        b = jTable1.getColumnModel().getColumn(1).getWidth();
        c = jTable1.getColumnModel().getColumn(2).getPreferredWidth();
        e = jTable1.getColumnModel().getColumn(4).getWidth();
        d = width -(a+b+c+e);
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
    private void setFramePosition(){        
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
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jSplitPane1.setMinimumSize(new java.awt.Dimension(400, 25));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
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

        jMenuSouborSmazatSipSoubory.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
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
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 560, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nactiSouboryDoTabulkySeSeznamem(){
        // důležité pro následné přidávání případných dalších souborů, aby se tabulka nemusela refreshovat celá znovu. Začnu prostě u posledního souboru, jež nebyl ještě přidán
        int pocetJizNahranychSouboru = seznamNahranychSouboru.size(); if(seznamNahranychSouboru.isEmpty()) pocetJizNahranychSouboru = 0;
        int pocetJizPridanychSIPvTabulce = tabulkaSIPsouboru.getRowCount(); if(tabulkaSIPsouboru == null) pocetJizPridanychSIPvTabulce = 0;
        // jestliže už v tabulce mám vypsáno 20 souborů ze seznamNahranychSouboru, musím začít až od souboru 21, čili cyklus proběhne o 20 méně cyklů;
        int pocetProCyklus = pocetJizNahranychSouboru;
        
        for(int i = pocetJizPridanychSIPvTabulce; i < pocetProCyklus; i++){
            SIP_MAIN sipSoubor = seznamNahranychSouboru.get(i);
            pridejRadekDoTabulky(i, sipSoubor);
        }  
    }
    
    private Object[] pridejRadekDoTabulky(int index, SIP_MAIN souborSIP){
        String g = souborSIP.getName();
        Object[] row = new Object[]{" " + (index+1) + ".", "???",  "?" + "????", " " + souborSIP.getName(), getVelikostSipuProTabulku(souborSIP.getLenght())};
        tabulkaSIPsouboru.addRow(row);
        return row;
    }
    
    static String getVelikostSipuProTabulku(long sipSizeLong){
        int sipSizeInt = (int) sipSizeLong;
        int sipSizeLenght = Integer.toString(sipSizeInt).length();
        String vysledek;
        
        if(sipSizeLenght < 4){
            vysledek = " " + Integer.toString(sipSizeInt);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci vrátit pouze první zaokrouhlenou číslici (takže mě zajímají jen první dvě.
        if(sipSizeLenght == 4 || sipSizeLenght == 7 || sipSizeLenght == 10){
            String s = Integer.toString(sipSizeInt).substring(0, 2);
            int v = round(Integer.parseInt(s) / 10);            
            vysledek =  " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci 2 (např 20 kB)
        if(sipSizeLenght == 5 || sipSizeLenght == 8){
            String s = Integer.toString(sipSizeInt).substring(0, 3);
            int v = round(Integer.parseInt(s) / 10);
            vysledek =  " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        }
        // chci např 200 kB
        if(sipSizeLenght == 6 || sipSizeLenght == 9){
            String s = Integer.toString(sipSizeInt).substring(0, 4);
            int v = round(Integer.parseInt(s) / 10);
            vysledek =  " " + Integer.toString(v);
            return vysledek + pridejJednotkyProVelikostSipSouboru(sipSizeLenght);
        } 
        return "nezjistěno";
        
    }
    
    static String pridejJednotkyProVelikostSipSouboru(long sipSizeLong){
        String b = " B";
        String kb = " kB";
        String mb = " MB";
        String gb = " GB";
        if(sipSizeLong < 4) return b;
        if(sipSizeLong == 4 || sipSizeLong == 5 | sipSizeLong == 6) return kb;
        if(sipSizeLong > 6 && sipSizeLong != 10) return mb;    
        return gb;
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed

        if(jSplitPane1.getLeftComponent().isVisible()){
        jSplitPane1.getLeftComponent().setVisible(false);
        jSplitPane1.setDividerLocation(0);
        }
        else{
            jSplitPane1.getLeftComponent().setVisible(true);
            jSplitPane1.setDividerLocation(401);
        }  
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void resetAllRow(){
        int radky = tabulkaSIPsouboru.getRowCount();
        for(int i = 0; i < radky; i++){
            tabulkaSIPsouboru.removeRow(0);
        }
        seznamNahranychSouboru.clear();
    }
    static void resetRow(int i, SIP_MAIN souborSIP){
//        if(!souborSIP.get_SIP_Parsed_Information().isEmpty()){
            tabulkaSIPsouboru.removeRow(i);
            Object[] o = new Object[]{" " + (i+1) + ".", SIP_MAIN_helper.get_SIP_type_XXX(souborSIP.getType()),  souborSIP.getSKznak() + souborSIP.getSKlhuta()," " + souborSIP.getName(), getVelikostSipuProTabulku(souborSIP.getLenght())};
            tabulkaSIPsouboru.insertRow(i, o);
//        }
  
    }

    private void jMenuBarKontrolyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuBarKontrolyMouseClicked

    }//GEN-LAST:event_jMenuBarKontrolyMouseClicked

    private void jMenuSouborSmazatSipSouboryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuSouborSmazatSipSouboryActionPerformed
        if(seznamNahranychSouboru != null){
            resetAllRow();
            
//            for(int i = 0; i < seznamNahranychSouboru.size(); i++){
//                
//               
//            }
        }
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
        jk = new JF_Kontrola_nastaveni();
        jk.setVisible(true);
    }//GEN-LAST:event_jMenuKontrolyNovakontrolaActionPerformed

    
    private void pridejSloupecDoTabulky(String nazevSloupce, boolean zarovnatNaStred){
        TableColumn tc = new TableColumn();
        tc.setHeaderValue(nazevSloupce);
        tc.setMinWidth(40);
        jTable1.addColumn(tc);
        
        if(zarovnatNaStred){
            DefaultTableCellRenderer defTableCellRendererCenterAlighment = new DefaultTableCellRenderer();
            defTableCellRendererCenterAlighment.setHorizontalAlignment(SwingConstants.CENTER);
            jTable1.getColumnModel().getColumn(jTable1.getColumnCount() - 1).setCellRenderer(defTableCellRendererCenterAlighment);
        }
        

    }
    
    public static void do_contoll(String typ_kontroly, int[] seznam, String id_kontroly_zadane){
        Thread t1 = new Thread(() -> 
        {
        long start_k=0, end_k=0;    
        JF_Kontrola_nastaveni.jProgressBar_kontrola.setStringPainted(true);
        worker = new ProgressWorker(JF_Kontrola_nastaveni.jProgressBar_kontrola, JF_Kontrola_nastaveni.jLabel_progresbar); 
        
        if(seznamNahranychSouboru != null){
            progresbar_pocet = seznamNahranychSouboru.size();
            String ads = id_kontroly_zadane;
            if(id_kontroly_zadane == null || id_kontroly_zadane.isEmpty()){
                ads = HelperTime.getActualDateString();
            }
            start_k = System.currentTimeMillis();
            for(int i = 0; i < seznamNahranychSouboru.size(); i++) {
                SIP_MAIN svf = seznamNahranychSouboru.get(i);
                svf.reset_data_Kontroly();
                
                List<UrovenKontroly> kontroly = SipValidator.pripravKontroly(true, null, seznam);
                try {
                    RozparsovaniSipSouboru rss = new RozparsovaniSipSouboru(svf);
                    resetRow(i, svf); // přepsání hodnoty v tabulce sipsouborů
                    
                    // provedeni kontrol
                    KontrolaContext ctx = new KontrolaContext(svf);
                    for(UrovenKontroly kontrola: kontroly) {
                    	kontrola.provedKontrolu(ctx);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex); 
                }
                
                
               try {
                    progresbar_index = i+1;
                    progresbar_textforlabel = svf.getName();
                    worker.doInBackground();
               } catch (Exception ex) {
                   Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex);
               }
            }
            progresbar_textforlabel = "VYTVÁŘÍM XML S VÝSLEDKY KONTROLY...";
            int ind = 0;
            String ads_i = ads;
            for(int start = 0; start < seznamNahranychSouboru.size(); start += davka){
                int end = Math.min(start + davka, seznamNahranychSouboru.size());
                List<SIP_MAIN> col = seznamNahranychSouboru.subList(start, end);
                ArrayList<SIP_MAIN> cola = new ArrayList();
                cola.addAll(col);
                try {
                    if(ind > 0) ads_i = ads + "_" + ind;
                    Create_xml c = new Create_xml(cola,"", ads_i, typ_kontroly, id_kontroly_zadane, seznam, program_name, program_version);
                    
                    if(ind == 0) cesta_xml_web = c.xml.getAbsolutePath();
                    else{
                        cesta_xml_web += " - " + c.xml.getName();
                    }
                } catch (TransformerException | IOException | ParserConfigurationException | SAXException ex) {
                   Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex);
                }
                ind++;

            }
            listSelection.clearSelection();
            end_k = System.currentTimeMillis();
            jTextArea_kontrola.setCaretPosition(0);
            
        }
        worker.done();
        worker.execute();
        jk.setVisible(false);
//        Analys_kontrola analys = new Analys_kontrola(seznamNahranychSouboru, JPanel_Kontrola_analyza.jTextArea_kontrola, "id_test_01", "test Kontroly", start_k, end_k);
        Analys analys1 = new Analys(JPanel_Kontrola_analyza.jTextArea_kontrola, id_kontroly_zadane, typ_kontroly, start_k, end_k);
        });  
        t1.start();
    }
    
    
    
    /**
     * @param args the command line arguments
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
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
    
    public static void do_contoll_web(String typ_kontroly, int[] seznam, String id_kontroly_zadane, String path_for_xml, String eset_welldone, String eset_errors){
        boolean eset;
        if(eset_welldone == null) eset = false;
        else eset = eset_welldone.equals("true");
        
        if(seznamNahranychSouboru != null){
            String ads = id_kontroly_zadane;
            if(id_kontroly_zadane == null || id_kontroly_zadane.isEmpty()){
                ads = HelperTime.getActualDateString();
            }
            for(int i = 0; i < seznamNahranychSouboru.size(); i++){
                SIP_MAIN svf = seznamNahranychSouboru.get(i);
                
                List<UrovenKontroly> kontroly = SipValidator.pripravKontroly(eset, eset_errors, seznam);
                
                try {
                    
                    // provedeni kontrol
                    KontrolaContext ctx = new KontrolaContext(svf);
                    for(UrovenKontroly kontrola: kontroly) {
                    	kontrola.provedKontrolu(ctx);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex); 
                }
            }
            int ind = 0;
            String ads_i = ads;
            for(int start = 0; start < seznamNahranychSouboru.size(); start += davka){
                int end = Math.min(start + davka, seznamNahranychSouboru.size());
                List<SIP_MAIN> col = seznamNahranychSouboru.subList(start, end);
                ArrayList<SIP_MAIN> cola = new ArrayList();
                cola.addAll(col);
                try {
                    if(ind > 0) ads_i = ads + "_" + ind;
                    Create_xml c = new Create_xml(cola,path_for_xml, ads_i, typ_kontroly, id_kontroly_zadane, seznam, program_name, program_version);
                    if(ind == 0) cesta_xml_web = c.xml.getAbsolutePath();
                    else{
                        cesta_xml_web += " - " + c.xml.getName();
                    }
                } catch (TransformerException | IOException | ParserConfigurationException | SAXException ex) {
                   Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);
                }
                ind++;

            }      
        }
    }
    
    public static void do_contoll_web_vypis(String typ_kontroly, int[] seznam, String id_kontroly_zadane, String path_for_xml, String eset_welldone, String eset_errors){
//        Thread t1 = new Thread(() -> {
            
//        worker = new ProgressWorker(JF_Kontrola_nastaveni.jProgressBar_kontrola, JF_Kontrola_nastaveni.jLabel_progresbar); 
        boolean eset;
        if(eset_welldone == null) eset = false;
        else eset = eset_welldone.equals("true");
        
        if(seznamNahranychSouboru != null){
//            progresbar_pocet = seznamNahranychSouboru.size();
            String ads = id_kontroly_zadane;
            if(id_kontroly_zadane == null || id_kontroly_zadane.isEmpty()){
                ads = HelperTime.getActualDateString();
            }
            System.out.println(HelperTime.getHodiny(System.currentTimeMillis()) + " ZAČÍNÁM S KONTROLAMI SOUBORŮ");
            System.out.println("");
            for(int i = 0; i < seznamNahranychSouboru.size(); i++){
                
                SIP_MAIN svf = seznamNahranychSouboru.get(i);
                System.out.println("* SOUBOR: " + svf.getName());
                
                List<UrovenKontroly> kontroly = SipValidator.pripravKontroly(eset, eset_errors, seznam);

                long zacatektestusouboru = System.currentTimeMillis();
                try {
                    boolean pokracuj_v_kontrole;
                    long st = System.currentTimeMillis();
                    RozparsovaniSipSouboru rss = new RozparsovaniSipSouboru(svf);
                    long en = System.currentTimeMillis();
                    System.out.println("    " + "Parsování: " + Helper.getDuration(st, en));
                    
                    
                    // provedeni kontrol
                    KontrolaContext ctx = new KontrolaContext(svf);
                    for(UrovenKontroly kontrola: kontroly) {
                        st = System.currentTimeMillis();

                        kontrola.provedKontrolu(ctx);
                        
                        en = System.currentTimeMillis();
                        
                        System.out.println("    " + kontrola.getNazev() + ": " + Helper.getDuration(st, en));
                    }
                                        
                } catch (Exception ex) {
                    Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex); 
                }
                long konectestusouboru = System.currentTimeMillis();
                
                System.out.println("* Soubor zkontrolován " + HelperTime.getHodiny(System.currentTimeMillis()) + "Doba: " + Helper.getDuration(zacatektestusouboru, konectestusouboru));
                System.out.println("");

//               try {
//                    progresbar_index = i+1;
//                    progresbar_textforlabel = svf.getName();
//                    worker.doInBackground();
//               } catch (Exception ex) {
//                   Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex);
//               }
            }
            System.out.println("");
            System.out.println(HelperTime.getHodiny(System.currentTimeMillis()) + " VŠECHNY SOUBORY BYLY ZKONTROLOVÁNY. ZAČÍNÁM SE ZAPISOVÁNÍM VÝSLEDKŮ DO XML.");
//            progresbar_textforlabel = "VYTVÁŘÍM XML S VÝSLEDKY KONTROLY...";
            int ind = 0;
            String ads_i = ads;
            
            long startxml = System.currentTimeMillis();
            
            for(int start = 0; start < seznamNahranychSouboru.size(); start += davka){
                int end = Math.min(start + davka, seznamNahranychSouboru.size());
                List<SIP_MAIN> col = seznamNahranychSouboru.subList(start, end);
                ArrayList<SIP_MAIN> cola = new ArrayList();
                cola.addAll(col);
                try {
                    if(ind > 0) ads_i = ads + "_" + ind;
                    Create_xml c = new Create_xml(cola,path_for_xml, ads_i, typ_kontroly, id_kontroly_zadane, seznam, program_name, program_version);
                    if(ind == 0) cesta_xml_web = c.xml.getAbsolutePath();
                    else{
                        cesta_xml_web += " - " + c.xml.getName();
                    }
                } catch (TransformerException | IOException | ParserConfigurationException | SAXException ex) {
                   Logger.getLogger(JFmain.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println(ex);
                }
                ind++;

            }
//            listSelection.clearSelection();
            long end_k = System.currentTimeMillis();
            System.out.println("");
            System.out.println(HelperTime.getHodiny(end_k) + " XML BYLO ZPRACOVÁNO." + "    " + "Doba: " + Helper.getDuration(startxml, end_k));
//            Analys_kontrola analys = new Analys_kontrola(seznamNahranychSouboru, JPanel_Kontrola_analyza.jTextArea_kontrola, "id_test_01", "test Kontroly", start_k, end_k);
//            jTextArea_kontrola.setCaretPosition(0);
            
        }
//        worker.done();
//        worker.execute();
//        jk.setVisible(false);
//        });  
//        t1.start();
//        System.exit(0);
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

    private static int get_davka(String string){
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
