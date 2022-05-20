/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loginPackage;

import auxiliaries.auxiliarMethods;
import java.io.IOException;
import poiupv.PoiUPVApp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import newUserPackage.SignupPrincipalFXMLController;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class LoginFXMLController implements Initializable {

    @FXML
    private Button signInButton;
    @FXML
    private Button registerButton;
    @FXML
    private TextField nameTextfield;
    @FXML
    private TextField passwordTextfield;
    @FXML
    private Label name404Label;
    @FXML
    private Label incorrectPasswdLabel;

    private SimpleBooleanProperty validUsername;
    private SimpleBooleanProperty correctPassword;
    private BooleanBinding validFields;
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validUsername = new SimpleBooleanProperty();
        correctPassword = new SimpleBooleanProperty();   
        
        correctPassword.setValue(Boolean.FALSE);
        validUsername.setValue(Boolean.FALSE);
                
        validFields = Bindings.and(validUsername, correctPassword);
        
        nameTextfield.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkName();
            }
        });
        
        passwordTextfield.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkPassword();
            }
        });
    }    
    
    /**
     * Checks if the username exists in the database
     * The password field wont unlock until a correct username has been introduced
     */
    // checkname structure from Signup project
    private void checkName() {
        String nameFieldinput = nameTextfield.textProperty().getValueSafe();
        if (!PoiUPVApp.navLib.exitsNickName(nameFieldinput)) {
            auxiliarMethods.manageError(name404Label, incorrectPasswdLabel, nameTextfield, validUsername);
            incorrectPasswdLabel.setText("Introduzca un nombre de usuario valido");
        } else {
            auxiliarMethods.manageCorrect(name404Label, incorrectPasswdLabel, nameTextfield, validUsername);
            incorrectPasswdLabel.setText("La contraseña introducida es incorrecta");
            PoiUPVApp.currentUser = PoiUPVApp.navLib.getUser(nameFieldinput);
        }
    }
    
    /**
     * Checks if the password input matches the password of the provided
     * username
     */
    // checkpassword structure from signup project
    private void checkPassword() {
        if (!PoiUPVApp.currentUser.getPassword().equals((passwordTextfield.textProperty().getValueSafe()))) {
            auxiliarMethods.manageError(incorrectPasswdLabel, passwordTextfield, correctPassword);
        } else {
            auxiliarMethods.manageCorrect(incorrectPasswdLabel, passwordTextfield, correctPassword);
        }
    }

    /**
     * Sign-in button controller
     * @param event 
     */
    @FXML
    private void signInPressed(ActionEvent event) {     
        if (validUsername.getValue() == true && correctPassword.getValue() == true) {
        PoiUPVApp.currentUser = PoiUPVApp.navLib.loginUser(PoiUPVApp.currentUser.getNickName(), PoiUPVApp.currentUser.getPassword());
        auxiliaries.sessionDataAux.initData();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../principalUsuarios/vpUsuariosFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Menu principal", 960, 540);
        signInButton.getScene().getWindow().hide();
        } else if (validUsername.getValue() == false) {
            auxiliarMethods.manageError(name404Label, nameTextfield, validUsername);
        } else if (correctPassword.getValue() == false) {
            auxiliarMethods.manageError(incorrectPasswdLabel, passwordTextfield, correctPassword);
        }
    }

    /**
     * Register button controller
     * @param event 
     */
    @FXML
    private void registerPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newUserPackage/signupPrincipalFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Nautica Signup", 800, 480);
        registerButton.getScene().getWindow().hide();
    }

    @FXML
    //Cant signin, must press the button with the mouse
    private void keyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if(nameTextfield.focusedProperty().getValue() == true && validUsername.getValue() == true) {
                passwordTextfield.setFocusTraversable(true);
            } else if (passwordTextfield.focusedProperty().getValue() == true && validFields.getValue() == true) {
                signInButton.setFocusTraversable(true);
            }
        }
    }
}
