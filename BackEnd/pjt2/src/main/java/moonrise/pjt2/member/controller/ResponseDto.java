package moonrise.pjt2.member.controller;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private int Status;
    private String message;
    private T data;
}
