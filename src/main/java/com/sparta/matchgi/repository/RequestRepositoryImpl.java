package com.sparta.matchgi.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.matchgi.dto.GetScoresResponseDto;
import com.sparta.matchgi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class RequestRepositoryImpl implements RequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final QRequest qRequest = QRequest.request;

    private final QUser qUser = QUser.user;

    private final QPost qPost = QPost.post;

    private final QImgUrl qImgUrl = QImgUrl.imgUrl;


    @Override
    public List<GetScoresResponseDto> ScoresSubject(User user, String subject, List<RequestStatus> requestStatusList) {
        List<GetScoresResponseDto> getScoresResponseDtoList = queryFactory.select(Projections.fields(
                        GetScoresResponseDto.class,
                        qPost.matchDeadline,
                        qPost.subject,
                        qRequest.requestStatus.as("status"),
                        qPost.title,
                        qPost.id.as("postId"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(qImgUrl.url)
                                        .from(qImgUrl)
                                        .where(qImgUrl.id.eq(
                                                JPAExpressions
                                                        .select(qImgUrl.id.min())
                                                        .from(qImgUrl)
                                                        .where(qImgUrl.post.eq(qPost))
                                        )),"imgUrl"
                        )

                        ))
                .from(qRequest)
                .join(qRequest.post, qPost)
                .join(qRequest.user, qUser)
                .where(qUser.eq(user))
                .where(qRequest.requestStatus.in(requestStatusList))
                .where(getSubject(subject))
                .orderBy(qPost.matchDeadline.desc())
                .fetch();

        return getScoresResponseDtoList;
    }

    private BooleanExpression getSubject(String subject){
        return subject.equals("ALL") ? null : qPost.subject.eq(SubjectEnum.valueOf(subject));
    }


}
