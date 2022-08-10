package com.sparta.matchgi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProfileImg {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PROFILEIMG_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(nullable = false)
    private String profileImgPath;

    public ProfileImg(User user, String profileImgPath) {
        this.user = user;
        this.profileImgPath = profileImgPath;
    }

    public void update(String profileImgPath){
        this.profileImgPath = profileImgPath;
    }

}
