package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.dto.ImagePathDto;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.repository.PostRepository;
import com.sparta.matchgi.util.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public ResponseEntity<?> createPost(CreatePostRequestDto createPostRequestDto, UserDetailsImpl userDetails) {

        Post post = new Post(createPostRequestDto,userDetails);

        postRepository.save(post);

        List<ImagePathDto> imagePathDtoList = createPostRequestDto.getImages();

        for(ImagePathDto imagePathDto:imagePathDtoList){
            ImgUrl imgUrl = new ImgUrl(post,imagePathDto.getPath());
            post.addImgUrl(imgUrl);
        }

        CreatePostResponseDto createPostResponseDto = DtoConverter.PostToCreateResponseDto(post);

        return new ResponseEntity<>(createPostResponseDto, HttpStatus.valueOf(201));
    }

}
