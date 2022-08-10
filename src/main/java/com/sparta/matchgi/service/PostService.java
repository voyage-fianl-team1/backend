package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.repository.PostRepository;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.dto.ImagePathDto;
import com.sparta.matchgi.util.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.sparta.matchgi.util.ImageChange.upload;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final ImageService imageService;

    private final AmazonS3 amazonS3;



    @Value("${S3.bucket.name}")
    private String bucket;


    public ResponseEntity<?> createPost(CreatePostRequestDto createPostRequestDto, MultipartFile file, UserDetailsImpl userDetails) throws IOException {

        Post post = new Post(createPostRequestDto, userDetails);

        postRepository.save(post);


        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        PutObjectRequest por = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(por);

        ImagePathDto imagePathDto = new ImagePathDto(filename);


        List<ImagePathDto> imagePathDtoList=new ArrayList<>();
        imagePathDtoList.add(imagePathDto);
        // createPostRequestDto.getImages(); //얘가 널이다

        for (ImagePathDto imagePathdto : imagePathDtoList) {
            ImgUrl imgUrl = new ImgUrl(post, imagePathdto.getPath());
            post.addImgUrl(imgUrl);
        }

        CreatePostResponseDto createPostResponseDto = DtoConverter.PostToCreateResponseDto(post);

        return new ResponseEntity<>(createPostResponseDto, HttpStatus.valueOf(201));
    }

//    public ResponseEntity<?> editPost(Long postId,CreatePostRequestDto requestDto,UserDetailsImpl userDetails){
//
//        Post post=postRepository.findPostById(postId);
//        if(!userDetails.getUser().getId().equals(post.getUser().getId()))
//            throw new IllegalArgumentException("수정 권한이 없습니다.");
//
//    }
}