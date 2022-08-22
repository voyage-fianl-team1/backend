package com.sparta.matchgi.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.matchgi.dto.PostFilterDto;
import com.sparta.matchgi.model.MatchStatus;
import com.sparta.matchgi.model.QPost;
import com.sparta.matchgi.model.QSubject;
import com.sparta.matchgi.model.SubjectEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{


    private final JPAQueryFactory queryFactory;

    private QPost post=QPost.post;

    private QSubject subject=QSubject.subject;



    //matchstatus는 무조건 ongoing 우선 정렬
    //api 하나 추가해서 경기 종료하면 그냥 end 되는 걸로


    //종목 필터링
    @Override
    public Slice<PostFilterDto> findAllBySubjectOrderByCreatedAt(SubjectEnum subject,Pageable pageable){

    List<PostFilterDto> returnPost=queryFactory.select(Projections.fields(
            PostFilterDto.class,
            post.id.as("postId"),//as를 꼭 해줘야 id가 들어감
            post.createdAt,
            post.title,
            post.subject,
            post.viewCount,
            post.matchDeadline,
            post.requestCount,
                    post.matchStatus))
            .from(post)
            .where(getSubject(subject))
            .orderBy(orderByOngoing(),post.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


    return new SliceImpl<>(returnPost,pageable,returnPost.iterator().hasNext());

    }

    //종목+조회수 필터
    @Override
    public Slice<PostFilterDto> findAllBySubjectOrderByCreatedAt(SubjectEnum subject, String sort, Pageable pageable) {
        List<com.sparta.matchgi.dto.PostFilterDto> returnPost = queryFactory.select(Projections.fields(
                        com.sparta.matchgi.dto.PostFilterDto.class,
                        post.id.as("postId"),//as를 꼭 해줘야 id가 들어감
                        post.createdAt,
                        post.title,
                        post.subject,
                        post.viewCount,
                        post.matchDeadline,
                        post.requestCount,
                        post.matchStatus))
                .from(post)
                .where(getSubject(subject))
                .orderBy(orderByOngoing(), post.viewCount.desc(), post.createdAt.desc())
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
                        post.subject,
                        post.viewCount,
                        post.matchDeadline,
                        post.requestCount,
                        post.matchStatus))
                .from(post)
                .where(post.title.contains(search).or(post.content.contains(search)))
                .orderBy(orderByOngoing(),post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        return new SliceImpl<>(returnPost,pageable,returnPost.iterator().hasNext());
    }




    private BooleanExpression getSubject(SubjectEnum subject){
        return subject.equals(SubjectEnum.valueOf("ALL"))?null:post.subject.eq(subject);
    }


    BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (Exception e) {
            return new BooleanBuilder();
        }
    }

    BooleanBuilder titleCt(String search) {
        return nullSafeBuilder(() -> post.title.contains(search));
    }
    BooleanBuilder contentCt(String search) {
        return nullSafeBuilder(() -> post.content.contains(search));
    }

    private OrderSpecifier<Integer> orderByOngoing() {
        LocalDateTime date = LocalDateTime.now();
        return new CaseBuilder().when(post.matchStatus.eq(MatchStatus.ONGOING)).then(1).otherwise(2).asc();
    }




}
