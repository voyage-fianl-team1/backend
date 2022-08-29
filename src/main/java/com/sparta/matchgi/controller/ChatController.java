package com.sparta.matchgi.controller;


import com.sparta.matchgi.dto.SendChatRequestDto;
import com.sparta.matchgi.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {


    private final ChatService chatService;

    @MessageMapping("/{roomId}")
    @SendTo("/room/{roomId}")
    public ResponseEntity<?> sendChat(@DestinationVariable Long roomId, SendChatRequestDto sendChatRequestDto, @Header("accessToken") String token){

        return chatService.sendChat(roomId,sendChatRequestDto,token);

    }

    @GetMapping("/api/rooms/{roomId}/chats")
    public ResponseEntity<?> showChats(@PathVariable Long roomId, @RequestParam(required = false,defaultValue = "-1") Long lastChat, @RequestParam int limit){
        return chatService.showChats(roomId,lastChat,limit);

    }


}
