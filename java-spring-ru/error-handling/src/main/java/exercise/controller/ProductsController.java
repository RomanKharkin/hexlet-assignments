package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/products")
    public List<Product> index() {
        return productRepository.findAll();
    }

    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // BEGIN
    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    Product show(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
    }

    @PutMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable Long id, @RequestBody Product data) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));
        product.setPrice(data.getPrice());
        product.setTitle(data.getTitle());
        return productRepository.save(product);
    }

    // END

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}
