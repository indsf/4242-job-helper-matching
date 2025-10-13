// src/main/java/com/test/post/service/PostQueryService.java
package com.test.post.service;

import com.test.comment.repository.CommentRepository;
import com.test.post.dto.PostSummaryDto;
import com.test.post.Entity.Post;          // ← 엔티티 패키지명에 맞춰주세요
import com.test.post.Entity.PostType;

import com.test.post.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;        // ✅ Spring Data
import org.springframework.data.domain.PageImpl;  // ✅ Spring Data
import org.springframework.data.domain.Pageable;   // ✅ Spring Data

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostQueryService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostQueryService(PostRepository postRepository,
                            CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Page<PostSummaryDto> list(Optional<PostType> typeOpt, Pageable pageable) {
        Page<Post> page = typeOpt
                .map(t -> postRepository.findByPostType(t, pageable))
                .orElseGet(() -> postRepository.findAll(pageable));

        List<Long> ids = page.getContent().stream().map(Post::getId).toList();

        Map<Long, Long> countMap = ids.isEmpty()
                ? Collections.emptyMap()
                : commentRepository.countByPostIds(ids).stream()
                .collect(Collectors.toMap(
                        CommentRepository.CountProjection::getPostId,
                        CommentRepository.CountProjection::getCnt
                ));

        List<PostSummaryDto> dtos = page.getContent().stream().map(p -> new PostSummaryDto(
                p.getId(),
                nz(p.getTitle()),
                p.getAuthor() != null ? nz(p.getAuthor().getNickname()) : "익명",
                p.getCreatedAt(),
                p.getAssistanceType() != null ? p.getAssistanceType().name() : null,
                countMap.getOrDefault(p.getId(), 0L)
        )).toList();

        return new PageImpl<>(dtos, pageable, page.getTotalElements());
    }

    private static String nz(String s) { return s == null ? "" : s; }
}
