package com.inditex.myapp.infrastructure.controller;

import com.inditex.myapp.application.service.ProductApplicationService;
import com.inditex.myapp.infrastructure.controller.model.ProductDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApi {

    private final ProductApplicationService productApplicationService;

    @Override
    public Mono<ResponseEntity<Flux<ProductDetailDto>>> getProductSimilar(String productId, ServerWebExchange exchange) {
        return productApplicationService.getProductSimilar(productId);
    }
}
