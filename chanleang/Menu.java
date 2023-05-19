
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Menu {
        @FXML
        private Button option1;
        public void btnOKclicked1(ActionEvent event) throws IOException{
            btnOKclicked1();
        }
        public void btnOKclicked1() throws IOException{
            App a = new App();
            a.changeScene("option1.fxml");
        }
        @FXML
        private Button option2;
        public void btnOKclicked2(ActionEvent event) throws IOException{
            btnOKclicked2();
        }
        public void btnOKclicked2() throws IOException{
            App a = new App();
            a.changeScene("option2.fxml");
        }
        @FXML
        private Button option3;
        public void btnOKclicked3(ActionEvent event) throws IOException{
            btnOKclicked3();
        }
        public void btnOKclicked3() throws IOException{
            App a = new App();
            a.changeScene("option3.fxml");
        }
        @FXML
        private Button option4;
        public void btnOKclicked4(ActionEvent event) throws IOException{
            btnOKclicked4();
        }
        public void btnOKclicked4() throws IOException{
            App a = new App();
            a.changeScene("table.fxml");
        }
        @FXML
        private Button option5;
        public void btnOKclicked5(ActionEvent event) throws IOException{
            btnOKclicked5();
        }
        public void btnOKclicked5() throws IOException{
            App a = new App();
            a.changeScene("personaltable.fxml");
        }

    
    }