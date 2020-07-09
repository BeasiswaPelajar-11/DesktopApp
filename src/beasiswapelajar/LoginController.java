/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beasiswapelajar;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label label;
    
    @FXML
    private TextField txtpass;

    @FXML
    private TextField txtname;

    @FXML
    private Button btnok;
    
    @FXML
    private Button buttonReset;
    
    Connection con;
    PreparedStatement pat;
    ResultSet rn;
    
    Stage dialogStage = new Stage();
    Scene scene;
    
    
    @FXML
    void login(ActionEvent event) {
            
           String username = txtname.getText();
           String password = txtpass.getText();
 
        
           if (username.equals("") && password.equals("")) {
                
               JOptionPane.showMessageDialog(null, "Username atau Password Masih Kosong ....");
               
        }
           else{
          
               try {
                   Class.forName("com.mysql.jdbc.Driver");
                   
                   con = DriverManager.getConnection("jdbc:mysql://localhost/beasiswa_pelajar","root","");
                   
                   pat = con.prepareStatement("select * from user where username = ? and password = ?");
                   
                   pat.setString(1, username);
                   pat.setString(2, password);
                   
                   rn =  pat.executeQuery();
                   
                   if (rn.next()) {
                        JOptionPane.showMessageDialog(null, "Login Success...");
                        
                        scene = new Scene(FXMLLoader.load(getClass().getResource("Dashboard.fxml")));
                        dialogStage.setScene(scene);
                        dialogStage.show();
                   }else{
                        JOptionPane.showMessageDialog(null, "Login Failed...");
                   }
                   
               } catch (ClassNotFoundException ex) {
                   java.util.logging.Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (SQLException ex) {
                   java.util.logging.Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
               } catch (IOException ex) {
                   Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
               }
                  
           }
        
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        buttonReset.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               txtname.setText("");
               txtpass.setText("");
               
            }
        });
        
        txtpass.setFocusTraversable(false);
    }    
    
}
