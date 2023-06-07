import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class pane1 implements Initializable{
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    private void home(MouseEvent event){
        System.out.println("You're at the home page");
    }
    @FXML
    private void page01(MouseEvent event) throws IOException{
        App a = new App();
        a.changeScene("checkout.fxml");
    }
    @FXML
    private void page02(MouseEvent event) throws IOException{
        App a = new App();
        a.changeScene("checkin.fxml");
    }
    @FXML
    private void page03(MouseEvent event) throws IOException{
        App a = new App();
        a.changeScene("onhold.fxml");
    }
    @FXML
    private void page04(MouseEvent event) throws IOException{
        App a = new App();
        a.changeScene("addbook.fxml");
    }
    @FXML
    private void page05(MouseEvent event) throws IOException{
        App a = new App();
        a.changeScene("removebook.fxml");
    }
    @FXML
    private void page06(MouseEvent event) throws IOException{
        App a = new App();
        a.changeScene("login.fxml");
    }
    
}