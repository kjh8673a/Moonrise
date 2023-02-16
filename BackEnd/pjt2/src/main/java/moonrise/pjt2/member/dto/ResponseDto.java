package moonrise.pjt2.member.dto;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private int status_code;
    private String message;
    private T data;
}
