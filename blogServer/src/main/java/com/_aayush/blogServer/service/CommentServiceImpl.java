package com._aayush.blogServer.service;

import com._aayush.blogServer.entity.Comment;
import com._aayush.blogServer.entity.Post;
import com._aayush.blogServer.repository.CommentRepository;
import com._aayush.blogServer.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment createComment(Long postId , String postedBy, String content){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            Comment comment = new Comment();
            comment.setPost(optionalPost.get());
            comment.setPostedBy(postedBy);
            comment.setCreatedAt(new Date());
            comment.setContent(content);
            return commentRepository.save(comment);
        }
        throw new EntityNotFoundException("Post not found");
    }
    public List<Comment> getCommentsByPostId(long postId){
        return commentRepository.findByPostId(postId);
    }

}
