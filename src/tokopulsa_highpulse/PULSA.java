/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tokopulsa_highpulse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FAWKES
 */
public class PULSA extends javax.swing.JInternalFrame {

    Connection conn;
    Statement stm;
    ResultSet rs;
    
    /**
     * Creates new form rockwell_what
     */
    public PULSA() {
        initComponents();
        siapIsi(false);
        tombolNormal();
        tampildaftarharga();
        stoksaldo();
       
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        txkodepulsa.setText(dateFormat.format(cal.getTime()));
    }
    
    public Connection setKoneksi(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost/tokopulsa_hp","root","");
            stm=conn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Koneksi Gagal :" +e);
        }
       return conn; 
    }
    
        private void kodenomer(){
        try {
            setKoneksi();
            String sql="select right (kodenomer,2)+1 from daftarharga ";
            ResultSet rs=stm.executeQuery(sql);
            if(rs.next()){
                rs.last();
                String no=rs.getString(1);
                while (no.length()<3){
                    no="0"+no;
                    txkodenomer.setText("KN"+no);}
                }
            else
            {
                txkodenomer.setText("KN001"); 
        }
        } catch (Exception e) 
        {
        } 
    }
    
    private void siapIsi(boolean a){
        txkodenomer.setEnabled(a);
        txkodepulsa.setEnabled(a);
        txhargasaldo.setEnabled(a);
        txhargajual.setEnabled(a);
        txincome.setEnabled(a);
    }
    
    private void tombolNormal(){
        bttambah.setEnabled(true);
        btsimpan.setEnabled(false);
        bthapus.setEnabled(false);
        btedit.setEnabled(false);
        btcari.setEnabled(true);
        
    }
    
    private void bersih(){
        txkodenomer.setText("");
        txkodepulsa.setText("");
        txhargasaldo.setText("");
        txhargajual.setText("");     
        txincome.setText("");
    }
    
    private void simpan(){
        try{
            setKoneksi();
            String sql="insert into daftarharga values('"+txkodenomer.getText()
                    +"','"+txkodepulsa.getText()
                    +"','"+txhargasaldo.getText()
                    +"','"+txhargajual.getText()
                    +"','"+txincome.getText() +"')";
            stm.executeUpdate(sql); 
            JOptionPane.showMessageDialog(null,"SIMPAN DATA BERHASIL :)");
            }
            catch (Exception e) {
        }
        tampildaftarharga();
       
    }
    
    private void perbarui(){
        try{
            setKoneksi();
            String sql="update daftarharga set kodenomer='"+txkodenomer.getText()
                    +"',kodepulsa='"+txkodepulsa.getText()
                    +"',hargasaldo='"+txhargasaldo.getText()
                    +"',hargajual='"+txhargajual.getText()
                    +"',income='"+txincome.getText()
                    +"' where kodenomer='"+txkodenomer.getText()+"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"EDIT DATA BERHASIL","HAVE A GOOD DAY:)",JOptionPane.INFORMATION_MESSAGE);
        } 
        catch(Exception e){
        }
        tampildaftarharga();
        
    }
    
    private void hapus(){
        try{
            String sql="delete from daftarharga where kodenomer='"+ txkodenomer.getText() +"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "HAPUS DATA SUKSES ");
            }
            catch (Exception e) {
            }
        tampildaftarharga();
    }
    
    public void tampildaftarharga(){
        Object header[]={"KDNO","KodePulsa","HargaSaldo","HargaJual","Income"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeldaftarharga.setModel(data);
        setKoneksi();
        String sql="select*from daftarharga";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }
    
    public void stoksaldo(){
    setKoneksi();
        String sql="SELECT * FROM stoksaldo";
        try{
            ResultSet rst = stm.executeQuery(sql);
            if (rst.next())
            {

            }
        } catch (SQLException ex) {
            Logger.getLogger(PULSA.class.getName()).log(Level.SEVERE, null, ex);
                }    
    }

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
        jLabel1 = new javax.swing.JLabel();
        txkodenomer = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txkodepulsa = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txhargasaldo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txhargajual = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txincome = new javax.swing.JTextField();
        btcari = new javax.swing.JButton();
        bttambah = new javax.swing.JButton();
        btsimpan = new javax.swing.JButton();
        btedit = new javax.swing.JButton();
        bthapus = new javax.swing.JButton();
        txpencarian = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabeldaftarharga = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("ENTRY DATA PULSA");

        jPanel1.setBackground(new java.awt.Color(127, 121, 104));

        jPanel2.setBackground(new java.awt.Color(127, 121, 104));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "INPUT SALDO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("KODE NOMER");

        txkodenomer.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txkodenomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txkodenomerActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("KODE PULSA");

        txkodepulsa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txkodepulsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txkodepulsaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("HARGA SALDO");

        txhargasaldo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txhargasaldo.setToolTipText("");
        txhargasaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txhargasaldoActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("HARGA JUAL");

        txhargajual.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txhargajual.setToolTipText("isi harga jual lalu tekan enter");
        txhargajual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txhargajualActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("INCOME");

        txincome.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btcari.setBackground(new java.awt.Color(199, 213, 216));
        btcari.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        btcari.setForeground(new java.awt.Color(51, 51, 51));
        btcari.setText("CARI");
        btcari.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (37).png"))); // NOI18N
        btcari.setPreferredSize(new java.awt.Dimension(87, 40));
        btcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcariActionPerformed(evt);
            }
        });

        bttambah.setBackground(new java.awt.Color(199, 213, 216));
        bttambah.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        bttambah.setForeground(new java.awt.Color(51, 51, 51));
        bttambah.setText("TAMBAH");
        bttambah.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (37).png"))); // NOI18N
        bttambah.setPreferredSize(new java.awt.Dimension(87, 40));
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });

        btsimpan.setBackground(new java.awt.Color(199, 213, 216));
        btsimpan.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        btsimpan.setForeground(new java.awt.Color(51, 51, 51));
        btsimpan.setText("SIMPAN");
        btsimpan.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (37).png"))); // NOI18N
        btsimpan.setPreferredSize(new java.awt.Dimension(87, 40));
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });

        btedit.setBackground(new java.awt.Color(199, 213, 216));
        btedit.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        btedit.setForeground(new java.awt.Color(51, 51, 51));
        btedit.setText("EDIT");
        btedit.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (37).png"))); // NOI18N
        btedit.setPreferredSize(new java.awt.Dimension(87, 40));
        btedit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bteditMouseClicked(evt);
            }
        });
        btedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bteditActionPerformed(evt);
            }
        });

        bthapus.setBackground(new java.awt.Color(199, 213, 216));
        bthapus.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        bthapus.setForeground(new java.awt.Color(51, 51, 51));
        bthapus.setText("HAPUS");
        bthapus.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/icon (37).png"))); // NOI18N
        bthapus.setPreferredSize(new java.awt.Dimension(87, 40));
        bthapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bthapusActionPerformed(evt);
            }
        });

        txpencarian.setFont(new java.awt.Font("Rockwell", 0, 11)); // NOI18N
        txpencarian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencarian.setToolTipText("Cari data berdasarkan kode ");
        txpencarian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txpencarianActionPerformed(evt);
            }
        });
        txpencarian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencarianKeyPressed(evt);
            }
        });

        tabeldaftarharga.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabeldaftarharga.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeldaftarhargaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabeldaftarharga);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txhargasaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txkodepulsa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txkodenomer, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txhargajual, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txincome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btcari, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txpencarian, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btedit, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bthapus, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bttambah, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txkodenomer)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txpencarian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btcari, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txkodepulsa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bttambah, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txhargasaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txhargajual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btedit, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txincome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(bthapus, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(57, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setBounds(0, 0, 865, 360);
    }// </editor-fold>//GEN-END:initComponents

    private void btcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcariActionPerformed
        // TODO add your handling code here:

        Object header[]={"KDNO","KodePulsa","HargaSaldo","HargaJual","Income"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeldaftarharga.setModel(data);
        setKoneksi();
        String sql="Select * from daftarharga where kodenomer like '%" + txpencarian.getText() + "%'" + "or kodepulsa like '%" +txpencarian.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);

                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
        stoksaldo();
    }//GEN-LAST:event_btcariActionPerformed

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        if(bttambah.getText().equalsIgnoreCase("tambah")){
            bttambah.setText("Refresh");
            bersih();
            siapIsi(true);

            txkodenomer.setEnabled(true);
            txkodepulsa.setEnabled(true);
            bttambah.setEnabled(true);
            btsimpan.setEnabled(true);
            bthapus.setEnabled(false);
            btedit.setEnabled(false);
            
            btcari.setEnabled(false);
            
        } else{
            bttambah.setText("Tambah");
            bersih();
            siapIsi(false);
            tombolNormal();
            tampildaftarharga();
            
        }
        stoksaldo();
    }//GEN-LAST:event_bttambahActionPerformed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        // TODO add your handling code here:
        if(txkodenomer.getText().isEmpty()||txkodepulsa.getText().isEmpty()|| txhargasaldo.getText().isEmpty()||txhargajual.getText().isEmpty()||txincome.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "LENGKAPI INPUTAN DATA!!!","",JOptionPane.INFORMATION_MESSAGE);
        } else{

            if(bttambah.getText().equalsIgnoreCase("Refresh")){
                if(bttambah.getText().equalsIgnoreCase("Refresh")){
                    simpan();
                } else{
                    JOptionPane.showMessageDialog(null, "SIMPAN DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if(btedit.getText().equalsIgnoreCase("batal")){
                if(btedit.getText().equalsIgnoreCase("batal")){
                    perbarui();
                } else{
                    JOptionPane.showMessageDialog(null, "EDIT DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            bersih();
            siapIsi(false);
            bttambah.setText("Tambah");
            btedit.setText("Edit");
            tombolNormal();
            stoksaldo();
        }
    }//GEN-LAST:event_btsimpanActionPerformed

    private void bteditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bteditActionPerformed
        // TODO add your handling code here:
        if(btedit.getText().equalsIgnoreCase("edit")){
            btedit.setText("Batal");
            siapIsi(true);
            txkodenomer.setEnabled(true);
            bttambah.setEnabled(true);
            
            btsimpan.setEnabled(true);
            bthapus.setEnabled(false);
            btedit.setEnabled(true);
            btcari.setEnabled(false);
        } else{
            btedit.setText("Edit");
            bersih();
            siapIsi(false);
            tombolNormal();
            stoksaldo();
        }
    }//GEN-LAST:event_bteditActionPerformed

    private void bthapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bthapusActionPerformed
        // TODO add your handling code here:
        int pesan=JOptionPane.showConfirmDialog(null, "YAKIN DATA AKAN DIHAPUS ?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(pesan==JOptionPane.YES_OPTION){
            if(pesan==JOptionPane.YES_OPTION){
                hapus();
                bersih();
                siapIsi(false);
                tombolNormal();
            } else{
                JOptionPane.showMessageDialog(null, "HAPUS DATA GAGAL :(");
            }
            
            stoksaldo();
        }
    }//GEN-LAST:event_bthapusActionPerformed

    private void txhargasaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txhargasaldoActionPerformed
        // TODO add your handling code here:
       
    }//GEN-LAST:event_txhargasaldoActionPerformed

    private void txhargajualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txhargajualActionPerformed
        // TODO add your handling code here:
        int hargajual=Integer.parseInt(txhargajual.getText());
        int hargasaldo=Integer.parseInt(txhargasaldo.getText());
        

        int income=hargajual-hargasaldo;
        txincome.setText(Integer.toString(income));
    }//GEN-LAST:event_txhargajualActionPerformed

    private void tabeldaftarhargaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeldaftarhargaMouseClicked
        // TODO add your handling code here:
        int baris = tabeldaftarharga.getSelectedRow();
        txkodenomer.setText(tabeldaftarharga.getModel().getValueAt(baris, 0).toString());
        txkodepulsa.setText(tabeldaftarharga.getModel().getValueAt(baris, 1).toString());
        txhargasaldo.setText(tabeldaftarharga.getModel().getValueAt(baris, 2).toString());
        txhargajual.setText(tabeldaftarharga.getModel().getValueAt(baris, 3).toString());
        txincome.setText(tabeldaftarharga.getModel().getValueAt(baris, 4).toString());
        
        bthapus.setEnabled(true);
        btedit.setEnabled(true);
    }//GEN-LAST:event_tabeldaftarhargaMouseClicked

    private void txpencarianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencarianKeyPressed
        // TODO add your handling code here:
        Object header[]={"KDNO","KodePulsa","HargaSaldo","HargaJual","Income"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeldaftarharga.setModel(data);
        setKoneksi();
        String sql="Select * from daftarharga where kodenomer like '%" + txpencarian.getText() + "%'" + "or kodepulsa like '%" + txpencarian.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);

                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_txpencarianKeyPressed

    private void bteditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bteditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bteditMouseClicked

    private void txkodenomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txkodenomerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txkodenomerActionPerformed

    private void txkodepulsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txkodepulsaActionPerformed
        // TDO add your handling code here:
    }//GEN-LAST:event_txkodepulsaActionPerformed

    private void txpencarianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpencarianActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txpencarianActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btcari;
    private javax.swing.JButton btedit;
    private javax.swing.JButton bthapus;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton bttambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabeldaftarharga;
    private javax.swing.JTextField txhargajual;
    private javax.swing.JTextField txhargasaldo;
    private javax.swing.JTextField txincome;
    private javax.swing.JTextField txkodenomer;
    private javax.swing.JTextField txkodepulsa;
    private javax.swing.JTextField txpencarian;
    // End of variables declaration//GEN-END:variables
}
