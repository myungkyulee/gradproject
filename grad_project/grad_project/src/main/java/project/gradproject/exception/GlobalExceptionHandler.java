package project.gradproject.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String loginExceptionHandler(LoginException loginException) {
        return "redirect:/login";
    }

    @ExceptionHandler
    public String authorizedExceptionHandler(AuthorizedException authorizedException) {
        return "redirect:/login";
    }

    /*@ExceptionHandler
    @ResponseBody
    public String exceptionHandler(Exception exception) {
        return "errorPage를 반환해야 합니다.";
    }*/
}
