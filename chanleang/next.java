
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class next {
        @FXML
        private Button button;
        public void btnOKclicked(ActionEvent event) throws IOException{
            btnOKclicked();
        }
        public void btnOKclicked() throws IOException{
            App a = new App();
            a.changeScene("Table.fxml");
        }
    
    }
