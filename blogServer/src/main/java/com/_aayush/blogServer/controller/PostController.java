package com._aayush.blogServer.controller;

import com._aayush.blogServer.entity.Post;
import com._aayush.blogServer.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // because we will be creating API's
@RequestMapping("/api/post")
@CrossOrigin(origins = "*") //allow api calls from all origins
public class PostController {
     @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post){
        try{
            //create a post obj
            Post createdPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        try{
            return ResponseEntity.status((HttpStatus.OK)).body(postService.getAllPosts());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable long postId){
        try{
            Post post = postService.getPostById(postId);
//            return ResponseEntity.status(HttpStatus.FOUND).body(post);
            return ResponseEntity.ok(post);
        }catch(EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable long postId){
        try{
            postService.likePost(postId);
            return ResponseEntity.ok(new String[]{"Post liked Sucessfully"});
        }catch(EntityNotFoundException e){
            return ResponseEntity.status((HttpStatus.NOT_FOUND)).body(e.getMessage());
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchByName(@PathVariable String name){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.searchByName(name));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
