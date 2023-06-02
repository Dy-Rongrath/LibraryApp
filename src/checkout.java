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

public class checkout implements Initializable{
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
    @FXML
    private TextField bookidonhold;
    @FXML
    private TextField booknameonhold;
    @FXML
    private TextField authornameonhold;
    @FXML
    private TextField subjectonhold;
    @FXML
    private TextField usernameonhold;
    @FXML
    private ChoiceBox<String> mychoicebox;
    private String[] status ={"on-loan","available"};
    @Override
public void initialize(java.net.URL location, ResourceBundle resources) {
    mychoicebox.getItems().addAll(status);
}

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
                    String tmpstatus = "on-loan";
                    String username = usernameonhold.getText();
                    LocalDate now = LocalDate.now();
                    LocalDate then = now.plusDays(7);
                    long timenow = System.currentTimeMillis();
                    long timereturn = System.currentTimeMillis() + ((long) 7*24*60*60*1000);
                    //Date d = new Date(time);
                    //System.out.println(d);
                    if(b.getStatus().equals("available")){
                    try {
                        String sql = "Insert into borrow(username,bookid,borrowstatus,date,returndate)values(?,?,?,?,?)";
                        Connection conn= DriverManager.getConnection(URL, user, password);
                        PreparedStatement pst = conn.prepareStatement(sql);
                        pst.setString(1,username);
                        pst.setInt(2,tmpbookid);
                        pst.setString(3, tmpstatus);
                        pst.setDate(4, new java.sql.Date(timenow));
                        pst.setDate(5, new java.sql.Date(timereturn));
                        pst.execute();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setHeaderText("Please return the book on "+then);
                        alert.setContentText("Sucessfully added to borrow");
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
                }else{
                    try {
                        String sql = "Select * from borrow where bookid LIKE '%" + tmpbookid + "%'";
                        Connection conn = DriverManager.getConnection(URL, user, password);
                        PreparedStatement pst = conn.prepareStatement(sql);
                        ResultSet rs = pst.executeQuery();
                        while (rs.next()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Book UNAVAILABLE");
                            alert.setHeaderText("This book will be available on "+ rs.getDate(5));
                            alert.setContentText("Added to on-hold");
                            alert.showAndWait();
                        }
                        String sql1 = "Insert into onhold(bookid,username)values(?,?)";
                        Connection conn1= DriverManager.getConnection(URL, user, password);
                        PreparedStatement pst1 = conn1.prepareStatement(sql1);
                        pst1.setInt(1,tmpbookid);
                        pst1.setString(2,username);
                        pst1.execute();
                    } catch (Exception event) {
                        System.out.println("failed");
                    }
                        
                }
                booknameonhold.setText(b.getBookname());  
                authornameonhold.setText(b.getAuthorname());  
                subjectonhold.setText(b.getSubject());  
                bookidonhold.setText(Integer.toString(b.getBookid()));  
        });
    }
public void handlebtnonhold(ActionEvent event){
    String sql = "update book set status = ? where bookid = ?";
    String status = mychoicebox.getValue();
    String username = usernameonhold.getText();
    int bookid = Integer.parseInt(bookidonhold.getText());  
    long timenow = System.currentTimeMillis();
    long timereturn = System.currentTimeMillis() + ((long) 7*24*60*60*1000);
    try{
    Connection conn = DriverManager.getConnection(URL, user, password);
    PreparedStatement pst = conn.prepareStatement(sql);
    pst.setString(1, status);
    pst.setInt(2, bookid);
    pst.execute();
    }
    catch(Exception e){
            System.out.println("Failed");
    }
    if(mychoicebox.getValue().equals("on-loan")){
        try {
            String sql1 = "delete from onhold where bookid LIKE '%" + Integer.parseInt(bookidonhold.getText()) + "%'";
            Connection conn1 = DriverManager.getConnection(URL, user, password);
            PreparedStatement pst1 = conn1.prepareStatement(sql1);
            pst1.execute();
            String sql2 = "Insert into borrow(username,bookid,borrowstatus,date,returndate)values(?,?,?,?,?)";
            Connection conn2= DriverManager.getConnection(URL, user, password);
            PreparedStatement pst2 = conn2.prepareStatement(sql2);
            pst2.setString(1, username);
            pst2.setInt(2,bookid);
            pst2.setString(3, status);
            pst2.setDate(4, new java.sql.Date(timenow));
            pst2.setDate(5, new java.sql.Date(timereturn));
            pst2.execute();

        } catch (SQLException ex) {
            Logger.getLogger(addbook.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    }

}
