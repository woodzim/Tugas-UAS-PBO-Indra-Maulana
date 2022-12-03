package Login;


import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import tokopulsa_highpulse.MENU;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author NITRO 5
 */
public class login extends javax.swing.JFrame {
    private final Login_code Account;
    boolean isLogin = false;
    private Object JOptionPanel;
    
    public login() throws ClassNotFoundException, SQLException {
        Account = new Login_code();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField2 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        LOGIN = new javax.swing.JLabel();
        Username = new javax.swing.JLabel();
        Pass = new javax.swing.JLabel();
        txtuser = new javax.swing.JTextField();
        Submit = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();
        HSchool = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtpass = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();

        jTextField2.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(60, 93, 124));
        jPanel1.setLayout(null);

        LOGIN.setFont(new java.awt.Font("Hatolie", 1, 100)); // NOI18N
        LOGIN.setForeground(new java.awt.Color(95, 150, 151));
        LOGIN.setText("LOGIN");
        LOGIN.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        jPanel1.add(LOGIN);
        LOGIN.setBounds(440, 50, 206, 88);

        Username.setBackground(new java.awt.Color(255, 255, 255));
        Username.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 18)); // NOI18N
        Username.setForeground(new java.awt.Color(58, 50, 39));
        Username.setText("USERNAME :");
        jPanel1.add(Username);
        Username.setBounds(352, 199, 97, 22);

        Pass.setFont(new java.awt.Font("Exotc350 DmBd BT", 0, 18)); // NOI18N
        Pass.setForeground(new java.awt.Color(58, 50, 39));
        Pass.setText("PASSWORD :");
        jPanel1.add(Pass);
        Pass.setBounds(352, 317, 114, 22);
        jPanel1.add(txtuser);
        txtuser.setBounds(352, 227, 395, 54);

        Submit.setBackground(new java.awt.Color(199, 213, 216));
        Submit.setFont(new java.awt.Font("Hatolie", 0, 24)); // NOI18N
        Submit.setForeground(new java.awt.Color(102, 102, 102));
        Submit.setText("SUBMIT");
        Submit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                SubmitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                SubmitMouseExited(evt);
            }
        });
        Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitActionPerformed(evt);
            }
        });
        jPanel1.add(Submit);
        Submit.setBounds(643, 429, 104, 46);

        Cancel.setBackground(new java.awt.Color(199, 213, 216));
        Cancel.setFont(new java.awt.Font("Hatolie", 0, 24)); // NOI18N
        Cancel.setForeground(new java.awt.Color(102, 102, 102));
        Cancel.setText("CANCEL");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });
        jPanel1.add(Cancel);
        Cancel.setBounds(352, 429, 104, 46);

        HSchool.setFont(new java.awt.Font("Hatolie", 1, 24)); // NOI18N
        HSchool.setForeground(new java.awt.Color(95, 150, 151));
        HSchool.setText("Toko Pulsa High Pulse");
        jPanel1.add(HSchool);
        HSchool.setBounds(450, 140, 180, 28);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(180, 184, 37, 0);

        txtpass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpassActionPerformed(evt);
            }
        });
        jPanel1.add(txtpass);
        txtpass.setBounds(350, 340, 400, 60);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ICON/Tak berjudul3_20221101193336.png"))); // NOI18N
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 820, 530);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitActionPerformed
        String Username = txtuser.getText();
        String Password = txtpass.getText();

        MENU hp = new MENU();

        try {
            this.isLogin = Account.authentication(Username, Password);
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (this.isLogin) {
            JOptionPane.showMessageDialog(null, "Selamat Datang!");
            this.dispose();
            new MENU().setVisible(true);
            
        }else{
            JOptionPane.showMessageDialog(null, "USERNAME ATAU PASSWORD SALAH");
            try {
                new login().setVisible(true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.dispose();
        }
    }//GEN-LAST:event_SubmitActionPerformed

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
dispose();        // TODO add your handling code here:
    }//GEN-LAST:event_CancelActionPerformed

    private void SubmitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubmitMouseEntered
        // TODO add your handling code here:
        Submit.setBackground(Color.gray);
    }//GEN-LAST:event_SubmitMouseEntered

    private void SubmitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SubmitMouseExited
        // TODO add your handling code here:
        Submit.setBackground(new java.awt.Color(199, 213, 216));
    }//GEN-LAST:event_SubmitMouseExited

    private void txtpassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpassActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new login().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel;
    private javax.swing.JLabel HSchool;
    private javax.swing.JLabel LOGIN;
    private javax.swing.JLabel Pass;
    private javax.swing.JButton Submit;
    private javax.swing.JLabel Username;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPasswordField txtpass;
    private javax.swing.JTextField txtuser;
    // End of variables declaration//GEN-END:variables
}
