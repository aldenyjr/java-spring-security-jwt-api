package dio.springsecurityjwt.handler;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.cglib.proxy.UndeclaredThrowableException;
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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

//    @ExceptionHandler({Exception.class})
//    private ResponseEntity<Object> handleGeneral(Exception e, WebRequest request) {
//
////        if(e instanceof ExpiredJwtException){
////            ResponseError error = responseError("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, "Token vencido!");
////            return handleExceptionInternal(e, error, headers(), HttpStatus.UNAUTHORIZED, request);
////        }
//
//        if (e.getClass().isAssignableFrom(UndeclaredThrowableException.class)) {
//            UndeclaredThrowableException exception = (UndeclaredThrowableException) e;
//            return handleBusinessException((BusinessException) exception.getUndeclaredThrowable(), request);
//        } else {
//            String message = messageSource.getMessage("error.server", new Object[]{e.getMessage()}, null);
//            ResponseError error = responseError("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, message);
//            return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
//        }
//    }

    @ExceptionHandler({BusinessException.class})
    private ResponseEntity<Object> handleBusinessException(BusinessException e, WebRequest request){
        ResponseError error = responseError("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return handleExceptionInternal(e, error, headers(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({UnauthorizedException.class})
    private ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e, WebRequest request){
        ResponseError error = responseError("UNAUTHORIZED", HttpStatus.UNAUTHORIZED, e.getMessage());
        return handleExceptionInternal(e, error, headers(), HttpStatus.UNAUTHORIZED, request);
    }


}
