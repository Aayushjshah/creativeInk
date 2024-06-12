package com._aayush.blogServer.service;

import com._aayush.blogServer.entity.Post;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface PostService {
    Post savePost(Post post);

    List<Post> getAllPosts();
    public Post getPostById(long postId);

    public void likePost(long postId);
    public List<Post> searchByName(String name);

}
