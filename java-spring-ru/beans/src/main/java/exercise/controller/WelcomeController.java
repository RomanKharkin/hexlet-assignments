package exercise.controller;

import exercise.daytime.Day;
import exercise.daytime.Daytime;
import exercise.daytime.Night;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

// BEGIN
@RestController
public class WelcomeController {

    private final Daytime daytime;

    @Autowired
    public WelcomeController(Daytime daytime) {
        this.daytime = daytime;
    }

    @GetMapping("/welcome")
    public String welcomeMessage() {
        if (daytime.getName().equals("Day")) {
            return "It is day now! Welcome to Spring!";
        } else {
            return "It is night now! Welcome to Spring!";
        }
    }
}
// END
