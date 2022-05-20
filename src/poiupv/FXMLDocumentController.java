/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.util.Optional;
import auxiliaries.auxiliarMethods;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Answer;
import model.Navegacion;
import model.Problem;
import poiupv.Poi;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {

    //=======================================
    // hashmap para guardar los puntos de interes POI
    private final HashMap<String, Poi> hm = new HashMap<>();
    // ======================================
    // la variable zoomGroup se utiliza para dar soporte al zoom
    // el escalado se realiza sobre este nodo, al escalar el Group no mueve sus nodos
    private Group zoomGroup;

    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
    @FXML
    private MenuButton map_pin;
    @FXML
    private MenuItem pin_info;
    @FXML
    private Label posicion;
    @FXML
    private RadioButton option1Button;
    @FXML
    private RadioButton option2Button;
    @FXML
    private RadioButton option3Button;
    @FXML
    private RadioButton option4Button;
    @FXML
    private Button cancelButton;
    @FXML
    private Button verifyButton;
    @FXML
    private Text mapTitle;
    @FXML
    private Label problemLabel;
    @FXML
    private Button lineButton;
    @FXML
    private Button arcButton;
    @FXML
    private Button pointButton;
    @FXML
    private Button textButton;
    @FXML
    private Button cursorButton;
    @FXML
    private Button anglerToggler;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private Button fillerButton;
    @FXML
    private Button incSizeButton;
    @FXML
    private Slider sizeSlider;
    @FXML
    private Button decSizeButton;
    @FXML
    private ImageView anglePortractor;
    @FXML
    private ToggleGroup answerGroup;
    @FXML
    private ImageView cancelButtonImage;
    @FXML
    private ImageView veirfyButonImage;

    private boolean fromMainMenu = false;
    private boolean answerVerified = false;
    
    //????
    private Alert exitAlert = new Alert(AlertType.CONFIRMATION);
    
    private Problem problem;
    private List<Answer> answersList;

    
    private Answer finalAnswer = null;
    private RadioButton selectedButton = null;
    
    
    
    @FXML
    void zoomIn(ActionEvent event) {
        //================================================
        // el incremento del zoom dependerá de los parametros del 
        // slider y del resultado esperado
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal += 0.1);
    }

    @FXML
    void zoomOut(ActionEvent event) {
        double sliderVal = zoom_slider.getValue();
        zoom_slider.setValue(sliderVal + -0.1);
    }
    
    // esta funcion es invocada al cambiar el value del slider zoom_slider
    private void zoom(double scaleValue) {
        //===================================================
        //guardamos los valores del scroll antes del escalado
        double scrollH = map_scrollpane.getHvalue();
        double scrollV = map_scrollpane.getVvalue();
        //===================================================
        // escalamos el zoomGroup en X e Y con el valor de entrada
        zoomGroup.setScaleX(scaleValue);
        zoomGroup.setScaleY(scaleValue);
        //===================================================
        // recuperamos el valor del scroll antes del escalado
        map_scrollpane.setHvalue(scrollH);
        map_scrollpane.setVvalue(scrollV);
    }

    private void initData() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initData();
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(2.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
        
        
        //
        
    }

    @FXML
    private void muestraPosicion(MouseEvent event) {
        posicion.setText("sceneX: " + (int) event.getSceneX() + ", sceneY: " + (int) event.getSceneY() + "\n"
                + "         X: " + (int) event.getX() + ",          Y: " + (int) event.getY());
    }

    private void cerrarAplicacion(ActionEvent event) {
        ((Stage)zoom_slider.getScene().getWindow()).close();
    }

    private void acercaDe(ActionEvent event) {
        Alert mensaje= new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Acerca de");
        mensaje.setHeaderText("IPC - 2022");
        mensaje.showAndWait();
    }

    @FXML
    private void option1Selected(ActionEvent event) {
        finalAnswer = answersList.get(0);
        selectedButton = option1Button;
    }

    @FXML
    private void option2Selected(ActionEvent event) {
        finalAnswer = answersList.get(1);
        selectedButton = option2Button;
    }

    @FXML
    private void option3Selected(ActionEvent event) {
        finalAnswer = answersList.get(2);
        selectedButton = option3Button;
    }

    @FXML
    private void option4Selected(ActionEvent event) {
        finalAnswer = answersList.get(3);
        selectedButton = option4Button;
    }

    @FXML
    private void cancelPressed(ActionEvent event) {
        if (fromMainMenu) {
            exitAlert.setHeaderText("Está seguro de querer salir al menu principal?");
            exitAlert.setContentText("Todo el progeso en el mapa será borrado");
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
            toMainMenu();
            }
        } else {
            exitAlert.setHeaderText("Está seguro de querer salir al menu de selección de problemas?");
            exitAlert.setContentText("Todo el progeso en el mapa será borrado y el problema dado como fallido");
            Optional<ButtonType> result = exitAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                PoiUPVApp.incorrect++;
                auxiliaries.sessionDataAux.increaseIncorrect();
                toProblemSelector();
            }
        }
    }
    
    private boolean verifySelection() {
        answerVerified = true;
        if (finalAnswer == null) {
            return false;
        }
        return finalAnswer.getValidity();
    }
    
    //
    //
    //Highlight is not showing
    //
    private void highlightAnswer(boolean selectionCorrect) {
        if (selectionCorrect) {
            System.out.println("Correct highlight");
            System.out.println(selectedButton.getId());
            selectedButton.styleProperty().setValue("-fx-background-color: #C7EEFF");
            selectedButton.styleProperty().setValue("-fx-background-radius: 10");
        } else {
            System.out.println("Incorrect highlight");
            RadioButton auxButton = getCorrectAnswer();
            System.out.println(auxButton.getId());
            auxButton.styleProperty().setValue("-fx-background-color: #C7EEFF");
            auxButton.styleProperty().setValue("-fx-background-radius: 10");
            selectedButton.styleProperty().setValue("-fx-background-color: #FCE5E0");
            selectedButton.styleProperty().setValue("-fx-background-radius: 10");
        }
    }
    
    private RadioButton getCorrectAnswer() {
        if (answersList.get(0).getValidity()) {
            return option1Button;
        } else if (answersList.get(1).getValidity()) {
            return option2Button;
        } else if (answersList.get(2).getValidity()) {
            return option3Button;
        }
        return option4Button;
    }

    @FXML
    private void verifyPressed(ActionEvent event) {
        if (selectedButton != null) {
            if (answerVerified) {
                verifyButton.getScene().getWindow().hide();
            } else {
                if (auxiliarMethods.promptAlert("verificar la respuesta", "No podrá volver a cambiar de respuesta, pero podrá reintentarlo más adelante eligiendolo en la ventana de selección de probelmas")) {
                    boolean selectionCorrect = verifySelection();
                    highlightAnswer(selectionCorrect);
                    if (selectionCorrect) {
                        PoiUPVApp.correct++;
                        auxiliaries.sessionDataAux.increaseCorrect();
                    } else {
                        PoiUPVApp.incorrect++;
                        auxiliaries.sessionDataAux.increaseIncorrect();
                    }
                    auxiliaries.sessionDataAux.midSessionUpdate();
                    dissableOptions();
                    verifyToExit();
                    cancelButton.setVisible(false);
                }
            }
        } else {
            auxiliarMethods.promptError("", "No ha seleccionado ninguna respuesta", "Selecciones una de las cuatro opociones para poder verificar su respuesta");
        }
        System.out.println(PoiUPVApp.correct);
        System.out.println(PoiUPVApp.incorrect);
    }

    @FXML
    private void linePressed(ActionEvent event) {
        
    }

    @FXML
    private void arcPressed(ActionEvent event) {
        
    }

    @FXML
    private void pointPressed(ActionEvent event) {
        
    }

    @FXML
    private void textPressed(ActionEvent event) {
        
    }

    @FXML
    private void cursorPressed(ActionEvent event) {
        
    }

    @FXML
    private void anglerPressed(ActionEvent event) {
        
    }

    @FXML
    private void changeFiller(ActionEvent event) {
        
    }

    @FXML
    private void incSize(ActionEvent event) {
        
    }

    @FXML
    private void decSize(ActionEvent event) {
        
    }
    
    //Untested
    public void setBlanckMap() {
        fromMainMenu = true;
        verifyButton.setDisable(true);
        problem = null;
        cancelButtonImage.setImage(new Image("/imgData/arrowLeftBlue.png"));
        cancelButton.setText("Menu principal");
        mapTitle.setText("Mapa en blanco");
        problemLabel.setText("Mapa en blanco para practicas");
        option1Button.setVisible(false);
        option2Button.setVisible(false);
        option3Button.setVisible(false);
        option4Button.setVisible(false);
    }
    
    //Untested
    public void setTest(model.Problem problem) {
        if (!fromMainMenu) {
            answersList = problem.getAnswers();
        }
        
        this.problem = problem;
        fromMainMenu = false;
        verifyButton.setDisable(false);
        cancelButtonImage.setImage(new Image("/imgData/cancelBlue.png"));
        cancelButton.setText("Cancelar");
        mapTitle.setText("Enunciado del problema");
        problemLabel.setText(problem.getText());
        option1Button.setText(answersList.get(0).getText());
        option2Button.setText(answersList.get(1).getText());
        option3Button.setText(answersList.get(2).getText());
        option4Button.setText(answersList.get(3).getText());
        option1Button.setVisible(true);
        option2Button.setVisible(true);
        option3Button.setVisible(true);
        option4Button.setVisible(true);
    }
    
    //Untested
    private void toMainMenu() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../principalUsuarios/vpUsuariosFXML.fxml"));
        auxiliarMethods.loadWindow(loader, "Menu Principal", 960, 540);
        cancelButton.getScene().getWindow().hide();
    }
    
    //Untested
    private void toProblemSelector() {
        //Save problem answer (failed)
        cancelButton.getScene().getWindow().hide();
    }

    private void dissableOptions() {
        option1Button.setDisable(true);
        option2Button.setDisable(true);
        option3Button.setDisable(true);
        option4Button.setDisable(true);
    }

    private void verifyToExit() {
        verifyButton.setText("Salir");
        veirfyButonImage.setImage(new Image("/imgData/exitLogo.png"));
    }
    
}
