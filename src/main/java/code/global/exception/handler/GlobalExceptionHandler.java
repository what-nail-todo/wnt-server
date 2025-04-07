package code.global.exception.handler;

import code.global.exception.entity.CustomErrorCode;
import code.global.exception.entity.ErrorCode;
import code.global.exception.entity.ErrorResponse;
import code.global.exception.entity.RestApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse<String>> handleRestApiException(RestApiException e){
        log.error("Error occur with : {}", e.getMessage());

        return handleExceptionInternal(e.getCode());
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse<String>> handleEndPointException(NoResourceFoundException e){
        log.error("Error occur with : {}", e.getMessage());

        return handleExceptionInternal(CustomErrorCode.NOT_VALID_API_ENDPOINT);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse<String>> handleSqlException(DataAccessException e){
        log.error("Error occur with : {}", e.getMessage());

        return handleExceptionInternal(CustomErrorCode.SQL_OPERATION_FAILED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse<List<String>>> handleValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        for (FieldError error : result.getFieldErrors()){
            String errorMessage = "[ " + error.getField() + " ]" +
                    "[ " + error.getDefaultMessage() + " ]" +
                    "[ " + error.getRejectedValue() + " ]";
            errorMessages.add(errorMessage);
        }

        log.error("Problem parameter : ");
        log.error("{}", errorMessages);

        return handleExceptionInternal(CustomErrorCode.DATA_VALIDATION_FAILED, errorMessages);
    }

    private ResponseEntity<ErrorResponse<String>> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ResponseEntity<ErrorResponse<List<String>>> handleExceptionInternal(ErrorCode errorCode, List<String> message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode, message));
    }

    private ErrorResponse<String> makeErrorResponse(ErrorCode errorCode){
        return ErrorResponse.<String>builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    private ErrorResponse<List<String>> makeErrorResponse(ErrorCode errorCode, List<String> message){
        return ErrorResponse.<List<String>>builder()
                .code(errorCode.getCode())
                .message(message)
                .build();
    }
}
