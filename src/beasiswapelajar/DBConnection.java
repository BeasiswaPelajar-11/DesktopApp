/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beasiswapelajar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Yepta
 */
public class DBConnection {
     public static Connection Connect(){
        try{
            String url = "jdbc:mysql://localhost/beasiswa_pelajar";
            String user = "root";
            String password = "";
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user,password);
            
            return conn;
        }catch(ClassNotFoundException |SQLException ex){
            
        }
        return null;
    }
}

