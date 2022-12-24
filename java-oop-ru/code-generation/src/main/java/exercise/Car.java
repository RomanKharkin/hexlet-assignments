package exercise;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

// BEGIN
@Getter
@AllArgsConstructor
@Value
@Builder
// END
class Car {
    int id;
    String brand;
    String model;
    String color;
    User owner;

    // BEGIN
//    private static ObjectMapper mapper;
    public String serialize() {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString= "";
        try {
            jsonString = mapper.writeValueAsString(toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return jsonString;
    }

    public static Car unserialize(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        Car car;
        try {
            car = mapper.readValue(jsonString, Car.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return car;
    }
    // END
}
