package dio.springsecurityjwt.handler;


public class BusinessException extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
