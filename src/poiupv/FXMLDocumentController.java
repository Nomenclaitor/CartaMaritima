/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import java.util.Optional;
import auxiliaries.auxiliarMethods;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Answer;
import model.Problem;

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
    ContextMenu sideMenu;
    boolean sideMenuCreated = false;
    
    
    @FXML
    private ScrollPane map_scrollpane;
    @FXML
    private Slider zoom_slider;
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
    private ToggleGroup mapGroup;
    @FXML
    private ToggleButton lineButton;
    @FXML
    private ToggleButton pointButton;
    @FXML
    private ToggleButton textButton;
    @FXML
    private ToggleButton cursorButton;
    @FXML
    private ToggleButton anglerToggler;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ToggleButton fillerButton;
    @FXML
    private Button incSizeButton;
    @FXML
    private Slider sizeSlider;
    @FXML
    private Button decSizeButton;
    @FXML
    private Button clearMapButton;
    @FXML
    private ImageView anglePortractor;
    @FXML
    private ToggleGroup answerGroup;
    @FXML
    private ImageView cancelButtonImage;
    @FXML
    private ImageView veirfyButonImage;
    @FXML
    private ChoiceBox<String> shapeChoiceBox;
    @FXML
    private StackPane anglerPane;
    @FXML
    private ToggleButton gridPressed;

    private boolean fromMainMenu = false;
    private boolean answerVerified = false;
    
    //???    
    private Problem problem;
    private List<Answer> answersList;

    
    private Answer finalAnswer = null;
    private RadioButton selectedButton = null;
    
    private Group markGroup;

    private ObservableList<String> shapeList = FXCollections.observableArrayList("Cuadrado", "Circulo");
    
    private double anglerInitialX, anglerInitialY, baseX, baseY;

    
    TextField text;
    Line line;
    Line hLine, vLine;
    Circle circle;
    double inicioXArc;
    boolean textCreated = false;
    @FXML
    private Button transportador;
    
    SimpleBooleanProperty anglerVisible = new SimpleBooleanProperty(false);

    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initData();
        cursorButton.requestFocus();
        //==========================================================
        // inicializamos el slider y enlazamos con el zoom
        zoom_slider.setMin(0.2);
        zoom_slider.setMax(2.5);
        zoom_slider.setValue(0.4);
        zoom_slider.valueProperty().addListener((o, oldVal, newVal) -> zoom((Double) newVal));
        
        //=========================================================================
        //Envuelva el contenido de scrollpane en un grupo para que 
        //ScrollPane vuelva a calcular las barras de desplazamiento tras el escalado
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(map_scrollpane.getContent());
        map_scrollpane.setContent(contentGroup);
        markGroup = new Group();
        zoomGroup.getChildren().add(markGroup);
        
        
        //Portractor visbity binding
        anglePortractor.visibleProperty().bind(anglerToggler.selectedProperty());
        anglerPane.disableProperty().bind(anglerToggler.selectedProperty().not());
        
        //Button id binding
        lineButton.setUserData("lineButton");
        gridPressed.setUserData("gridButton");
        pointButton.setUserData("circleButton");
        textButton.setUserData("textButton");
        cursorButton.setUserData("cursorButton");
        anglerToggler.setUserData("angleToggler");
        
        mapGroup.selectedToggleProperty().addListener((obs, oldV, newV) -> {
            if (newV == null) {
                mapGroup.selectToggle(cursorButton);
                cursorButton.requestFocus();
            }
        });
        
        //Shape choices initializer
        shapeChoiceBox.setItems(shapeList);
        shapeChoiceBox.setValue("Circulo");
    }
    
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
        zoom_slider.setValue(sliderVal += -0.1);
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
            if (auxiliarMethods.promptAlert("salir al menu principal", "Todo el progreso en el mapa será borrado")) {
                toMainMenu();
            }
        } else {
            if (auxiliarMethods.promptAlert("salir al menu de selección de problemas", "Todo el progreso en el mapa será borrado y el problema dado como fallido")) {
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
    private void incSize(ActionEvent event) {
        sizeSlider.setValue(sizeSlider.getValue() + 0.1);
    }

    @FXML
    private void decSize(ActionEvent event) {
        sizeSlider.setValue(sizeSlider.getValue() - 0.1);
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
    
    @FXML
    private void clearPressed(ActionEvent event) {
        if (markGroup.getChildren().size() > 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Borrar todo");
            alert.setHeaderText("Está seguro de limpiar el mapa?");
            alert.setContentText("Está seguro de quitar todas las marcas del mapa?\nEsta acción no se puede revertir");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                markGroup.getChildren().clear();
            }
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Borrar todo");
            alert.setHeaderText("Limpiar el mapa");
            alert.setContentText("Ahora mismo no hay ningun marca para borrar.");
            alert.showAndWait();
        }
    }

    @FXML
    private void anglerDragged(MouseEvent event) {
        double despX = event.getSceneX() - anglerInitialX;
        double despY = event.getSceneY() - anglerInitialY;
        
        double X = despX + baseX;
        double Y = despY + baseY;
        
        double transLayoutX = anglerPane.getLayoutX();
        double transWidth = anglerPane.getWidth();
        double transLayoutY = anglerPane.getLayoutY();
        double transHeight = anglerPane.getHeight();
        
        Bounds parentBounds = anglerPane.getParent().getLayoutBounds();

        if ((transLayoutX + X > -1) && (transLayoutX + X < parentBounds.getWidth() - transWidth)) {
                anglerPane.setTranslateX(X);
            } else if (transLayoutX + (X) < 0) {
                anglerPane.setTranslateX(-transLayoutX);
            } else {
                anglerPane.setTranslateX(parentBounds.getWidth() - transLayoutX - transWidth);
        }
        
        if ((transLayoutY + Y < parentBounds.getHeight() - transHeight) && (transLayoutY + Y > -1)) {
                anglerPane.setTranslateY(Y);
            } else if (transLayoutY + Y < 0) {
                anglerPane.setTranslateY(-transLayoutY);
            } else {
                anglerPane.setTranslateY(parentBounds.getHeight() - transLayoutY - transHeight);
        }

        event.consume();
    }

    @FXML
    private void anglerClicked(MouseEvent event) {
        anglerInitialX = event.getSceneX();
        anglerInitialY = event.getSceneY();
        baseX = anglerPane.getTranslateX();
        baseY = anglerPane.getTranslateY();
        event.consume();
    }


    @FXML
    private void mapOptionSelect(ActionEvent event) {
        map_scrollpane.setPannable(false);
        if (mapGroup.getSelectedToggle() == cursorButton) map_scrollpane.setPannable(true);
    }

    @FXML
    private void mapReleased(MouseEvent event) {
        line = null;
        circle = null;
    }

    @FXML
    private void mapDragged(MouseEvent event) {
        switch(mapGroup.getSelectedToggle().getUserData().toString()) {
            case "lineaBoton":
                if (line != null) {
                    line.setEndX(event.getX());
                    line.setEndY(event.getY());
                    event.consume();
                }
                break;
            case "circuloBoton":
                if (circle != null) {
                    double radio = Math.abs(event.getX()-inicioXArc);
                    circle.setRadius(radio);
                    event.consume();
                }
                break;
            default:
                break;
        }
    }

    @FXML
    private void mapPressed(MouseEvent event) {
        if (textCreated) {
            createText(new ActionEvent());
            return;
        }
        if (event.getButton() == MouseButton.PRIMARY) {
            String option = mapGroup.getSelectedToggle().getUserData().toString();
            if (option.equals("textButton")) {
                System.out.println("text!");
                createText(event);
            } else if (option.equals("circleButton") && fillerButton.selectedProperty().get() == true) {
                System.out.println("point!");
                createPoint(event, shapeChoiceBox.getValue());
            } else if (option.equals("lineButton")) {
                System.out.println("line!");
                createLine(event);
            } else if (option.equals("circleButton") && fillerButton.selectedProperty().get() == false) {
                System.out.println("circle!");
                createCircle(event);
            } else if (option.equals("gridButton")) {
                System.out.println("grid!");
                createGrid(event);
            }
        }
    }
    
    private void createPoint(MouseEvent event, String shape) {
        switch (shape) {
            case "Circulo":
                createCircle(event.getX(),event.getY());
                break;
            case "Cuadrado":
                createRectangle(event.getX(), event.getY());
                break;
        }
        event.consume();
    }
    
    private void createRectangle(double x, double y) {
        double v = sizeSlider.getValue()*2;
        Rectangle r = new Rectangle(v,v);
        if (fillerButton.selectedProperty().getValue() == true) {
            r.setFill(colorPicker.getValue());
        }
        r.setStroke(colorPicker.getValue());
        r.setX(x-(r.getWidth()/2));
        r.setY(y-(r.getWidth()/2));
        addToList(r);
    }
    
    private void createCircle(double x, double y){
        Circle c = new Circle(sizeSlider.getValue());
        if (fillerButton.selectedProperty().getValue() == true) {
            c.setFill(colorPicker.getValue());
        }
        c.setStroke(colorPicker.getValue());
        c.setCenterX(x);
        c.setCenterY(y);
        addToList(c);
    }
    
    /**
     * Creates an TextField on the place of the event, gets the user input and converts passes it on 
     * to createText which creates an Text on the place of the event (click)
     * @param event 
     */
    private void createText(MouseEvent event) {
        text = new TextField();
        markGroup.getChildren().add(text);
        text.setLayoutX(event.getX());
        text.setLayoutY(event.getY());
        Font nuevaFuente = Font.font(3 * sizeSlider.getValue());
        text.setFont(nuevaFuente);
        text.requestFocus();
        text.setOnAction(this::createText);
        textCreated = true;
        event.consume();
    }
    
    /**
     * Creates an text on the place of the event
     * @param event 
     */
    private void createText(ActionEvent event){
        if (!text.getText().isBlank()){
            Text textoT = new Text(text.getText());
            textoT.setX(text.getLayoutX());
            textoT.setY(text.getLayoutY());
            setTextCharacteristics(textoT);
            addToList(textoT);
        }
        markGroup.getChildren().remove(text);
        textCreated = false;
        event.consume();
    }
    
    private void createLine (MouseEvent event) {
        line = new Line(event.getX(),event.getY(),event.getX(),event.getY());
        setLineCharacteristics(line);
        addToList(line);
        event.consume();
    }
    
    private void createCircle (MouseEvent event) {
        circle = new Circle(1);
        circle.setFill(Color.TRANSPARENT);
        setCircleCharacteristics(circle);
        addToList(circle);
        circle.setCenterX(event.getX());
        circle.setCenterY(event.getY());
        inicioXArc = event.getX();
        event.consume();
    }
    
    private void createGrid (MouseEvent event) {
        ImageView immg = (ImageView) event.getSource();
        hLine = new Line(0,event.getY(),immg.getBoundsInLocal().getMaxX(),event.getY());
        vLine = new Line(event.getX(),0,event.getX(),immg.getBoundsInLocal().getMaxY());
        setLineCharacteristics(hLine);
        setLineCharacteristics(vLine);
        addToList(hLine);
        addToList(vLine);
        event.consume();
    }

    private void setTextCharacteristics(Text text) {
        text.setFill(colorPicker.getValue());
        Font nuevaFuente = Font.font(sizeSlider.getValue() * 3);
        text.setFont(nuevaFuente);
    }
    
    private void setLineCharacteristics(Line line) {
        line.setFill(colorPicker.getValue());
        line.setStroke(colorPicker.getValue());
        line.setStrokeWidth(sizeSlider.getValue());
    }
    
    private void setCircleCharacteristics(Circle circle) {
        circle.setStroke(colorPicker.getValue());
        circle.setStrokeWidth(sizeSlider.getValue());
    }
    
    private void editCircularPoint(Circle circle) {
        if (shapeChoiceBox.getValue().equals("Circulo")) {
            circle.setFill(colorPicker.getValue());
            circle.setStroke(colorPicker.getValue());
            circle.setRadius(sizeSlider.getValue());
        } else {
            createRectangle(circle.getCenterX(), circle.getCenterY());
            markGroup.getChildren().remove(circle);
        }
    }
    
    private void editRectangularPoint(Rectangle rectangle) {
        if (shapeChoiceBox.getValue().equals("Cuadrado")) {
            rectangle.setFill(colorPicker.getValue());
            rectangle.setStroke(colorPicker.getValue());
            rectangle.setHeight(sizeSlider.getValue() * 2);
            rectangle.setWidth(sizeSlider.getValue() * 2);
        } else {
            createCircle(rectangle.getX() + (rectangle.getHeight() / 2), rectangle.getY() + (rectangle.getHeight() / 2));
            markGroup.getChildren().remove(rectangle);
        }
    }
    
    private void addToList(Node shapeNode) {
        System.out.println(shapeNode);
        markGroup.getChildren().add(shapeNode);
        shapeNode.setOnContextMenuRequested(this::contextMenuCreator);
        shapeNode.setOnMousePressed(this::elementSelector);
    }

    private void elementSelector(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY){
            Node n = (Node)e.getSource();
            editarNodo(n);
        }
    }
    
    private void contextMenuCreator (ContextMenuEvent event){
        if (!sideMenuCreated){
            sideMenuCreated = true;
            sideMenu = new ContextMenu();
            MenuItem borrarItem = new MenuItem("Eliminar");
            sideMenu.getItems().add(borrarItem);
            
            borrarItem.setOnAction(ev -> {
                markGroup.getChildren().remove((Node)event.getSource());
                ev.consume();
            });
            
            sideMenu.setOnHidden(ev -> {
                sideMenuCreated = false;
            });
        } 
        if (sideMenu!=null)
        sideMenu.show((Node)event.getSource(),event.getScreenX(), event.getScreenY());
        event.consume();
    }
    
    private void editarNodo(Node shapeNode) {
        if (shapeNode instanceof Text){
                Text texto = (Text) shapeNode;
                setTextCharacteristics(texto);
        } else if (shapeNode instanceof Circle){
            Circle circle = (Circle) shapeNode;
            if (circle.getFill() == Color.TRANSPARENT) {
                setCircleCharacteristics(circle);
            } else {
                editCircularPoint(circle);
            }
        } else if (shapeNode instanceof Line) {
            Line line = (Line) shapeNode;
            setLineCharacteristics(line);
        } else {
            Rectangle r = (Rectangle) shapeNode;
            editRectangularPoint(r);
        }
    }



}
