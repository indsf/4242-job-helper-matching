package com.test.model.service;

import com.test.model.dto.externalPublicDto.FastApiAck;
import com.test.model.dto.externalPublicDto.OutPostReq;
import com.test.model.mapper.ModelMapper;
import com.test.post.Entity.Post;
import com.test.post.exception.PostIdInvalidCheck;
import com.test.post.mapper.PostMapper;
import com.test.post.repository.PostRepository;
import com.test.post.service.PostService;
import com.test.utils.api.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;


//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/post/srv")
//public class PostDataService {
//
//
//    private final FastApiGateway fastApiGateway;
//    private final PostService postService;
//    private final ModelMapper modelMapper;
//
//    @Qualifier("fastapiClient")
//    private final WebClient fastapiClient;
//
//    @Operation(summary = "게시글 정보-> fastapi 보내기",description = "정보 보내기")
//    @PostMapping("/to-fastapi")
//    public ResponseEntity<FastApiAck> sendPostApi(Long postId){
//        Post post = postService.findPostIdOrExe(postId);
//        OutPostReq body = ModelMapper.toReq(post);
//        FastApiAck apiAck = fastApiGateway.send(body);
//        return ResponseEntity.ok(apiAck);
//    }
//
//}
