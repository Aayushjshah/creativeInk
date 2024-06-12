import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { CommentService } from 'src/app/service/comment.service';
import { PostService } from 'src/app/service/post.service';

@Component({
  selector: 'app-view-post',
  templateUrl: './view-post.component.html',
  styleUrls: ['./view-post.component.scss']
})
export class ViewPostComponent {
  postId = this.activatedRoute.snapshot.params[`id`];
  postData : any;
  commentForm!: FormGroup;
  comments : any;

  constructor(private postService : PostService,
    private snackBar : MatSnackBar,
    private activatedRoute : ActivatedRoute, //rovides access to information about a route associated with a component that is loaded in an outlet. Use to traverse the RouterState tree and extract information from nodes
    private fb:FormBuilder,
    private commentService : CommentService
  ){}


  ngOnInit(){
    console.log(this.postId);
    this.getPostById();
    this.commentForm = this.fb.group({
      postedBy : [null , Validators.required],
      content : [null , [Validators.required , Validators.maxLength(500)]]
    })
  }

//post Functions
  getPostById(){
    this.postService.getPostById(this.postId).subscribe(res =>{
      this.postData = res;
      console.log(this.postData);
      this.getCommentsByPost();
    },error => {
      this.snackBar.open("Something went wrong","ok")
    })
  }

  likePost(){
    this.postService.likePost(this.postId).subscribe(res =>{      
      this.snackBar.open("Post Like Sucessfully","ok");
      this.getPostById();
    },error => {
      this.snackBar.open("Something went wrong","ok")
    })
  }
//Comment Functions

  publishComment(){
    // const data = this.commentForm.value;
    const postedBy = this.commentForm.get('postedBy')?.value;
    const content = this.commentForm.get('content')?.value;

    this.commentService.createComment(this.postId,postedBy,content).subscribe(res =>{
      this.snackBar.open("Comment Published Sucessfully","ok");
      this.getPostById();
      this.getCommentsByPost();
    },error=>{
      this.snackBar.open("Something went wrong","ok");
    })
  }


  getCommentsByPost(){
    this.commentService.getAllCommentsByPost(this.postId).subscribe(res=>{
      this.comments=res;
    },error=>{
      this.snackBar.open("Something went wrong","ok");
    })
  }
}