package com.inditex.myapp.infrastructure.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.inditex.myapp.domain.exception.MyAppException;
import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.domain.service.ExistingApiService;
import com.inditex.myapp.infrastructure.exception.ExistingApisErrorException;
import com.inditex.myapp.infrastructure.exception.ProductNotFoundException;
import com.inditex.myapp.infrastructure.mapper.InputProductDetailMapper;
import com.inditex.myapp.infrastructure.rest.DefaultApi;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ExistingApiServiceImpl implements ExistingApiService {

    private final DefaultApi defaultApi;

    private final InputProductDetailMapper inputProductDetailMapper;

    @Override
    public Mono<ProductDetail> getProduct(String productId) {
        return defaultApi.getProductProductId(productId)
                .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.error(ProductNotFoundException::new))
                .onErrorResume(WebClientResponseException.InternalServerError.class, internalServerError -> Mono.error(ExistingApisErrorException::new))
                .map(inputProductDetailMapper::map);
    }

    @Override
    public Mono<Set<String>> getSimilarProducts(String productId) {
        return defaultApi.getProductSimilarids(productId)
                .onErrorMap(WebClientResponseException.NotFound.class, ProductNotFoundException::new)
                .onErrorMap(WebClientResponseException.InternalServerError.class, ExistingApisErrorException::new);
    }
}
