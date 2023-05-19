
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.plaf.ButtonUI;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class personalinfo {
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";
    
    private ObservableList<booklist> data;
        @FXML
        private TableView<booklist> tablepersonalinfo;
        @FXML
        private Button btnenter;
        @FXML
        private TableColumn<?,?> columnname;
        @FXML
        private TableColumn<?,?> columnbookname;
        @FXML
        private TableColumn<?,?> columndate;
        @FXML
        private TableColumn<?,?> columnstatus;
        @FXML
        private TextField namefield;
        @FXML
        private ImageView backbutton1;

        private void setcelltable(){
            columnname.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnbookname.setCellValueFactory(new PropertyValueFactory<>("bookname"));
            columndate.setCellValueFactory(new PropertyValueFactory<>("date"));
            columnstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            data = FXCollections.observableArrayList();
        }
        public void btnOKclicked(ActionEvent event) throws IOException{
            setcelltable();
            namefield.setText("chanleang");
            searchbook();
            handlebackbutton();
        }
        private void searchbook(){
                if(namefield.getText().equals("chanleang")){
                    String sql = "Select * from book where date LIKE '%"+namefield.getText()+"%'"
                    +" UNION Select * from book where name LIKE '%"+namefield.getText()+"%'";
                try{
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while(rs.next()){
                    data.add(new booklist(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
                }
                tablepersonalinfo.setItems(data);
                }
                catch(Exception event){
                    System.out.println("Failed hz");
                }
            }
            }
            public void handlebackbutton(){
                backbutton1.setOnMouseClicked(e->{
                App a = new App();
                try {
                    a.changeScene("test.fxml");
                } catch (IOException e1) {
                    System.out.println("failed");
                }
                });
            }
            
        
}