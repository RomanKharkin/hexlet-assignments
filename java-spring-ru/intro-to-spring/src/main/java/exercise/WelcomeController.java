package exercise;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// BEGIN
@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return String.format("Welcome to Spring");
    }

    @GetMapping("/hello")
    public String helllo(@RequestParam(value = "name", defaultValue = "World") String name) {
        String result = "Hello, " + name + "!";
        return String.format(result);
    }
}
// END
