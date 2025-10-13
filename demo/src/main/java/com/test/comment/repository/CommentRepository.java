// src/main/java/com/test/comment/repository/CommentRepository.java
package com.test.comment.repository;

import com.test.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    interface CountProjection {
        Long getPostId();
        Long getCnt();
    }

    // 게시글 ID 목록별 댓글 수 집계
    @Query("""
        select c.post.id as postId, count(c.id) as cnt
        from Comment c
        where c.post.id in :postIds
        group by c.post.id
    """)
    List<CountProjection> countByPostIds(@Param("postIds") List<Long> postIds);

    // 특정 게시글의 댓글 목록
    @Query("""
        select c
        from Comment c
        where c.post.id = :postId
        order by c.id asc
    """)
    List<Comment> findByPostId(@Param("postId") Long postId);
}
