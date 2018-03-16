package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static String mapperJson = "";

    public static void save(ArrayList<Shape3D> shapes) {

        List<SceneShape> sceneShapeList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();


        //   if (!(shapes == null) || (shapes.size() == 0)) {

        for (Shape3D shape : shapes) {

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

            sceneShapeList.add(new SceneShape(color));

        }

        try {
            PrintWriter writer = new PrintWriter("src/main/resources/scene.json", "UTF-8");
            mapper.writeValue(writer , sceneShapeList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Shape3D> open () {

        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Shape3D> shapes = new ArrayList<>();

        ArrayList<SceneShape> sceneShapeList = new ArrayList<>();

        TypeFactory typeFactory = mapper.getTypeFactory();
        JavaType sceneShapeType = typeFactory.constructParametricType(ArrayList.class, SceneShape.class);

//        try {
//            sceneShapeList = mapper.readValue(new File("src/main/resources/scene.json"), sceneShapeType);
//            System.out.println(sceneShapeList);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        for (SceneShape shape : sceneShapeList) {

            String color = "WHITE";

            Shape3D object = new Sphere(100);

            PhongMaterial material = new PhongMaterial();

            if(shape.getColor() == "BLACK") {
                material.setDiffuseColor(Color.BLACK);
            } else if(shape.getColor() == "GREEN") {
                material.setDiffuseColor(Color.BLACK);
            } else if(shape.getColor() == "RED") {
                material.setDiffuseColor(Color.BLACK);
            } else if(shape.getColor() == "YELLOW") {
                material.setDiffuseColor(Color.BLACK);
            } else if(shape.getColor() == "BROWN") {
                material.setDiffuseColor(Color.BLACK);
            }

            object.setMaterial(material);

            shapes.add(object);
        }

        return shapes;
    }
}
