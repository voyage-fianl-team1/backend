package com.sparta.matchgi.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.matchgi.dto.PostFilterDto;
import com.sparta.matchgi.model.QPost;
import com.sparta.matchgi.model.QSubject;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    private QPost post=QPost.post;

    private QSubject subject=QSubject.subject;




    @Override
    public Slice<PostFilterDto> findAllBySubjectOrderByCreatedAt(SubjectEnum subject,String sort,Pageable pageable){


    List<PostFilterDto> returnPost=queryFactory.select(Projections.fields(
            PostFilterDto.class,
            post.id.as("postId"),//as를 꼭 해줘야 id가 들어감
            post.createdAt,
            post.title,
            post.subject,
            post.viewCount,
            post.peopleDeadline,
            post.requestCount))
            .from(post)
            .where(getSubject(subject))
            .orderBy(post.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

    return new SliceImpl<>(returnPost,pageable,returnPost.iterator().hasNext());

    }

    private BooleanExpression getSubject(SubjectEnum subject){
        return subject.equals(SubjectEnum.valueOf("ALL"))?null:post.subject.eq(subject);
    }

//    private BooleanBuilder getSort(String sort){
//        return sort.equals("viewcount")?null:post.viewCount.desc();
//    }
}
