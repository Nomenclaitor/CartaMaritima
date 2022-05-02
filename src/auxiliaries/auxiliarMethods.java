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
    
    public static void manageError(Label errorLabel, TextField firstTextField, TextField secondTextField, TextField thirdTextField, BooleanProperty boolprop) {
        boolprop.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, firstTextField, secondTextField, thirdTextField);
    }
    
    public static void showErrorMessage(Label errorLabel,TextField textField) {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    
    public static void showErrorMessage(Label errorLabel, TextField firstTextField, TextField secondTextField, TextField thirdTextField) {
        errorLabel.visibleProperty().set(true);
        firstTextField.styleProperty().setValue("-fx-background-color: #FCE5E0");
        secondTextField.styleProperty().setValue("-fx-background-color: #FCE5E0");
        thirdTextField.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }
    
    public static void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
    }
    
    public static void manageCorrect(Label errorLabel, TextField firstTextField, TextField secondTextField, TextField thirdTextField, BooleanProperty boolprop) {
       boolprop.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, firstTextField, secondTextField, thirdTextField);
    }
    
    public static void hideErrorMessage(Label errorLabel,TextField textField) {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("-fx-background-color: #C2FFDA");
    }
    
    public static void hideErrorMessage(Label errorLabel, TextField firstTextField, TextField secondTextField, TextField thirdTextField) {
        errorLabel.visibleProperty().set(false);
        firstTextField.styleProperty().setValue("-fx-background-color: #C2FFDA");
        secondTextField.styleProperty().setValue("-fx-background-color: #C2FFDA");
        thirdTextField.styleProperty().setValue("-fx-background-color: #C2FFDA");
    }
}
