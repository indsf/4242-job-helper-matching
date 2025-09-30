package com.test.post.repository;


import com.test.Member.entity.DisabilityType;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Post;
import com.test.post.Entity.PostType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    // 단일 조회

    // 작성자 기준 조회
    List<Post> findByMember_Id(Long memberId);

    // 게시글 종류별 조회
    List<Post> findByPostType(PostType postType);

    // 장애 유형 필터링
    List<Post> findByDisabilityTypeIn(List<DisabilityType> disabilityTypes);

    // 도움 유형 필터링
    List<Post> findByAssistanceTypeIn(List<AssistanceType> assistanceTypes);
}
