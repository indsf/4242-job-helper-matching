// src/main/java/com/test/matching/repository/MatchingRepository.java
package com.test.matching.repository;

import com.test.comment.entity.Comment;
import com.test.matching.entity.Matching;
import com.test.matching.entity.MatchingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    boolean existsByComment(Comment comment);

    // ✅ 인터페이스 기반 프로젝션: alias 이름과 메서드명이 일치해야 함
    public interface SimpleItem {
        Long getMatchingId();
        Long getPostId();
        String getPostTitle();
        MatchingStatus getStatus();
    }

    // ✅ PENDING 목록: post.author.id 또는 comment.member.id 가 요청자일 때
    @Query("""
        select 
           m.matchingId      as matchingId,
           p.id              as postId,
           p.title           as postTitle,
           m.matchingStatus  as status
        from Matching m
        join m.post p
        join m.comment c
        where (p.author.id = :memberId or c.member.id = :memberId)
          and m.matchingStatus = com.test.matching.entity.MatchingStatus.PENDING
        order by m.matchingId desc
    """)
    List<SimpleItem> findPendingSimpleByMember(@Param("memberId") Long memberId);

    // ✅ DONE 목록: 동일 조건 + status = DONE
    @Query("""
        select 
           m.matchingId      as matchingId,
           p.id              as postId,
           p.title           as postTitle,
           m.matchingStatus  as status
        from Matching m
        join m.post p
        join m.comment c
        where (p.author.id = :memberId or c.member.id = :memberId)
          and m.matchingStatus = com.test.matching.entity.MatchingStatus.DONE
        order by m.matchingId desc
    """)
    List<SimpleItem> findDoneSimpleByMember(@Param("memberId") Long memberId);
}
