package exercise.controller;

import java.net.URI;
import java.util.List;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BooksController {
    @Autowired
    private BookService bookService;

    // BEGIN
    @GetMapping("")
    ResponseEntity<List<BookDTO>> index() {
        var books = bookService.getAll();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(books.size()))
                .body(books);
    }

    @GetMapping(path = "/{id}")
    ResponseEntity<BookDTO> show(@PathVariable long id) {

        var book = bookService.findById(id);

        return ResponseEntity.ok()
//                .header("AuthorFirstName", String.valueOf(book.getAuthorFirstName()))
                .body(book);
    }

    @PostMapping(path = "")
    ResponseEntity<BookDTO> create(@Valid @RequestBody BookCreateDTO bookData) {

        var bookDTO = bookService.create(bookData);

        return ResponseEntity.created(URI.create("/" + bookDTO.getId()))
                .body(bookDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<BookDTO> update(@RequestBody @Valid BookUpdateDTO bookData, @PathVariable Long id) {
        var bookDTO = bookService.update(bookData, id);

        return ResponseEntity.ok()
                .body(bookDTO);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity delete(@PathVariable long id) {

        bookService.delete(id);

        return ResponseEntity.ok().build();
    }
    // END
}
