
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

import javax.management.Query;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

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

public class tablecontroller {
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";
    
    private ObservableList<booklist> data;
        @FXML
        private TableView<booklist> tablebook;
        @FXML
        private TableColumn<?,?> columnname;
        @FXML
        private TableColumn<?,?> columnbookname;
        @FXML
        private TableColumn<?,?> columndate;
        @FXML
        private TableColumn<?,?> columnstatus;
        @FXML
        private TextField nameonhold;
        @FXML
        private TextField booknameonhold;
        @FXML
        private TextField dateonhold;
        @FXML
        private TextField statusonhold;
        @FXML
        private TextField txtsearch;
        @FXML
        private ImageView backbutton;
        

        private void setcelltable(){
            columnname.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnbookname.setCellValueFactory(new PropertyValueFactory<>("bookname"));
            columndate.setCellValueFactory(new PropertyValueFactory<>("date"));
            columnstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            data = FXCollections.observableArrayList();
        }
        private void loaddatafromdatabase(){
            data.clear();
            try{
            Connection conn = DriverManager.getConnection(URL, user, password);
            String sql = "Select * from book";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                data.add(new booklist(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
            }
            }
            catch(SQLException ex){
                Logger.getLogger(addproductcontroller.class.getName()).log(Level.SEVERE, null, ex);
            }
            tablebook.setItems(data); 
        }
        public void btnOKclicked(ActionEvent event) throws IOException{
            setcelltable();
            loaddatafromdatabase();
            setcellvaluefromtabletotextfield();
            loaddatafromdatabase();
            searchbook();
            countcolandrow();
            handlebackbutton();
        }
        private void setcellvaluefromtabletotextfield(){
            tablebook.setOnMouseClicked(e->  {
                    booklist b = tablebook.getItems().get(tablebook.getSelectionModel().getSelectedIndex());
                    nameonhold.setText(b.getName());
                    booknameonhold.setText(b.getBookname());
                    dateonhold.setText(Integer.toString(b.getDate()));    
                    statusonhold.setText(b.getStatus());
        });
        }
        public void handlebtnonhold(ActionEvent event){
            String sql = "update book set name = ?, bookname = ?, status = ? where date = ?";
            try{
            String name = nameonhold.getText();
            String bookname = booknameonhold.getText();
            int date = Integer.parseInt(dateonhold.getText()); 
            String status = statusonhold.getText(); 
            Connection conn = DriverManager.getConnection(URL, user, password);
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, bookname);
            pst.setString(3, status);
            pst.setInt(4, date);
            pst.execute();
            }
            catch(Exception e){
                    System.out.println("Failed");
            }
        }
        private void searchbook(){
            txtsearch.setOnKeyReleased(e->{
                if(txtsearch.getText().equals("")){
                    loaddatafromdatabase();
                }
                else{
                    data.clear();
                    String sql = "Select * from book where date LIKE '%"+txtsearch.getText()+"%'"
                    +" UNION Select * from book where bookname LIKE '%"+txtsearch.getText()+"%'"
                    +" UNION Select * from book where name LIKE '%"+txtsearch.getText()+"%'";
                try{
                Connection conn = DriverManager.getConnection(URL, user, password);
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while(rs.next()){
                    data.add(new booklist(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
                }
                tablebook.setItems(data);
                }
                catch(Exception event){
                    System.out.println("Failed hz");
                }
            }
            });
            
        }
        private void countcolandrow(){
            try{
                Connection conn = DriverManager.getConnection(URL, user, password);
                String sql = "select * from book";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery(sql);
                ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
                int numberOfColumn = rsmd.getColumnCount();
                System.out.println("Number Of Columns: " + numberOfColumn);
                int row = 0;
                while (rs.next()) {
                    row++;
                }
                System.out.println("Number of rows: " + row);
            
        }
            catch(Exception event){
                System.out.println("fail");
            }
        }
        public void handlebackbutton(){
            backbutton.setOnMouseClicked(e->{
            App a = new App();
            try {
                a.changeScene("test.fxml");
            } catch (IOException e1) {
                System.out.println("failed");
            }
            });
        }
    }


