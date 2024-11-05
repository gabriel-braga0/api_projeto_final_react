package org.serratec.backend.apireact.exceptionhandler;

import org.serratec.backend.apireact.exception.EmailException;
import org.serratec.backend.apireact.exception.ErroResposta;
import org.serratec.backend.apireact.exception.SenhaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> erros = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erros.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        ErroResposta erroResposta = new ErroResposta(status.value(), "Existem campos inválidos", LocalDateTime.now(),
                erros);
        return ResponseEntity.status(status).body(erroResposta);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EmailException.class)
    private ResponseEntity<Object> handleEmailException(EmailException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

    @ExceptionHandler(SenhaException.class)
    private ResponseEntity<Object> handleSenhaException(SenhaException ex) {
        return ResponseEntity.unprocessableEntity().body(ex.getMessage());
    }

}
