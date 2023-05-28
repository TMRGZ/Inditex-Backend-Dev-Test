package com.inditex.myapp.domain.service;

import com.inditex.myapp.domain.model.ProductDetail;
import reactor.core.publisher.Flux;

public interface ProductService {

    Flux<ProductDetail> productSimilar(String productId);

}
