
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class dialog extends Application {
    private static Stage stg;
    public void start(Stage primarystage) throws Exception {
        stg = primarystage;
        primarystage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("table.fxml"));
        primarystage.setTitle("Welcome to Library App");
        primarystage.setScene(new Scene(root, 650, 400));
        primarystage.show();
        JDBCDemo db = new JDBCDemo();
        db.connect();
    }
    
}