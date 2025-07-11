package com.jarvis.accounts.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class Response<T> {
    private int status;
    private String message;
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private T data;
    private Date timestamp;

    public static final Response<?> SUCCESS = new Response<>(200, "Success");
    public static final Response<?> CREATED = new Response<>(201, "Created");
    public static final Response<?> FAILURE = new Response<>(205, "Failed");
    public static final Response<?> NOT_FOUND = new Response<>(404, "Resource not found");
    public static final Response<?> BAD_REQUEST = new Response<>(400, "Bad Request");
    public static final Response<?> SERVER_ERROR = new Response<>(500, "Server Error");

    private static <T> Response<T> restResult(T data, HttpStatus status, String message) {
        Response<T> apiResult = new Response<>();
        apiResult.setStatus(status.value());
        apiResult.setMessage(message);
        apiResult.setData(data);
        return apiResult;
    }

    public static <T> Response<T> ok(T data) {
        return restResult(data, HttpStatus.OK, "SUCCESS");
    }

    public static <T> Response<T> ok() {
        return restResult(null, HttpStatus.OK, "SUCCESS");
    }

    public Response(int statusCode, String message, T data) {
        this();
        this.status = statusCode;
        this.message = message;
        this.data = data;
    }

    public Response(int statusCode, String message) {
        this();
        this.status = statusCode;
        this.message = message;
    }

    public Response(int status, T data) {
        this();
        this.status = status;
        this.data = data;
    }

    public Response(T data) {
        this(200, "Success");
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response [statusCode=" + status + ", message=" + message + ", data=" + data + "]";
    }

    public Response() {
        super();
        this.timestamp = new Date();
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
