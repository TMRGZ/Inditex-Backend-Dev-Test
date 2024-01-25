package com.inditex.myapp.domain.repository;

import com.inditex.myapp.domain.model.ProductDetail;
import reactor.core.publisher.Mono;

public interface ReactiveProductDetailRepository {

    Mono<ProductDetail> save(ProductDetail productDetail);
}
