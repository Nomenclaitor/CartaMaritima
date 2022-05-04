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
        
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validUsername = new SimpleBooleanProperty();
        correctPassword = new SimpleBooleanProperty();   
        
        correctPassword.setValue(Boolean.FALSE);
        validUsername.setValue(Boolean.FALSE);
        
        signInButton.setDisable(true);
        
        BooleanBinding validFields = Bindings.and(validUsername, correctPassword);
        
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
    
    // checkname structure from Signup project
    private void checkName() {
        String nameFieldinput = nameTextfield.textProperty().getValueSafe();
        if (!PoiUPVApp.navLib.exitsNickName(nameFieldinput)) {
            auxiliarMethods.manageError(name404Label, incorrectPasswdLabel, nameTextfield, validUsername);
            passwordTextfield.disableProperty().bind(Bindings.not(validUsername));
            passwordTextfield.opacityProperty().set(0.25);
            incorrectPasswdLabel.setText("Introduzca un nombre de usuario valido para continuar");
        } else {
            auxiliarMethods.manageCorrect(name404Label, incorrectPasswdLabel, nameTextfield, validUsername);
            passwordTextfield.opacityProperty().set(1);
            incorrectPasswdLabel.setText("La contraseña introducida es incorrecta");
            PoiUPVApp.currentUser = PoiUPVApp.navLib.getUser(nameFieldinput);
            signInButton.setDisable(false);
        }
    }
    
    // checkpassword structure from signup project
    private void checkPassword() {
        if (!PoiUPVApp.currentUser.getPassword().equals((passwordTextfield.textProperty().getValueSafe()))) {
            auxiliarMethods.manageError(incorrectPasswdLabel, passwordTextfield, correctPassword);
        } else {
            auxiliarMethods.manageCorrect(incorrectPasswdLabel, passwordTextfield, correctPassword);
        }
    }

    @FXML
    private void signInPressed(ActionEvent event) {
        //Main window link
        signInButton.getScene().getWindow().hide();
    }

    @FXML
    private void registerPressed(ActionEvent event) {
        //User register link
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newUserPackage/signupPrincipalFXML.fxml"));
            Parent root = loader.load();
            
            Scene scene = new Scene(root, 800, 480);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Nautica Signup");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            
            registerButton.getScene().getWindow().hide();
        } catch (IOException e) {
            System.out.println("IOException at signupPrincipal fxml loader");
        }
    }
}
