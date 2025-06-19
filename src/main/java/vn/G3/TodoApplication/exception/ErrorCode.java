package vn.G3.TodoApplication.exception;

public enum ErrorCode {
    PASSWORD_INVALID(2, "password is invalid"),
    USERNAME_INVALID(1, "username is invalid"),
    AUTHENTICATION_INVALID(3, "You do not have permission to access this resource"),
    AUTHORIZED_INVALID(4, "Unauthorized, Please Login!"),
    EXIST_USER(5, "User exist! Please try again"),
    NOT_FOUND(6, "Task not found!"),
    USER_NOTFOUND(7, "User not found!");

    private int code;
    private String message;

    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
