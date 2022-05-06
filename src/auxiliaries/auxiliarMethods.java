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
    
    /**
     * Auxiliary method to manage error messages
     * @param errorLabel error label to prompt
     * @param textField field to highlight
     * @param boolProp Boolean variable 
     */
     public static void manageError(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel,textField);
        //textfield focus disable to allow more flexibility for the users
        //textField.requestFocus();
     }
    
     /**
      * Same auxiliary method as the one above
      * Can control 2 error labels at the same time
      * @param errorLabel error label 1
      * @param secondErrorLabel error label 2
      * @param firstTextField field to highlight
      * @param boolprop Boolean variable
      */
    public static void manageError(Label errorLabel, Label secondErrorLabel, TextField firstTextField, BooleanProperty boolprop) {
        boolprop.setValue(Boolean.FALSE);
        showErrorMessage(errorLabel, secondErrorLabel, firstTextField);
    }
    
    /**
     * Private method to show error labels and highlight the textField
     * @param errorLabel error label
     * @param textField field to highlight
     */
    private static void showErrorMessage(Label errorLabel,TextField textField) {
        errorLabel.visibleProperty().set(true);
        textField.styleProperty().setValue("-fx-background-color: #FCE5E0");    
    }
    
    /**
     * Same private method as the one above
     * Shows two error labels at the same time
     * @param errorLabel error label 1
     * @param secondErrorLabel error label 2
     * @param firstTextField field to highlight
     */
    private static void showErrorMessage(Label errorLabel, Label secondErrorLabel, TextField firstTextField) {
        errorLabel.visibleProperty().set(true);
        secondErrorLabel.visibleProperty().set(true);
        firstTextField.styleProperty().setValue("-fx-background-color: #FCE5E0");
    }
    
    /**
     * Auxiliary method to hide error messages and un-highlight the fields
     * @param errorLabel error label to hide
     * @param textField field to un-highlight
     * @param boolProp Boolean property to update
     */
    public static void manageCorrect(Label errorLabel,TextField textField, BooleanProperty boolProp ){
        boolProp.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel,textField);
    }
    
    /**
     * Same method as the one above
     * Hides two error labels
     * @param errorLabel error label 1
     * @param secondErrorLabel error label 2
     * @param firstTextField field to highlight
     */
    public static void manageCorrect(Label errorLabel, Label secondErrorLabel, TextField firstTextField,BooleanProperty boolprop) {
       boolprop.setValue(Boolean.TRUE);
        hideErrorMessage(errorLabel, secondErrorLabel, firstTextField);
    }
    
    /**
     * Private auxiliary method to hide error label and un-highlight
     * @param errorLabel error label to hide
     * @param textField field to un-highlight
     */
    private static void hideErrorMessage(Label errorLabel,TextField textField) {
        errorLabel.visibleProperty().set(false);
        textField.styleProperty().setValue("-fx-background-color: #C7EEFF");
    }
    
    /**
     * Private auxiliary method to hide error label and un-highlight
     * @param errorLabel first error label to hide
     * @param secondErrorLabel second error label to hide
     * @param textField field to un-highlight
     */
    private static void hideErrorMessage(Label errorLabel, Label secondErrorLabel, TextField firstTextField) {
        errorLabel.visibleProperty().set(false);
        secondErrorLabel.visibleProperty().set(false);
        firstTextField.styleProperty().setValue("-fx-background-color: #C7EEFF");
    }
}
