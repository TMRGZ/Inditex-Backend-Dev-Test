package unit.com.inditex.myapp.infrastructure.service.impl;

import com.inditex.myapp.application.model.ProductDetailResponse;
import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.infrastructure.exception.ExistingApisErrorException;
import com.inditex.myapp.infrastructure.exception.ProductNotFoundException;
import com.inditex.myapp.infrastructure.mapper.InputProductDetailMapper;
import com.inditex.myapp.infrastructure.rest.DefaultApi;
import com.inditex.myapp.infrastructure.service.impl.ExistingApiServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ExistingApiServiceImplUnitTest {

    @InjectMocks
    private ExistingApiServiceImpl existingApiService;

    @Mock
    private DefaultApi defaultApi;

    @Mock
    private InputProductDetailMapper inputProductDetailMapper;

    @Test
    void getProductUnitTest() {
        ProductDetailResponse response = new ProductDetailResponse();
        Mockito.when(defaultApi.getProductProductId(Mockito.anyString())).thenReturn(Mono.just(response));

        existingApiService.getProduct("TEST");

        Mockito.verify(defaultApi).getProductProductId(Mockito.anyString());
    }

    @Test
    void getProductButClientErrorExceptionUnitTest() {
        String productId = "TEST";

        Mockito.when(defaultApi.getProductProductId(productId))
                .thenReturn(Mono.error(WebClientResponseException.create(HttpStatus.NOT_FOUND.value(), "", null, null, null)));

        Mono<ProductDetail> productDetailMono = existingApiService.getProduct(productId);

        StepVerifier.create(productDetailMono)
                .expectNext()
                .expectErrorMatches(throwable -> throwable instanceof ProductNotFoundException)
                .verify();
    }

    @Test
    void getProductButServerErrorExceptionUnitTest() {
        String productId = "TEST";

        Mockito.when(defaultApi.getProductProductId(productId))
                .thenReturn(Mono.error(WebClientResponseException.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null, null, null)));

        Mono<ProductDetail> productDetailMono = existingApiService.getProduct(productId);

        StepVerifier.create(productDetailMono)
                .expectNext()
                .expectErrorMatches(throwable -> throwable instanceof ExistingApisErrorException)
                .verify();
    }

    @Test
    void getSimilarProductsUnitTest() {
        Mockito.when(defaultApi.getProductSimilarids(Mockito.anyString())).thenReturn(Flux.empty());

        Flux<String> similarProducts = existingApiService.getSimilarProducts("TEST");

        Assertions.assertNotNull(similarProducts);
        Mockito.verify(defaultApi).getProductSimilarids(Mockito.any());
    }

    @Test
    void getSimilarProductsButClientErrorExceptionUnitTest() {
        String productId = "TEST";

        Mockito.when(defaultApi.getProductSimilarids(productId))
                .thenReturn(Flux.error(WebClientResponseException.create(HttpStatus.NOT_FOUND.value(), "", null, null, null)));

        Flux<String> idFlux = existingApiService.getSimilarProducts(productId);

        StepVerifier.create(idFlux)
                .expectNext()
                .expectErrorMatches(throwable -> throwable instanceof ProductNotFoundException)
                .verify();
    }

    @Test
    void getSimilarProductsButServerErrorExceptionUnitTest() {
        String productId = "TEST";

        Mockito.when(defaultApi.getProductSimilarids(productId))
                .thenReturn(Flux.error(WebClientResponseException.create(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null, null, null)));

        Flux<String> idFlux = existingApiService.getSimilarProducts(productId);

        StepVerifier.create(idFlux)
                .expectNext()
                .expectErrorMatches(throwable -> throwable instanceof ExistingApisErrorException)
                .verify();
    }
}
