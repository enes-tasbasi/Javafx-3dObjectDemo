package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();

        Controller myController = loader.getController();
        Scene scene = new Scene(root);

        myController.initialize();

        PerspectiveCamera camera = new PerspectiveCamera(false);

        camera.setTranslateZ(-0.1);
        camera.setNearClip(0.1);
        camera.setFarClip(2000.0);
        camera.setFieldOfView(30);

        scene.setCamera(camera);
        scene.getStylesheets().add("/sample/main.css");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void close() {
        System.exit(0);
    }
}
