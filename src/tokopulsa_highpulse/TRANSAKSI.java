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
public class TRANSAKSI extends javax.swing.JInternalFrame {

    Connection conn;
    Statement stm;
    ResultSet rs;
    
    /**
     * Creates new form rockwell_what
     */
    public TRANSAKSI() {
        initComponents();
        //this.setLocation(130,120);
        siapIsi(false);
        tombolNormal();
        tampiltabeltransaksi();
        stoksaldo();
        txpin.setVisible(false);
        txbatal.setVisible(false);
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        txtanggal.setText(dateFormat.format(cal.getTime()));
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
    
    private void siapIsi(boolean a){
        txkodetransaksi.setEnabled(a);
        txtanggal.setEnabled(a);
        txkodepulsa.setEnabled(a);
        txhargasaldo.setEnabled(a);
        txhargajual.setEnabled(a);
        txnomerhp.setEnabled(a);
        txpinawalsaldo.setEnabled(a);
        txsisasaldo.setEnabled(a);
        txincome.setEnabled(a);
    }
    
    private void tombolNormal(){
        bttambah.setEnabled(true);
        btsimpan.setEnabled(false);
        bthapus.setEnabled(false);
        tbtransaksi.setEnabled(true);
        btcari.setEnabled(true);
        btpulsa.setEnabled(false);
        btproses.setEnabled(false);
    }
    
    private void bersih(){
        txkodetransaksi.setText("");       
        //txtanggal.setText("");     
        txkodepulsa.setText("");
        txhargasaldo.setText("");
        txhargajual.setText("");
        txnomerhp.setText("");
        txpinawalsaldo.setText("");
        txsisasaldo.setText("");
        txincome.setText("");
        jTextArea1.setText("");
        txpin.setText("");
        
    }
    
    private void kodetransaksi(){
        try {
            setKoneksi();
            String sql="select right (kodetransaksi,2)+1 from transaksipulsa";
            ResultSet rs=stm.executeQuery(sql);
            if(rs.next()){
                rs.last();
                String no=rs.getString(1);
                while (no.length()<3){
                    no="0"+no;
                    txkodetransaksi.setText("KT"+no);}
                }
            else
            {
                txkodetransaksi.setText("KT001"); 
        }
        } catch (Exception e) 
        {
        }
    }
    
public void stoksaldo(){
    setKoneksi();
        String sql="SELECT * FROM stoksaldo";
        try{
            ResultSet rst = stm.executeQuery(sql);
            if (rst.next())
            {
               lbstoksaldo.setText(rst.getString(2));
            }
        } catch (SQLException ex) {
                }    
    }

private void simpan(){
        try{
            setKoneksi();
            String sql="insert into transaksipulsa values('"+txkodetransaksi.getText()
                    +"','"+txnomerhp.getText()
                    +"','"+txtanggal.getText()
                    +"','"+txkodepulsa.getText()
                    +"','"+txhargasaldo.getText()
                    +"','"+txhargajual.getText()
                    +"','"+txpinawalsaldo.getText()
                    +"','"+txsisasaldo.getText()
                    +"','"+txincome.getText()
                    +"','"+ txpin.getText() +"')";
            stm.executeUpdate(sql); 
            JOptionPane.showMessageDialog(null,"Transaksi Berhasil :)");
            }
            catch (Exception e) {
        }
        tampiltabeltransaksi();
        PerbaruiStokSaldo();
    }

private void PerbaruiStokSaldo(){
        try{
            setKoneksi();
            String sql="update stoksaldo set totalsaldo='"+txsisasaldo.getText()
                    
                    +"' where pinsaldo='"+txpin.getText()+"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Total Saldo Diperbarui","",JOptionPane.INFORMATION_MESSAGE);
            }
            catch (Exception e) {
        }
        tampiltabeltransaksi();
    }

private void perbarui (){
        try{
            setKoneksi();
            String sql="update transaksipulsa set tanggal='"+txnomerhp.getText()
                    +"',sisasaldo='"+txtanggal.getText()
                    +"',saldomasuk='"+txkodepulsa.getText()
                    +"',totalsaldo='"+txhargasaldo.getText()
                    +"',totalsaldo='"+txhargajual.getText()
                    +"',totalsaldo='"+txpinawalsaldo.getText()
                    +"',totalsaldo='"+txsisasaldo.getText()
                    +"',totalsaldo='"+txincome.getText()
                    +"',totalsaldo='"+txpin.getText()
                    +"' where kodetransaksi='"+txkodetransaksi.getText()+"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Edit Data Berhasil, Lakukan Langkah Berikutnya",")",JOptionPane.INFORMATION_MESSAGE);
        } 
        catch(Exception e){
        }
        tampiltabeltransaksi();
        PerbaruiStokSaldo();
    }

private void hapus(){
        try{
            String sql="delete from transaksipulsa where kodetransaksi='"+ txkodetransaksi.getText() +"'";
            stm.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Transaksi Telah Dibatalkan ");
            }
            catch (Exception e) {
            }
        tampiltabeltransaksi();
    }

public void tampiltabeltransaksi(){
        Object header[]={"KDTR","Nomer","TGL","KodePulsa","HargaSaldo","HargaJual","SaldoAwal","SisaSaldo","Income"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeltransaksi.setModel(data);
        setKoneksi();
        String sql="select*from transaksipulsa";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);
                
                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9};
                data.addRow(kolom);
            }
        } catch (Exception e) {
        }
    }

public void tampildaftarhargapulsa(){
        Object header[]={"KDNO","KodePulsa","HargaSaldo","HargaJual","Income"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeldaftarhargapulsa.setModel(data);
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

private void tabelmouseklikarea (){
    jTextArea1.setText("\nKelompok 2,"+ 
                                 "\n============TOKO PULSA HIGH PULSE=============" + "\n" 
                                      + "                   Kode Transaksi             : " + txkodetransaksi.getText()+ "\n" 
                                      + "                   Tanggal                           : " + txtanggal.getText()+ "\n" 
                                      + "                   Kode Pulsa                     : " + txkodepulsa.getText()+ "\n"
                                      + "                   Jumlah Saldo                   : " + txhargasaldo.getText()+ "\n" 
                                      + "                   Harga Jual                      : " + txhargajual.getText() +"\n"
                                      + "                   Nomer HP           : " + txnomerhp.getText() +"\n"
                                  +"=============================================="+"\n"            
                                      + "                   Awal Saldo                      : " + txpinawalsaldo.getText() +"\n"
                                      + "                   Harga Saldo-                  : " + txhargasaldo.getText()+ "\n"            
                                      + "                   Sisa Saldo                      : " + txsisasaldo.getText() +"\n" 
                                      + "                   Income                             : " + txincome.getText() +                                        
                                 "\n=============================================="+
                                 "\nTERIMA KASIH SUDAH MENGGUNAKAN APLIKASI KAMI!,"+
                                 "\n");
              stoksaldo();
}

public void jdialog(){
    jDialog2.setLocationRelativeTo(null);
        tampildaftarhargapulsa();
        jDialog2.setVisible(true);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabeldaftarhargapulsa = new javax.swing.JTable();
        txpencarianpulsa = new javax.swing.JTextField();
        jDialog2 = new javax.swing.JDialog();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jPanel4 = new javax.swing.JPanel();
        btcari = new javax.swing.JButton();
        txpencariantransaksi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabeltransaksi = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txincome = new javax.swing.JTextField();
        txsisasaldo = new javax.swing.JTextField();
        txpinawalsaldo = new javax.swing.JTextField();
        txnomerhp = new javax.swing.JTextField();
        txhargajual = new javax.swing.JTextField();
        txhargasaldo = new javax.swing.JTextField();
        txkodepulsa = new javax.swing.JTextField();
        txtanggal = new javax.swing.JTextField();
        txkodetransaksi = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel6 = new javax.swing.JPanel();
        lbstoksaldo = new javax.swing.JLabel();
        btpulsa = new javax.swing.JButton();
        btproses = new javax.swing.JButton();
        btsimpan = new javax.swing.JButton();
        bttambah = new javax.swing.JButton();
        bthapus = new javax.swing.JButton();
        txpin = new javax.swing.JLabel();
        txbatal = new javax.swing.JLabel();
        tbtransaksi = new javax.swing.JButton();

        jDialog1.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog1.setBackground(new java.awt.Color(0, 0, 51));
        jDialog1.setMinimumSize(new java.awt.Dimension(694, 430));
        jDialog1.setModal(true);
        jDialog1.setResizable(false);

        jInternalFrame1.setTitle("DAFTAR PULSA");
        jInternalFrame1.setPreferredSize(new java.awt.Dimension(694, 430));
        jInternalFrame1.setVisible(true);
        jInternalFrame1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jInternalFrame1MouseClicked(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(51, 51, 51));

        tabeldaftarhargapulsa.setAutoCreateRowSorter(true);
        tabeldaftarhargapulsa.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tabeldaftarhargapulsa.setModel(new javax.swing.table.DefaultTableModel(
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
        tabeldaftarhargapulsa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeldaftarhargapulsaMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabeldaftarhargapulsa);

        txpencarianpulsa.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        txpencarianpulsa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencarianpulsa.setText("INI KOLOM PENCARIAN / BUKAN KOLOM IKAN");
        txpencarianpulsa.setPreferredSize(new java.awt.Dimension(87, 30));
        txpencarianpulsa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencarianpulsaKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txpencarianpulsa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txpencarianpulsa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jDialog2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDialog2.setMinimumSize(new java.awt.Dimension(843, 442));
        jDialog2.setModal(true);

        jInternalFrame2.setTitle("TABEL TRANSAKSI");
        jInternalFrame2.setToolTipText("");
        jInternalFrame2.setVisible(true);

        jPanel4.setBackground(new java.awt.Color(51, 51, 51));

        btcari.setBackground(new java.awt.Color(0, 0, 0));
        btcari.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        btcari.setForeground(new java.awt.Color(255, 255, 255));
        btcari.setText("CARI");
        btcari.setPreferredSize(new java.awt.Dimension(87, 40));
        btcari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btcariActionPerformed(evt);
            }
        });

        txpencariantransaksi.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        txpencariantransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpencariantransaksi.setText("INI KOLOM PENCARIAN / BUKAN KOLOM IKAN");
        txpencariantransaksi.setPreferredSize(new java.awt.Dimension(87, 30));
        txpencariantransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txpencariantransaksiActionPerformed(evt);
            }
        });
        txpencariantransaksi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txpencariantransaksiKeyPressed(evt);
            }
        });

        tabeltransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tabeltransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabeltransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabeltransaksi);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 807, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btcari, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txpencariantransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txpencariantransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btcari, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDialog2Layout = new javax.swing.GroupLayout(jDialog2.getContentPane());
        jDialog2.getContentPane().setLayout(jDialog2Layout);
        jDialog2Layout.setHorizontalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog2Layout.createSequentialGroup()
                .addComponent(jInternalFrame2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jDialog2Layout.setVerticalGroup(
            jDialog2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jInternalFrame2)
        );

        setBackground(new java.awt.Color(199, 213, 216));
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("TRANSAKSI PULSA");

        jPanel1.setBackground(new java.awt.Color(127, 121, 104));

        jPanel2.setBackground(new java.awt.Color(127, 121, 104));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setLayout(null);

        jPanel3.setBackground(new java.awt.Color(127, 121, 104));

        jLabel1.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("KODE TRANSAKSI");

        jLabel2.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("TANGGAL");

        jLabel3.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("KODE PULSA");

        jLabel4.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("HARGA SALDO");

        jLabel5.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("HARGA JUAL");

        jLabel7.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("NOMER HP");

        jLabel8.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("MASUKAN PIN");

        jLabel9.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("SISA SALDO");

        jLabel10.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("INCOME");

        txincome.setEditable(false);
        txincome.setBackground(new java.awt.Color(204, 204, 204));
        txincome.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txsisasaldo.setEditable(false);
        txsisasaldo.setBackground(new java.awt.Color(204, 204, 204));
        txsisasaldo.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txpinawalsaldo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txpinawalsaldo.setToolTipText("Masukan pin saldo lalu tekan enter, HARUS !");
        txpinawalsaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txpinawalsaldoActionPerformed(evt);
            }
        });

        txnomerhp.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txnomerhp.setToolTipText("Masukan nomer hp / token");

        txhargajual.setEditable(false);
        txhargajual.setBackground(new java.awt.Color(204, 204, 204));
        txhargajual.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txhargasaldo.setEditable(false);
        txhargasaldo.setBackground(new java.awt.Color(204, 204, 204));
        txhargasaldo.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txhargasaldo.setToolTipText("isi harga jual lalu tekan enter");
        txhargasaldo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txhargasaldoActionPerformed(evt);
            }
        });

        txkodepulsa.setEditable(false);
        txkodepulsa.setBackground(new java.awt.Color(204, 204, 204));
        txkodepulsa.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txkodepulsa.setToolTipText("");
        txkodepulsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txkodepulsaActionPerformed(evt);
            }
        });

        txtanggal.setEditable(false);
        txtanggal.setBackground(new java.awt.Color(204, 204, 204));
        txtanggal.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txkodetransaksi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txkodetransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txkodetransaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txincome, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txsisasaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txpinawalsaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txnomerhp, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txkodepulsa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txkodetransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txhargasaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txhargajual, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(txkodetransaksi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtanggal)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txkodepulsa)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txhargasaldo)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txhargajual)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txnomerhp)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txpinawalsaldo)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txsisasaldo)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txincome)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.add(jPanel3);
        jPanel3.setBounds(40, 20, 236, 300);

        jTextArea1.setEditable(false);
        jTextArea1.setBackground(new java.awt.Color(243, 234, 205));
        jTextArea1.setColumns(20);
        jTextArea1.setForeground(new java.awt.Color(127, 121, 104));
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "Program PBO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Exotc350 DmBd BT", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jTextArea1.setSelectedTextColor(new java.awt.Color(127, 121, 104));
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextArea1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea1);
        jTextArea1.getAccessibleContext().setAccessibleName("ProjectBlek");

        jPanel2.add(jScrollPane2);
        jScrollPane2.setBounds(430, 20, 355, 298);

        jPanel6.setBackground(new java.awt.Color(127, 121, 104));

        lbstoksaldo.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 15)); // NOI18N
        lbstoksaldo.setForeground(new java.awt.Color(255, 255, 255));
        lbstoksaldo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbstoksaldo.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)), "MySaldo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Exotc350 DmBd BT", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N

        btpulsa.setBackground(new java.awt.Color(199, 213, 216));
        btpulsa.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        btpulsa.setForeground(new java.awt.Color(51, 51, 51));
        btpulsa.setText("PULSA");
        btpulsa.setPreferredSize(new java.awt.Dimension(87, 40));
        btpulsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btpulsaActionPerformed(evt);
            }
        });

        btproses.setBackground(new java.awt.Color(199, 213, 216));
        btproses.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        btproses.setForeground(new java.awt.Color(51, 51, 51));
        btproses.setText("PROSES");
        btproses.setPreferredSize(new java.awt.Dimension(87, 40));
        btproses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btprosesActionPerformed(evt);
            }
        });

        btsimpan.setBackground(new java.awt.Color(199, 213, 216));
        btsimpan.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        btsimpan.setForeground(new java.awt.Color(51, 51, 51));
        btsimpan.setText("SIMPAN");
        btsimpan.setPreferredSize(new java.awt.Dimension(87, 40));
        btsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btsimpanActionPerformed(evt);
            }
        });

        bttambah.setBackground(new java.awt.Color(199, 213, 216));
        bttambah.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        bttambah.setForeground(new java.awt.Color(51, 51, 51));
        bttambah.setText("TAMBAH");
        bttambah.setPreferredSize(new java.awt.Dimension(87, 40));
        bttambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bttambahActionPerformed(evt);
            }
        });

        bthapus.setBackground(new java.awt.Color(199, 213, 216));
        bthapus.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        bthapus.setForeground(new java.awt.Color(51, 51, 51));
        bthapus.setText("HAPUS DATA");
        bthapus.setPreferredSize(new java.awt.Dimension(87, 40));
        bthapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bthapusActionPerformed(evt);
            }
        });

        txpin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txpin.setForeground(new java.awt.Color(255, 255, 255));
        txpin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txpin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        txbatal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txbatal.setForeground(new java.awt.Color(255, 255, 255));
        txbatal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txbatal.setText("BATAL");
        txbatal.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));

        tbtransaksi.setBackground(new java.awt.Color(199, 213, 216));
        tbtransaksi.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 12)); // NOI18N
        tbtransaksi.setForeground(new java.awt.Color(51, 51, 51));
        tbtransaksi.setText("TB-TR");
        tbtransaksi.setToolTipText("Lihat Tabel Transaksi");
        tbtransaksi.setFocusPainted(false);
        tbtransaksi.setPreferredSize(new java.awt.Dimension(87, 40));
        tbtransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbtransaksiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btpulsa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbstoksaldo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btproses, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bthapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bttambah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txpin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txbatal))
                    .addComponent(tbtransaksi, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbstoksaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btpulsa, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btproses, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btsimpan, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bttambah, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bthapus, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tbtransaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txbatal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txpin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.add(jPanel6);
        jPanel6.setBounds(286, 7, 125, 275);

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
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                .addContainerGap())
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

        setBounds(0, 0, 865, 393);
    }// </editor-fold>//GEN-END:initComponents

    private void txkodepulsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txkodepulsaActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txkodepulsaActionPerformed

    private void txhargasaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txhargasaldoActionPerformed
        // TODO add your handling code here:
        int hargajual=Integer.parseInt(txhargasaldo.getText());
        int hargasaldo=Integer.parseInt(txkodepulsa.getText());

        int income=hargajual-hargasaldo;
        txhargajual.setText(Integer.toString(income));
    }//GEN-LAST:event_txhargasaldoActionPerformed

    private void btpulsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btpulsaActionPerformed
        // TODO add your handling code here:
        jDialog1.setLocationRelativeTo(null);
        tampildaftarhargapulsa();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_btpulsaActionPerformed

    private void btprosesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btprosesActionPerformed
        setKoneksi();
        String sql="SELECT * FROM stoksaldo WHERE pinsaldo like '"+txpinawalsaldo.getText()+"'";
        try{
            ResultSet rst = stm.executeQuery(sql);
            if (rst.next())
            {
                txpin.setText(rst.getString(1));
                txpinawalsaldo.setText(rst.getString(2));

            }
            else
            {
                JOptionPane.showMessageDialog(null, "PIN SALDO TIDAK DIKENALI ","",JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
        }
        
        int saldoawal=Integer.parseInt(txpinawalsaldo.getText());
        int hargasaldo=Integer.parseInt(txhargasaldo.getText());

        if(hargasaldo>saldoawal){
            JOptionPane.showMessageDialog(null, "Saldo Anda Tidak Mencukupi");
        }else{

            int sisasaldo=saldoawal-hargasaldo;
            txsisasaldo.setText(Integer.toString(sisasaldo));

            jTextArea1.setText("\nPRAKTIKUM PBO,"+
                "\n===========TRANSAKSI PULSA PROJECT ==========" + "\n"
                + "                   Kode Transaksi             : " + txkodetransaksi.getText()+ "\n"
                + "                   Tanggal                           : " + txtanggal.getText()+ "\n"
                + "                   Kode Pulsa                     : " + txkodepulsa.getText()+ "\n"
                + "                   Jumlah Saldo                   : " + txhargasaldo.getText()+ "\n"
                + "                   Harga Jual                      : " + txhargajual.getText() +"\n"
                + "                   Nomer Hp/Token           : " + txnomerhp.getText() +"\n"
                +"=============================================="+"\n"
                + "                   Awal Saldo                      : " + txpinawalsaldo.getText() +"\n"
                + "                   Harga Saldo-                  : " + txhargasaldo.getText()+ "\n"
                + "                   Sisa Saldo                      : " + txsisasaldo.getText() +"\n"
                + "                   Income                             : " + txincome.getText() +
                "\n=============================================="+
                "\n,"+
                "\n");
            stoksaldo();
        }
    }//GEN-LAST:event_btprosesActionPerformed

    private void btsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btsimpanActionPerformed
        // TODO add your handling code here:
        if(txnomerhp.getText().isEmpty()
            || txpin.getText().isEmpty()
            || txpinawalsaldo.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "LENGKAPI INPUTAN DATA!!!","",JOptionPane.INFORMATION_MESSAGE);
        } else{

            if(bttambah.getText().equalsIgnoreCase("Refresh")){
                if(bttambah.getText().equalsIgnoreCase("Refresh")){
                    simpan();
                } else{
                    JOptionPane.showMessageDialog(null, "SIMPAN DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            //if(btedit.getText().equalsIgnoreCase("BATAL")){
                //  if(btedit.getText().equalsIgnoreCase("BATAL")){
                    //      perbarui();
                    //  } else{
                    //    JOptionPane.showMessageDialog(null, "EDIT DATA GAGAL, PERIKSA KEMBALI :( ","",JOptionPane.INFORMATION_MESSAGE);
                    // }
                // }
            bersih();
            siapIsi(false);
            bttambah.setText("TAMBAH");
            //btedit.setText("Edit");
            tombolNormal();
            stoksaldo();
            jdialog();
        }
    }//GEN-LAST:event_btsimpanActionPerformed

    private void bttambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bttambahActionPerformed
        // TODO add your handling code here:
        if(bttambah.getText().equalsIgnoreCase("TAMBAH")){
            bttambah.setText("Refresh");
            bersih();
            siapIsi(true);
            kodetransaksi();
            
            txkodetransaksi.setEnabled(true);
            bttambah.setEnabled(true);
            btsimpan.setEnabled(true);
            tbtransaksi.setEnabled(false);
            bthapus.setEnabled(false);
            
            btproses.setEnabled(true);
            btpulsa.setEnabled(true);
            //txtotalsaldo.setEnabled(true);
        } else{
            bttambah.setText("TAMBAH");
            bersih();
            siapIsi(false);
            tombolNormal();
            tampiltabeltransaksi();
        }
        stoksaldo();
    }//GEN-LAST:event_bttambahActionPerformed

    private void bthapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bthapusActionPerformed
        // TODO add your handling code here:
int pesan=JOptionPane.showConfirmDialog(null, "YApakah anda yakin untuk membatalkan transaksi ?","Konfirmasi",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        if(pesan==JOptionPane.YES_OPTION){
            if(pesan==JOptionPane.YES_OPTION){
                hapus();
                
            } else{
                JOptionPane.showMessageDialog(null, "HAPUS DATA GAGAL :(");
            }
            
            
        
            int sisasaldo=Integer.parseInt(txsisasaldo.getText());
            int hargasaldo=Integer.parseInt(txhargasaldo.getText());

            int wtfbataltransaksi=sisasaldo+hargasaldo;
            txsisasaldo.setText(Integer.toString(wtfbataltransaksi));

            siapIsi(false);
            tombolNormal();
            tampiltabeltransaksi();
            bersih();
            stoksaldo();

        }
    }//GEN-LAST:event_bthapusActionPerformed

    private void tabeldaftarhargapulsaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeldaftarhargapulsaMouseClicked
        // TODO add your handling code here:
        int baris = tabeldaftarhargapulsa.getSelectedRow();
        txkodepulsa.setText(tabeldaftarhargapulsa.getModel().getValueAt(baris, 1).toString());
        txhargasaldo.setText(tabeldaftarhargapulsa.getModel().getValueAt(baris, 2).toString());
        txhargajual.setText(tabeldaftarhargapulsa.getModel().getValueAt(baris, 3).toString());
        txincome.setText(tabeldaftarhargapulsa.getModel().getValueAt(baris, 4).toString());
        jDialog1.dispose();
    }//GEN-LAST:event_tabeldaftarhargapulsaMouseClicked

    private void txpencarianpulsaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencarianpulsaKeyPressed
        // TODO add your handling code here:
        Object header[]={"KDNO","KodePulsa","HargaSaldo","HargaJual","Income"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeldaftarhargapulsa.setModel(data);
        setKoneksi();
        String sql="Select * from daftarharga where kodenomer like '%" + txpencarianpulsa.getText() + "%'" + "or kodepulsa like '%" + txpencarianpulsa.getText()+"%'";
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
    }//GEN-LAST:event_txpencarianpulsaKeyPressed

    private void jInternalFrame1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jInternalFrame1MouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_jInternalFrame1MouseClicked

    private void txpinawalsaldoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpinawalsaldoActionPerformed
        // TODO add your handling code here:
        setKoneksi();
        String sql="SELECT * FROM stoksaldo WHERE pinsaldo like '"+txpinawalsaldo.getText()+"'";
        try{
            ResultSet rst = stm.executeQuery(sql);
            if (rst.next())
            {
                txpin.setText(rst.getString(1));
                txpinawalsaldo.setText(rst.getString(2));

            }
            else
            {
                JOptionPane.showMessageDialog(null, "PIN SALDO TIDAK DIKENALI, MOHON PERIKSA KEMBALI ","",JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (SQLException ex) {
        }
    }//GEN-LAST:event_txpinawalsaldoActionPerformed

    private void btcariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btcariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btcariActionPerformed

    private void txpencariantransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txpencariantransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txpencariantransaksiActionPerformed

    private void txpencariantransaksiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txpencariantransaksiKeyPressed
        // TODO add your handling code here:
        Object header[]={"KDTR","Nomer","TGL","KodePulsa","HargaSaldo","HargaJual","SaldoAwal","SisaSaldo","Income"};
        DefaultTableModel data=new DefaultTableModel(null,header);
        tabeltransaksi.setModel(data);
        setKoneksi();
        String sql="Select * from transaksipulsa where kodetransaksi like '%" + txpencariantransaksi.getText() + "%'" + "or nomer like '%" + txpencariantransaksi.getText()+"%'";
        try {
            ResultSet rs=stm.executeQuery(sql);
            while (rs.next())
            {
                String kolom1=rs.getString(1);
                String kolom2=rs.getString(2);
                String kolom3=rs.getString(3);
                String kolom4=rs.getString(4);
                String kolom5=rs.getString(5);
                String kolom6=rs.getString(6);
                String kolom7=rs.getString(7);
                String kolom8=rs.getString(8);
                String kolom9=rs.getString(9);

                String kolom[]={kolom1,kolom2,kolom3,kolom4,kolom5,kolom6,kolom7,kolom8,kolom9};
                data.addRow(kolom);
            }

        } catch (Exception e) {
        }
    }//GEN-LAST:event_txpencariantransaksiKeyPressed

    private void tabeltransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabeltransaksiMouseClicked
        // TODO add your handling code here:
        int baris = tabeltransaksi.getSelectedRow();
        txkodetransaksi.setText(tabeltransaksi.getModel().getValueAt(baris, 0).toString());
        txnomerhp.setText(tabeltransaksi.getModel().getValueAt(baris, 1).toString());
        txtanggal.setText(tabeltransaksi.getModel().getValueAt(baris, 2).toString());
        txkodepulsa.setText(tabeltransaksi.getModel().getValueAt(baris, 3).toString());
        txhargasaldo.setText(tabeltransaksi.getModel().getValueAt(baris, 4).toString());
        txhargajual.setText(tabeltransaksi.getModel().getValueAt(baris, 5).toString());
        txpinawalsaldo.setText(tabeltransaksi.getModel().getValueAt(baris, 6).toString());
        txsisasaldo.setText(tabeltransaksi.getModel().getValueAt(baris, 7).toString());
        txincome.setText(tabeltransaksi.getModel().getValueAt(baris, 8).toString());
        bthapus.setEnabled(true);
        
        tabelmouseklikarea ();
        jDialog2.dispose();
    }//GEN-LAST:event_tabeltransaksiMouseClicked

    private void txkodetransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txkodetransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txkodetransaksiActionPerformed

    private void tbtransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbtransaksiActionPerformed
        // TODO add your handling code here:
        jDialog2.setLocationRelativeTo(null);
        tampildaftarhargapulsa();
        jDialog2.setVisible(true);
    }//GEN-LAST:event_tbtransaksiActionPerformed

    private void jTextArea1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextArea1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btcari;
    private javax.swing.JButton bthapus;
    private javax.swing.JButton btproses;
    private javax.swing.JButton btpulsa;
    private javax.swing.JButton btsimpan;
    private javax.swing.JButton bttambah;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lbstoksaldo;
    private javax.swing.JTable tabeldaftarhargapulsa;
    private javax.swing.JTable tabeltransaksi;
    private javax.swing.JButton tbtransaksi;
    private javax.swing.JLabel txbatal;
    private javax.swing.JTextField txhargajual;
    private javax.swing.JTextField txhargasaldo;
    private javax.swing.JTextField txincome;
    private javax.swing.JTextField txkodepulsa;
    private javax.swing.JTextField txkodetransaksi;
    private javax.swing.JTextField txnomerhp;
    private javax.swing.JTextField txpencarianpulsa;
    private javax.swing.JTextField txpencariantransaksi;
    private javax.swing.JLabel txpin;
    private javax.swing.JTextField txpinawalsaldo;
    private javax.swing.JTextField txsisasaldo;
    private javax.swing.JTextField txtanggal;
    // End of variables declaration//GEN-END:variables
}
