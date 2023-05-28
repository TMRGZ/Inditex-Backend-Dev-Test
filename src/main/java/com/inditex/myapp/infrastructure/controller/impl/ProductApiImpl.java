package com.inditex.myapp.infrastructure.controller.impl;

import com.inditex.myapp.application.service.ProductApplicationService;
import com.inditex.myapp.infrastructure.controller.ProductApi;
import com.inditex.myapp.infrastructure.controller.model.ProductDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ProductApiImpl implements ProductApi {

    @Autowired
    private ProductApplicationService productApplicationService;

    @Override
    public Mono<ResponseEntity<Flux<ProductDetailDto>>> getProductSimilar(String productId, ServerWebExchange exchange) {
        return productApplicationService.getProductSimilar(productId);
    }
}
