package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.dto.RevisePostRequetDto;
import com.sparta.matchgi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    @PostMapping("/api/posts")
    public ResponseEntity<?> createPost(@RequestPart CreatePostRequestDto createPostRequestDto, @RequestPart(required = false) List<MultipartFile> file, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(createPostRequestDto, file, userDetails);
    }

    @PutMapping("/api/posts/{postId}")
    public ResponseEntity<?> editPost(@PathVariable Long postId, @RequestPart(required = false) List<MultipartFile> file, @RequestPart RevisePostRequetDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        return postService.editPost(postId, requestDto, file, userDetails);
    }

    @DeleteMapping("/api/posts/{postId}")
    public void deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails);
    }

    @GetMapping("/api/posts/{postId}")
    public CreatePostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);

    }


}

