/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Biometrics;

import Biometrics.Conn;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Dev-24
 */
public class ValidateClass {
    // static Blob imageData;

    // Method to validate EmpId and Image 
    public static InputStream validateImageAndEmpId(Connection con, String empId) {
//         /byte[] byt= null;
          InputStream is=null;
        try {
            PreparedStatement ps = con.prepareStatement("select image from fingerprint where empId=?");
            ps.setString(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
               if(rs!=null){
                 is=rs.getBinaryStream("image");
               }else{
                   System.out.println("Image value is null");
               }
//               Blob blob = rs.getBlob("image");
//
//              byt = blob.getBytes(1, (int) blob.length());

                
            }

             
              
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }
    
 
}
