/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principalUsuarios;

import DBAccess.NavegacionDAOException;
import auxiliaries.ConcurrentClock;
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
    private Button userprogressButton;
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
        URL fxmlLocation = getClass().getResource("/dataModPackage/dataModFXML.fxml");
        System.out.println(fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        auxiliarMethods.loadWindow(loader, "Modificar datos del usuario", 960, 540);
        datamodButton.getScene().getWindow().hide();
    }

    @FXML
    private void progressPressed(ActionEvent event) {
    }

    @FXML
    private void helpPressed(ActionEvent event) {
    }

    @FXML
    private void closeSessionPressed(ActionEvent event) {
        //Añadir sesion antes de cerrar sesion
        //Por testear
        try {
            PoiUPVApp.currentUser.addSession(PoiUPVApp.currentSession);
        } catch (NavegacionDAOException e) {
            System.out.println("Error saving current user session");
        }
        PoiUPVApp.currentUser = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginPackage/loginFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Login", 800, 480);
        changeUserButton.getScene().getWindow().hide();
    }

    @FXML
    private void problemsPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/problemSelect/problemSelectWindow.fxml"));
        auxiliarMethods.loadWindow(loader, "Seleccion de problemas", 960, 540);
        problemsWindowButton.getScene().getWindow().hide();
    }

    @FXML
    private void showMapPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/poiupv/FXMLDocument.fxml"));
        auxiliarMethods.loadWindow(loader, "Mapa en blanco", 960, 540);
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
}
