package exercise.service;

import exercise.model.User;
import exercise.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    // BEGIN
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> createUser(User user) {
        // Логика создания нового пользователя
        return userRepository.save(user);
    }

    public Mono<User> getUserById(long userId) {
        // Логика получения пользователя по идентификатору
        return userRepository.findById(userId);
    }

    public Mono<User> updateUser(long userId, User updatedUser) {
        // Логика обновления данных пользователя
        return userRepository.findById(userId)
                .flatMap(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setEmail(updatedUser.getEmail());
                    return userRepository.save(existingUser);
                });
    }

    public Mono<Void> deleteUser(long userId) {
        // Логика удаления пользователя
        return userRepository.deleteById(userId);
    }
    // END
}
