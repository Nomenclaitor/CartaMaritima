/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliaries;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author qiyao
 */
public class auxiliarMethods {
     public static void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        //textfield focus disable to allow more flexibility for the users
        //textField.requestFocus();
     }
    
    public static void manageError(Label errorLabel, Label secondErrorLabel, TextField firstTextField, BooleanProperty boolprop) {
        boolprop.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, secondErrorLabel, firstTextField);
    }
    
    public static void showErrorMessage(Label errorLabel,TextField textField) {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    
    public static void showErrorMessage(Label errorLabel, Label secondErrorLabel, TextField firstTextField) {
        errorLabel.visibleProperty().set(true);
        secondErrorLabel.visibleProperty().set(true);
        firstTextField.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }
    
    public static void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
    }
    
    public static void manageCorrect(Label errorLabel, Label secondErrorLabel, TextField firstTextField,BooleanProperty boolprop) {
       boolprop.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, secondErrorLabel, firstTextField);
    }
    
    public static void hideErrorMessage(Label errorLabel,TextField textField) {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("-fx-background-color: #C7EEFF");
    }
    
    public static void hideErrorMessage(Label errorLabel, Label secondErrorLabel, TextField firstTextField) {
        errorLabel.visibleProperty().set(false);
        secondErrorLabel.visibleProperty().set(false);
        firstTextField.styleProperty().setValue("-fx-background-color: #C7EEFF");
    }
}
