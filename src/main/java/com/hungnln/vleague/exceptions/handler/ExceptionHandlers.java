package com.hungnln.vleague.exceptions.handler;

import com.hungnln.vleague.constant.validation_message.ValidationMessage;
import com.hungnln.vleague.exceptions.*;
import com.hungnln.vleague.response.ListResponseDTO;
import com.hungnln.vleague.response.ResponseDTO;
import com.hungnln.vleague.constant.response.ResponseStatusDTO;
import com.hungnln.vleague.response.ResponseWithTotalPage;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlers extends RuntimeException{
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Object> notFoundException(NotFoundException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = ListEmptyException.class)
    public ResponseEntity<Object> listEmptyException(ListEmptyException exception) {
        ListResponseDTO dto = new ListResponseDTO();
        dto.setMessage(exception.getMessage());
        List data = new ArrayList();
        dto.setData(data);
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.ok().body(dto);
    }
    @ExceptionHandler(value = ExistException.class)
    public ResponseEntity<Object> existException(ExistException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = NotNullException.class)
    public ResponseEntity<Object> notNullException(NotNullException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }@ExceptionHandler(value = EmptyException.class)
    public ResponseEntity<Object> emptyException(EmptyException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(value = NotValidException.class)
    public ResponseEntity<Object>notValidException(NotValidException exception) {
        ResponseDTO dto = new ResponseDTO();
        dto.setMessage(exception.getMessage());
        dto.setStatus(ResponseStatusDTO.FAILURE);
        return ResponseEntity.badRequest().body(dto);
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ResponseDTO dto = new ResponseDTO();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        dto.setMessage("ValidationMessage.");
        dto.setStatus(ResponseStatusDTO.FAILURE);
        dto.setData(errors);
        return ResponseEntity.badRequest().body(dto);
    }
}
