package Connectify.controllers;

import Connectify.Servcies.PostService;
import Connectify.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    //Admin Only
    @GetMapping
    ResponseEntity<List<PostDTO>> getAllPost() {

        return ResponseEntity.ok(postService.getAllPost());

    }

    @GetMapping("/myposts")
    ResponseEntity<List<PostDTO>> getMyPosts() {


        return ResponseEntity.ok(postService.getAllPostOfLoggedInUser());
    }

    @PostMapping
    ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {

        return ResponseEntity.ok(postService.createPost(postDTO));
    }
}
