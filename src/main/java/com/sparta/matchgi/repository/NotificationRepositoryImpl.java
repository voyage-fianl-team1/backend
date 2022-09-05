package com.sparta.matchgi.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.matchgi.dto.NotificationDetailResponseDto;
import com.sparta.matchgi.model.QNotification;
import com.sparta.matchgi.model.QPost;
import com.sparta.matchgi.model.QUser;
import com.sparta.matchgi.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private QNotification notification = QNotification.notification;

    private QPost post = QPost.post;

    private QUser qUser = QUser.user;

    @Override
    public List<NotificationDetailResponseDto> getNotice(User user) {
        List<NotificationDetailResponseDto> notificationDetailResponseDtos = queryFactory.select(Projections.fields(
                        NotificationDetailResponseDto.class,
                        post.id.as("postId"),
                        notification.id,
                        notification.content,
                        notification.createdAt,
                        notification.isread

                ))
                .from(notification)
                .join(notification.post, post)
                .join(notification.user, qUser)
                .where(qUser.eq(user))
                .orderBy(orderByisread(),notification.createdAt.desc())
                .fetch();


        return notificationDetailResponseDtos;
    }

    private OrderSpecifier<Integer> orderByisread() {
        return new CaseBuilder()
                .when(notification.isread.eq(true)).then(1)
                .otherwise(2).desc();
    }

}