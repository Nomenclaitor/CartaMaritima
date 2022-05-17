/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userProgressPackage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
    private ListView<?> sessionList;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifyPressed(ActionEvent event) {
    }

    @FXML
    private void mainMenuPressed(ActionEvent event) {
    }

    @FXML
    private void helpPressed(ActionEvent event) {
    }

    @FXML
    private void closeSessionPressed(ActionEvent event) {
    }

    @FXML
    private void showData(ActionEvent event) {
    }
    
}
