/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataModPackage;

import DBAccess.NavegacionDAOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.beans.binding.Bindings;

import poiupv.PoiUPVApp;
import auxiliaries.auxiliarMethods;
import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Optional;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import newUserPackage.SignupPrincipalFXMLController;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class DataModFXMLController implements Initializable {

    @FXML
    private Text usernameText;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button userProgressButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button changeuUerButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField PasswordRepField;
    @FXML
    private Button fileSelectButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Button saveButton;

    
    private SimpleBooleanProperty validEmail = new SimpleBooleanProperty(true);
    private SimpleBooleanProperty validBirthday = new SimpleBooleanProperty(true);
    private SimpleBooleanProperty validPassword = new SimpleBooleanProperty(true);
    private SimpleBooleanProperty passwordRepCorrect = new SimpleBooleanProperty(false);
    private BooleanBinding validFields;
    
    private String newEmail;
    private LocalDate newBirthday;
    private String newPassword;
    private Image newProfileImage;
    
    private final String currentEmail = PoiUPVApp.currentUser.getEmail();
    private final LocalDate currentBirthday = PoiUPVApp.currentUser.getBirthdate();
    private final String currentPassWord = PoiUPVApp.currentUser.getPassword();
    private final Image currentProfileImage = PoiUPVApp.currentUser.getAvatar();
    
    private String inputPassword;
    private String passwordRep;
    
    @FXML
    private Label birthdayLabel;
    @FXML
    private Button profilePic1;
    @FXML
    private Button profilePic2;
    @FXML
    private Button profilePic3;
    @FXML
    private Button profilePic4;
    @FXML
    private Button profilePic5;
    @FXML
    private ImageView customProfilePic;
    @FXML
    private Label filePathLabel;
    @FXML
    private Label invalidEmailLabel;
    @FXML
    private Label passwordFormatLabel;
    @FXML
    private Label passwordDontMatchLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // TODO
        
        newEmail = currentEmail;
        newBirthday = currentBirthday;
        newPassword = currentPassWord;
        newProfileImage = currentProfileImage;
        
        validFields = Bindings.and(validEmail, validBirthday).and(validPassword).and(passwordRepCorrect);
        
        
        nameField.setDisable(true);
        nameField.setText(PoiUPVApp.currentUser.getNickName());
        usernameText.setText(PoiUPVApp.currentUser.getNickName());
        emailField.setText(currentEmail);
        datePicker.setValue(currentBirthday);
        passwordField.setText(currentPassWord);
        customProfilePic.setImage(PoiUPVApp.currentUser.getAvatar());
                
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

    @FXML
    private void mainMenuPressed(ActionEvent event) {
        cancelPressed(event);
    }

    @FXML
    private void progressPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../userProgressPackage/userProgressFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Progreso del usuario", 960, 540);
        userProgressButton.getScene().getWindow().hide();
    }

    @FXML
    private void helpPressed(ActionEvent event) {
    }

    @FXML
    private void changeUserPressed(ActionEvent event) {
        if (auxiliarMethods.promptAlert("salir al menu de Login", "Si ha modificado alguno de los campos, estos no se guardarán.\nHaga click en OK para cerrar sesión o cancelar para cerrar esta ventana emergente.")) {
            //add Session
            PoiUPVApp.saveSession();
            PoiUPVApp.currentUser = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginPackage/loginFXML.fxml"));
            auxiliarMethods.loadWindow(loader, "nautica Login", 800, 480);
            changeuUerButton.getScene().getWindow().hide();
        }
    }
    
    private void checkEmail() {
        String inputEmail = emailField.textProperty().getValueSafe();
        if (!model.User.checkEmail(inputEmail)) {
            auxiliarMethods.manageError(invalidEmailLabel, emailField, validEmail);
        } else {
            auxiliarMethods.manageCorrect(invalidEmailLabel, emailField, validEmail);
            newEmail = inputEmail;
        }
    }
    
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
            newBirthday = inputBirthday;
        }
    }
    
    private void checkPassword() {
        inputPassword = passwordField.textProperty().getValueSafe();
        if (!model.User.checkPassword(inputPassword)) {
            auxiliarMethods.manageError(passwordFormatLabel, passwordField, validPassword);
        } else {
            auxiliarMethods.manageCorrect(passwordFormatLabel, passwordField, validPassword);
        }
    }
    
    private void checkPasswordRep() {
        passwordRep = PasswordRepField.textProperty().getValueSafe();
        if (!inputPassword.equals(passwordRep)) {
            auxiliarMethods.manageError(passwordDontMatchLabel, PasswordRepField, passwordRepCorrect);
        } else {
            auxiliarMethods.manageCorrect(passwordDontMatchLabel, PasswordRepField, passwordRepCorrect);
            newPassword = inputPassword;
        }
        System.out.println(passwordRepCorrect.getValue());
    }

    @FXML
    private void cancelPressed(ActionEvent event) {
        if (auxiliarMethods.promptAlert("salir al menu principal", "Si ha modificado alguno de los campos, estos no se guardarán.\nHaga click en OK para ir al menu principal o cancelar para cerrar esta ventana emergente.")) {
            loadMainMenu();
        }
    }

    @FXML
    private void savePressed(ActionEvent event) {
        finalCheck();
        if (validFields.getValue() == true) {
            if (auxiliarMethods.promptAlert("guardar los datos y volver al menu principal", "Todos los campos modificados se guardarán.\nHaga click en OK para guardar los datos y volver al menu principal o cancelar para cerrar esta ventana emergente.")) {
                saveData();
                loadMainMenu();
            }
        }
    }
    
    private void loadMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/principalUsuarios/vpUsuariosFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Menu principal", 960, 540);
        cancelButton.getScene().getWindow().hide();
    }
    
    private void saveData() {
        try {
            finalCheck();
            if (validFields.getValue() == true) {
                PoiUPVApp.currentUser.setEmail(newEmail);
                PoiUPVApp.currentUser.setBirthdate(newBirthday);
                PoiUPVApp.currentUser.setPassword(newPassword);
                PoiUPVApp.currentUser.setAvatar(newProfileImage);
            }
        } catch (NavegacionDAOException e) {
            revertSaving();
        }
    }
    
    private void revertSaving() {
        try {
            PoiUPVApp.currentUser.setEmail(currentEmail);
            PoiUPVApp.currentUser.setBirthdate(currentBirthday);
            PoiUPVApp.currentUser.setPassword(currentPassWord);
            PoiUPVApp.currentUser.setAvatar(currentProfileImage);
            auxiliarMethods.errorPrompt("Error guardando datos", "Error guardando nuevos datos", "Ha ocurrido un error guardando los datos, hemos revertido los cambios de su perfil a los valores anteriores");
        } catch (NavegacionDAOException e) {
            auxiliarMethods.errorPrompt("Error guardando datos", "Error revertiendo los datos a valores anteriores", "Ha ocurrido un error revertiendo datos a sus valores anteriores, error de base de datos");
        }
    }
    
    private void finalCheck() {
        checkEmail();
        checkBirthday();
        checkPassword();
        checkPasswordRep();
    }

    @FXML
    private void selectPic1(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(newProfileImage.getUrl()));
        this.newProfileImage = new Image("../imgData/avatars/avatar1.png");
        auxiliarMethods.highlightSelection(profilePic1);
    }

    @FXML
    private void selectPic2(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(newProfileImage.getUrl()));
        this.newProfileImage = new Image("../imgData/avatars/avatar2.png");
        auxiliarMethods.highlightSelection(profilePic2);
    }

    @FXML
    private void selectPic3(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(newProfileImage.getUrl()));
        this.newProfileImage = new Image("../imgData/avatars/avatar3.png");
        auxiliarMethods.highlightSelection(profilePic3);
    }

    @FXML
    private void selectPic4(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(newProfileImage.getUrl()));
        this.newProfileImage = new Image("../imgData/avatars/avatar4.png");
        auxiliarMethods.highlightSelection(profilePic4);
    }

    @FXML
    private void selectPic5(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(newProfileImage.getUrl()));
        this.newProfileImage = new Image("../imgData/avatars/1652720318714.png");
        auxiliarMethods.highlightSelection(profilePic5);
    }
    
    @FXML
    private void fileSelectPressed(ActionEvent event) {
        FileChooser profileSelector = new FileChooser();
        profileSelector.setTitle("Abrir avatar");
        profileSelector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg" , "*.jpeg"));
        File ImagenSeleccionada = profileSelector.showOpenDialog((Stage) fileSelectButton.getScene().getWindow());
        if (ImagenSeleccionada != null) {
            auxiliarMethods.unhighlightSelection(getButton(newProfileImage.getUrl()));
            customProfilePic.setImage(new Image(ImagenSeleccionada.toURI().toString()));
            customProfilePic.setVisible(true);
            filePathLabel.setText(ImagenSeleccionada.toURI().toString());
            newProfileImage = new Image(filePathLabel.getText());
        }
    }
    
    private Button getButton(String imageString) {
        try {
            switch(imageString) {
                case "../imgData/avatars/avatar1.png":
                    return profilePic1;
                case "../imgData/avatars/avatar2.png":
                    return profilePic2;
                case "../imgData/avatars/avatar3.png":
                    return profilePic3;
                case "../imgData/avatars/avatar4.png":
                    return profilePic4;
                case "../imgData/avatars/1652720318714.png":
                    return profilePic5;
                default:
                    System.out.println("Ooops, either it was a custom image or something went extremely wrong");
                break;
            }
        } catch (NullPointerException e) {
            
        }
        return null;
    }
    
    private void hideCustoms() {
        customProfilePic.setVisible(false);
        filePathLabel.setText("");
    }

    
    
}
