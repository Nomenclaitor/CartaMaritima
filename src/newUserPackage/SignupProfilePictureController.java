/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newUserPackage;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

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
    private ImageView defaultPic6;
    @FXML
    private Button fileSelectButton;
    @FXML
    private Label filePathLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void previousClicked(ActionEvent event) {
    }

    @FXML
    private void createAccountPressed(ActionEvent event) {
    }
    
}
