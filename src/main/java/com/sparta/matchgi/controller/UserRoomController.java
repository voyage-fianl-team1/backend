package com.sparta.matchgi.controller;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.GetRoomUserResponseDto;
import com.sparta.matchgi.dto.ShowRoomResponseDto;
import com.sparta.matchgi.service.UserRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserRoomController {

    private final UserRoomService userRoomService;


    @GetMapping("/api/users/rooms")
    public ResponseEntity<List<ShowRoomResponseDto>> showUserRoom(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userRoomService.showUserRoom(userDetails);
    }

    @PutMapping("/api/room/{roomId}/lastActive")
    public ResponseEntity<?> updateLastActive(@PathVariable Long roomId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userRoomService.updateLastActive(roomId,userDetails);
    }

    @GetMapping("/api/users/rooms/{roomId}/userList")
    public ResponseEntity<List<GetRoomUserResponseDto>> getRoomUserList(@PathVariable Long roomId){
        return userRoomService.getRoomUserList(roomId);
    }
}