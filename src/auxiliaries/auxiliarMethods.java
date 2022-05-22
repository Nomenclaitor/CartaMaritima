/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliaries;

import java.io.IOException;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    
    public static void loadWindow(FXMLLoader loader, String title, int width, int height) {
        try {
            Parent root = loader.load();
            
            Scene scene = new Scene(root, width, height);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.setTitle(title);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
       
    }
    
    public static void highlightSelection(Button highlightButton) {
        highlightButton.styleProperty().setValue("-fx-border-color: #0685BC");
        highlightButton.styleProperty().setValue("-fx-border-width: 2");
        highlightButton.styleProperty().setValue("-fx-border-radius: 10C");
        highlightButton.styleProperty().setValue("-fx-background-radius: 10");
    }
    
    public static void unhighlightSelection(Button unhighlightButton) {
        unhighlightButton.styleProperty().setValue("-fx-border-color: ");
        unhighlightButton.styleProperty().setValue("-fx-border-width: ");
        unhighlightButton.styleProperty().setValue("-fx-border-radius: ");
        unhighlightButton.styleProperty().setValue("-fx-background-radius: ");
    }
    
    public static void errorPrompt(String title, String header, String bodyText) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(bodyText);
        errorAlert.showAndWait();
    }
    
    public static boolean promptAlert(String headerText, String bodyText) {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Confirmación de Salida");
        exitAlert.setHeaderText("Está seguro de " + headerText +"?");
        exitAlert.setContentText(bodyText);
        Optional<ButtonType> result = exitAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }
        return false;
    }
    
    public static void promptError(String titleText, String headerText, String bodyText) {
        Alert errorAlert = new Alert(Alert.AlertType.WARNING);
        errorAlert.setTitle(titleText);
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(bodyText);
        errorAlert.showAndWait();
    }
    
    public static void promptInformation(String titleText, String headerText, String bodyText) {
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
        infoAlert.setTitle(titleText);
        infoAlert.setHeaderText(headerText);
        infoAlert.setContentText(bodyText);
        infoAlert.showAndWait();
    }
    
    public static void dumpErrorTrace(StackTraceElement[] traceArray) {
        for (int aux = 0; aux < traceArray.length; aux++) {
            System.out.println(traceArray[aux].toString());
        }
    }
}
