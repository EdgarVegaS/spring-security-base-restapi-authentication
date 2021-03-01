package com.tibianos.tibianosfanpage.repositories;

import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

import com.tibianos.tibianosfanpage.entities.PostEntity;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

@Repository
public interface PostRepository extends PagingAndSortingRepository<PostEntity,Long> {
    
    List<PostEntity> getByUserIdOrderByCreatedAtDesc(long userId);

    @Query(value = "SELECT * FROM posts p WHERE p.exposure_id = :exposure and p.expires_at > :now ORDER BY created_at DESC LIMIT 20 ",nativeQuery = true)
    List<PostEntity> getLastPublicPost(@Param("exposure")  long exposureId,
                                       @Param("now")  Date now);
    
    PostEntity getByPostId(String postId);
}