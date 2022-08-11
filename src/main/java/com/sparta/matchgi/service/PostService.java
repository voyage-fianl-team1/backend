package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.dto.ImagePathDto;
import com.sparta.matchgi.dto.RevisePostRequetDto;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.repository.ImageRepository;
import com.sparta.matchgi.repository.ImgUrlRepository;

import com.sparta.matchgi.repository.PostRepository;
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

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final ImageService imageService;
    private final ImgUrlRepository imgUrlRepository;
    private final AmazonS3 amazonS3;




    @Value("${S3.bucket.name}")
    private String bucket;


    public ResponseEntity<?> createPost(
            CreatePostRequestDto createPostRequestDto,List<MultipartFile> file, UserDetailsImpl userDetails)
            throws IOException
    {
        Post post = new Post(createPostRequestDto, userDetails);
        postRepository.save(post);

        List<ImagePathDto> imagePathDtoList=new ArrayList<>();
        for(MultipartFile filed:file){
            ImagePathDto imagePathDto=update(filed); //파일을 하나씩 s3에 업데이트
            imagePathDtoList.add(imagePathDto); //파일을 하나씩 db에 업데이트
        }

        for (ImagePathDto imagePathdto : imagePathDtoList) {
            ImgUrl imgUrl = new ImgUrl(post, imagePathdto.getPath());
            post.addImgUrl(imgUrl); //imgurl 따로 post에 저장
        }//포스트에 이미지url만 저장

        CreatePostResponseDto createPostResponseDto = DtoConverter.PostToCreateResponseDto(post);

        return new ResponseEntity<>(createPostResponseDto, HttpStatus.valueOf(201));
    }

    public ResponseEntity<?> editPost(
            Long postId, RevisePostRequetDto revisePostRequetDto, List<MultipartFile> file, UserDetailsImpl userDetails)
            throws IOException {

        //---------------------포스트 내용 수정--------------------------
        Post post=postRepository.findPostById(postId);
        if(!userDetails.getUser().getId().equals(post.getUser().getId()))
            throw new IllegalArgumentException("수정 권한이 없습니다.");

        post.editPost(revisePostRequetDto,userDetails);//콘텐츠는 마지막에 이미지까지 한꺼번에 받자 수정 완료!
        postRepository.save(post);

        //---------------------이미지 파일 수정-----------------------------
        List<ImagePathDto> deleteList=revisePostRequetDto.getDeleteImages();

        if(file==null)//파일이 비어있으면
        {

            for (ImagePathDto listed : deleteList) {
                if (!listed.getPath().equals("-1")) {//deleteimages가 -1이 아니면 지우고, -1이면 안지움
                    deleteImages(listed);
                }
                post.addImgUrl(null);
            }
        }
        else {//파일이 존재하면
            for (ImagePathDto listed : deleteList) {
                if (!listed.getPath().equals("-1")) {//그리고 지울 이미지도 존재하면 deleteimages가 -1이 아니면 지우고, -1이면 안지움
                    deleteImages(listed);
                }
                post.addImgUrl(null);
            }

            List<ImagePathDto> imagePathDtoList=new ArrayList<>();
            for(MultipartFile filed:file){
                ImagePathDto imagePathDto=update(filed); //파일을 하나씩 s3에 업데이트
                imagePathDtoList.add(imagePathDto); //파일을 하나씩 db에 업데이트
            }

            for (ImagePathDto imagePathdto : imagePathDtoList) {
                ImgUrl imgUrl = new ImgUrl(post, imagePathdto.getPath());
                post.addImgUrl(imgUrl); //imgurl 따로 post에 저장
            }//포스트에 이미지url만 저장

        }

        return new ResponseEntity<>(HttpStatus.valueOf(201));

    }


    public ResponseEntity<?> deletePost(Long postId, UserDetailsImpl userDetails){
        Post post=postRepository.findById(postId)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        if(!userDetails.getUser().getEmail().equals(post.getUser().getEmail())){
            throw new IllegalArgumentException("접근 권한이 없는 사용자입니다.");
        }
        List<ImgUrl> imageList=imgUrlRepository.findByPostId(postId);
        List<ImagePathDto> pathDtoList=new ArrayList<>();
        for(ImgUrl imgurl:imageList){
            ImagePathDto path=imgurl.getImagePathDto();
            deleteImages(path);
        }
        postRepository.deleteById(postId);

        return new ResponseEntity<>(HttpStatus.valueOf(201));

    }

    public CreatePostResponseDto getPost(Long postId){
        Post post=postRepository.findById(postId)
                .orElseThrow( () -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
        List<ImgUrl> imageList=imgUrlRepository.findByPostId(postId);
        List<ImagePathDto> pathDtoList=new ArrayList<>();
        for(ImgUrl imgurl:imageList){
            ImagePathDto path=imgurl.getImagePathDto();
            pathDtoList.add(path);
        }

        return new CreatePostResponseDto(post,pathDtoList);



    }


    //하나씩 업데이트
    public ImagePathDto update(MultipartFile file) throws IOException {

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            PutObjectRequest por = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(por);

            ImagePathDto imagePathDto = new ImagePathDto(filename);

        return imagePathDto;
    }

    //하나씩 지우기
    public void deleteImages(ImagePathDto filePaths) {
            amazonS3.deleteObject(bucket,filePaths.getPath());

    }
}