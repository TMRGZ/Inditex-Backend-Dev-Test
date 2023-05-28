package com.inditex.myapp.application.service;

import com.inditex.myapp.infrastructure.controller.model.ProductDetailDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductApplicationService {

    Mono<ResponseEntity<Flux<ProductDetailDto>>> getProductSimilar(String productId);

}
