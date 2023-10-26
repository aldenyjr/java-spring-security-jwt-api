package dio.springsecurityjwt.handler.user;

import dio.springsecurityjwt.handler.ResponseError;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;

@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;
    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseError responseError(String status, HttpStatus statusCode, String message) {
        ResponseError responseError = new ResponseError();
        responseError.setStatus(status);
        responseError.setStatusCode(statusCode.value());
        responseError.setMessageError(message);
        return responseError;
    }

    @ExceptionHandler({UserException.class})
    private ResponseEntity<Object> handleUserException(UserException ex, WebRequest request){
        ResponseError error = responseError("CONFLICT", HttpStatus.CONFLICT, ex.getMessage());
        return handleExceptionInternal(ex, error, headers(), HttpStatus.CONFLICT, request);
    }

}
