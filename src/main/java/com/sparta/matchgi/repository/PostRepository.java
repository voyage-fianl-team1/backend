package com.sparta.matchgi.repository;

import com.sparta.matchgi.dto.PostFilterDto;
import com.sparta.matchgi.model.Post;
import com.sparta.matchgi.model.Request;
import com.sparta.matchgi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<Post,Long>, PostRepositoryCustom {


    Post findPostById(Long id);
    List<Post> findAll();

    List<Post> findAllByUser(User user);

//    @Query(value = "SELECT id, created_at, title, lat, lng, longitude,viewCount,matchDeadline,requestCount,matchStatus"
//            + "ROUND(ST_DISTANCE_SPHERE(:myPoint, POINT(p.lng, p.lat))) AS 'distance' "//두 좌표 사이의 거리
//            + "FROM post AS p "
//            + "WHERE ST_DISTANCE_SPHERE(:myPoint, POINT(p.longitude, p.latitude)) < :distance "
//            + "ORDER BY matchStatus ")
//    List<PostFilterDto> findlocation(double lat, double lng, double distance);



}
