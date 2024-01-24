package com.inditex.myapp.domain.service;

import com.inditex.myapp.domain.model.ProductDetail;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface ExistingApiService {

    Mono<ProductDetail> getProduct(String productId);

    Mono<Set<String>> getSimilarProducts(String productId);

}
