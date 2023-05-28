package com.inditex.myapp.domain.service.impl;

import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.domain.service.ExistingApiService;
import com.inditex.myapp.domain.service.ProductService;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ExistingApiService existingApiService;

    @Override
    public Flux<ProductDetail> productSimilar(String productId) {
        return getSimilarProductIds(productId)
                .flatMap(this::getProductDetail);
    }

    private Flux<String> getSimilarProductIds(String productId) {
        return existingApiService.getSimilarProducts(productId);
    }

    private Mono<ProductDetail> getProductDetail(String productId) {
        return existingApiService.getProduct(productId);
    }
}
