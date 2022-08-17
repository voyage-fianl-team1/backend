package com.sparta.matchgi.model;



import com.sparta.matchgi.auth.auth.UserDetailsImpl;
import com.sparta.matchgi.dto.CreatePostRequestDto;
import com.sparta.matchgi.util.converter.DateConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Timestamped{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime peopleDeadline;

    @Column(nullable = false)
    private LocalDateTime matchDeadline;



    @Column(nullable = false)
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private SubjectEnum subject;

    @Column(nullable = false)
    private Double lat;

    @Column(nullable = false)
    private Double lng;

    @Column(nullable = false)
    private int viewCount;

    @Column(nullable = false)
    private int requestCount; //경기목록더보기-추가


    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MatchStatus matchStatus;


    @Column(nullable = false)
    private String address;

    @OneToMany(cascade =CascadeType.ALL,mappedBy ="post")
    private List<ImgUrl> imageList=new ArrayList<>();

    @Column(nullable = false)
    private int owner;



    public Post(CreatePostRequestDto createPostRequestDto, UserDetailsImpl userDetails) {
        this.user = userDetails.getUser();
        this.title = createPostRequestDto.getTitle();
        this.peopleDeadline = DateConverter.dateToLocalDateTime(createPostRequestDto.getPeopleDeadline());
        this.matchDeadline = DateConverter.dateToLocalDateTime(createPostRequestDto.getMatchDeadline());
        this.address = createPostRequestDto.getAddress();
        this.lat = createPostRequestDto.getLat();
        this.lng = createPostRequestDto.getLng();
        this.content = createPostRequestDto.getContent();
        this.subject = createPostRequestDto.getSubject();
        this.viewCount = 0;
        this.requestCount = 0;
        this.matchStatus = MatchStatus.ONGOING;
        this.owner=-1;//일단 작성자 인걸로 세팅
        this.imageList=imageList;

    }
    public void addImgUrl(ImgUrl imgUrl){
        this.imageList.add(imgUrl);
    }

    public void setOwner(int ownernum){
        this.owner=ownernum;
    }



    public void updatePost(CreatePostRequestDto createPostRequestDto,UserDetailsImpl userDetails) {
        this.user = userDetails.getUser();
        this.title = createPostRequestDto.getTitle();
        this.address = createPostRequestDto.getAddress();
        this.lat = createPostRequestDto.getLat();
        this.lng = createPostRequestDto.getLng();
        this.content = createPostRequestDto.getContent();
        this.subject = createPostRequestDto.getSubject();
    }

    public void addRequestCount() {
        this.requestCount += 1;
    }
}
