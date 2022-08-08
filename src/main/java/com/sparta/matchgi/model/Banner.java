package com.sparta.matchgi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Banner {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "BANNER_ID")
    private Long id;


    @Column(nullable = false)
    private String bannerUrl;
}
