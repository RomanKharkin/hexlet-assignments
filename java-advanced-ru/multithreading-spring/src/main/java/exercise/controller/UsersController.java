package exercise.controller;

import exercise.model.User;
import exercise.controller.UsersController;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import exercise.service.UserService;


@RestController
@RequestMapping("/users")
public class UsersController {

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private UserService userService;

    @GetMapping(path = "")
    public Flux<User> getUsers() {
        return userService.findAll();
    }

    // BEGIN
    @RestController
    @RequestMapping("/users")
    public class UserController {

        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @PostMapping
        public Mono<User> createUser(@RequestBody User user) {
            return userService.createUser(user);
        }

        @GetMapping("/{userId}")
        public Mono<User> getUser(@PathVariable long userId) {
            return userService.getUserById(userId);
        }

        @PutMapping("/{userId}")
        public Mono<User> updateUser(@PathVariable long userId, @RequestBody User updatedUser) {
            return userService.updateUser(userId, updatedUser);
        }

        @DeleteMapping("/{userId}")
        public Mono<Void> deleteUser(@PathVariable long userId) {
            return userService.deleteUser(userId);
        }
    }

    @PatchMapping("/{userId}")
    public Mono<User> updateUser(@PathVariable long userId, @RequestBody User updatedUser) {
        return userService.updateUser(userId, updatedUser);
    }
    // END
}
