package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.*;
import com.sparta.matchgi.service.PostService;
import com.sparta.matchgi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @PutMapping("/api/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return userService.refreshToken(request);
    }

    @PostMapping("/api/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.registerUser(signupRequestDto);
    }

    //닉네임 변경
    @PutMapping("/api/users")
    public ResponseEntity<ReviseUserResponseDto> reviseUser(
            @RequestBody ReviseUserRequestDto reviseUserRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return userService.reviseUser(reviseUserRequestDto, userDetails);
    }

    //비밀번호 변경
    @PutMapping("/api/users/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.changePassword(changePasswordDto, userDetails);
    }

    @GetMapping("/api/users/rank")
    public ResponseEntity<?> personalRanking(@RequestParam int page, @RequestParam int size, @RequestParam String subject) {
        return userService.personalRanking(page, size, subject);
    }


    //내 경기 리스트
    @GetMapping("/api/users/requests")
    public ResponseEntity<MyMatchResponseDto> getMyMatch(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMyMatch(userDetails);
    }

    //내 게시글
    @GetMapping("/api/users/posts")
    public ResponseEntity<MyPostResponseDto> getMyPost(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMyPost(userDetails);
    }

    //마이페이지
    @GetMapping("/api/users")
    public ResponseEntity<MyPageResponseDto> getMyPage(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getMyPage(userDetails);
    }

    @PutMapping("/api/images/users")
    public ResponseEntity<?> putMyImage(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestPart MultipartFile file) throws IOException {
        return userService.putMyImage(userDetails, file);
    }

    @GetMapping("/api/users/{userId}/ranking")
    public ResponseEntity<?> getScores(@RequestParam String subject,@PathVariable Long userId){
        return userService.getScores(subject,userId);
    }


}
