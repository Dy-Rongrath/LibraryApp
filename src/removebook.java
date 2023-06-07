
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
import javafx.fxml.Initializable;
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

public class removebook {
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";

    private ObservableList<booklist> data;
    @FXML
    private Pane mypane;
    @FXML
    private TableView<booklist> tablebook;
    @FXML
    private TableColumn<?, ?> columnbookid;
    @FXML
    private TableColumn<?, ?> columnbookname;
    @FXML
    private TableColumn<?, ?> columnauthorname;
    @FXML
    private TableColumn<?, ?> columnsubject;
    @FXML
    private TableColumn<?, ?> columnstatus;
    @FXML
    private TextField txtsearch;
    @FXML
    private TextField counter;
    @FXML
    private ImageView backbutton;


    private void setcelltable() {
        columnbookid.setCellValueFactory(new PropertyValueFactory<>("bookid"));
        columnbookname.setCellValueFactory(new PropertyValueFactory<>("bookname"));
        columnauthorname.setCellValueFactory(new PropertyValueFactory<>("authorname"));
        columnsubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        columnstatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        data = FXCollections.observableArrayList();
    }

    private void loaddatafromdatabase() {
        data.clear();
        try {
            Connection conn = DriverManager.getConnection(URL, user, password);
            String sql = "Select * from book";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new booklist(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),rs.getString(5)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(addbook.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablebook.setItems(data);
    }

    public void btnOKclicked(ActionEvent event) throws IOException {
        setcelltable();
        loaddatafromdatabase();
        loaddatafromdatabase();
        searchbook();
        handlebackbutton();
        clickontable();
    }

    public void execute(MouseEvent e) {
        setcelltable();
        loaddatafromdatabase();
        loaddatafromdatabase();
        searchbook();
        handlebackbutton();
        clickontable();
    }

    public void executekey(KeyEvent e) {
        setcelltable();
        loaddatafromdatabase();
        loaddatafromdatabase();
        searchbook();
        handlebackbutton();
        clickontable();
    }

    private void searchbook() {
        txtsearch.setOnKeyReleased(e -> {
            if (txtsearch.getText().equals("")) {
                loaddatafromdatabase();
            } else {
                data.clear();
                String sql = "Select * from book where bookname LIKE '%" + txtsearch.getText() + "%'"
                +" UNION Select * from book where bookid LIKE '%" + txtsearch.getText() + "%'"
                +" UNION Select * from book where subject LIKE '%" + txtsearch.getText() + "%'"
                +" UNION Select * from book where authorname LIKE '%" + txtsearch.getText() + "%'";
                int row2=0;
                try {
                    Connection conn = DriverManager.getConnection(URL, user, password);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    
                    while (rs.next()) {
                        data.add(new booklist(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
    public void handlebackbutton() {
        backbutton.setOnMouseClicked(e -> {
            App a = new App();
            try {
                a.changeScene("pane1.fxml");
            } catch (IOException e1) {
                System.out.println("failed");
            }
        });
    }

    private void clickontable(){
            tablebook.setOnMouseClicked(e-> {
                    booklist b = tablebook.getItems().get(tablebook.getSelectionModel().getSelectedIndex());
                    int tmpbookid = b.getBookid();
                    try {
                        String sql = "delete from book where bookid LIKE '%" + tmpbookid +"%'";
                        Connection conn= DriverManager.getConnection(URL, user, password);
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.execute();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Deleted successfully");
                        alert.setContentText("Sucessfully deleted");
                        alert.showAndWait();

                    } catch (Exception event) {
                        System.out.println("failed");
                    }
                    try {
                        String sql1 = "update book set status = ? where bookid = '"+tmpbookid+ "'";
                        Connection conn1= DriverManager.getConnection(URL, user, password);
                        PreparedStatement pst1 = conn1.prepareStatement(sql1);
                        pst1.setString(1,"on-loan");
                        pst1.execute();
                    } catch (Exception event) {
                        System.out.println("failed d");
                    }
        });
    }

}
