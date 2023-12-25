package cn.sustech.cs209backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.sustech.cs209backend.dto.GlobalResponse;
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(value = MyException.class)
    @ResponseBody
    public GlobalResponse<String> exceptionHandler(MyException e){
        log.warn("User Defined Exception ！Reason is :"+e);
        return GlobalResponse.<String>builder()
            .code(e.getCode())
            .msg(e.getMessage())
            .build();
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public GlobalResponse<String> exceptionHandler(Exception e){
        log.error("Unknow Exception！Reason is :"+e);
        return GlobalResponse.<String>builder()
            .code(-10086)
            .msg(e.getMessage() + e.getStackTrace())
            .build();
    }
}
