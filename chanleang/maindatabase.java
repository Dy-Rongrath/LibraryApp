
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class maindatabase extends Application{

    @Override
    public void start(Stage primarystage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("databasesample.fxml"));
        primarystage.setTitle("Hello world");
        primarystage.setScene(new Scene(root, 300,275));
        primarystage.show();

        JDBCDemo db = new JDBCDemo();
        db.connect();

    }
    public static void main(String[] args) {
        launch(args);
    }
    
}
