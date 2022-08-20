package com.sparta.matchgi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subject {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SubjectEnum subjectName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="POST_ID")
    private Post post;

    public Subject(Post post,SubjectEnum subjectName){
        this.post=post;
        this.subjectName=subjectName;
    }
}
