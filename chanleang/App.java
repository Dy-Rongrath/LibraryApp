
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage stg;
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws Exception {
        stg = primarystage;
        primarystage.setResizable(true);
        //Alert alert = new Alert(Alert.AlertType.INFORMATION);
        //alert.setTitle("Loan Alert");
        //alert.setHeaderText("Loan Management");
        //alert.setContentText("Book Available");
        //alert.showAndWait();
        Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
        primarystage.setTitle("Welcome to Library App");
        primarystage.setScene(new Scene(root, 640, 400));
        primarystage.show();

        JDBCDemo db = new JDBCDemo();
        db.connect();
    }

    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }
    
}
