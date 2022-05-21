/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poiupv;

import DBAccess.NavegacionDAOException;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.Navegacion;
import model.Session;

/**
 *
 * @author jose
 */
public class PoiUPVApp extends Application {
    
    public static Navegacion navLib;
    public static Session currentSession;
    public static model.User currentUser;
    
    private static Scene scene;
       
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
    
    public static void updateSession() {
        
    }
    
    public static void saveSession() {
        
    }
    
}
