package com.sparta.matchgi.model;

import com.sparta.matchgi.dto.UpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Request extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "REQUEST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus requestStatus;

    public Request(Post post,User user){
        this.post = post;
        this.user = user;
        this.requestStatus = RequestStatus.PENDING;
    }
    public Request(Post post,User user,RequestStatus requestStatus){
        this.post = post;
        this.user = user;
        this.requestStatus = requestStatus;
    }


    public void updateStatus(UpdateRequestDto updateRequestDto) {
        this.requestStatus = updateRequestDto.getStatus();
    }
}
