package cn.sustech.cs209backend.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MyException extends RuntimeException {

    private int code;
    private String message;


    public MyException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
