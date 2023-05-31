
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class onhold {
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";
    
    private ObservableList<onholdlist> data;
        @FXML
        private TableView<onholdlist> tableonhold;
        @FXML
        private Pane mypane;
        @FXML
        private TableColumn<?,?> columnbookid;
        @FXML
        private TableColumn<?,?> columnusername;
        @FXML
        private TextField search;
        @FXML
        private ImageView backbutton1;
        
        public void execute(MouseEvent e) {
            setcelltable();
            loaddatafromdatabase();
            loaddatafromdatabase();
            search();
        }
    
        public void executekey(KeyEvent e) {
            setcelltable();
            loaddatafromdatabase();
            loaddatafromdatabase();
            search();
        }
        public void handlebackbutton() {
            backbutton1.setOnMouseClicked(e -> {
                App a = new App();
                try {
                    a.changeScene("pane1.fxml");
                } catch (IOException e1) {
                    System.out.println("failed");
                }
            });
        }

        private void setcelltable(){
            columnbookid.setCellValueFactory(new PropertyValueFactory<>("bookid"));
            columnusername.setCellValueFactory(new PropertyValueFactory<>("username"));
            data = FXCollections.observableArrayList();
        }
        private void loaddatafromdatabase() {
            data.clear();
            try {
                Connection conn = DriverManager.getConnection(URL, user, password);
                String sql = "Select * from onhold";
                PreparedStatement pst = conn.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    data.add(new onholdlist(rs.getInt(1), rs.getString(2)));
                }
            } catch (SQLException ex) {
                Logger.getLogger(addbook.class.getName()).log(Level.SEVERE, null, ex);
            }
            tableonhold.setItems(data);
        }
        private void search() {
            search.setOnKeyReleased(e -> {
                if (search.getText().equals("")) {
                    loaddatafromdatabase();
                } else {
                    data.clear();
                    String sql = "Select * from onhold where bookid LIKE '%" + search.getText() + "%'"
                    +" UNION Select * from onhold where username LIKE '%"+search.getText()+"%'";;
                    try {
                        Connection conn = DriverManager.getConnection(URL, user, password);
                        PreparedStatement pst = conn.prepareStatement(sql);
                        ResultSet rs = pst.executeQuery();
                        while (rs.next()) {
                            data.add(new onholdlist(rs.getInt(1), rs.getString(2)));
                        }
                        tableonhold.setItems(data);
                    } catch (Exception event) {
                        System.out.println("Failed hz");
                    }
                }
            });
    
        }
            
        
}
