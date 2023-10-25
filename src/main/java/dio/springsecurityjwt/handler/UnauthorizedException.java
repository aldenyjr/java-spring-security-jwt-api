package dio.springsecurityjwt.handler;

public class UnauthorizedException extends BusinessException{
    public UnauthorizedException(String message) {
        super(message);
    }
}
