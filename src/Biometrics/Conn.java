/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Biometrics;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 *
 * @author Dev-23
 */
public class Conn {

    public static Connection getConnection() {
         Connection con=null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/finaco", "root", "123");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    
}
