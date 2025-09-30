package com.test.model.controller;

import com.test.model.dto.externalPublicDto.FastApiAck;
import com.test.model.dto.externalPublicDto.OutPostReq;
import com.test.model.mapper.ModelMapper;
import com.test.model.service.FastApiGateway;
import com.test.post.Entity.Post;
import com.test.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post/srv")
public class FastApiSocketController {

    private final FastApiGateway fastApiGateway;
    private final PostService postService;


    @Operation(summary = "게시글 정보-> fastapi 보내기",description = "정보 보내기")
    @GetMapping("/to-fastapi/{post_id}")
    public ResponseEntity<FastApiAck> sendPostApi(@PathVariable("post_id") Long postId){
        Post post = postService.findPostIdOrExe(postId);
        OutPostReq body = ModelMapper.toReq(post);
        FastApiAck apiAck = fastApiGateway.send(body);
        return ResponseEntity.ok(apiAck);
    }
}
