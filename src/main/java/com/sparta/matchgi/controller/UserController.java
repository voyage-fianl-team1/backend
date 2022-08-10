package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.ChangePasswordDto;
import com.sparta.matchgi.dto.ReviseUserRequestDto;
import com.sparta.matchgi.dto.ReviseUserResponseDto;
import com.sparta.matchgi.dto.SignupRequestDto;
import com.sparta.matchgi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequestDto signupRequestDto){
        ResponseEntity<?>  responseEntity = userService.registerUser(signupRequestDto);

        return responseEntity;
    }

    @PutMapping("/api/users")
    public ResponseEntity<ReviseUserResponseDto> reviseUser(
            @RequestPart("key") ReviseUserRequestDto reviseUserRequestDto, //dto
            @RequestPart(required = false) MultipartFile file,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return userService.reviseUser(reviseUserRequestDto, file, userDetails);
    }

    @PutMapping("/api/users/password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto,
                               @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.changePassword(changePasswordDto, userDetails);
    }





}
