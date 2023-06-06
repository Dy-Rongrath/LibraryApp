
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
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

public class checkin {
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";

    private ObservableList<borrowlist> data;
    @FXML
    private Pane mypane;
    @FXML
    private TableView<borrowlist> tableborrow;
    @FXML
    private TableColumn<?, ?> columnusername;
    @FXML
    private TableColumn<?, ?> columnbookid;
    @FXML
    private TableColumn<?, ?> columnborrowstatus;
    @FXML
    private TableColumn<?, ?> columndate;
    @FXML
    private TableColumn<?, ?> columnreturndate;
    @FXML
    private TextField txtsearch;
    @FXML
    private TextField counter;
    @FXML
    private ImageView backbutton;

    private void setcelltable() {
        columnusername.setCellValueFactory(new PropertyValueFactory<>("username"));
        columnbookid.setCellValueFactory(new PropertyValueFactory<>("bookid"));
        columnborrowstatus.setCellValueFactory(new PropertyValueFactory<>("borrowstatus"));
        columndate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnreturndate.setCellValueFactory(new PropertyValueFactory<>("returndate"));
        data = FXCollections.observableArrayList();
    }

    private void loaddatafromdatabase() {
        data.clear();
        try {
            Connection conn = DriverManager.getConnection(URL, user, password);
            String sql = "Select * from borrow";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new borrowlist(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getDate(5)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(addbook.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableborrow.setItems(data);
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
    public void handleviewcart(ActionEvent event) throws IOException {
        App a = new App();
        a.changeScene("cart.fxml");
    }

    private void searchbook() {
        txtsearch.setOnKeyReleased(e -> {
            if (txtsearch.getText().equals("")) {
                loaddatafromdatabase();
            } else {
                data.clear();
                String sql = "Select * from borrow where bookid LIKE '%" + txtsearch.getText() + "%'";
                int row2=0;
                try {
                    Connection conn = DriverManager.getConnection(URL, user, password);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    ResultSet rs = pst.executeQuery();
                    
                    while (rs.next()) {
                        data.add(new borrowlist(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getDate(4), rs.getDate(5)));                        row2++;
                    }
                    String row3 = Integer.toString(row2);
                    counter.setText(row3);
                    tableborrow.setItems(data);
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
        tableborrow.setOnMouseClicked(e-> {
                borrowlist b = tableborrow.getItems().get(tableborrow.getSelectionModel().getSelectedIndex());
                int tmpbookid = b.getBookid();
                try {
                    String sql = "delete from borrow where bookid LIKE '%" + tmpbookid + "%'";
                    Connection conn = DriverManager.getConnection(URL, user, password);
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.execute();
        
                } catch (SQLException ex) {
                    Logger.getLogger(addbook.class.getName()).log(Level.SEVERE, null, ex);
                }
    });
}


    }
