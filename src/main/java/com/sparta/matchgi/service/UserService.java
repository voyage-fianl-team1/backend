package com.sparta.matchgi.service;

import com.sparta.matchgi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    public ResponseEntity<?> registerUser(SignupRequestDto signupRequestDto) {
        String nickname = signupRequestDto.getNickname();
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();

        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("중복된 이메일이 존재합니다", HttpStatus.valueOf(400));
        }

        User user = new User(nickname,email, passwordEncoder.encode(password));

        userRepository.save(user);


        return new ResponseEntity<>("회원가입에 성공했습니다",HttpStatus.valueOf(200));

    }
}