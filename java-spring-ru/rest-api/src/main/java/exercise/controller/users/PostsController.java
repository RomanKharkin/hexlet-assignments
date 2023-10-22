package exercise.controller.users;

import exercise.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// BEGIN
@RestController
@RequestMapping("/api/users")
public class PostsController {
    private List<Post> posts = new ArrayList<>();

    @GetMapping("/{id}/posts") // Вывод страниц
    @ResponseStatus(HttpStatus.OK)
    public List<Post> show(@PathVariable int id) {
        return posts.stream()
                .filter(p -> (p.getUserId() == id))
                .toList();
    }

    @PostMapping("/{userId}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post newPost, @PathVariable int userId) {
        var post = new Post(userId, newPost.getSlug(), newPost.getTitle(), newPost.getBody());
        this.posts.add(post);
        return post;
    }
}
// END
