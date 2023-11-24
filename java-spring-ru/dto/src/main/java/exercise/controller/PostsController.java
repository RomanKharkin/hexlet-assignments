package exercise.controller;

import exercise.model.Comment;
import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
//@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostRepository repository;

    @Autowired
    private CommentRepository repositoryComment;

    @GetMapping("/posts")
    List<PostDTO> index() {
        var posts = repository.findAll();
        var result = posts.stream()
                .map(this::toDTO)
                .toList();
        return result;
    }

    @GetMapping("/posts/{id}")
//    @ResponseStatus(HttpStatus.OK)
    PostDTO show(@PathVariable Long id) {
        var post = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
        return toDTO(post);
    }

    // Для удобства и чтобы не дублировать
    private PostDTO toDTO(Post post) {
        var dto = new PostDTO();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setBody(post.getBody());
        dto.setComments(getCommentsByPostId(post.getId()));
        return dto;
    }

    private CommentDTO toCommentDTO(Comment comment) {
        var dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setBody(comment.getBody());
        return dto;
    }


    private List<CommentDTO> getCommentsByPostId(long postId) {
        var comments = repositoryComment.findByPostId(postId);
        var result = comments.stream()
                .map(this::toCommentDTO)
                .toList();
        return result;
    }
}
// END
