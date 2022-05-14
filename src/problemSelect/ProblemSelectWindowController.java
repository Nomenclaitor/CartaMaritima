/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemSelect;

import auxiliaries.auxiliarMethods;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import poiupv.PoiUPVApp;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class ProblemSelectWindowController implements Initializable {

    @FXML
    private Text usernameText;
    @FXML
    private Button userProgressButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button datamodButton;
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
    @FXML
    private Button backButton;
    @FXML
    private Button changeuUserButton;

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
        //add update session
        PoiUPVApp.currentUser = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../loginPackage/loginFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Login", 800, 480);
        changeuUserButton.getScene().getWindow().hide();
    }

    @FXML
    private void openRandomPressed(ActionEvent event) {
        
    }

    @FXML
    private void openSelectedPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../poiupv/FXMLDocument.fxml"));
        auxiliarMethods.loadWindow(loader, "Map", 800, 480);
        changeuUserButton.getScene().getWindow().hide();
    }

    @FXML
    private void mainMenuPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../principalUsuarios/vpUsuariosFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Menu Principal", 800, 480);
        changeuUserButton.getScene().getWindow().hide();
    }



    
}
