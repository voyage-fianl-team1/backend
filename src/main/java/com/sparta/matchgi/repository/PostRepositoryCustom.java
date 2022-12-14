package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.PostFilterDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostRepositoryCustom {


    Slice<PostFilterDto> findAllBySubjectOrderByCreatedAt(String subject,String sort,Pageable pageable);

    Slice<PostFilterDto> findAllBySearchOrderByCreatedAt(String search,Pageable pageable);

    List<PostFilterDto> findAllByLocation(double lat,double lng);
    List<PostFilterDto> findAllByLocationWithPoint(double NElat, double NElng, double SWlat, double SWlng);

}
