package com.test.post.Entity;

import lombok.Getter;

@Getter
public enum Collage {
    공공인재대학("공공인재대학"),
    글로벌경영대학("글로벌경영대학"),
    사회과학대학("사회과학대학"),
    보건바이오대학("보건바이오대학"),
    IT공과대학("IT-공과대학"),
    디자인예술대학("디자인예술대학"),
    사범대학("사범대학"),
    재활과학대학("재활과학대학"),
    체육레저학부("체육레저학부"),
    문화콘텐츠학부("문화콘텐츠학부"),
    자유전공학부("자유전공학부"),
    글로컬라이프대학("글로컬라이프대학"),
    국제대학("국제대학");

    private String collageValue;

    Collage(String collageValue) {
        this.collageValue = collageValue;
    }
}
