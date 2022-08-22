package com.sparta.matchgi.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.matchgi.dto.ShowRoomResponseDto;
import com.sparta.matchgi.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class UserRoomRepositoryImpl implements UserRoomRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    private QUserRoom userRoom = QUserRoom.userRoom;

    private QPost post = QPost.post;

    private QRoom room = QRoom.room;

    private QImgUrl imgUrl = QImgUrl.imgUrl;

    private QChat chat = QChat.chat;

    private QUser user = QUser.user;


    @Override
    public List<ShowRoomResponseDto> ShowRoomPost(User user1, LocalDateTime lastActive) {

        List<ShowRoomResponseDto> showRoomResponseDtoList = queryFactory.select(Projections.fields(
                        ShowRoomResponseDto.class,
                        room.id.as("roomId"),
                        post.id.as("postId"),
                        post.title,
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(chat.count())
                                        .from(chat)
                                        .where(chat.room.eq(room),chat.createdAt.gt(lastActive))
                                        .groupBy(chat.room),"unreadMessageCount"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(imgUrl.path)
                                        .from(imgUrl)
                                        .where(imgUrl.id.eq(
                                                JPAExpressions
                                                        .select(imgUrl.id.min())
                                                        .from(imgUrl)
                                                        .where(imgUrl.post.eq(post))
                                        )),"imgUrl"
                        ),
                    ExpressionUtils.as(
                            JPAExpressions
                                    .select(chat.id)
                                    .from(chat)
                                    .where(chat.room.eq(room))
                                    .where(chat.id.eq(
                                            JPAExpressions
                                                    .select(chat.id.max())
                                                    .from(chat)
                                                    .where(chat.room.eq(room))
                                                    .groupBy(chat.room)
                                ))
                                .groupBy(chat.room),"chatId"
                ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(chat.message)
                                        .from(chat)
                                        .where(chat.room.eq(room))
                                        .where(chat.id.eq(
                                                JPAExpressions
                                                        .select(chat.id.max())
                                                        .from(chat)
                                                        .where(chat.room.eq(room))
                                                        .groupBy(chat.room)
                                        ))
                                        .groupBy(chat.room),"message"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(chat.createdAt)
                                        .from(chat)
                                        .where(chat.room.eq(room))
                                        .where(chat.id.eq(
                                                JPAExpressions
                                                        .select(chat.id.max())
                                                        .from(chat)
                                                        .where(chat.room.eq(room))
                                                        .groupBy(chat.room)
                                        ))
                                        .groupBy(chat.room),"createdAt"
                        ),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(user.nickname)
                                        .from(chat)
                                        .join(chat.user,user)
                                        .where(chat.room.eq(room))
                                        .where(chat.id.eq(
                                                JPAExpressions
                                                        .select(chat.id.max())
                                                        .from(chat)
                                                        .where(chat.room.eq(room))
                                                        .groupBy(chat.room)
                                        ))
                                        .groupBy(chat.room),"nickname"
                        )


                )
        )
                .from(userRoom)
                .join(userRoom.room,room)
                .join(room.post,post)
                .where(userRoom.user.eq(user1))
                .orderBy(room.id.desc())
                .fetch();

        return showRoomResponseDtoList;
    }


}
