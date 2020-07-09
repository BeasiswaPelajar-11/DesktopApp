/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beasiswapelajar;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author Yepta
 */
public class DashboardController implements Initializable {
    
    @FXML
    private TableView<Model> table;
    @FXML
    private TableColumn<?, ?> t_nama;
    @FXML
    private TableColumn<?, ?> t_deskripsi;
    @FXML
    private TableColumn<?, ?> t_nominal;
    @FXML
    private TableColumn<?, ?> t_id;
    
    @FXML
    private TextField nama;
    @FXML
    private TextField deskripsi;
    @FXML
    private TextField nominal;
    @FXML
    private TextField id;
    
    @FXML
    private Button btnlogout;
    @FXML
    private Button exit;
    @FXML
    private Button reset;
    
    
    private Connection con = null;
    private PreparedStatement pre = null;
    private ResultSet res = null;
    private ObservableList<Model>datamodel;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        
        con = DBConnection.Connect();
        datamodel = FXCollections.observableArrayList();
        setCell();
        loadDataFromDatabase();
        setCellValueFromTable();
        
        reset.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               nama.setText("");
               deskripsi.setText("");
               nominal.setText("");
            }
        });
        
        btnlogout.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) btnlogout.getScene().getWindow();
                Parent root;
                
                try {
                    root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    
                } catch (IOException iOException) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, iOException);
                            
                }
            }
            
        }
        );
        
        exit.setOnAction(event -> Platform.exit());
    }

    @FXML
    private void setCell() {       
        t_nama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        t_deskripsi.setCellValueFactory(new PropertyValueFactory<>("deskripsi"));
        t_nominal.setCellValueFactory(new PropertyValueFactory<>("nominal"));
        t_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        
    }
    
    private void loadDataFromDatabase(){
        try{
            pre = con.prepareStatement("select * from beasiswa");
            res = pre.executeQuery();
            while(res.next()){
                datamodel.add(new Model(res.getInt(1),res.getString(2),res.getString(3), res.getInt(4)));
            }
        }catch(SQLException e){
            
        }
        table.setItems(null);
        table.setItems(datamodel);
    }
    
    private void setCellValueFromTable(){
        table.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
               Model dm = table.getItems().get(table.getSelectionModel().getSelectedIndex());
                              
               nama.setText(String.valueOf(dm.getNama()));
               deskripsi.setText(dm.getDeskripsi());
               nominal.setText(String.valueOf(dm.getNominal()));
               id.setText(String.valueOf(dm.getId()));
               
               
            }
            
        });
    }
    
    @FXML
    private void refresh(ActionEvent Event) throws IOException{
       try{
          Node node = (Node) Event.getSource();
          Stage stage = (Stage) node.getScene().getWindow();
          Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
          Scene scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
        }catch(Exception e){
            
        }
      
    }
    
    @FXML
    private void tambahBeasiswa(ActionEvent event) {
         try{
            if(nama.getText().isEmpty() && deskripsi.getText().isEmpty() && nominal.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information");
                    alert.setContentText("Data belum terisi semua");
                    alert.showAndWait();
            }
            else{
                Connection conn = DBConnector.getConnection();
                String query = "insert into beasiswa(nama, deskripsi, nominal) values(?,?,?)";
                    PreparedStatement pre = conn.prepareStatement(query);
                    pre.setString(1, nama.getText());
                    pre.setString(2, deskripsi.getText());
                    pre.setString(3, nominal.getText());
                    
                    pre.execute();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Data berhasil disimpan. Silahkan Refresh");
                    alert.showAndWait();
                    
                    
                    
            }
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    private void updateBeasiswa(ActionEvent Event){
        try{
            String sql = "update beasiswa set nama = ?,deskripsi = ?,nominal = ? where id = ?";            
            
            String namas = nama.getText();
            String deskripsis = deskripsi.getText();
            String nominals = nominal.getText();
            String ids = id.getText();
                  
            
            pre = con.prepareStatement(sql);
            pre.setString(1, namas);
            pre.setString(2, deskripsis);
            pre.setString(3, nominals);
            pre.setString(4, ids);
            
            
            int i = pre.executeUpdate();
            if(i == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Data berhasil disimpan. Silahkan Refresh");
                    alert.showAndWait();
            }
        }catch(Exception ex){
            
        }
    }
    @FXML
    private void hapusBeasiswa(ActionEvent Event){
        try{
            String ids = id.getText();
            String sql = "delete from beasiswa where id = "+ids;
            Statement st = con.createStatement();
            boolean i= st.execute(sql);
            
            if(i=true){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Data berhasil dihapus. Silahkan Refresh");
                    alert.showAndWait();
            }
        }catch(Exception e){
            
        }
    }
    
}
