package com.sparta.matchgi.controller;


import com.sparta.matchgi.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/image")
    public ResponseEntity<?> upload(@RequestPart MultipartFile file) throws IOException {
        return imageService.upload(file);
    }

}
