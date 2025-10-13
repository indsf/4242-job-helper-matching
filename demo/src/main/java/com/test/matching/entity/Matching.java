package com.test.matching.entity;

import com.test.comment.entity.Comment;
import com.test.post.Entity.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    private Long matchingId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Enumerated(EnumType.STRING)
    @Column(name = "matching_status", nullable = false)
    private MatchingStatus matchingStatus = MatchingStatus.PENDING; // ✅ 기본값

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false, unique = true)
    private Comment comment;

    // ✅ 안전장치: 저장 전 기본값 보장
    @PrePersist
    void prePersist() {
        if (matchingStatus == null) {
            matchingStatus = MatchingStatus.PENDING;
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }

    // ✅ 팩토리: status 안 받도록(실수 방지)
    public static Matching createMatching(Post post, Comment comment) {
        return Matching.builder()
                .post(post)
                .comment(comment)
                .matchingStatus(MatchingStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 기존 시그니처 유지가 필요하면 null 방어만 추가
    public static Matching createMatching(Post post, Comment comment, MatchingStatus status) {
        return Matching.builder()
                .post(post)
                .comment(comment)
                .matchingStatus(status != null ? status : MatchingStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}