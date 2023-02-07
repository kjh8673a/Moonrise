package moonrise.pjt3.commons.response;

import lombok.Data;

@Data
public class ResponseDto<T> {
    private int status_code;
    private String message;
    private T data;
}

