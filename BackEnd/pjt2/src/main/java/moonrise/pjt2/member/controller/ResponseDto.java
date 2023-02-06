package moonrise.pjt2.member.controller;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private int status_code;
    private String message;
    private T data;
}
