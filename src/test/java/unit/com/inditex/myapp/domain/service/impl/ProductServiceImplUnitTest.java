package unit.com.inditex.myapp.domain.service.impl;

import com.inditex.myapp.domain.service.ExistingApiService;
import com.inditex.myapp.domain.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplUnitTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ExistingApiService existingApiService;

    @Test
    void productSimilarUnitTest() {
        String productId = "TEST";

        List<String> similarIdList = Collections.singletonList("SIMILARTEST");
        Mockito.when(existingApiService.getSimilarProducts(Mockito.anyString())).thenReturn(Flux.fromIterable(similarIdList));

        productService.productSimilar(productId);

        Mockito.verify(existingApiService).getSimilarProducts(productId);
    }
}
