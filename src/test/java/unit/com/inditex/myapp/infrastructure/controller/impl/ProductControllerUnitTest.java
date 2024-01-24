package unit.com.inditex.myapp.infrastructure.controller.impl;

import com.inditex.myapp.application.service.ProductApplicationService;
import com.inditex.myapp.infrastructure.controller.ProductController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ServerWebExchange;

@ExtendWith(MockitoExtension.class)
class ProductControllerUnitTest {

    @InjectMocks
    private ProductController productApi;

    @Mock
    private ProductApplicationService productApplicationService;

    @Test
    void getProductSimilarUnitTest() {
        productApi.getProductSimilar("TEST", Mockito.mock(ServerWebExchange.class));

        Mockito.verify(productApplicationService).getProductSimilar(Mockito.anyString());
    }
}
