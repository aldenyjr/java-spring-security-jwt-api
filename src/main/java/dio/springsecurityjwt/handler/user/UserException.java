package dio.springsecurityjwt.handler.user;

import dio.springsecurityjwt.handler.BusinessException;

public class UserException extends BusinessException {
    public UserException(String message) {
        super(message);
    }
}
