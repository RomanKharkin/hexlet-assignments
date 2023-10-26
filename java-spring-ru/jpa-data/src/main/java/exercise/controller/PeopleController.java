package exercise.controller;

import exercise.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import exercise.model.Person;

@RestController
//@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping(path = "/people/{id}")
    public Person show(@PathVariable long id) {
        return personRepository.findById(id).get();
    }

    // BEGIN
    @GetMapping("/people")
    public List<Person> index() {
        return personRepository.findAll();
    }

    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    Person create(@RequestBody Person person) {
        personRepository.save(person);
        return person;
    }

    @DeleteMapping("/people/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void destroy(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
    // END
}
