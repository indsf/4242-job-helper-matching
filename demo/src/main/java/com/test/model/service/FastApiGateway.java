package com.test.model.service;

import com.test.model.dto.externalPublicDto.FastApiAck;
import com.test.model.dto.externalPublicDto.OutPostReq;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class FastApiGateway {


    private final WebClient fastapiClient;

    public FastApiGateway(@Qualifier("fastapiClient") WebClient fastapiClient) {
        this.fastapiClient = fastapiClient;
    }


    public FastApiAck send(OutPostReq body){
        return fastapiClient.post()
                .uri("/recommend")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(FastApiAck.class)
                .block(); // 데모용(동기)
    }
}
