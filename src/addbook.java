
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class addbook {
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";
        @FXML
        private Button btnaddbook;
        @FXML
        private TextField txtbookid;
        @FXML
        private TextField txtbookname;
        @FXML
        private TextField txtauthorname;
        @FXML
        private TextField txtsubject;
        @FXML
        private TextField txtstatus;
        @FXML
        private ImageView backbutton;
        @FXML
        private void handleaddbook(ActionEvent event){
            int bookid = Integer.parseInt(txtbookid.getText());
            String bookname = txtbookname.getText();
            String authorname = txtauthorname.getText();
            String subject = txtsubject.getText();
            String status = txtstatus.getText();
            try{
                String sql = "Insert into book(bookid,bookname,authorname,subject,status)values(?,?,?,?,?)";
                Connection conn= DriverManager.getConnection(URL, user, password);
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1,bookid);
                pst.setString(2,bookname);
                pst.setString(3, authorname);
                pst.setString(4, subject);
                pst.setString(5, status);
                pst.execute();
                System.out.println("Data INserted");
            

            }catch(SQLException ex){
                Logger.getLogger(addbook.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void btnOKclicked(ActionEvent event) throws IOException{
            handleaddbook(event);
            handlebackbutton();
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

            
        }

        //Addbook java file

        //Add book
    
