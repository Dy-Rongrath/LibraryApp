
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class option4 {
        @FXML
        private Button searchbutton;
        public void btnOKclicked(ActionEvent event) throws IOException{
            btnOKclicked();
        }
        public void btnOKclicked() throws IOException{
            App a = new App();
            a.changeScene("test.fxml");
        }
    
    }