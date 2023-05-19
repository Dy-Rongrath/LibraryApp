
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
import javax.swing.Action;

import com.mysql.cj.jdbc.result.ResultSetMetaData;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class option2 {
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";
    
    private ObservableList<booklist> data;
        @FXML
        private Pane mypane;
        @FXML
        private ColorPicker mycolorpicker;
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
        private TextField txtsearch;
        @FXML
        private TextField counter;
        @FXML
        private ImageView backbutton;


        public void changecolor(ActionEvent event){
            Color mycolor = mycolorpicker.getValue();
            mypane.setBackground(new Background(new BackgroundFill(mycolor, null, null)));
        }
        

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
            loaddatafromdatabase();
            searchbook();
            handlebackbutton();
        }
        public void execute(MouseEvent e){
                setcelltable();
                loaddatafromdatabase();
                loaddatafromdatabase();
                searchbook();
                handlebackbutton();
        }
        public void executekey(KeyEvent e){
            setcelltable();
            loaddatafromdatabase();
            loaddatafromdatabase();
            searchbook();
            handlebackbutton();
        }
        private void searchbook() {
            txtsearch.setOnKeyReleased(e -> {
                if (txtsearch.getText().equals("")) {
                    loaddatafromdatabase();
                } else {
                    data.clear();
                    String sql = "Select * from book where name LIKE '%" + txtsearch.getText() + "%'";
                    int row2=0;
                    try {
                        Connection conn = DriverManager.getConnection(URL, user, password);
                        PreparedStatement pst = conn.prepareStatement(sql);
                        ResultSet rs = pst.executeQuery();
                        while (rs.next()) {
                            data.add(new booklist(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getString(4)));
                            row2++;
                        }
                        String row3 = Integer.toString(row2);
                        counter.setText(row3);
                        tablebook.setItems(data);
                    } catch (Exception event) {
                        System.out.println("Failed hz");
                    }
                }
            });
    
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


