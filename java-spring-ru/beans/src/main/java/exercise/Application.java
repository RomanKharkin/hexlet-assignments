package exercise;

import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.time.LocalTime;

import exercise.daytime.Daytime;
import exercise.daytime.Day;
import exercise.daytime.Night;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

// BEGIN
@Configuration
// END

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
        @Bean
        public Day day() {
            return new Day();
        }

        @Bean
        public Night night() {
            return new Night();
        }

        @Bean
        @Primary
//        @Getter
        public Daytime getName() {
            LocalTime now = LocalTime.now();
            if (now.isAfter(LocalTime.of(6, 0)) && now.isBefore(LocalTime.of(22, 0))) {
                return day();
            } else {
                return night();
            }
        }
    // END
}
