
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

public class addproductcontroller {
    String URL = "jdbc:mysql://localhost/book";
    String user = "root";
    String password = "";
        @FXML
        private Button btnaddbook;
        @FXML
        private TextField txtname;
        @FXML
        private TextField txtbookname;
        @FXML
        private TextField txtdate;
        @FXML
        private TextField txtstatus;
        @FXML
        private void handleaddbook(ActionEvent event){
            String name = txtname.getText();
            String bookname = txtbookname.getText();
            int date = Integer.parseInt(txtdate.getText());
            String status = txtstatus.getText();
            try{
                String sql = "Insert into book(name,bookname,date,status)values(?,?,?,?)";
                JDBCDemo db = new JDBCDemo();
                Connection conn= DriverManager.getConnection(URL, user, password);
                db.connect();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1,name);
                pst.setString(2,bookname);
                pst.setInt(3, date);
                pst.setString(4, status);
                pst.execute();
                System.out.println("Data INserted");
            

            }catch(SQLException ex){
                Logger.getLogger(addproductcontroller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void btnOKclicked(ActionEvent event) throws IOException{
            handleaddbook(event);
        }
        public void btnOKclicked() throws IOException{
            App a = new App();
            a.changeScene("test.fxml");
        }

            
        }
    
