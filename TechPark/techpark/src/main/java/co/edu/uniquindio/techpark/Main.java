package co.edu.uniquindio.techpark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // 1. Cargar el archivo FXML (la Vista)
            // Cambia "VistaPrincipal.fxml" por la ruta de tu archivo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/InicioSesionView.fxml"));
            Parent root = loader.load();

            // Nota: El Controlador se vincula automáticamente si pusiste 
            // fx:controller="tu.paquete.TuControlador" en la etiqueta raíz del FXML.

            // 2. Crear la Escena con el nodo raíz del FXML
            Scene scene = new Scene(root);

            // 3. Configurar el Escenario (Stage)
            primaryStage.setTitle("TechPark");
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // El main solo se encarga de lanzar la aplicación
        launch(args);
    }
}