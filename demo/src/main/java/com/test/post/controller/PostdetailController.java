package com.test.post.controller;

import com.test.post.dto.PostSummaryDto;
import com.test.post.service.PostQueryService;
import com.test.post.Entity.PostType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts/simple")
@RequiredArgsConstructor
public class PostdetailController {

    private final PostQueryService postQueryService; // Lombok이 생성자 자동 생성

    @GetMapping
    public Page<PostSummaryDto> list(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable,
            @RequestParam(name = "postType", required = false) String postType,
            @RequestParam(name = "category", required = false) String categoryAlias
    ) {
        String key = (postType != null && !postType.isBlank()) ? postType : categoryAlias;
        Optional<PostType> typeOpt = parsePostType(key);
        return postQueryService.list(typeOpt, pageable);
    }

    private Optional<PostType> parsePostType(String raw) {
        if (raw == null || raw.isBlank()) return Optional.empty();
        try {
            return Optional.of(PostType.valueOf(raw.trim().toUpperCase(Locale.ROOT)));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
