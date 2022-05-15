/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemSelect;

import DBAccess.NavegacionDAO;
import DBAccess.NavegacionDAOException;
import auxiliaries.auxiliarMethods;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import poiupv.PoiUPVApp;

import model.Problem;
import poiupv.PoiUPVApp;
import java.util.Random;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author qiyao
 */
public class ProblemSelectWindowController implements Initializable {

    @FXML
    private Text usernameText;
    @FXML
    private Button userProgressButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button datamodButton;
    @FXML
    private Button openSelectedButton;
    @FXML
    private ListView<String> problemListView;
    @FXML
    private Label problemTextLabel;
    @FXML
    private RadioButton option1Selector;
    @FXML
    private RadioButton option2Selector;
    @FXML
    private RadioButton option3Selector;
    @FXML
    private Button backButton;
    @FXML
    private Button changeuUserButton;
    
    private List<Problem> listaProblemas;
    private ObservableList<String> observaleProblems;
    
    private String[] problemTexts;
    private Problem selectedProblem;
    @FXML
    private RadioButton option4Selector;
    @FXML
    private Button openRandomButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        usernameText.setText(PoiUPVApp.currentUser.getNickName());
        listaProblemas = PoiUPVApp.navLib.getProblems();
        problemTexts = new String[listaProblemas.size()];
        
        problemTexts = getSentences(problemTexts);
        
        observaleProblems = FXCollections.observableArrayList(problemTexts);
        problemListView.setItems(observaleProblems);
        
        openSelectedButton.setDisable(true);
        
        
        problemListView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            if (problemListView.isFocused()) {
                openSelectedButton.setDisable(false);
            }
        });
        
        problemListView.getSelectionModel().selectedIndexProperty().addListener((o, oldVal, newVal) -> {
            if (newVal.intValue() == -1) {
                problemTextLabel.setText("El enunciado del problema seleccionado aparecerá aqui.");
            } else {
                selectedProblem = listaProblemas.get(newVal.intValue());
                problemTextLabel.setText(selectedProblem.getText());
                option1Selector.setText(selectedProblem.getAnswers().get(0).getText());
                option2Selector.setText(selectedProblem.getAnswers().get(1).getText());
                option3Selector.setText(selectedProblem.getAnswers().get(2).getText());
                option4Selector.setText(selectedProblem.getAnswers().get(3).getText());
            }
        });
    }    

    @FXML
    private void modifyPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../dataModPackage/dataModFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Datos del usuario", 960, 540);
        datamodButton.getScene().getWindow().hide();
    }

    @FXML
    private void progressPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../userProgressPackage/userProgressFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Progreso del usuario", 960, 540);
        datamodButton.getScene().getWindow().hide();
    }

    @FXML
    private void helpPressed(ActionEvent event) {
    }

    @FXML
    private void changeUserPressed(ActionEvent event) {
        //add update session
        PoiUPVApp.currentUser = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../loginPackage/loginFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Login", 800, 480);
        changeuUserButton.getScene().getWindow().hide();
    }

    // Problem list error
    // Problem list size is 0
    @FXML
    private void openRandomPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../poiupv/FXMLDocument.fxml"));
            Parent root = loader.load();
            poiupv.FXMLDocumentController mapController = loader.getController();
            
            mapController.setTest(getRandom());
            
            Scene scene = new Scene(root, 960, 540);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Mapa y problema");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            System.out.println("IOException loading the map from random exercise window");
        }
    }

    @FXML
    private void openSelectedPressed(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../poiupv/FXMLDocument.fxml"));
            Parent root = loader.load();
            poiupv.FXMLDocumentController mapController = loader.getController();
            
            mapController.setTest(selectedProblem);
            
            Scene scene = new Scene(root, 960, 540);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Mapa y problema");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            System.out.println("IOException loading the map from random exercise window");
        }
    }

    @FXML
    private void mainMenuPressed(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../principalUsuarios/vpUsuariosFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Menu Principal", 960, 540);
        changeuUserButton.getScene().getWindow().hide();
    }

    private Problem getRandom() {
        System.out.println(listaProblemas.size());
        return listaProblemas.get(new Random().nextInt(listaProblemas.size()));
    }

    private String[] getSentences(String[] sentencesArray) {
        for (int aux = 0; aux < listaProblemas.size(); aux++) {
            sentencesArray[aux] = listaProblemas.get(aux).getText();
        }
        return sentencesArray;
    }  
}
