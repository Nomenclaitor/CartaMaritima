/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemSelect;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class ProblemSelectWindowController implements Initializable {

    @FXML
    private Text usernameText;
    @FXML
    private Button datamodButton;
    @FXML
    private Button userProgressButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button changeuUerButton;
    @FXML
    private Button openRandomBUtotn;
    @FXML
    private Button openSelectedButton;
    @FXML
    private ListView<?> problemListView;
    @FXML
    private Label problemTextLabel;
    @FXML
    private RadioButton option1Selector;
    @FXML
    private RadioButton option2Selector;
    @FXML
    private RadioButton option3Selector;
    @FXML
    private RadioButton option4Selection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifyPressed(ActionEvent event) {
    }

    @FXML
    private void progressPressed(ActionEvent event) {
    }

    @FXML
    private void helpPressed(ActionEvent event) {
    }

    @FXML
    private void changeUserPressed(ActionEvent event) {
    }

    @FXML
    private void openRandomPressed(ActionEvent event) {
    }

    @FXML
    private void openSelectedPressed(ActionEvent event) {
    }
    
}
