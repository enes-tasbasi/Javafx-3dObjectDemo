package main;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class NewFrame extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("New Frame");
		
		Button btn = new Button("Third Button");
		btn.setLayoutX(100);
		btn.setLayoutY(50);
		Pane pane = new Pane(btn);
		
		Scene scene = new Scene(pane, 400, 200);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
}
