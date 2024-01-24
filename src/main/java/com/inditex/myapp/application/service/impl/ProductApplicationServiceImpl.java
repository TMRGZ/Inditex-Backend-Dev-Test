package com.inditex.myapp.application.service.impl;

import com.inditex.myapp.application.mapper.OutputProductDetailMapper;
import com.inditex.myapp.application.service.ProductApplicationService;
import com.inditex.myapp.domain.service.ProductService;
import com.inditex.myapp.infrastructure.controller.model.ProductDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductApplicationServiceImpl implements ProductApplicationService {

    private final ProductService productService;

    private final OutputProductDetailMapper outputProductDetailMapper;

    @Override
    public Mono<ResponseEntity<Flux<ProductDetailDto>>> getProductSimilar(String productId) {
        Flux<ProductDetailDto> similarProductDetailList = productService.productSimilar(productId)
                .map(outputProductDetailMapper::map);

        return Mono.just(ResponseEntity.ok(similarProductDetailList));
    }
}
