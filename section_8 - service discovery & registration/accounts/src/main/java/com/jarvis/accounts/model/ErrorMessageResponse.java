package com.jarvis.accounts.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data @AllArgsConstructor
public class ErrorMessageResponse {
    private int status;
    private String message;
    private Date timeStamp;
}
