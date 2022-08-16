package com.sparta.matchgi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.matchgi.dto.ImagePathDto;
import com.sparta.matchgi.model.ImgUrl;
import com.sparta.matchgi.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

    private final AmazonS3 amazonS3;



    @Value("${S3.bucket.name}")
    private String bucket;

    public ResponseEntity<?> upload(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        PutObjectRequest por = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(por);

        ImagePathDto imagePathDto = new ImagePathDto(filename);

        return new ResponseEntity<>(imagePathDto, HttpStatus.valueOf(201));

    }

    public ResponseEntity<?> deleteImages(List<ImagePathDto> filePaths) {
        for(ImagePathDto imagePathDto:filePaths){
            amazonS3.deleteObject(bucket,imagePathDto.getPath());
        }

        return new ResponseEntity<>(filePaths, HttpStatus.valueOf(200));
    }
}
