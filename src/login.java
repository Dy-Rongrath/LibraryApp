
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

public class login {
    @FXML
    private Button btnlogin;
    @FXML
    private TextField txtusername;
    @FXML
    private TextField txtpassword;

    private void handlelogin(ActionEvent event) throws IOException {
        String username = txtusername.getText();
        String password = txtpassword.getText();
        if (username.equals("admin") && password.equals("admin")) {
            App a = new App();
            a.changeScene("pane1.fxml");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Login sucessful");
            alert.setContentText("Correct username and password");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Invalid");
            alert.setHeaderText("Login unsucessful");
            alert.setContentText("incorrect username and password");
            alert.showAndWait();
        }

    }

    public void btnOKclicked(ActionEvent event) throws IOException {
        handlelogin(event);
    }
}
