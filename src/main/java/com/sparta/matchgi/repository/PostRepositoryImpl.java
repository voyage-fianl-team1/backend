package com.sparta.matchgi.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.matchgi.dto.PostFilterDto;
import com.sparta.matchgi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static org.aspectj.runtime.internal.Conversions.doubleValue;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{


    private final JPAQueryFactory queryFactory;
    private QPost post=QPost.post;

    private QImgUrl imgUrl = QImgUrl.imgUrl;



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


    @Override
    public List<PostFilterDto> findAllByLocation(Double lat, Double lng){
        NumberExpression<Double> distanceExpression=getLocation(lat,lng);
        NumberPath<Double> distancePath = Expressions.numberPath(Double.class, "distance");
        Expressions.as(distanceExpression, distancePath);
        List<PostFilterDto> returnPost= queryFactory.select(Projections.fields(
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
                ),Expressions.as(distanceExpression, distancePath)))
                .from(post)
                .where(distancePath.loe(new Double("5.00")))
                .orderBy(orderByOngoing())
                .fetch();

        return returnPost;

    }

    //querydsl에서는 Doubld에서 double로 형 변환이 안되네
    //서비스에서 하려니까 데이터가 너무 많아서 for문 돌리는게 안될 것 같음





    private NumberExpression<Double> getLocation(double lat, double lng){
        NumberExpression<Double> distanceExpression =
                acos(cos(radians(Expressions.constant(lat)))
                        .multiply(cos(radians(post.lat))
                                .multiply(cos(radians(post.lng))
                                        .subtract(radians(Expressions.constant(lng)))
                                        .add(sin(radians(Expressions.constant(lat)))
                                                .multiply(sin(radians(post.lat))))))).multiply(6371);


        System.out.println("현재위도,경도 : "+lat+", "+lng);
        return distanceExpression;
    }

//    private  BooleanExpression list(double lat,double lng){
//        NumberPath<Double> distancePath = Expressions.numberPath(Double.class, "distance");
//
//        NumberExpression<Double> range=getLocation(lat,lng);
//        return range.loe(ExpressionUtils.any(new Double("5.00")));
//        }






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