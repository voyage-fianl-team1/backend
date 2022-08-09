package com.sparta.matchgi.controller;


import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.PostRequestDto;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.repository.PostRepository;
import com.sparta.matchgi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostRepository postRepository;

    private final PostService postService;

    @PostMapping("/api/posts")
    public Post createPost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetail){

    }


}
