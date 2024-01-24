package com.inditex.myapp.infrastructure.controller;

import com.inditex.myapp.domain.exception.MyAppException;
import com.inditex.myapp.infrastructure.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice(basePackageClasses = ProductController.class)
public class ProductControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public Mono<ResponseEntity<Void>> handleNotFound() {
        return Mono.just(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(MyAppException.class)
    public Mono<ResponseEntity<Void>> handleGeneral() {
        return Mono.just(ResponseEntity.internalServerError().build());
    }

}
