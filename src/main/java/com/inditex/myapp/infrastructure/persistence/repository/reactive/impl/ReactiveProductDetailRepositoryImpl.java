package com.inditex.myapp.infrastructure.persistence.repository.reactive.impl;

import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.domain.repository.ReactiveProductDetailRepository;
import com.inditex.myapp.infrastructure.persistence.repository.imperative.ProductDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class ReactiveProductDetailRepositoryImpl implements ReactiveProductDetailRepository {

    private final ProductDetailRepository productDetailRepository;

    @Override
    public Mono<ProductDetail> save(ProductDetail productDetail) {
//        return Mono.fromCallable(() -> productDetailRepository.save(productDetail))
//                .subscribeOn(Schedulers.boundedElastic());
        return Mono.just(productDetailRepository.save(productDetail));
    }
}
