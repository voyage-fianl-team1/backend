package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.auth.jwt.HeaderTokenExtractor;
import com.sparta.matchgi.auth.jwt.JwtDecoder;
import com.sparta.matchgi.auth.jwt.JwtTokenUtils;
import com.sparta.matchgi.dto.*;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final PostRepository postRepository;

    private final RequestRepository requestRepository;

    private final AmazonS3 amazonS3;

    private final ScoreRepository scoreRepository;

    private final JwtDecoder jwtDecoder;

    private final HeaderTokenExtractor extractor;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${S3.bucket.name}")
    private String bucket;

    public ResponseEntity<?> registerUser(SignupRequestDto signupRequestDto) {
        String nickname = signupRequestDto.getNickname();
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();

        if (userRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("중복된 이메일이 존재합니다", HttpStatus.valueOf(400));
        }

        User user = new User(nickname, email, passwordEncoder.encode(password));
        userRepository.save(user);

        return new ResponseEntity<>("회원가입에 성공했습니다", HttpStatus.valueOf(200));

    }

    public ResponseEntity<ReviseUserResponseDto> reviseUser(ReviseUserRequestDto reviseUserRequestDto,
                                                            UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        if (userRepository.existsByNickname(reviseUserRequestDto.getNickname())) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        //닉네임 변경
        user.updateNickAndprofileImageUrl(reviseUserRequestDto);

        userRepository.save(user);

        return new ResponseEntity<>(new ReviseUserResponseDto(), HttpStatus.valueOf(200));

    }

    //비밀번호 변경
    public ResponseEntity<?> changePassword(ChangePasswordDto changePasswordDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        if (passwordEncoder.matches(changePasswordDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("같은 비밀번호입니다.");
        }

        user.changePassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.valueOf(200));
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request) {

        String accessToken = extractor.extract(request.getHeader("Authorization"),request);

        String refreshToken = request.getHeader("refreshToken");

        String email = jwtDecoder.decodeEmail(accessToken);

        Optional<RefreshToken> refreshTokenFound = refreshTokenRepository.findByEmail(email);

        if(refreshTokenFound.isPresent()){
            if(refreshTokenFound.get().getRefreshToken().equals(refreshToken)){
                if(jwtDecoder.isExpiredToken(refreshToken)){
                    return new ResponseEntity<>("토큰 유효기간이 만료되었습니다. 다시 로그인 해주세요",HttpStatus.valueOf(401));
                }else{
                    accessToken = JwtTokenUtils.generateJwtToken(email);
                    refreshToken = JwtTokenUtils.generateJwtRefreshToken();
                    refreshTokenFound.get().updateRefreshToken(refreshToken);
                }
            }
            else{
                return new ResponseEntity<>("유효한 토큰이 아닙니다",HttpStatus.valueOf(401));
            }
        }else{
            return new ResponseEntity<>("유효한 토큰이 아닙니다",HttpStatus.valueOf(401));
        }

        return new ResponseEntity<>(new RefreshResponseDto(accessToken,refreshToken),HttpStatus.valueOf(200));
    }
    //나의 경기
    public ResponseEntity<MyMatchResponseDto> getMyMatch(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Request> requests = requestRepository.findAllByUser(user);
        List<MyMatchDetailResponseDto> myMatchDetailResponseDtos = new ArrayList<>();

        for (Request request : requests) {
            MyMatchDetailResponseDto myMatchDetailResponseDto = MyMatchDetailResponseDto.builder()
                    .id(request.getPost().getId())
                    .title(request.getPost().getTitle())
                    .subject(request.getPost().getSubject())
                    .matchStatus(request.getPost().getMatchStatus())
                    .build();
            myMatchDetailResponseDtos.add(myMatchDetailResponseDto);
        }

        return new ResponseEntity<>(new MyMatchResponseDto(myMatchDetailResponseDtos), HttpStatus.valueOf(200));
    }
//
    //나의 게시글
    public ResponseEntity<MyPostResponseDto> getMyPost(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Post> posts = postRepository.findAllByUser(user);
        List<MyPostDetailResponseDto> myPostDetailResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            MyPostDetailResponseDto myPostDetailResponseDto = MyPostDetailResponseDto.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .subject(post.getSubject())
                    .build();
            myPostDetailResponseDtos.add(myPostDetailResponseDto);
        }

        return new ResponseEntity<>(new MyPostResponseDto(myPostDetailResponseDtos), HttpStatus.valueOf(200));
    }

    public ResponseEntity<MyPageResponseDto> getMyPage(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return new ResponseEntity<>(userRepository.myRanking(user), HttpStatus.valueOf(200));

    }

}

