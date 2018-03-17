package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.PointLight;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


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

    @FXML
    public MenuBar menuBar;

    @FXML
    public Menu fileMenu;

    @FXML
    public MenuItem closeMenuItem;

    @FXML
    public MenuItem saveMenuItem;

    @FXML
    public MenuItem openMenuItem;

    public MenuItem rotateMenuItem;

    public MenuItem deleteMenuItem;

    public Shape3D currentRightClick;

    private ArrayList<Shape3D> shapes = new ArrayList<>();

    private ContextMenu contextMenu;

    HashMap<Shape3D, Integer> map = new HashMap<>();

    public void initialize() {

        // Make dimension text fields invisible by default
        tf1.setVisible(false);
        tf2.setVisible(false);
        tf3.setVisible(false);

        GridPane.setConstraints(colorComboBox, 0, 1);
        GridPane.setConstraints(addButton, 0, 2);



        // initialize context menu ( right-click menu )
        contextMenu = new ContextMenu();


        // create the two menuitems that are going to be inside of the context menu
        rotateMenuItem = new MenuItem("Rotate");
        rotateMenuItem.setOnAction(e -> {
            TextField rotateTextField = new TextField();
            rotateTextField.setPromptText("Degrees");
            GridPane.setConstraints(rotateTextField, 0, 8);

            Button setRotationButton = new Button("Rotate");
            GridPane.setConstraints(setRotationButton, 0, 9);
            setRotationButton.setOnAction(event -> {
                if (!(Integer.parseInt(rotateTextField.getText()) > 50 || Integer.parseInt(rotateTextField.getText()) < -50)) {
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
            shapes.remove(currentRightClick);

        });

        contextMenu.getItems().addAll(rotateMenuItem, deleteMenuItem);


        saveMenuItem.setOnAction(event -> Utils.save(shapes, map));
        openMenuItem.setOnAction(event -> {
            ArrayList<Shape3D> shapes = Utils.open();
            stackPane.getChildren().clear();
            this.shapes.clear();
            for (Shape3D shape : shapes) {
                shape.setOnMouseClicked(event1 -> registerMouseClickListener(event1, shape));
                shape.setOnMouseDragged(event1 -> registerMouseDragListener(event1, shape));
                shape.setCursor(Cursor.HAND);
            }

            stackPane.getChildren().addAll(shapes);
            this.shapes.addAll(shapes);


        });
        closeMenuItem.setOnAction(event -> Main.exit());


    }

    public void addShape() {
        Shape3D object = null;
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.WHITE);

        if (shapeComboBox.getValue() != null) {
            switch (shapeComboBox.getValue().toString()) {
                case "Sphere":
                    object = new Sphere(Integer.parseInt(tf1.getText()));
                    map.put(object, Integer.parseInt(tf1.getText()));
                    break;
                case "Cylinder":
                    object = new Cylinder(Integer.parseInt(tf1.getText()),
                            Integer.parseInt(tf2.getText()));
                    map.put(object, Integer.parseInt(tf1.getText()));
                    break;
                case "Box":
                    object = new Box(Integer.parseInt(tf3.getText()),
                            Integer.parseInt(tf2.getText()),
                            Integer.parseInt(tf3.getText()));
                    break;
            }
        }

        if (colorComboBox.getValue() != null && shapeComboBox.getValue() != "") {
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


        if (shapeComboBox.getValue() != null) {
            if (colorComboBox.getValue() != null) {
                assert object != null;
                object.setMaterial(material);
            }

            object.setCursor(Cursor.HAND);

            Shape3D finalObject = object;
            object.setOnMouseDragged(e -> registerMouseDragListener(e, finalObject));

            object.setOnMouseClicked(e -> registerMouseClickListener(e, finalObject));

            shapes.add(object);

            stackPane.getChildren().add(object);

            String musicFile = "/sounds/click.wav";     // For example


        }
    }

    public void getShape() {
        switch (shapeComboBox.getValue().toString()) {
            case "Sphere":
                tf1.setVisible(true);
                tf2.setVisible(false);
                tf3.setVisible(false);
                GridPane.setConstraints(colorComboBox, 0, 2);
                GridPane.setConstraints(addButton, 0, 3);
                break;
            case "Cylinder":
                tf1.setVisible(true);
                tf2.setVisible(true);
                tf3.setVisible(false);
                GridPane.setConstraints(colorComboBox, 0, 3);
                GridPane.setConstraints(addButton, 0, 4);
                break;
            case "Box":
                tf1.setVisible(true);
                tf2.setVisible(true);
                tf3.setVisible(true);
                GridPane.setConstraints(colorComboBox, 0, 4);
                GridPane.setConstraints(addButton, 0, 5);
                break;
        }
    }

    private void registerMouseDragListener(MouseEvent e, Shape3D finalObject) {

        if (e.getButton() == MouseButton.PRIMARY) {
            finalObject.setTranslateX(finalObject.getTranslateX() + e.getX());
            finalObject.setTranslateY(finalObject.getTranslateY() + e.getY());
        }

    }

    private void registerMouseClickListener(MouseEvent e, Shape3D finalObject) {

        if (e.getButton() == MouseButton.SECONDARY) {
            contextMenu.show(finalObject, e.getScreenX(), e.getScreenY());
            currentRightClick = finalObject;
        }
    }
}
