package main;

import Biometrics.Insertionclass;
import Biometrics.Conn;
import Biometrics.Utils;
import Biometrics.ValidateClass;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import static main.CEntityForm.con;

/**
 *
 * @author lenovo
 */
public class RidgePanel extends javax.swing.JPanel {

    static String $Imagepath = "";
    static Connection con = null;
    private BufferedImage image;
    static String $emp_Id = "";
    int abc = 0;
    List<String> list1 = new ArrayList<>();
    JFrame f = new JFrame();

    /**
     * Creates new form RidgePanel
     */
    public RidgePanel() {
        initComponents();
        empid.setText($emp_Id);
        fieldDisabled();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        empid = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(153, 153, 153));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setOpaque(true);

        jButton1.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jButton1.setText("Open File");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        jButton2.setText("Done");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton2MouseEntered(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        empid.setBackground(new java.awt.Color(255, 255, 255));
        empid.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        empid.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 11)); // NOI18N
        jLabel2.setText("  Employee Code ");
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(102, 102, 102));
        jLabel3.setOpaque(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                        .addComponent(empid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(empid, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        JFileChooser file = new JFileChooser();
        file.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg", "jpeg", "png");
        file.addChoosableFileFilter(filter);
        int result = file.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File SelectedFile = file.getSelectedFile();
            $Imagepath = SelectedFile.getAbsolutePath();

            File input = new File($Imagepath);
            try {
                System.out.println("path of Image=" + $Imagepath);
                image = ImageIO.read(input);
                BufferedImage $resize_img = Utils.scale(image, 92, 109);
                //image = new Histogram().equalize(image); 
                jLabel1.setIcon(new ImageIcon($resize_img));
                jLabel1.getIcon();
                jButton2.setEnabled(true);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (result == JFileChooser.CANCEL_OPTION) {
            System.out.println("No File Selected !");
        }
    }//GEN-LAST:event_jButton1ActionPerformed


    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //        // TODO add your handling code here:

        try {
            con = Conn.getConnection();
            Insertionclass.insertImage(con, $Imagepath, $emp_Id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseEntered

    public static void main(String args[]) {
        try {
            new RidgePanel().mainfunctionMethod(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mainfunctionMethod(String[] arg) {
        try {
            if (arg[0].equals("add")) {
                
                $emp_Id = arg[1];
                System.out.println("empId=" + $emp_Id);
                f.getContentPane().add(new RidgePanel());
                f.pack();
                f.setLocationRelativeTo(null);
                f.setVisible(true);
                jButton1.setEnabled(true);
            } else if (arg[0].equals("validate")) {
                   jButton1.setEnabled(false);
                try {
                    $emp_Id = arg[1];
                    System.out.println("empid" + $emp_Id);
                    System.out.println("validate" + arg[0]);
                    con = Conn.getConnection();
                    System.out.println("<<<<<<<<11");
                    InputStream is = ValidateClass.validateImageAndEmpId(con, $emp_Id);
                    System.out.println("<<<<<<<<22" + is);
                    image = ImageIO.read(is);
                    BufferedImage $resize_img = Utils.scale(image, 92, 109);
                 
                    // Image set at label after calling Ridgepanel constructor
                    f.getContentPane().add(new RidgePanel());
                    ImageIcon imageIcon = new ImageIcon($resize_img);
                    jLabel1.setIcon(imageIcon);
                    f.pack();
                    f.setLocationRelativeTo(null);
                    f.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to set field enabled and disabled
    public void fieldDisabled() {
        try {
            empid.setEnabled(false);
            jButton2.setEnabled(false);
            jButton1.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel empid;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private static javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private static javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables

    //Main function Method 
}
