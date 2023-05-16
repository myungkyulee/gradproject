package project.gradproject.exception;

public class AuthorizedException extends MyCustomException {
    public AuthorizedException(ErrorCode errorCode){
        super(errorCode);
    }
}
