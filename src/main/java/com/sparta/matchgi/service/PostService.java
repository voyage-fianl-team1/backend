package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.dto.CreatePostResponseDto;
import com.sparta.matchgi.dto.ImagePathDto;
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
    private final ImageRepository imageRepository;
    private final AmazonS3 amazonS3;




    @Value("${S3.bucket.name}")
    private String bucket;


    public ResponseEntity<?> createPost(
            CreatePostRequestDto createPostRequestDto, MultipartFile file, UserDetailsImpl userDetails)
            throws IOException
    {

        Post post = new Post(createPostRequestDto, userDetails);
        postRepository.save(post);

        ImagePathDto imagePathDto=update(file); //말그대로 String의 image path를 저장
        List<ImagePathDto> imagePathDtoList=new ArrayList<>();
        imagePathDtoList.add(imagePathDto); //imagepath dto에 저장

        for (ImagePathDto imagePathdto : imagePathDtoList) {
            ImgUrl imgUrl = new ImgUrl(post, imagePathdto.getPath());
            post.addImgUrl(imgUrl); //imgurl 따로 post에 저장
        }

        CreatePostResponseDto createPostResponseDto = DtoConverter.PostToCreateResponseDto(post);

        return new ResponseEntity<>(createPostResponseDto, HttpStatus.valueOf(201));
    }

    public ResponseEntity<?> editPost(
            Long postId,CreatePostRequestDto createPostRequestDto,MultipartFile file,UserDetailsImpl userDetails)
            throws IOException {

        Post post=postRepository.findPostById(postId);
        if(!userDetails.getUser().getId().equals(post.getUser().getId()))
            throw new IllegalArgumentException("수정 권한이 없습니다.");

        post.updatePost(createPostRequestDto,userDetails);
        postRepository.save(post);
        //--------------------여기까지 게시물 수정 완료------------------------------------//
        //--------------------이제 이미지 수정하자---------------------------------------//

        if(!file.isEmpty())//업로드한 사진 없으면 그냥 기존사진 쓰기, 근데 비어있지 않으면
        {
            //저장된 이미지 다 불러와서 지우고(현재로써는)(나중에 개별 지우기 구현)
            //새로 업데이트
            List<ImgUrl> imgUrls = imgUrlRepository.findByPostId(postId); //기존 이미지들 다 지우기
            List<ImagePathDto> imagePath=new ArrayList<>();

            for(ImgUrl imgUrl:imgUrls)
            {
                ImagePathDto path=imgUrl.getImagePathDto();
                imagePath.add(path);
                System.out.println(path.getPath()); //path는 잘 받아옴
                deleteImages(imagePath);
                imgUrlRepository.delete(imgUrl);
                System.out.println("삭제 성공!");

            }

            ImagePathDto imagePathDto = update(file);

            List<ImagePathDto> imagePathDtoList=new ArrayList<>();
            imagePathDtoList.add(imagePathDto);

            for (ImagePathDto imagePathdto : imagePathDtoList) {
                ImgUrl imgUrl = new ImgUrl(post, imagePathdto.getPath());
                post.addImgUrl(imgUrl); //db에서 삭제
            }

        }


        CreatePostResponseDto createPostResponseDto = DtoConverter.PostToCreateResponseDto(post);

        return new ResponseEntity<>(createPostResponseDto, HttpStatus.valueOf(201));
    }


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

    public void deleteImages(List<ImagePathDto> filePaths) {
        for(ImagePathDto imagePathDto:filePaths){
            amazonS3.deleteObject(bucket,imagePathDto.getPath());
        }
    }
}