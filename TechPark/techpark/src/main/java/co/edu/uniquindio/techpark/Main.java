package co.edu.uniquindio.techpark;

import co.edu.uniquindio.techpark.controller.ParqueController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/InicioSesionView.fxml")
            );
            Parent root = loader.load();

            primaryStage.setOnCloseRequest(event -> 
                ParqueController.getParque().guardarDatos()
            );

            Scene scene = new Scene(root);
            primaryStage.setTitle("TechPark");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        ParqueController.getParque().guardarDatos();
    }

    public static void main(String[] args) {
        launch(args);
    }
}