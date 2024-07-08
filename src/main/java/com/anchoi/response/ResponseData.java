package com.anchoi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ResponseData<T> {
    private String message;
    private Integer status;
    private T data;

    public static <T> ResponseData ok(T data){
        ResponseData responseData = new ResponseData();
        responseData.setData(data);
        responseData.setStatus(200);
        responseData.setMessage("OK");
        return responseData;
    }

    public static <T> ResponseData error(T data, String message){
        ResponseData responseData = new ResponseData();
        responseData.setData(data);
        responseData.setStatus(400);
        responseData.setMessage(message);
        return responseData;
    }
}
