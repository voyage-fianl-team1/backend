package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(@RequestPart CreatePostRequestDto createPostRequestDto, @RequestPart MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(createPostRequestDto,file,userDetails);
    }

//    @PutMapping("api/posts/{postId}")
//    public ResponseEntity<?> editPost(@PathVariable Long postId, @RequestBody CreatePostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        return postService.editPost(postId,requestDto,userDetails);
//    }



}
