/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userProgressPackage;

import auxiliaries.auxiliarMethods;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Session;
import poiupv.PoiUPVApp;
import auxiliaries.sessionDataAux;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class UserProgressFXMLController implements Initializable {

    @FXML
    private ImageView userprofilePicture;
    @FXML
    private Text usernameText;
    @FXML
    private Button datamodButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button changeUserButton;
    @FXML
    private BarChart<?, ?> sessionChart;
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
    private DatePicker lowerLimitPicker;
    @FXML
    private DatePicker upperLimitPicker;
    @FXML
    private Button showDataButton;
    @FXML
    private ListView<Session> sessionList;
    
    
    private List<Session> userSessionList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("Initializing user progress screen");
        userSessionList = sessionDataAux.getAllSessions();
        correctLabel.setText(Integer.toString(sessionDataAux.getTotalCorrect()));
        failureLabel.setText(Integer.toString(sessionDataAux.getTotalIncorrect()));
        sucessRateLabel.setText(Double.toString(sessionDataAux.getTotalCorrectPercentage()) + "%");
        failureRateLabel.setText(Double.toString(sessionDataAux.getTotalIncorrectPercentage()) + "%");
        gradeLabel.setText(String.format("%.2f", sessionDataAux.getGrade()));
    }    

    @FXML
    private void modifyPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dataModPackage/dataModFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Modificar datos del usuario", 960, 540);
        datamodButton.getScene().getWindow().hide();
    }

    @FXML
    private void mainMenuPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../principalUsuarios/vpUsuariosFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Menu Principal", 960, 540);
        mainMenuButton.getScene().getWindow().hide();
    }

    @FXML
    private void helpPressed(ActionEvent event) {
    }

    @FXML
    private void closeSessionPressed(ActionEvent event) {
        if (auxiliarMethods.promptAlert("Salir al menu de Login", "Se guardarán todos los datos de la sesión y se cerrará la sesión actual.")) {
            //add Session
            PoiUPVApp.saveSession();
            PoiUPVApp.currentUser = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/loginPackage/loginFXML.fxml"));
            auxiliarMethods.loadWindow(loader, "nautica Login", 800, 480);
        }
    }

    @FXML
    private void showData(ActionEvent event) {
    }
    
    private List<Session> fromTimeFrame(LocalDateTime base, LocalDateTime ceiling) {
        List<Session> result = new ArrayList<>();
        
        return result;
    }
    
}
