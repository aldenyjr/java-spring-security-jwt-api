package dio.springsecurityjwt.handler;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
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
public class JwtException extends ResponseEntityExceptionHandler {

    @Resource
    private MessageSource messageSource;

    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private ResponseError responseError(String status, HttpStatus statusCode, String message){
        ResponseError responseError = new ResponseError();
        responseError.setStatus(status);
        responseError.setStatusCode(statusCode.value());
        responseError.setMessageError(message);
        return responseError;
    }

    @ExceptionHandler({ExpiredJwtException.class})
    private ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request){
        ResponseError error = responseError("Unauthorized", HttpStatus.UNAUTHORIZED, ex.getMessage());
        return handleExceptionInternal(ex,error,headers(),HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({MalformedJwtException.class})
    private ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex, WebRequest request){
        ResponseError error = responseError("Unauthorized", HttpStatus.UNAUTHORIZED, ex.getMessage());
        return handleExceptionInternal(ex,error,headers(),HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({SignatureException.class})
    private ResponseEntity<Object> handleSignatureException(SignatureException ex, WebRequest request){
        ResponseError error = responseError("Unauthorized", HttpStatus.UNAUTHORIZED, ex.getMessage());
        return handleExceptionInternal(ex,error,headers(),HttpStatus.UNAUTHORIZED, request);
    }
}
