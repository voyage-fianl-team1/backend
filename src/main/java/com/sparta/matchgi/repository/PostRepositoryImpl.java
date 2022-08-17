package com.sparta.matchgi.repository;

import com.querydsl.apt.jpa.JPAAnnotationProcessor;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.matchgi.dto.PostFilterDto;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.QPost;
import com.sparta.matchgi.model.QSubject;
import com.sparta.matchgi.model.SubjectEnum;
import com.sparta.matchgi.util.converter.DtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    private QPost post=QPost.post;

    private QSubject subject=QSubject.subject;




    @Override
    public Slice<PostFilterDto> findAllBySubjectOrderByCreatedAt(SubjectEnum subject, Pageable pageable){
        JPAQuery<Post> returnPost= queryFactory
                .selectFrom(post)
                .where(
                        post.subject.eq(subject)
                );

        List<PostFilterDto> postList = returnPost.fetch()
                .stream().map(DtoConverter::ofSummary).collect(Collectors.toList());
        boolean hasNext = false;

        if (postList.size() > pageable.getPageSize()) {
            postList.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(postList, pageable, hasNext);
    }

    private BooleanExpression subjectEq(SubjectEnum subject){
        return ObjectUtils.isEmpty(subject)?null:post.subject.eq(subject);
    }
}
