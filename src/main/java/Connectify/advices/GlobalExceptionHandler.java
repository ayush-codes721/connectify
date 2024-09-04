package Connectify.advices;

import io.jsonwebtoken.JwtException;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiError> apiError(Exception e) {

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .error(e.getMessage())
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JwtException.class)
    ResponseEntity<ApiError> jwtError(JwtException e) {

        ApiError apiError = ApiError.builder().status(HttpStatus.BAD_REQUEST).build();

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
