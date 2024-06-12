package com._aayush.blogServer.service;

import com._aayush.blogServer.entity.Post;
import com._aayush.blogServer.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService{
//Need to inject postRepository here .will do that using Autowired annotation
    @Autowired
    private PostRepository postRepository;

    //Write a method to save a post
    public Post savePost(Post post){
        //we need to set likeOCunt and viewCount as 0;
        post.setLikeCount(0);;
        post.setViewCount(0);

        System.out.println(post.getImage());
        //set Date
        post.setDate(new Date());
        return postRepository.save(post); //will store in DB //since our Repository is a JPA repository we get save etc methods

    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Post getPostById(long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            post.setViewCount(post.getViewCount()+1);
            return postRepository.save(post);

        }else{
            throw new EntityNotFoundException("Post not found");
        }
    }

    public void likePost(long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            post.setLikeCount(post.getLikeCount()+1);
            postRepository.save(post);
        }else{
            throw new EntityNotFoundException("Post Not Found "+ postId);
        }
    }

    public List<Post> searchByName(String name){
        return postRepository.findAllByNameContaining(name);
    }
}
