package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.auth.jwt.JwtDecoder;
import com.sparta.matchgi.dto.ChangePasswordDto;
import com.sparta.matchgi.dto.ReviseUserRequestDto;
import com.sparta.matchgi.dto.ReviseUserResponseDto;
import com.sparta.matchgi.dto.SignupRequestDto;
import com.sparta.matchgi.dto.*;
import com.sparta.matchgi.service.PostService;
import com.sparta.matchgi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;


    @PutMapping("/api/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        return userService.refreshToken(request);
    }


    @PostMapping("/api/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto signupRequestDto){
         return userService.registerUser(signupRequestDto);
    }

    //닉네임 변경
    @PutMapping("/api/users")
    public ResponseEntity<ReviseUserResponseDto> reviseUser(
            @RequestPart ReviseUserRequestDto reviseUserRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return userService.reviseUser(reviseUserRequestDto, userDetails);
    }

    //비밀번호 변경
    @PutMapping("/api/users/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.changePassword(changePasswordDto, userDetails);
    }

    //내 경기 리스트
    @GetMapping("/api/users/requests")
    public ResponseEntity<MyMatchResponseDto> getMyMatch(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMyMatch(userDetails);
    }

    @GetMapping("/api/users/posts")
    public ResponseEntity<MyPostResponseDto> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMyPost(userDetails);
    }

    @GetMapping("/api/users")
    public ResponseEntity<MyPageResponseDto> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMyPage(userDetails);
    }





}
