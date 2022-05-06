/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newUserPackage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

import poiupv.PoiUPVApp;
import auxiliaries.auxiliarMethods;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class SignupFXMLController implements Initializable {

    @FXML
    private Button previousButton;
    @FXML
    private Button nextButton;
    @FXML
    private TextField passwordField;
    @FXML
    private Label passworLabel;
    @FXML
    private TextField PasswordRepField;
    @FXML
    private Label passwordRepLabel;
    
    private SimpleBooleanProperty validPassword;
    private SimpleBooleanProperty passwordRepCorrect;

    private String inputPassword;
    private String passwordRep;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validPassword = new SimpleBooleanProperty();
        passwordRepCorrect = new SimpleBooleanProperty();
        
        validPassword.setValue(Boolean.FALSE);
        passwordRepCorrect.setValue(Boolean.FALSE);
        
        BooleanBinding passwordOk = Bindings.and(validPassword, passwordRepCorrect);
        passwordField.focusedProperty().addListener((observable, oldvalue,newValue) -> {
            if (!newValue) {
                checkPassword();
            }
        });
        
        PasswordRepField.focusedProperty().addListener((observable, oldvalue,newValue) -> {
            if (!newValue) {
                checkPasswordRep();
            }
        });
        
    } 
    
    /**
     * Shows the data input of the user in the fields when coming back to this window
     * Retrieves the input of the user and loads it into the respective fields
     */
    protected void showData() {
        
    }

    //Check what is wrong with the password
    //Would be good to have
    //Test passed
    /**
     * Checks if the password meets the requirements (combination and length)
     */
    private void checkPassword() {
        inputPassword = passwordField.textProperty().getValueSafe();
        if (!model.User.checkPassword(inputPassword)) {
            auxiliarMethods.manageError(passworLabel, passwordField, validPassword);
        } else {
            auxiliarMethods.manageCorrect(passworLabel, passwordField, validPassword);
        }
    }
    
    //Test passed
    /**
     * Checks if the password repetition is equal to the previous one
     */
    private void checkPasswordRep() {
        passwordRep = PasswordRepField.textProperty().getValueSafe();
        if (!inputPassword.equals(passwordRep)) {
            auxiliarMethods.manageError(passwordRepLabel, PasswordRepField, passwordRepCorrect);
        } else {
            auxiliarMethods.manageCorrect(passwordRepLabel, PasswordRepField, passwordRepCorrect);
            SignupPrincipalFXMLController.password = inputPassword;
        }
    }
    
    /**
     * Previous clicked
     * @param event 
     */
    @FXML
    private void previousClicked(ActionEvent event) {
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/newUserPackage/signupPrincipalFXML.fxml"));
        Parent root = loader.load();
        
        SignupPrincipalFXMLController previousSignupWindow = loader.getController();
        previousSignupWindow.showData();

        Scene scene = new Scene(root, 800, 480);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Nautica Signup");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
        nextButton.getScene().getWindow().hide();
        } catch (IOException e) {
            System.out.println("Signup landing page loading failed");
        }
    }

    /**
     * Next button controller
     * Next clicked
     * @param event 
     */
    @FXML
    private void nextClicked(ActionEvent event) {
    }
    
}
