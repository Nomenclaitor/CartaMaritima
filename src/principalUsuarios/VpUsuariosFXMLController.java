/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principalUsuarios;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import auxiliaries.ConcurrentClock;
import auxiliaries.auxiliarMethods;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import poiupv.PoiUPVApp;
/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class VpUsuariosFXMLController implements Initializable {

    @FXML
    private ImageView userprofilePicture;
    @FXML
    private Text usernameText;
    @FXML
    private Button datamodButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button changeUserButton;
    @FXML
    private Label emailLabel;
    @FXML
    private Label birthdayLabel;
    @FXML
    private Label clockLabel;
    @FXML
    private Label correctLabel;
    @FXML
    private Label sucessRateLabel;
    @FXML
    private Label failureLabel;
    @FXML
    private Label failureRateLabel;
    @FXML
    private Label gradeLabel;
    @FXML
    private Button problemsWindowButton;
    @FXML
    private Button showMapButton;
    @FXML
    private Button userprogressButton;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameText.setText(PoiUPVApp.currentUser.getNickName());
        userprofilePicture.setImage(PoiUPVApp.currentUser.getAvatar());
        emailLabel.setText(PoiUPVApp.currentUser.getEmail());
        birthdayLabel.setText(PoiUPVApp.currentUser.getBirthdate().toString());
        localClockInitialize();
        
    }    

    @FXML
    private void modifyPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dataModPackage/dataModFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Modificar datos del usuario", 960, 540);
        datamodButton.getScene().getWindow().hide();
    }


    @FXML
    private void helpPressed(ActionEvent event) {
    }

    @FXML
    private void closeSessionPressed(ActionEvent event) {
        if (auxiliarMethods.promptAlert("Salir al menu de Login", "Se guardarán todos los datos de la sesión y se cerrará la sesión actual.")) {
            //Añadir sesion antes de cerrar sesion
            //Por testear
            PoiUPVApp.currentUser = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../loginPackage/loginFXML.fxml"));
            auxiliarMethods.loadWindow(loader, "Login", 800, 480);
            changeUserButton.getScene().getWindow().hide();
        }
    }

    @FXML
    private void problemsPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../problemSelect/problemSelectWindow.fxml"));
        auxiliarMethods.loadWindow(loader, "Seleccion de problemas", 960, 540);
        problemsWindowButton.getScene().getWindow().hide();
    }

    @FXML
    private void showMapPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../poiupv/FXMLDocument.fxml"));
            Parent root = loader.load();
            poiupv.FXMLDocumentController mapController = loader.getController();
            
            mapController.setBlanckMap();
            
            Scene scene = new Scene(root, 960, 540);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Mapa en blanco");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            System.out.println("IOException loading blanck map");
        }
        showMapButton.getScene().getWindow().hide();
    }
    
    private void localClockInitialize() {
        ConcurrentClock clock;
        clock = new ConcurrentClock();
        Thread threadedClock = new Thread(clock);
        threadedClock.setDaemon(true);
        threadedClock.start();
        clockLabel.textProperty().bind(clock.messageProperty());
    }

    @FXML
    private void progressPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../userProgressPackage/userProgressFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Progreso del usuario", 960, 540);
        userprogressButton.getScene().getWindow().hide();
    }



}
