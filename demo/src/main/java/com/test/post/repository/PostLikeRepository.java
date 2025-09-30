package com.test.post.repository;

import com.test.post.Entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

//    @Modifying(flushAutomatically = true, clearAutomatically = true)
//    @Query()
//    void de
}
