package com.sparta.matchgi.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.matchgi.dto.PostFilterDto;
import com.sparta.matchgi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    private QPost post=QPost.post;
    private QImgUrl imgUrl = QImgUrl.imgUrl;



    //matchstatus는 무조건 ongoing 우선 정렬
    //api 하나 추가해서 경기 종료하면 그냥 end 되는 걸로



    //종목+조회수 필터
    @Override
    public Slice<PostFilterDto> findAllBySubjectOrderByCreatedAt(String subject, String sort, Pageable pageable) {
        List<com.sparta.matchgi.dto.PostFilterDto> returnPost = queryFactory.select(Projections.fields(
                        com.sparta.matchgi.dto.PostFilterDto.class,
                        post.id.as("postId"),//as를 꼭 해줘야 id가 들어감
                        post.createdAt,
                        post.title,
                        subjectCaseBuilder().as("subject"),
                        post.viewCount,
                        post.matchDeadline,
                        post.requestCount,
                        post.matchStatus,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(imgUrl.url)
                                        .from(imgUrl)
                                        .where(imgUrl.id.eq(
                                                JPAExpressions
                                                        .select(imgUrl.id.min())
                                                        .from(imgUrl)
                                                        .where(imgUrl.post.eq(post))
                                        )),"imgUrl"
                        )))
                .from(post)
                .where(getSubject(subject))
                .orderBy(orderByOngoing(), OrderBySort(sort))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        boolean hasNext = false;
        if (returnPost.size() > pageable.getPageSize()) {
            returnPost.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(returnPost, pageable, hasNext);
    }


    //검색 기능
    @Override
    public Slice<PostFilterDto> findAllBySearchOrderByCreatedAt(String search,Pageable pageable){
        List<PostFilterDto> returnPost=queryFactory.select(Projections.fields(
                        PostFilterDto.class,
                        post.id.as("postId"),//as를 꼭 해줘야 id가 들어감
                        post.createdAt,
                        post.title,
                        subjectCaseBuilder().as("subject"),
                        post.viewCount,
                        post.matchDeadline,
                        post.requestCount,
                        post.matchStatus,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(imgUrl.url)
                                        .from(imgUrl)
                                        .where(imgUrl.id.eq(
                                                JPAExpressions
                                                        .select(imgUrl.id.min())
                                                        .from(imgUrl)
                                                        .where(imgUrl.post.eq(post))
                                        )),"imgUrl"
                        )))
                .from(post)
                .where(post.title.contains(search).or(post.content.contains(search)))
                .orderBy(orderByOngoing(),post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return new SliceImpl<>(returnPost,pageable,returnPost.iterator().hasNext());
    }




    private BooleanExpression getSubject(String subject){
        return subject.equals("ALL") ? null : post.subject.eq(SubjectEnum.valueOf(subject));
    }



    private OrderSpecifier<Integer> orderByOngoing() {
        return new CaseBuilder().when(post.matchStatus.eq(MatchStatus.ONGOING)).then(1).otherwise(2).asc();
    }

    private StringExpression subjectCaseBuilder(){
        return new CaseBuilder().when(post.subject.eq(SubjectEnum.SOCCER)).then("축구")
                .when(post.subject.eq(SubjectEnum.BADMINTON)).then("배드민턴")
                .when(post.subject.eq(SubjectEnum.BASKETBALL)).then("농구")
                .when(post.subject.eq(SubjectEnum.BILLIARDS)).then("당구")
                .when(post.subject.eq(SubjectEnum.BOWLING)).then("볼링")
                .when(post.subject.eq(SubjectEnum.TENNIS)).then("테니스")
                .otherwise("기타");

    }

    private OrderSpecifier<?> OrderBySort(String sort) {
        if ("viewcount".equals(sort)) {
            return post.viewCount.desc();
        }
        return post.createdAt.desc();
    }




}
