package main;

import Biometrics.ValidateClass;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import Biometrics.CFingerPrint;
import Biometrics.CFingerPrint;
import Biometrics.Conn;
import Biometrics.Conn;
import Biometrics.ValidateClass;
//import Biometrics.CFingerPrintGraphics;
import java.awt.image.*;
import javax.imageio.*;
import java.io.File;
import java.lang.Exception;
import java.awt.Color;
import java.awt.event.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import static main.RidgePanel.$emp_Id;

public class CEntityForm extends JFrame {

    class BJPanel extends JPanel {

        public BufferedImage bi;

        public BJPanel() {
            this.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent m) {
                    JOptionPane.showMessageDialog(null, "(" + Integer.toString(m.getPoint().x) + ";" + Integer.toString(m.getPoint().y) + ")", "Point", JOptionPane.PLAIN_MESSAGE);
                }
            });
        }

        public BJPanel(BufferedImage bi) {
            this.bi = bi;
            setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
        }

        public void setBufferedImage(BufferedImage bi) {
            this.bi = bi;
            setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
            this.repaint();
        }

        public void paintComponent(Graphics g) {
            g.drawImage(bi, 0, 0, this);
        }
    }
    static Connection con = null;
    private JToolBar jtool = new JToolBar();
    private JPanel jimage = new JPanel();
    private JButton jButtonStep1 = new JButton("1 to 1 Match");

    //uses our finger print libery
    private CFingerPrint m_finger1 = new CFingerPrint();
    private CFingerPrint m_finger2 = new CFingerPrint();
//  private CFingerPrintGraphics m_fingergfx = new CFingerPrintGraphics();
    private BJPanel m_panel1 = new BJPanel();
    private BJPanel m_panel2 = new BJPanel();
    private BufferedImage m_bimage1 = new BufferedImage(m_finger1.FP_IMAGE_WIDTH, m_finger1.FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage m_bimage2 = new BufferedImage(m_finger2.FP_IMAGE_WIDTH, m_finger2.FP_IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);

    private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
    private double finger2[] = new double[m_finger2.FP_TEMPLATE_MAX_SIZE];

    public CEntityForm() {

        jButtonStep1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jButtonStep3_actionPerformed(e);
            }
        });

        /*jButtonStep1 for 1 to 1 match fingerprint matching which is not in use */
        jtool.add(jButtonStep1);

        try {
            // Taking picture1
            //Set picture new
             con = Conn.getConnection();
            InputStream is = ValidateClass.validateImageAndEmpId(con, "00");
            m_bimage1 = ImageIO.read(is);
           // m_bimage1 = ImageIO.read(new File(new java.io.File("").getAbsolutePath() + "\\L4Center.Jpeg"));

          // m_panel1.setBufferedImage(m_bimage1);
            
            //Send image for skeletinization
            m_finger1.setFingerPrintImage(m_bimage1);
            finger1 = m_finger1.getFingerPrintTemplate();
            //See what skeletinized image looks like
            m_bimage1 = m_finger1.getFingerPrintImageDetail();
            //m_panel1.setBufferedImage(m_bimage1);

            //fingerprint matching details of finger 1 in number format so commented jtextfield2
            // jTextField1.setText(m_finger1.ConvertFingerPrintTemplateDoubleToString(finger1));
            /*end of picture 1 details*/
            // Taking picture2
            //Set picture new
           

            m_bimage2 = ImageIO.read(new File(new java.io.File("").getAbsolutePath()+"\\L3Right.Jpeg"));
            m_panel2.setBufferedImage(m_bimage2);
            //Send image for skeletinization
            m_finger2.setFingerPrintImage(m_bimage2);
            finger2 = m_finger2.getFingerPrintTemplate();
            //See what skeletinized image looks like
            m_bimage2 = m_finger2.getFingerPrintImageDetail();
            m_panel2.setBufferedImage(m_bimage2);

            //fingerprint matching details of finger 2 in number format so commented jtextfield2
            //jTextField2.setText(m_finger2.ConvertFingerPrintTemplateDoubleToString(finger2));
            /*end of picture 1 details*/
        } catch (Exception ex) {
            
            if(ex instanceof java.lang.IllegalArgumentException){
                JOptionPane.showMessageDialog(null,"EmpId not exits");
            }
              ex.printStackTrace();
             System.out.println("Not found image");
            //JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.PLAIN_MESSAGE);
        }
        this.getContentPane().setLayout(new GridLayout(2, 2));
        this.getContentPane().add(m_panel1);
        this.getContentPane().add(m_panel2);
        this.getContentPane().add(jtool);

        this.setTitle("Entity");
        this.setSize(new Dimension(800, 700));
    }

    /* Method to calling fingerprint matching */
    private void jButtonStep3_actionPerformed(ActionEvent e) {
        //fingerprint matching call
        try {
            JOptionPane.showMessageDialog(null, Double.toString(m_finger1.Match(finger1, finger2, 65, false)), "Match %", JOptionPane.PLAIN_MESSAGE);
            String x = Double.toString(m_finger1.Match(finger1, finger2, 65, false));
            System.out.println("Match % " + x);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error Message", JOptionPane.PLAIN_MESSAGE);
        }
    }

}//End Class entity
