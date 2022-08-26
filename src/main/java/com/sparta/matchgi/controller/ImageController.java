package com.sparta.matchgi.controller;


import com.sparta.matchgi.dto.ImagePathDto;
import com.sparta.matchgi.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/image")
    public ResponseEntity<?> upload(@RequestPart MultipartFile file) throws IOException {
        return imageService.upload(file);
    }

    @DeleteMapping("/api/image")
    public ResponseEntity<?> deleteImages(@RequestBody ImagePathDto filePaths) {
        return imageService.deleteImages(filePaths);
    }

}
