//package com.test;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//public class FastApiClient {
//    public static void main(String[] args) {
//        RestTemplate restTemplate = new RestTemplate();
//        String url = "http://10.143.3.131:8000/recommend?region=test&disability=test&pay=12312";
//
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        System.out.println(response.getBody());
//    }
//}