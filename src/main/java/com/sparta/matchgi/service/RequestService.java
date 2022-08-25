package com.sparta.matchgi.service;

import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.ParticipationResponseDto;
import com.sparta.matchgi.dto.RequestResponseDto;
import com.sparta.matchgi.dto.UpdateRequestDto;
import com.sparta.matchgi.model.*;
import com.sparta.matchgi.repository.*;
import com.sparta.matchgi.util.converter.DateConverter;
import com.sparta.matchgi.util.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestService {

    private final PostRepository postRepository;
    private final RequestRepository requestRepository;

    private final ScoreRepository scoreRepository;

    private final NotificationRepository notificationRepository;

    private final RoomRepository roomRepository;

    private final UserRoomRepository userRoomRepository;

    public ResponseEntity<?> registerMatch(Long postId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        Optional<Post> postFound = postRepository.findById(postId);
        if(!postFound.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 포스트입니다.");
        }
        if(postFound.get().getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("본인이 쓴 글에는 참가신청 할 수 없습니다.");
        }
        if(requestRepository.findByUserAndPost(user,postFound.get()).isPresent()){
            throw new IllegalArgumentException("참가신청을 중복으로 할 수 없습니다.");
        }

        Request request = new Request(postFound.get(),user);
        requestRepository.save(request);
        postFound.get().addRequestCount();

        registerMatchAddNotification(user,postFound.get(),request.getRequestStatus());


        RequestResponseDto requestResponseDto = new RequestResponseDto(request.getId(),user.getNickname(),request.getRequestStatus());

        return new ResponseEntity<>(requestResponseDto, HttpStatus.valueOf(201));

    }


    public ResponseEntity<?> updateRequest(Long requestId, UpdateRequestDto updateRequestDto, UserDetailsImpl userDetails) {
        Optional<Request> requestFound = requestRepository.findById(requestId);

        if(!requestFound.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 request입니다");
        }
        if(!requestFound.get().getPost().getUser().getId().equals(userDetails.getUser().getId())){
            throw new IllegalArgumentException("권한이 없습니다.");
        }

        Request request = requestFound.get();

        request.updateStatus(updateRequestDto);

        scoreUpdate(request,updateRequestDto.getStatus());

        UpdateRequestAddNotification(request);

        inviteRoom(request);

        RequestResponseDto requestResponseDto = new RequestResponseDto(request.getId(),request.getUser().getNickname(),request.getRequestStatus());

        return new ResponseEntity<>(requestResponseDto,HttpStatus.valueOf(201));

    }

    private void inviteRoom(Request request) {
        RequestStatus requestStatus = request.getRequestStatus();

        if(requestStatus.equals(RequestStatus.ACCEPT)){
            User user = request.getUser();

            Room room = roomRepository.findById(request.getPost().getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 채팅방입니다. ")
            );

            UserRoom userRoom = new UserRoom(user,room, DateConverter.millsToLocalDateTime(System.currentTimeMillis()));
            userRoomRepository.save(userRoom);

        } else if (requestStatus.equals(RequestStatus.REJECT)) {
            User user = request.getUser();

            Room room = roomRepository.findById(request.getPost().getId()).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 채팅방입니다. ")
            );

            Optional<UserRoom> userRoom = userRoomRepository.findByUserAndRoom(user,room);

            userRoom.ifPresent(userRoomRepository::delete);

        }

    }


    public ResponseEntity<?> showParticipationList(Long postId) {
        Optional<Post> postFound = postRepository.findById(postId);
        if(!postFound.isPresent()){
            throw new IllegalArgumentException("존재하지 않는 포스트입니다.");
        }

        return new ResponseEntity<>(new ParticipationResponseDto(requestRepository.showParticipationList(postFound.get())),HttpStatus.valueOf(200));

    }


    private void scoreUpdate(Request request,RequestStatus status){
        if(status.equals(RequestStatus.ACCEPT)||status.equals(RequestStatus.REJECT)){
            return;
        }

        User user = request.getUser();

        SubjectEnum subject = request.getPost().getSubject();

        Optional<Score> scoreFound = scoreRepository.findByUserAndSubject(user,subject);

        if(!scoreFound.isPresent()){
            Score score = new Score(user,subject);
            scoreRepository.save(score);
            status.addScore(score);
        }else{
            status.addScore(scoreFound.get());
        }
    }




    private void registerMatchAddNotification(User user, Post post,RequestStatus status) {
        Notification notification = new Notification(status.getNotificationContent(post.getTitle(), user.getNickname()),post.getUser());
        notificationRepository.save(notification);
    }

    private void UpdateRequestAddNotification(Request request) {
        Notification notification = new Notification(request.getRequestStatus().getNotificationContent(request.getPost().getTitle(),null),request.getUser());
        notificationRepository.save(notification);
    }

}
