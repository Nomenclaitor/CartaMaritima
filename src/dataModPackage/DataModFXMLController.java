/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModPackage;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import poiupv.PoiUPVApp;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class DataModFXMLController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private SimpleBooleanProperty validEmail;
    private SimpleBooleanProperty validBirthday;
    
    @FXML
    private Label passwordRepLabel;

    @FXML
    private Button mainMenuButton;

    @FXML
    private Label emailLabel1;

    @FXML
    private Button helpButton;

    @FXML
    private HBox nameTextField;

    @FXML
    private TextField nameField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField emailField;

    @FXML
    private Label passworLabel;

    @FXML
    private Button changeuUerButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button userProgressButton;

    @FXML
    private Button fileSelectButton;

    @FXML
    private Text usernameText;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField PasswordRepField;

    @FXML
    private Button saveButton;

    @FXML
    private Label emailLabel;

    @FXML
    private ImageView defaultPic6;

    @FXML
    void fileSelectPressed(ActionEvent event) {

    }

    @FXML
    void cancelPressed(ActionEvent event) {

    }

    @FXML
    void savePressed(ActionEvent event) {

    }

    @FXML
    void modifyPressed(ActionEvent event) {

    }

    @FXML
    void progressPressed(ActionEvent event) {

    }

    @FXML
    void helpPressed(ActionEvent event) {

    }

    @FXML
    void changeUserPressed(ActionEvent event) {

    }

    @FXML
    void ff4a4a(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        emailField.setText(PoiUPVApp.currentUser.getEmail());

        datePicker.setValue(PoiUPVApp.currentUser.getBirthdate());

        passwordField.setText(PoiUPVApp.currentUser.getPassword());

        validEmail = new SimpleBooleanProperty();
        validBirthday = new SimpleBooleanProperty();

        validEmail.setValue(Boolean.FALSE);
        validBirthday.setValue(Boolean.FALSE);

        

        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
               // importar checkEmail
            }
        });

        datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                //checkBirthday();
            }
        });
    }

}
