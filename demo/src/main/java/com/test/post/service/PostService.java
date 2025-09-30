package com.test.post.service;

import com.test.Member.entity.DisabilityType;
import com.test.Member.entity.Member;
import com.test.Member.service.MemberService;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Collage;
import com.test.post.Entity.Post;
import com.test.post.dto.PostEnumResDto;
import com.test.post.dto.PostReqDto;
import com.test.post.exception.PostIdInvalidCheck;
import com.test.post.repository.PostRepository;
import jakarta.persistence.Enumerated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

import static com.test.post.mapper.PostMapper.toEntity;

@RequiredArgsConstructor
@Service

public class PostService {

    private final MemberService memberService;
    private final PostRepository postRepository;



    //게시글 생성
    @Transactional
    public Long createPost(PostReqDto postReqDto,Long memberId) {
        Member member = memberService.findMemberIdOrExe(memberId);
        Post post = toEntity(postReqDto, member);
        return postRepository.save(post).getId();
    }

    public PostEnumResDto getPostEnums() {
        return PostEnumResDto.builder()
                .assistanceTypes(List.of(AssistanceType.values()))
                .collages(List.of(Collage.values()))
                .disabilityTypes(List.of(DisabilityType.values()))
                .build();
    }

    // postId없으면  error 반환
    @Transactional(readOnly = true)
    public Post findPostIdOrExe(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> PostIdInvalidCheck.BUSSINESS_EXCEPTION);
    }



}
