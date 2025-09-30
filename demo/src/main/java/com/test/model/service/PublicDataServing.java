package com.test.model.service;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.test.model.dto.externalPublicDto.Body;
import com.test.model.dto.externalPublicDto.Items;
import com.test.model.dto.externalPublicDto.JobItem;
import com.test.model.dto.externalPublicDto.Response;
import com.test.model.dto.fastApiDto.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PublicDataServing {

    private final WebClient publicDataClient;
    private final XmlMapper xmlMapper;

    @Autowired
    public PublicDataServing(
            @Qualifier("publicDataClient") WebClient publicDataClient,
            @Qualifier("xmlMapper") XmlMapper xmlMapper
    ) {
        this.publicDataClient = publicDataClient;
        this.xmlMapper = xmlMapper;
    }

    @Value("${openapi.job-list-path}")
    String jobListPath;

    @Value("${openapi.service-key-decoding}")
    String serviceKeyDecoding;

    /** 후보자 리스트 불러오기 */
    public List<Candidate> fetchCandidates(String page, String rows) {
        String xml = publicDataClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(jobListPath)
                        .queryParam("serviceKey", serviceKeyDecoding)
                        .queryParam("pageNo", page)
                        .queryParam("numOfRows", rows)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 데모용. 운영에선 타임아웃/리액티브 체인을 권장

        try {
            Response resp = xmlMapper.readValue(xml, Response.class);
            List<JobItem> items = Optional.ofNullable(resp.getBody())
                    .map(Body::getItems)
                    .map(Items::getItem)
                    .orElse(List.of());

            return items.stream().map(this::toCandidate).toList();
        } catch (Exception e) {
            throw new RuntimeException("XML parse error", e);
        }
    }

    /** XML → Candidate 변환 */
    private Candidate toCandidate(JobItem j) {
        Candidate c = new Candidate();
        c.setId(makeHashId(j));
        c.setTitle(Objects.toString(j.getBusplaName(), ""));
        c.setDistrict(List.of(
                "envBothHands:" + Objects.toString(j.getEnvBothHands(), ""),
                "envEyesight:" + Objects.toString(j.getEnvEyesight(), ""),
                "envHandwork:" + Objects.toString(j.getEnvHandwork(), ""),
                "envLiftPower:" + Objects.toString(j.getEnvLiftPower(), ""),
                "envLstnTalk:" + Objects.toString(j.getEnvLstnTalk(), ""),
                "envStndWalk:" + Objects.toString(j.getEnvStndWalk(), "")
        ));
        c.setTags(List.of(
                "salary:" + Objects.toString(j.getSalary(), ""),
                "salary_type:" + Objects.toString(j.getSalaryType(), "")
        ));
        return c;
    }

    private String makeHashId(JobItem jobItem) {
        return Integer.toHexString(Objects.hash(
                jobItem.getCompAddr(),
                jobItem.getJobNm(),
                jobItem.getEmpType()
        ));
    }

    /** 원본 JobItem 리스트 불러오기 */
    public List<JobItem> fetchJobItems(String page, String rows) {
        String xml = publicDataClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(jobListPath)
                        .queryParam("serviceKey", serviceKeyDecoding)
                        .queryParam("pageNo", page)
                        .queryParam("numOfRows", rows)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();

        try {
            Response resp = xmlMapper.readValue(xml, Response.class);
            return Optional.ofNullable(resp.getBody())
                    .map(Body::getItems)
                    .map(Items::getItem)
                    .orElse(List.of());
        } catch (Exception e) {
            throw new RuntimeException("XML parse error", e);
        }
    }
}
