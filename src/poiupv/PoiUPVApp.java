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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Navegacion;

/**
 *
 * @author jose
 */
public class PoiUPVApp extends Application {
    
    public static Navegacion navLib;
    public static model.User currentUser;
    
    @Override
    public void start(Stage stage) throws Exception {
        try {
            navLib = Navegacion.getSingletonNavegacion();
        } catch (NavegacionDAOException e) {
            System.out.println("Navegation singleton init error");
        }
        //navLib.registerUser("nombreTest", "correoTest", "123456", LocalDate.now());
        Parent root = FXMLLoader.load(getClass().getResource("../loginPackage/loginFXML.fxml"));
        
        Scene scene = new Scene(root);
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
    
}
