package main;

import javafx.application.*;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.geometry.*;

public class Demo extends Application {

	Sphere sphere;
	Cylinder cylinder;
	ComboBox<String> comboBox;
	ComboBox<String> comboBox2;
	
	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Demo");
		
		// initialize border pane
		BorderPane borderPane = new BorderPane();
		
		// initialize grid pane
		GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(8);
		gridPane.setHgap(5);
		gridPane.setId("gridPane");
		
		// initialize combo box 1 for object selection
		comboBox = new ComboBox<>();
		comboBox.getItems().addAll(
				"Sphere",
				"Cylinder",
				"Box"
				);
		comboBox.setPromptText("Select an object");
		GridPane.setConstraints(comboBox, 0, 0);
		
		// initilize combo box 2 for color selection
		comboBox2 = new ComboBox<>();
		comboBox2.getItems().addAll(
				"White",
				"Black",
				"Red",
				"Green",
				"Yellow",
				"Brown"
				);
		comboBox2.setPromptText("Select Color");
		GridPane.setConstraints(comboBox2, 0, 2);
		
		Button addSphere = new Button("Add");
		addSphere.setOnAction(e -> {
			
			Shape3D object = null;
			PhongMaterial material = new PhongMaterial();
			
			switch (comboBox.getValue()) {
			case "Sphere":
				object = new Sphere(40);
				break;
			case "Cylinder":
				object = new Cylinder(40, 20);
				break;
			case "Box":
				object = new Box(60, 50, 80);
			break;
			}
			
			switch (comboBox2.getValue()) {
				case "White":
					material.setDiffuseColor(Color.WHITE);
					break;
				case "Black":
					material.setDiffuseColor(Color.BLACK);
					break;
				case "Red":
					material.setDiffuseColor(Color.RED);
					break;
				case "Green":
					material.setDiffuseColor(Color.GREEN);
					break;
				case "Yellow":
					material.setDiffuseColor(Color.YELLOW);
					break;
				case "Brown":
					material.setDiffuseColor(Color.BROWN);
					break;
			}
			
			if(comboBox.getValue() != null) {
				if(comboBox2.getValue() != null) {
					object.setMaterial(material);
				}
				object.setTranslateX(20);
				object.setTranslateY(20);
				object.setTranslateZ(50);
				
				borderPane.setCenter(object);
			}
			
		});
		GridPane.setConstraints(addSphere, 0, 3);
		
		
		gridPane.getChildren().addAll(comboBox, comboBox2, addSphere);
		
		borderPane.setLeft(gridPane);
		
		Scene scene = new Scene(borderPane, 400, 300);
		
		PointLight light = new PointLight();
		light.setTranslateX(150);
		light.setTranslateY(100);
		light.setTranslateZ(300);
		
		PerspectiveCamera camera = new PerspectiveCamera(false);
		camera.setTranslateX(0);
		camera.setTranslateY(0);
		camera.setTranslateZ(0);
		
		scene.getStylesheets().add("/main.css");
		scene.setCamera(camera);
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

}	


