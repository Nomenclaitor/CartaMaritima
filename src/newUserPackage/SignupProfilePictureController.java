/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newUserPackage;

import DBAccess.NavegacionDAOException;
import auxiliaries.auxiliarMethods;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import poiupv.PoiUPVApp;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class SignupProfilePictureController implements Initializable {

    @FXML
    private Button previousButton;
    @FXML
    private Button createAccountButton;
    @FXML
    private Button fileSelectButton;
    @FXML
    private Label filePathLabel;
    @FXML
    private Button profilePic1;
    @FXML
    private Button profilePic2;
    @FXML
    private Button profilePic3;
    @FXML
    private Button profilePIc4;
    @FXML
    private Button profilePic5;
    @FXML
    private ImageView customPicTure;
    @FXML
    private Text customPicLabel;

    
    private String profileUrl = "../imgData/avatars/1652720318714.png";
    private ImageView pic1;
    @FXML
    private ImageView defaultPic6;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        auxiliarMethods.highlightSelection(profilePic5);
    }    

    @FXML
    private void previousClicked(ActionEvent event) {
        try {        
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/newUserPackage/signupFXML.fxml"));
            Parent root = loader.load();
            
            SignupFXMLController passwordSignup = loader.getController();
            passwordSignup.showData();
            
            Scene scene = new Scene(root, 800, 480);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Nautica Signup");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            previousButton.getScene().getWindow().hide();
        } catch (IOException e) {
            System.out.println("IOException loading main signup");
        }
    }

    @FXML
    private void createAccountPressed(ActionEvent event) {
        try {
            poiupv.PoiUPVApp.navLib.registerUser(SignupPrincipalFXMLController.username, SignupPrincipalFXMLController.email, SignupPrincipalFXMLController.password, new Image(profileUrl), SignupPrincipalFXMLController.birthday);
            PoiUPVApp.currentUser = PoiUPVApp.navLib.loginUser(SignupPrincipalFXMLController.username, SignupPrincipalFXMLController.password);
            openMainMenu();
        } catch (NavegacionDAOException e) {
            System.out.println("Error inserting user in the data base");
        }
    }

    @FXML
    private void selectPic1(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(profileUrl));
        this.profileUrl = "../imgData/avatars/avatar1.png";
        auxiliarMethods.highlightSelection(profilePic1);
    }

    @FXML
    private void selectPic2(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(profileUrl));
        this.profileUrl = "../imgData/avatars/avatar2.png";
        auxiliarMethods.highlightSelection(profilePic2);
    }

    @FXML
    private void selectPic3(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(profileUrl));
        this.profileUrl = "../imgData/avatars/avatar3.png";
        auxiliarMethods.highlightSelection(profilePic3);
    }

    @FXML
    private void selectPic4(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(profileUrl));
        this.profileUrl = "../imgData/avatars/avatar4.png";
        auxiliarMethods.highlightSelection(profilePIc4);
    }

    @FXML
    private void selectPic5(ActionEvent event) {
        hideCustoms();
        auxiliarMethods.unhighlightSelection(getButton(profileUrl));
        this.profileUrl = "../imgData/avatars/1652720318714.png";
        auxiliarMethods.highlightSelection(profilePic5);
    }

    @FXML
    private void fileSelect(ActionEvent event) {
        FileChooser profileSelector = new FileChooser();
        profileSelector.setTitle("Abrir avatar");
        profileSelector.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg" , "*.jpeg"));
        File ImagenSeleccionada = profileSelector.showOpenDialog((Stage) fileSelectButton.getScene().getWindow());
        if (ImagenSeleccionada != null) {
            auxiliarMethods.unhighlightSelection(getButton(profileUrl));
            customPicTure.setImage(new Image(ImagenSeleccionada.toURI().toString()));
            customPicTure.setVisible(true);
            customPicLabel.setVisible(true);
            filePathLabel.setText(ImagenSeleccionada.toURI().toString());
            profileUrl = filePathLabel.getText();
        }
    }
    
    private Button getButton(String imageString) {
        switch(imageString) {
            case "../imgData/avatars/avatar1.png":
                return profilePic1;
            case "../imgData/avatars/avatar2.png":
                return profilePic2;
            case "../imgData/avatars/avatar3.png":
                return profilePic3;
            case "../imgData/avatars/avatar4.png":
                return profilePIc4;
            case "../imgData/avatars/1652720318714.png":
                return profilePic5;
            default:
                System.out.println("Ooops, either it was a custom image or something went extremely wrong");
            break;
        }
        return null;
    }
    
    private void openMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../principalUsuarios/vpUsuariosFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Menu principal", 960, 540);
        createAccountButton.getScene().getWindow().hide();
    }
    
    private void hideCustoms() {
        customPicTure.setVisible(false);
        customPicLabel.setVisible(false);
        filePathLabel.setText("");
    }
    
}
