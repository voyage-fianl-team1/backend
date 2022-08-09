package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequestDto createPostRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(createPostRequestDto,userDetails);
    }


}
