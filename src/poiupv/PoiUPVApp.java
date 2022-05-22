/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Navegacion;
import model.Session;

/**
 *
 * @author jose
 */
public class PoiUPVApp extends Application {
    
    public static Navegacion navLib;
    public static model.User currentUser;
    
    private static Session currentSession;
    public static int correct = 0;
    public static int incorrect = 0;
    
    private static Scene scene;
    private static Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            navLib = Navegacion.getSingletonNavegacion();
        } catch (NavegacionDAOException e) {
            System.out.println("Navegation singleton init error");
        }
        
        //navLib.registerUser("nombreTest", "correoTest", "123456", LocalDate.now());
        //currentUser = navLib.getUser("nombreTest");
        //currentUser.setAvatar(new Image("/imgData/85498161615209203_1636332751.jpg"));
        //currentUser.setBirthdate(LocalDate.now());
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/loginPackage/loginFXML.fxml"));
        
        scene = new Scene(root);
        stage.setTitle("Nautica418");
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public static void saveSession() {
        currentSession = new Session(LocalDateTime.now(), correct, incorrect);
        try {
            currentUser.addSession(currentSession);
        } catch (NavegacionDAOException e) {
            System.out.println("Error saving current user session");
            System.out.println(e);
        }
    }
    
    //ALERT
    /**
    public void stop() {
        currentSession = new Session(LocalDateTime.now(), correct, incorrect);
        try {
            if (currentUser != null) {
                auxiliaries.auxiliarMethods.promptAlert("salir de la apicacion", "Los datos de esta sesión se guardará y se cerrerá su sesión");
                currentUser.addSession(currentSession);
            }
        } catch (NavegacionDAOException e) {
            
        }
    }
    * */

}
