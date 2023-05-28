package unit.com.inditex.myapp.application.service.impl;

import com.inditex.myapp.application.mapper.OutputProductDetailMapper;
import com.inditex.myapp.application.service.impl.ProductApplicationServiceImpl;
import com.inditex.myapp.domain.model.ProductDetail;
import com.inditex.myapp.domain.service.ProductService;
import com.inditex.myapp.infrastructure.controller.model.ProductDetailDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class ProductApplicationServiceImplUnitTest {

    @InjectMocks
    private ProductApplicationServiceImpl productApplicationService;

    @Mock
    private ProductService productService;

    @Mock
    private OutputProductDetailMapper outputProductDetailMapper;

    @Test
    void getProductSimilarUnitTest() {
        String productId = "TEST";

        List<ProductDetail> productDetailList = Collections.emptyList();
        Mockito.when(productService.productSimilar(Mockito.anyString())).thenReturn(Flux.fromIterable(productDetailList));

        Mono<ResponseEntity<Flux<ProductDetailDto>>> response = productApplicationService.getProductSimilar(productId);

        StepVerifier.create(response)
                .expectNextMatches(responseEntity ->
                        Objects.nonNull(responseEntity) && Objects.equals(responseEntity.getStatusCode(), HttpStatus.OK)
                )
                .expectComplete()
                .verify();

        Mockito.verify(productService).productSimilar(Mockito.anyString());
        Mockito.verify(outputProductDetailMapper, Mockito.times(productDetailList.size())).map(Mockito.anyList());
    }
}
