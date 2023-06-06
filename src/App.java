import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage stg;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    @Override
    public void start(Stage primarystage) throws Exception {
        stg = primarystage;
        primarystage.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primarystage.setTitle("Welcome to Library App");
        primarystage.setScene(new Scene(root, 650, 400));
        primarystage.show();
    }

    public void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

}