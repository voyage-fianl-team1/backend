package com.sparta.matchgi.util.Image;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.matchgi.dto.ImagePathDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Image {

    private final AmazonS3 amazonS3;

    @Value("${S3.bucket.name}")
    private String bucket;

    public String upload(MultipartFile file) throws IOException {
        String filename = UUID.randomUUID()+"_"+file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        PutObjectRequest por = new PutObjectRequest(bucket, filename, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);
        amazonS3.putObject(por);

        return filename;

    }

    public void deleteImages(List<ImagePathDto> filePaths) {
        for(ImagePathDto imagePathDto:filePaths){
            amazonS3.deleteObject(bucket,imagePathDto.getPath());
        }
    }
}
