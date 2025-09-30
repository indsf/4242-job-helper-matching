package com.test.model.config;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class xmlConfig {


    @Bean
    public XmlMapper xmlMapper(){
        return new XmlMapper(); // xml파일의 return값을 바인딩
    }
}
