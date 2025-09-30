package com.test.model.mapper;

import com.test.model.dto.externalPublicDto.OutPostReq;
import com.test.post.Entity.AssistanceType;
import com.test.post.Entity.Post;

public final class ModelMapper {

    private ModelMapper(){

    }

    public static OutPostReq toReq(Post p){
        return OutPostReq.builder()
                .id(p.getId())
                .title(p.getTitle())
                .content(p.getContent())
                .build();
    }
}
