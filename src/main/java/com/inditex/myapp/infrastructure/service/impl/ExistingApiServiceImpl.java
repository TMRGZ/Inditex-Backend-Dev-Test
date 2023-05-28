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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Service
public class ExistingApiServiceImpl implements ExistingApiService {

    @Autowired
    private DefaultApi defaultApi;

    @Autowired
    private InputProductDetailMapper inputProductDetailMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Mono<ProductDetail> getProduct(String productId) {
        return defaultApi.getProductProductId(productId)
                .delayElement(Duration.ofSeconds(1))
                .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Mono.error(ProductNotFoundException::new))
                .onErrorResume(WebClientResponseException.InternalServerError.class, internalServerError -> Mono.error(ExistingApisErrorException::new))
                .map(inputProductDetailMapper::map);
    }

    @Override
    public Flux<String> getSimilarProducts(String productId) {
        return defaultApi.getProductSimilarids(productId)
                .onErrorResume(WebClientResponseException.NotFound.class, notFound -> Flux.error(ProductNotFoundException::new))
                .onErrorResume(WebClientResponseException.InternalServerError.class, internalServerError -> Flux.error(ExistingApisErrorException::new))
                .flatMapIterable(this::mapToFlux);
    }

    private List<String> mapToFlux(String s) {
        try {
            return objectMapper.readValue(s, TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            throw new MyAppException(e);
        }
    }
}
