package playground;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class jsonPlayground {

    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        Animal animal = new Animal(true, 5, "Zeus");

        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(animal);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
