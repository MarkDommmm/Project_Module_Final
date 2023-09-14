package ra.security.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityExistsException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> loginFail(LoginException loginException) {
        return new ResponseEntity<>(loginException.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> loginFails(AccessDeniedException loginException) {
        return new ResponseEntity<>("Ban khong du tu cach truy cap", HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<String> CheckUsername(EntityExistsException entityExistsException) {
        return new ResponseEntity<>(entityExistsException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validateUsername(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> err = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    err.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
        );
        return new ResponseEntity<>(err, HttpStatus.FORBIDDEN);
    }
}
