/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Biometrics;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author Dev-23
 */
public class Insertionclass {
    // Method to insert a image into database for parameter add
    public static String insertImage(Connection con, String file,String empid) {
        String result = "Data not inserted";
        String date=Utils.getCurrentTimeStamp();
        try {
            int x = 0;
            PreparedStatement ps = con.prepareStatement("insert into fingerprint(empId,image,datetime) values(?,?,?)");
            File f1 = new File(file);
            FileInputStream fs = new FileInputStream(f1);
            ps.setString(1, empid);
            ps.setBinaryStream(2, fs);
            ps.setString(3, date);
             x = ps.executeUpdate();
            if (x > 0) {
                result = "Data inserted";
                JOptionPane.showMessageDialog(null, result);
                System.exit(0);
                
            } else {
                System.out.println("Data not inserted !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
