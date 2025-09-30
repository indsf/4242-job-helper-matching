package com.test.model.dto.externalPublicDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)     // 공공데이터 xml 없으면 무시
@Data
public class JobItem {

        @JacksonXmlProperty(localName = "busplaName")  private String busplaName;
        @JacksonXmlProperty(localName = "cntctNo")     private String cntctNo;
        @JacksonXmlProperty(localName = "compAddr")    private String compAddr;
        @JacksonXmlProperty(localName = "empType")     private String empType;
        @JacksonXmlProperty(localName = "enterType")   private String enterType;
        @JacksonXmlProperty(localName = "jobNm")       private String jobNm;
        @JacksonXmlProperty(localName = "envBothHands") private String envBothHands;
        @JacksonXmlProperty(localName = "envEyesight") private String envEyesight;
        @JacksonXmlProperty(localName = "envHandwork") private String envHandwork;
        @JacksonXmlProperty(localName = "envLiftPower") private String envLiftPower;
        @JacksonXmlProperty(localName = "envLstnTalk")  private String envLstnTalk;
        @JacksonXmlProperty(localName = "envStndWalk") private String envStndWalk;

        // yyyyMMdd → LocalDate
        @JacksonXmlProperty(localName = "offerregDt")
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate offerregDt;

        @JacksonXmlProperty(localName = "regDt")
        @JsonFormat(pattern = "yyyyMMdd")
        private LocalDate regDt;

        @JacksonXmlProperty(localName = "regagnName")  private String regagnName;
        @JacksonXmlProperty(localName = "reqCareer")   private String reqCareer;
        @JacksonXmlProperty(localName = "reqEduc")     private String reqEduc;
        @JacksonXmlProperty(localName = "rno")         private String rno;
        @JacksonXmlProperty(localName = "rnum")        private String rnum;
        @JacksonXmlProperty(localName = "salary")      private String salary;
        @JacksonXmlProperty(localName = "salaryType")  private String salaryType;
        @JacksonXmlProperty(localName = "termDate")    private String termDate; // xmlProperty로 정확한 매칭
}
