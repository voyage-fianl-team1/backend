package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.ParticipationResponseDto;
import com.sparta.matchgi.dto.RequestResponseDto;
import com.sparta.matchgi.dto.UpdateRequestDto;
import com.sparta.matchgi.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/api/posts/{postId}/request")
    public ResponseEntity<RequestResponseDto> registerMatch (@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return requestService.registerMatch(postId,userDetails);
    }

    @PutMapping("/api/requests/{requestId}")
    public ResponseEntity<RequestResponseDto> updateRequest(@PathVariable Long requestId, @RequestBody UpdateRequestDto updateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return requestService.updateRequest(requestId,updateRequestDto,userDetails);
    }

    @GetMapping("/api/posts/{postId}/request")
    public ResponseEntity<ParticipationResponseDto> showParticipationList(@PathVariable Long postId){
        return requestService.showParticipationList(postId);
    }

    @DeleteMapping("/api/posts/{postId}/request")
    public ResponseEntity<?> quitRequest(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return requestService.quitRequest(postId,userDetails);
    }

    @GetMapping("/api/posts/{postId}/request/accept")
    public ResponseEntity<List<RequestResponseDto>> getAcceptRequest(@PathVariable Long postId){
        return requestService.getAcceptRequest(postId);
    }


}
