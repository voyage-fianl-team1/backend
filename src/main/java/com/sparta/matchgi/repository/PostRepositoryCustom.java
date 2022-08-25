package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.PostFilterDto;
import com.sparta.matchgi.model.SubjectEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepositoryCustom {


    Slice<PostFilterDto> findAllBySubjectOrderByCreatedAt(String subject,String sort,Pageable pageable);

    Slice<PostFilterDto> findAllBySearchOrderByCreatedAt(String search,Pageable pageable);



}
