package project.gradproject.exception;

public class MyCustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String message;

    public MyCustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }

    @Override
    public String toString() {
        return message;
    }
}
