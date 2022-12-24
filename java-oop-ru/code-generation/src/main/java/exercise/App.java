package exercise;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

// BEGIN
class App {

//    private static ObjectMapper mapper;
    public static void save(Path path, Car car) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(String.valueOf(path)), car);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Car extract(Path path) {
        ObjectMapper mapper = new ObjectMapper();
        Car car;
        try {
            car = mapper.readValue(new File(String.valueOf(path)), Car.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return car;
    }


}
// END
