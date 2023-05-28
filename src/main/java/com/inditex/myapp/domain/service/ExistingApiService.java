package com.inditex.myapp.domain.service;

import com.inditex.myapp.domain.model.ProductDetail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExistingApiService {

    Mono<ProductDetail> getProduct(String productId);

    Flux<String> getSimilarProducts(String productId);

}
