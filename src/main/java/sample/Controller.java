package sample;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.PointLight;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;


public class Controller {

    @FXML
    public ComboBox shapeComboBox, colorComboBox;

    @FXML
    public Button addButton;

    @FXML
    public TextField tf1, tf2, tf3;

    @FXML
    public HBox hbox;

    @FXML
    public GridPane gridPane;

    @FXML
    public StackPane stackPane;

    public MenuItem rotateMenuItem;

    public MenuItem deleteMenuItem;

    public Shape3D currentRightClick;

    private ArrayList<Shape3D> shapes = new ArrayList<>();

    private ContextMenu contextMenu;


    public void initialize() {
        tf1.setVisible(false);
        tf2.setVisible(false);
        tf3.setVisible(false);

        GridPane.setConstraints(colorComboBox, 0, 1);
        GridPane.setConstraints(addButton, 0, 2);

        contextMenu = new ContextMenu();

        rotateMenuItem = new MenuItem("Rotate");
        rotateMenuItem.setOnAction(e -> {
            TextField rotateTextField = new TextField();
            rotateTextField.setPromptText("Degrees");
            GridPane.setConstraints(rotateTextField, 0, 8);

            Button setRotationButton = new Button("Rotate");
            GridPane.setConstraints(setRotationButton, 0, 9);
            setRotationButton.setOnAction(event -> {
                if (! (Integer.parseInt(rotateTextField.getText()) > 50 || Integer.parseInt(rotateTextField.getText()) < -50)) {
                    try {
                        currentRightClick.getTransforms().add(new Rotate(Integer.parseInt(rotateTextField.getText()), 0, 0, 0));
                    } catch (NumberFormatException e1) {
                        e1.printStackTrace();
                    }
                }

                gridPane.getChildren().removeAll(rotateTextField, setRotationButton);

            });

            gridPane.getChildren().addAll(rotateTextField, setRotationButton);
        });

        deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(event -> {

            stackPane.getChildren().remove(currentRightClick);

        });

        contextMenu.getItems().addAll(rotateMenuItem, deleteMenuItem);


    }

    public void addShape(){

        Shape3D object = null;
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.WHITE);

        switch (shapeComboBox.getValue().toString()) {
            case "Sphere":
                object = new Sphere(Integer.parseInt(tf1.getText()));
                break;
            case "Cylinder":
                object = new Cylinder(Integer.parseInt(tf1.getText()),
                        Integer.parseInt(tf2.getText()));
                break;
            case "Box":
                object = new Box(Integer.parseInt(tf3.getText()),
                        Integer.parseInt(tf2.getText()),
                        Integer.parseInt(tf3.getText()));
                break;
        }

        if(colorComboBox.getValue() != null && shapeComboBox.getValue() != "") {
            switch (colorComboBox.getValue().toString()) {
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
                default:
                    material.setDiffuseColor(Color.WHITE);
                    break;
            }
        }


        if(shapeComboBox.getValue() != null) {
            if(colorComboBox.getValue() != null) {
                assert object != null;
                object.setMaterial(material);
            }

            object.setCursor(Cursor.HAND);

            Shape3D finalObject = object;
            object.setOnMouseDragged(event -> {
                if(event.getButton() == MouseButton.PRIMARY) {
                    finalObject.setTranslateX(finalObject.getTranslateX() + event.getX());
                    finalObject.setTranslateY(finalObject.getTranslateY() + event.getY());
                }
            });

            object.setOnMouseClicked(e -> {
                if(e.getButton() == MouseButton.SECONDARY) {
                   contextMenu.show(finalObject, e.getScreenX(), e.getScreenY());
                   currentRightClick = finalObject;
                    // stackPane.getChildren().remove(finalObject);
                }
            });


            shapes.add(object);

            stackPane.getChildren().add(object);
        }
    }

    public void getShape() {
        switch (shapeComboBox.getValue().toString()) {
            case "Sphere":
                tf1.setVisible(true);
                tf2.setVisible(false);
                tf3.setVisible(false);
                GridPane.setConstraints(colorComboBox, 0,2);
                GridPane.setConstraints(addButton, 0,3);
                break;
            case "Cylinder":
                tf1.setVisible(true);
                tf2.setVisible(true);
                tf3.setVisible(false);
                GridPane.setConstraints(colorComboBox, 0,3);
                GridPane.setConstraints(addButton, 0,4);
                break;
            case "Box":
                tf1.setVisible(true);
                tf2.setVisible(true);
                tf3.setVisible(true);
                GridPane.setConstraints(colorComboBox, 0,4);
                GridPane.setConstraints(addButton, 0,5);
                break;
        }
    }
}
