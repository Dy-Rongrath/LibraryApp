import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javafx.fxml.Initializable;

public class LoanBookController implements Initializable {
    private static final int MAX_BOOK_TO_ISSUE = 3;
    Connection con;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField bcategoryTxt;

    @FXML
    private TextField bquantityTxt;

    @FXML
    private TextField btitleTxt;

    @FXML
    private Button clearBtn;

    @FXML
    private Button issueBookBtn;

    @FXML
    private DatePicker issueDate;

    @FXML
    private Button searchBookBtn;

    @FXML
    private TextField searchBookCode;

    @FXML
    private Button searchStudentBtn;

    @FXML
    private TextField searchStudentID;

    @FXML
    private TextField stNameTxt;

    @FXML
    private TextField stPasswordTxt;

    @FXML
    private TextField stUserNameTxt;

    public void clear() {

        //clear book form
        searchBookCode.setDisable(false);
        searchStudentID.setDisable(false);
        searchBookCode.setText("");
        btitleTxt.setText("");
        bquantityTxt.setText("");
        bcategoryTxt.setText("");

        //clear student form
        searchStudentID.setText("");
        stNameTxt.setText("");
        stUserNameTxt.setText("");
        stPasswordTxt.setText("");
        issueDate.setValue(null);

    }

    @FXML
    void goBack(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void goToLoanList(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoanList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }


    @FXML
    void searchBook(ActionEvent event) {
        ConnectToBook();
        ResultSet rs = null;
        PreparedStatement pst = null;
        String id = searchBookCode.getText();
        String sql = "select *from books where id = '" + id + "' ";
        try {

            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            if (rs.next()) {
                System.out.println("Book Available");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Loan Alert");
                alert.setHeaderText("Loan Management");
                alert.setContentText("Book Available");
                alert.showAndWait();
                // msgLabel.setText("Book Available");
                searchBookCode.setDisable(false);
                btitleTxt.setText(rs.getString(2));
                bcategoryTxt.setText(rs.getString(4));
                bquantityTxt.setText(rs.getString(3));
                rs.close();
                pst.close();
            } else {
                System.out.println("Book is not available");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Loan Alert");
                alert.setHeaderText("Loan Management");
                alert.setContentText("Book is not Available");
                alert.showAndWait();
                // msgLabel.setText("Book Not Available");
                //JOptionPane.showMessageDialog(null,"The book does not exist, check the book ID well");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }
    }

    //Seach students
    @FXML
    public void searchStudent(ActionEvent event) throws IOException {
        ResultSet rs = null;
        PreparedStatement pst = null;
        ConnectToStudent();
        String stud = searchStudentID.getText();

        String sql = "select *from students where id  = '" + stud + "'";
        try {
            // Connection con = Connectivity.ConnectDb();
            pst = con.prepareStatement(sql);

            rs = pst.executeQuery();

            if (rs.next()) {
                stNameTxt.setText(rs.getString(2));
                stUserNameTxt.setText(rs.getString(3));
                stPasswordTxt.setText(rs.getString(4));
                searchStudentID.setDisable(true);
                rs.close();
                pst.close();
            } else {
                JOptionPane.showMessageDialog(null, "The student does not exist in the database");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {

            }
        }
    }

    @FXML
    void issueBook(ActionEvent event) {
        conne();
        clear();
    }

    public void conne() {
        ResultSet rs = null;
        PreparedStatement pst = null;
        ConnectToStudent();

        String reg = searchStudentID.getText();
        String sql = "select *from students where id='" + reg + "'";
        String sql3 = "update students set noIssued = ? where id='" + reg + "' ";
        try {
            // Connection con = Connectivity.ConnectDb();
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            int noIssued = rs.getInt(6);

            //int noIssued = Integer.parseInt(no);
            if (noIssued < MAX_BOOK_TO_ISSUE) {
                issue();
                PreparedStatement prepstm = con.prepareStatement(sql3);
                //String cnt = Integer.toString(counter);
                int counter = noIssued;
                counter++;
                prepstm.setInt(1, counter);
                prepstm.executeUpdate();
                pst.close();
                rs.close();
            } else {
                JOptionPane.showMessageDialog(null, "You cannot be issued with another book" + "\nYou already have 3 books");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    //Issue
    public void issue() {
        ResultSet rs = null, rs1, rs2, rs3 = null;
        PreparedStatement pst = null, pst1, pst2, pst3 = null;
        // ConnectToBook();
        // ConnectToStudent();

        String reg = searchStudentID.getText();
        String bId = searchBookCode.getText();

        String sql1 = "select *from students where id = '" + reg + "'";
        String sql2 = "select *from books where id = '" + bId + "'";
        String sql4 = "select counter from students where id = '" + reg + "'";
        String sql = "insert into issued (id,name,username,bid,booktitle,quantity,category,dateissued) value(?,?,?,?,?,?,?,?)";

        try {
            ConnectToStudent();
            // Connection con = Connectivity.ConnectDb();
            pst1 = con.prepareStatement(sql1);
            rs1 = pst1.executeQuery();
            pst2 = con.prepareStatement(sql1);
            rs2 = pst2.executeQuery();
            if (rs1.next() && rs2.next()) {

                try {

                    //pst3 = conn.prepareStatement(sql4);
                    //rs3 = pst3.executeQuery();
                    //String numBook = rs3.getString(8);
                    //int numOfBook = Integer.parseInt(numBook);
                    pst = con.prepareStatement(sql);
                    {

                        pst.setString(1, searchStudentID.getText());
                        pst.setString(2, stNameTxt.getText());
                        pst.setString(3, stUserNameTxt.getText());
                        pst.setString(4, searchBookCode.getText());
                        pst.setString(5, btitleTxt.getText());
                        pst.setString(6, bquantityTxt.getText());
                        pst.setString(7, bcategoryTxt.getText());
                        LocalDate localDate = issueDate.getValue();
                        pst.setString(8, localDate.toString());



                        pst.execute();
                        pst.close();
                        JOptionPane.showMessageDialog(null, "Book issued Successfully");

                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                    System.out.println("error");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Either the book ID or the Student Registration number is incorrect");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "sasa");
        }
        try {
            ConnectToBook();
        String sql3 = "DELETE FROM books where id = '"+bId+"'";
        pst3 = con.prepareStatement(sql3);
        pst3.executeUpdate(sql3);
        pst3.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
        } finally {
            try {
                rs.close();
                pst.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "The book is removed from the shelf");
            }
        }
    }


    //call the book database
    public void ConnectToBook(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:/book","root","");
            System.out.println("sucessfully connected yayyyy");
        } catch (ClassNotFoundException ex) {
          System.out.println("hikhik");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //call the student database
    public void ConnectToStudent(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:/student","root","");
            System.out.println("sucessfully connected to student database yayyy");
        } catch (ClassNotFoundException ex) {
          System.out.println("hikhik");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        ConnectToBook();
        ConnectToStudent();
    }
    
}
