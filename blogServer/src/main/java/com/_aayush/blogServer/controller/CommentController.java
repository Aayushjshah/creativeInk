package com._aayush.blogServer.controller;

import com._aayush.blogServer.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@CrossOrigin(origins = "*") //allow api calls from all origins
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("comments/create")
    public ResponseEntity<?> createComment(@RequestParam long postId ,@RequestParam String postedBy , @RequestBody String content){
        try{
            return ResponseEntity.ok(commentService.createComment(postId,postedBy,content));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }
    @GetMapping("comments/{postId}")
    public  ResponseEntity<?> getCommentsByPostId(@PathVariable long postId){
        try{
            return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
