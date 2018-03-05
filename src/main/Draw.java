package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.*;

public class Draw extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Box box = new Box();
		box.setTranslateX(150);
		box.setTranslateY(0);
		box.setTranslateZ(400);
		
		final PhongMaterial redMaterial = new PhongMaterial();
	     redMaterial.setSpecularColor(Color.ORANGE);
	     redMaterial.setDiffuseColor(Color.RED);
		
		Sphere sphere = new Sphere(50);
		sphere.setTranslateX(350);
		sphere.setTranslateY(100);
		sphere.setTranslateZ(500);
		sphere.setMaterial(redMaterial);
		
		Cylinder cylinder = new Cylinder(50, 20);
		cylinder.setTranslateX(500);
		cylinder.setTranslateY(100);
		cylinder.setTranslateZ(500);
		
		PointLight light = new PointLight();
		light.setTranslateX(150);
		light.setTranslateY(100);
		light.setTranslateZ(300);

		PerspectiveCamera camera = new PerspectiveCamera(false);
		camera.setTranslateX(0);
		camera.setTranslateY(-200);
		camera.setTranslateZ(300);
		
		Group root = new Group(box, sphere, cylinder, light);
		

			
		Scene scene = new Scene(root, 600, 400, true);
		scene.setCamera(camera);
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				light.setTranslateX(event.getX());
				light.setTranslateY(event.getY());
				
			}
			
		});
		
	
		primaryStage.setScene(scene);
		primaryStage.setTitle("3D shapes demo");
		primaryStage.show();
	}

}
