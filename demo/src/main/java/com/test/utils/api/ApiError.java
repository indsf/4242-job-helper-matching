package com.test.utils.api;

import org.springframework.http.HttpStatus;

public class ApiError<T> {

    private T body;
    private HttpStatus status;

    public ApiError(T body, HttpStatus status) {
        this.body = body;
        this.status = status;
    }

    public T getBody() { return body; }
    public void setBody(T body) { this.body = body; }

    public HttpStatus getStatus() { return status; }
    public void setStatus(HttpStatus status) { this.status = status; }

    // =============================
    // CustomBody static inner class
    // =============================
    public static class CustomBody<D> {

        private Boolean success;
        private D data;
        private String error; // 메시지 문자열 그대로 담음

        public CustomBody(Boolean success, D data, String error) {
            this.success = success;
            this.data = data;
            this.error = error;
        }

        public Boolean getSuccess() { return success; }
        public void setSuccess(Boolean success) { this.success = success; }

        public D getData() { return data; }
        public void setData(D data) { this.data = data; }

        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }
}
