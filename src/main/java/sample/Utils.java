package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Utils {

    private static String mapperJson = "";

    public static void save(ArrayList<Shape3D> shapes, HashMap<Shape3D, Integer> map) {

        List<SceneShape> sceneShapeList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();


        if (!(shapes == null) || (shapes.size() == 0)) {

            for (Shape3D shape : shapes) {

                SceneShape sceneShape = new SceneShape();

                String shapeType = "Sphere";

                if (shape.getClass().toString().contains("Sphere")) {
                    shapeType = "Sphere";

                    sceneShape.setDimension1(map.get(shape));
                    System.out.println(sceneShape.getDimension1());
                } else if (shape.getClass().toString().contains("Cylinder")) {
                    shapeType = "Cylinder";

                    sceneShape.setDimension1(map.get(shape));
                    sceneShape.setDimension2(shape.getLayoutBounds().getHeight());
                } else if (shape.getClass().toString().contains("Box")) {
                    shapeType = "Box";

                    sceneShape.setDimension1(shape.getLayoutBounds().getWidth());
                    sceneShape.setDimension2(shape.getLayoutBounds().getHeight());
                    sceneShape.setDimension3(shape.getLayoutBounds().getWidth());
                }


                String color = "WHITE";

                if (shape.getMaterial() != null) {
                    color = shape.getMaterial().toString().substring(27, 37);

                    if (color.equals(Color.BLACK.toString())) {
                        color = "BLACK";
                    } else if (color.equals(Color.RED.toString())) {
                        color = "RED";
                    } else if (color.equals(Color.GREEN.toString())) {
                        color = "GREEN";
                    } else if (color.equals(Color.YELLOW.toString())) {
                        color = "YELLOW";
                    } else if (color.equals(Color.BROWN.toString())) {
                        color = "BROWN";
                    }

                }

                System.out.println(shape.getLayoutBounds());

                sceneShape.setDimension3(shape.getLayoutBounds().getDepth());
                sceneShape.setTranslateX(shape.getTranslateX());
                sceneShape.setTranslateY(shape.getTranslateY());

                double yx = shape.getLocalToParentTransform().getMyx();
                double yy = shape.getLocalToParentTransform().getMyy();
                double angle = Math.atan2(yx, yy);
                sceneShape.setRotation(angle);

                sceneShape.setColor(color);
                sceneShape.setShapeType(shapeType);
                sceneShapeList.add(sceneShape);

            }
        }

        try {
            PrintWriter writer = new PrintWriter("src/main/resources/scene.json", "UTF-8");
            mapper.writeValue(writer, sceneShapeList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Shape3D> open() {

        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Shape3D> shapes = new ArrayList<>();

        ArrayList<SceneShape> sceneShapeList = new ArrayList<>();

        TypeFactory typeFactory = mapper.getTypeFactory();
        JavaType sceneShapeType = typeFactory.constructParametricType(ArrayList.class, SceneShape.class);

        try {
            sceneShapeList = mapper.readValue(new File("src/main/resources/scene.json"), sceneShapeType);

        } catch (IOException e) {
            e.printStackTrace();
        }


        for (SceneShape shape : sceneShapeList) {

            Shape3D object = new Sphere(100);


            switch (shape.getShapeType()) {
                case "Sphere":
                    object = new Sphere(shape.getDimension1());
                    break;
                case "Cylinder":
                    object = new Cylinder(shape.getDimension1(), shape.getDimension2());
                    break;
                case "Box":
                    object = new Box(shape.getDimension1(), shape.getDimension2(), shape.getDimension3());

                    break;
            }

            PhongMaterial material = new PhongMaterial();

            switch (shape.getColor()) {
                case "WHITE":
                    material.setDiffuseColor(Color.WHITE);
                    break;
                case "BLACK":
                    material.setDiffuseColor(Color.BLACK);
                    break;
                case "GREEN":
                    material.setDiffuseColor(Color.GREEN);
                    break;
                case "RED":
                    material.setDiffuseColor(Color.RED);
                    break;
                case "YELLOW":
                    material.setDiffuseColor(Color.YELLOW);
                    break;
                case "BROWN":
                    material.setDiffuseColor(Color.BROWN);
                    break;
            }
            object.setTranslateX(shape.translateX);
            object.setTranslateY(shape.translateY);
            object.setRotate(shape.getRotation());
            object.setMaterial(material);
            shapes.add(object);
        }

        return shapes;
    }
}
