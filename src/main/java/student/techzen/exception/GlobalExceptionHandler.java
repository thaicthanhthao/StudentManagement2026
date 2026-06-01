package student.techzen.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import student.techzen.dto.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) Bắt lỗi Validation (MethodArgumentNotValidException)
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApiResponse<Map<String, String>>> handerException(
        MethodArgumentNotValidException ex ,
        HttpServletRequest requestError
   ){
       Map<String, String> fieldErrors = new HashMap<>();

       ex.getBindingResult().getFieldErrors().forEach(error ->
               fieldErrors.put(
                       error.getField(),
                       error.getDefaultMessage())
       );

       return ResponseEntity.badRequest().body(
               ApiResponse.<Map<String, String>>builder()
                       .success(false)
                       .data(fieldErrors)
                       .error(ApiResponse.ApiError.builder()
                               .code("VALIDATION_FAILED")
                               .message("Input validation failed")
                               .path(requestError.getRequestURI())
                               .build())
                       .build()
       );
   }

    // 2) Bắt ResponseStatusException (ném từ Service)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatus(
            ResponseStatusException ex,
            HttpServletRequest request) {

        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());

        return ResponseEntity.status(status).body(
                ApiResponse.<Void>builder()
                        .success(false)
                        .error(ApiResponse.ApiError.builder()
                                .code(status.name())
                                .message(ex.getReason())
                                .path(request.getRequestURI())
                                .build())
                        .build()
        );
    }

    // 3) Bắt mọi exception không mong đợi (fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.<Void>builder()
                        .success(false)
                        .error(ApiResponse.ApiError.builder()
                                .code("INTERNAL_ERROR")
                                .message("An unexpected error occurred")
                                .path(request.getRequestURI())
                                .build())
                        .build()
        );
    }
}
