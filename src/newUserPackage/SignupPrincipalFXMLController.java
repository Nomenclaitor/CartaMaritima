/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newUserPackage;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import poiupv.PoiUPVApp;
import java.time.format.DateTimeFormatter;
import auxiliaries.auxiliarMethods;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.Period;
import javafx.scene.control.DatePicker;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class SignupPrincipalFXMLController implements Initializable {

    @FXML
    private Button nextButton;
    @FXML
    private TextField userNameField;
    @FXML
    private Label userNameLabel;
    @FXML
    private TextField emailField;
    @FXML
    private Label emailLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private DatePicker datePicker;

    private SimpleBooleanProperty validUsername;
    private SimpleBooleanProperty validEmail;
    private SimpleBooleanProperty validBirthday;
    
    private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd MM yyyy");
    
    protected static String username;
    protected static String email;
    protected static LocalDate birthday;
    protected static String password;
    protected static String profilePath;
    
    protected BooleanBinding validFields;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        validUsername = new SimpleBooleanProperty();
        validEmail = new SimpleBooleanProperty();
        validBirthday = new SimpleBooleanProperty();
        
        validUsername.setValue(Boolean.FALSE);
        validEmail.setValue(Boolean.FALSE);
        validBirthday.setValue(Boolean.FALSE);
        
        
        
        BooleanBinding userEmailBinding = Bindings.and(validUsername, validEmail);
        validFields = Bindings.and(userEmailBinding, validBirthday);
        
        userNameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkUsername();
            }
        });
        
        emailField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkEmail();
            }
        });
        
        datePicker.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                checkBirthday();
            }
        });
        
    }  
    
    protected void showData() {
        userNameField.setText(username);
        emailField.setText(email);
        datePicker.setValue(birthday);
        
        validUsername.setValue(Boolean.TRUE);
        validEmail.setValue(Boolean.TRUE);
        validBirthday.setValue(Boolean.TRUE);
        System.out.println(password);
    }
    
    //Test passed
    private void checkUsername() {
        String inputUsername = userNameField.textProperty().getValueSafe();
        
        if (PoiUPVApp.navLib.exitsNickName(inputUsername)) {
            auxiliarMethods.manageError(userNameLabel, userNameField, validUsername);
        } else if(!model.User.checkNickName(inputUsername)) {
            userNameLabel.setText("El nombre de usuario no cumple con los requisitos.");
            auxiliarMethods.manageError(userNameLabel, userNameField, validUsername);
        } else {
            auxiliarMethods.manageCorrect(userNameLabel, userNameField, validUsername);
            username = inputUsername;
        }
    }
    
    //Test passed
    private void checkEmail() {
        String inputEmail = emailField.textProperty().getValueSafe();
        if (!model.User.checkEmail(inputEmail)) {
            auxiliarMethods.manageError(emailLabel, emailField, validEmail);
        } else {
            auxiliarMethods.manageCorrect(emailLabel, emailField, validEmail);
            email = inputEmail;
        }
    }
    
    
    //test passed
    private void checkBirthday() {
        LocalDate inputBirthday = datePicker.getValue();
        if (Period.between(inputBirthday, LocalDate.now()).getYears() < 16) {
            validBirthday.setValue(Boolean.FALSE);
            birthdayLabel.visibleProperty().set(true);
            datePicker.styleProperty().setValue("-fx-background-color: #FCE5E0");
        } else {
            validBirthday.setValue(Boolean.TRUE);
            birthdayLabel.visibleProperty().set(false);
            datePicker.styleProperty().setValue("-fx-background-color: #C2FFDA");
            birthday = inputBirthday;
        }
    }

    @FXML
    private void nextClicked(ActionEvent event) {
        if (validFields.getValue() == true) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/newUserPackage/signupFXML.fxml"));
                Parent root = loader.load();

                Scene scene = new Scene(root, 800, 480);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Nautica Signup");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();

                nextButton.getScene().getWindow().hide();
            } catch (IOException e) {
                System.out.println("IOException at signup fxml loader");
            }
        }
    }
    
}
