package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.ChangePasswordDto;
import com.sparta.matchgi.dto.ReviseUserRequestDto;
import com.sparta.matchgi.dto.ReviseUserResponseDto;
import com.sparta.matchgi.dto.SignupRequestDto;
import com.sparta.matchgi.model.ProfileImg;
import com.sparta.matchgi.model.User;
import com.sparta.matchgi.repository.ProfileImgRepository;
import com.sparta.matchgi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final ProfileImgRepository profileImgRepository;
    private final AmazonS3 amazonS3;

    @Value("${S3.bucket.name}")
    private String bucket;

    public ResponseEntity<?> registerUser(SignupRequestDto signupRequestDto) {
        String nickname = signupRequestDto.getNickname();
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();

        //기본 이미지
        String imageUrl = "e74929fc-ee95-461c-a50b-7b2b34c9b71e_3636_10174_4958.jpg";

        if (userRepository.findByEmail(email).isPresent()) {
            return new ResponseEntity<>("중복된 이메일이 존재합니다", HttpStatus.valueOf(400));

        }

        User user = new User(nickname, email, passwordEncoder.encode(password));

        ProfileImg profileImg = new ProfileImg(user, imageUrl);
        userRepository.save(user);

        //기본 이미지 저장
        profileImgRepository.save(profileImg);

        return new ResponseEntity<>("회원가입에 성공했습니다", HttpStatus.valueOf(200));

    }

    public ResponseEntity<ReviseUserResponseDto> reviseUser(ReviseUserRequestDto reviseUserRequestDto,
                                                            MultipartFile file,
                                                            UserDetailsImpl userDetails) throws IOException {
        User user = userDetails.getUser();

        if (userRepository.existsByNickname(reviseUserRequestDto.getNickname())){
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        //유저의 프로필이미지 찾기
        Optional<ProfileImg> profileImg = profileImgRepository.findByUser(user);
        if (!profileImg.isPresent()){
            throw new IllegalArgumentException("없는 유저입니다.");
        }

        if(file == null){

            if(!reviseUserRequestDto.getDeleteImage().equals("-1")){
                amazonS3.deleteObject(bucket, reviseUserRequestDto.getDeleteImage());
            }
            user.updateNick(reviseUserRequestDto);

            userRepository.save(user);

            return new ResponseEntity<>(new ReviseUserResponseDto(null), HttpStatus.valueOf(200));

        }else{
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            PutObjectRequest por = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(por);

            if(!reviseUserRequestDto.getDeleteImage().equals("-1")){
                amazonS3.deleteObject(bucket, reviseUserRequestDto.getDeleteImage());
            }
            //이미지 삭제

            //닉네임 변경
            user.updateNick(reviseUserRequestDto);

            userRepository.save(user);

            //path 수정
            profileImg.get().update(filename);

            return new ResponseEntity<>(new ReviseUserResponseDto(filename), HttpStatus.valueOf(200));
        }

        //이미지 올리기


    }
//1
    public ResponseEntity<?> changePassword(ChangePasswordDto changePasswordDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        if (passwordEncoder.matches(changePasswordDto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("같은 비밀번호입니다.");
        }

        user.changePassword(passwordEncoder.encode(changePasswordDto.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("비밀번호가 변경되었습니다.", HttpStatus.valueOf(200));
    }

}

