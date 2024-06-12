import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { PostService } from 'src/app/service/post.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.scss']
})
export class CreatePostComponent {

  postForm!: FormGroup;
  tags:String[] = [];

  constructor(private fb:FormBuilder,
    private router:Router, //A service that provides navigation among views and URL manipulation capabilities
    private snackBar:MatSnackBar, // toDisplat messages
    private postService : PostService,
  ){}

  //define Controls for the form
  ngOnInit(){
    this.postForm = this.fb.group({
      name : [null , Validators.required],
      content: [null,[Validators.required , Validators.maxLength(5000)]],
      image: [null,Validators.required],
      postedBy: [null,Validators.required]
    })
  }

  add(event : any){
    const value = (event.value || '').trim();
    if(value){
      this.tags.push(value);
    }
    event.chipInput!.clear();
  }

  remove(tag : any){
    const index = this.tags.indexOf(tag);
    if(index >=0){
      this.tags.splice(index,1);
    }
  }

  createPost(){
    const data = this.postForm.value;
    data.tags = this.tags;
    console.log(data);

    this.postService.createNewPost(data).subscribe(res=>{
      this.snackBar.open("Post Created Sucessfully","Ok");
      this.router.navigateByUrl("/view-all"); //to home page
    }, error =>{
      this.snackBar.open("Something went wrong","ok");
    })
  }
}
