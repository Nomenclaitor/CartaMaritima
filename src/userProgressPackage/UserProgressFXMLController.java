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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
    private BarChart<Integer, Integer> sessionChart;
    private XYChart.Series correctBar = new XYChart.Series();
    private XYChart.Series incorrectBar = new XYChart.Series();
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
    private ListView<String> sessionList;
    @FXML
    private Button eraseDatesButton;
    
    private String[] observableSession;
    private List<Session> userSessionList;
    private ObservableList<String> observableListView;
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println("Initializing user progress screen");
        if (!PoiUPVApp.currentUser.getSessions().isEmpty()) {
            showAllSessions();
        } else {
            auxiliarMethods.promptInformation("Info", "No hay datos de progreso", "Todavia no hay datos de progreso que mostrar!\nEn cuanto haga ejercicios se ira guardando su progreso!");
        }
        
        
        userprofilePicture.setImage(PoiUPVApp.currentUser.getAvatar());
        usernameText.setText(PoiUPVApp.currentUser.getNickName());
        
        correctBar.getData().add(new XYChart.Data("", sessionDataAux.getTotalCorrect()));
        incorrectBar.getData().add(new XYChart.Data("", sessionDataAux.getTotalIncorrect()));
        
        correctBar.setName("Correctos");
        incorrectBar.setName("Incorrectos");
        
        sessionChart.getData().add(correctBar);
        sessionChart.getData().add(incorrectBar);
        
        sessionList.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            if (newVal.intValue() != -1) {
                showSelectedSession(userSessionList.get(newVal.intValue()));
            }
        });
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
            changeUserButton.getScene().getWindow().hide();
        }
    }

    @FXML
    private void showData(ActionEvent event) {
        LocalDate lowerLimit = lowerLimitPicker.getValue();
        LocalDate upperLimit = upperLimitPicker.getValue();
        System.out.println(lowerLimitPicker.getValue());
        System.out.println(upperLimitPicker.getValue());
        if (lowerLimit == null) lowerLimit = sessionDataAux.getMinDate();
        if (upperLimit == null) upperLimit = sessionDataAux.getMaxDate();
        userSessionList = sessionDataAux.getTimeframe(lowerLimitPicker.getValue(), upperLimitPicker.getValue());
        
        setList(userSessionList.size(), userSessionList);
        int correct = sessionDataAux.getCorrect(userSessionList);
        int incorrect = sessionDataAux.getIncorrect(userSessionList);
        correctLabel.setText(Integer.toString(correct));
        failureLabel.setText(Integer.toString(incorrect));
        sucessRateLabel.setText(Double.toString(sessionDataAux.calcCorrectPercentage(correct, incorrect)) + "%");
        failureRateLabel.setText(Double.toString(sessionDataAux.calcIncorrectPercentage(correct, incorrect)) + "%");
        gradeLabel.setText(String.format("%.2f", sessionDataAux.calcGrade(correct, incorrect)));
        
        System.out.println("Info updated");
    }

    @FXML
    private void erasePressed(ActionEvent event) {
        showAllSessions();
    }
    
    private void showAllSessions() {
        userSessionList = sessionDataAux.getAllSessions();
        setList(userSessionList.size(), userSessionList);
        correctLabel.setText(Integer.toString(sessionDataAux.getTotalCorrect()));
        failureLabel.setText(Integer.toString(sessionDataAux.getTotalIncorrect()));
        sucessRateLabel.setText(Double.toString(sessionDataAux.getTotalCorrectPercentage()) + "%");
        failureRateLabel.setText(Double.toString(sessionDataAux.getTotalIncorrectPercentage()) + "%");
        gradeLabel.setText(String.format("%.2f", sessionDataAux.getGrade()));
        
        sessionChart.getData().clear();
        correctBar.getData().clear();
        incorrectBar.getData().clear();
        correctBar.getData().add(new XYChart.Data("", sessionDataAux.getTotalCorrect()));
        incorrectBar.getData().add(new XYChart.Data("", sessionDataAux.getTotalIncorrect()));
        sessionChart.getData().add(correctBar);
        sessionChart.getData().add(incorrectBar);
    }
    
    private void setList(int listSize, List<Session> sessionListArgument) {
        observableSession = new String[listSize];
        getStringFormat(sessionListArgument);
        observableListView = FXCollections.observableArrayList(observableSession);
        sessionList.setItems(observableListView);
    }
    
    private void getStringFormat(List<Session> sessionList) {
        for (int aux = 0; aux < sessionList.size(); aux++) {
            observableSession[aux] = sessionList.get(aux).getLocalDate() + "   Correctos: " + sessionList.get(aux).getHits() + "   Fallos: " + sessionList.get(aux).getFaults();
        }
    }
    
    private void showSelectedSession(Session selectedSession) {
        int correctInSelected = selectedSession.getHits();
        int incorrectInSelected = selectedSession.getFaults();
        correctLabel.setText(Integer.toString(correctInSelected));
        failureLabel.setText(Integer.toString(incorrectInSelected));
        sucessRateLabel.setText(Double.toString(sessionDataAux.calcCorrectPercentage(correctInSelected, incorrectInSelected)));
        failureRateLabel.setText(Double.toString(sessionDataAux.calcIncorrectPercentage(correctInSelected, incorrectInSelected)));
        gradeLabel.setText(Double.toString(sessionDataAux.calcGrade(correctInSelected, incorrectInSelected)));
        
        sessionChart.getData().clear();
        correctBar.getData().clear();
        incorrectBar.getData().clear();
        correctBar.getData().add(new XYChart.Data("", correctInSelected));
        incorrectBar.getData().add(new XYChart.Data("", incorrectInSelected));
        sessionChart.getData().add(correctBar);
        sessionChart.getData().add(incorrectBar);
    }
    
}
