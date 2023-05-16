package project.gradproject.exception;

public class LoginException extends MyCustomException {

    public LoginException(ErrorCode errorCode){
        super(errorCode);
    }
}
