package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequestDto createPostRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        System.out.println(createPostRequestDto.getAddress());
        System.out.println(createPostRequestDto.getContent());
        System.out.println(createPostRequestDto.getTitle());
        System.out.println(createPostRequestDto.getLat());
        System.out.println(createPostRequestDto.getLng());
        System.out.println(createPostRequestDto.getSubject());


        return postService.createPost(createPostRequestDto,userDetails);
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<?> editPost(@PathVariable Long postId,@RequestPart MultipartFile file,@RequestPart CreatePostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.editPost(postId,requestDto,file,userDetails);
    }

    @DeleteMapping("/api/posts/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails);
    }

    @GetMapping("/api/posts/{postId}")
    public CreatePostResponseDto getPost(@PathVariable Long postId,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPost(postId,userDetails);

    }


    @PostMapping("/api/images/posts/{postId}")
    public void imageUpload(@PathVariable Long postId,@RequestPart List<MultipartFile> files,UserDetailsImpl userDetails) throws IOException {

        postService.imageUpload(postId,files,userDetails);
    }

    @DeleteMapping("/api/images/posts/{objectKey}")
    public void imageDelete(@PathVariable String objectKey,UserDetailsImpl userDetails){
        postService.imageDelete(objectKey,userDetails);
    }

//    @GetMapping("/api/posts")
//    public ResponseEntity<?> postList(@RequestParam Subject subject, Pageable pageable){
//        return
//    }



}

