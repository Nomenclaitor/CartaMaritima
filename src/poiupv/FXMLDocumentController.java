/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;
//1211085207@Qq
import java.util.Optional;
import auxiliaries.auxiliarMethods;
import java.awt.MouseInfo;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Navegacion;
import poiupv.Poi;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
    
    private Circle circle;
    private double centerX, centerY;
    private double radius;
    boolean firstClicked;
            
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
    private Pane pane;

    private boolean fromMainMenu = false;
    
    private Alert exitAlert = new Alert(AlertType.CONFIRMATION);
    @FXML
    private ImageView cancelButtonImage;
    
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
    
        @FXML
    void decSize(ActionEvent event) {
        double sliderVal = sizeSlider.getValue();
        sizeSlider.setValue(sliderVal -= 1);

    }
    @FXML
    void incSize(ActionEvent event) {
    double sliderVal = sizeSlider.getValue();
    sizeSlider.setValue(sliderVal += 1);
    }

    private void initData() {
        hm.put("2F", new Poi("2F", "Edificion del DSIC", 325, 225));
        hm.put("Agora", new Poi("Agora", "Agora", 600, 360));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initData();
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        zoom_slider.setMin(0.5);
        zoom_slider.setMax(1.5);
        zoom_slider.setValue(1.0);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        sizeSlider.setMin(5);
        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
        
        sizeSlider.setValue(1);
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
    }

    @FXML
    private void option2Selected(ActionEvent event) {
    }

    @FXML
    private void option3Selected(ActionEvent event) {
    }

    @FXML
    private void option4Selected(ActionEvent event) {
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
            toProblemSelector();
            }
        }
    }

    @FXML
    private void verifyPressed(ActionEvent event) {
    }

    @FXML
    private void linePressed(ActionEvent event) {
    }

    @FXML
    private void arcPressed(ActionEvent event) {
    }

    @FXML
    private void pointPressed(ActionEvent event) {
        firstClicked = true;
        // al pulsar el botón se crea un circulo
        circle = new Circle(sizeSlider.getValue());
        circle.setFill(colorPicker.getValue());
        circle.setStroke(colorPicker.getValue());
        zoomGroup.getChildren().add(circle);
        circle.setVisible(false);
        
        // al clicar sobre el mapa se establece la posición del circulo
        // se permite establecer la posición de un circulo solo una vez
         
        map_scrollpane.setOnMouseClicked(                  
            e -> {
                if(firstClicked){
                    //centerX = e.getSceneX();
                    //centerY = e.getSceneY();
                    //centerX = e.getX();
                    //centerY = e.getY();
                    centerX = MouseInfo.getPointerInfo().getLocation().x;
                    centerY = MouseInfo.getPointerInfo().getLocation().y;
                    // sets the center
                    circle.setCenterX(centerX);
                    circle.setCenterY(centerY);
                    circle.setVisible(true);
                    e.consume();
                    firstClicked = false;
                }
            }
        );  
        event.consume(); 
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

    
    //Untested
    public void setBlanckMap() {
        fromMainMenu = true;
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
        fromMainMenu = false;
        cancelButtonImage.setImage(new Image("/imgData/cancelBlue.png"));
        cancelButton.setText("Cancelar");
        mapTitle.setText("Enunciado del problema");
        problemLabel.setText(problem.getText());
        List<model.Answer> answers = problem.getAnswers();
        option1Button.setText(answers.get(0).getText());
        option2Button.setText(answers.get(1).getText());
        option3Button.setText(answers.get(2).getText());
        option4Button.setText(answers.get(3).getText());
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

}
