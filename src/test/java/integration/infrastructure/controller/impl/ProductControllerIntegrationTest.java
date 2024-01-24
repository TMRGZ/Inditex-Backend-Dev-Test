package integration.infrastructure.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.inditex.myapp.MyAppApplication;
import com.inditex.myapp.application.model.ProductDetailResponse;
import com.inditex.myapp.infrastructure.controller.model.ProductDetailDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

@AutoConfigureWebTestClient
@AutoConfigureWireMock(port = 0)
@SpringBootTest(classes = MyAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest {

    private static final String SIMILAR_PRODUCTS_URL = "/product/{productId}/similar";
    private static final String EXISTING_API_SIMILAR_PRODUCTS_URL = "/product/{productId}/similarids";
    private static final String EXISTING_API_PRODUCT_URL = "/product/{productId}";
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProductSimilarIntegrationTest() throws Exception {
        String existingProduct = "EXISTING_PRODUCT";
        String relatedProduct = "RELATED_PRODUCT";

        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setId(relatedProduct);
        productDetailResponse.setName(relatedProduct);
        productDetailResponse.setPrice(BigDecimal.valueOf(0));
        productDetailResponse.setAvailability(false);

        URI existingApiSimilarProductUri = UriComponentsBuilder.fromUriString(EXISTING_API_SIMILAR_PRODUCTS_URL).build(existingProduct);
        WireMock.stubFor(WireMock.get(urlEqualTo(existingApiSimilarProductUri.getPath()))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(Arrays.asList(relatedProduct, relatedProduct)))
                )
        );

        URI existingApiProductUri = UriComponentsBuilder.fromUriString(EXISTING_API_PRODUCT_URL).build(relatedProduct);
        WireMock.stubFor(WireMock.get(urlEqualTo(existingApiProductUri.getPath()))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(productDetailResponse))
                )
        );

        URI myAppUri = UriComponentsBuilder.fromUriString(SIMILAR_PRODUCTS_URL).build(existingProduct);
        List<ProductDetailDto> result = webTestClient.get().uri(myAppUri.toString())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ProductDetailDto.class).returnResult().getResponseBody();

        Assertions.assertNotNull(result);

        result.forEach(productDetailDto -> {
            Assertions.assertNotNull(productDetailDto);
            Assertions.assertNotNull(productDetailDto.getId());
            Assertions.assertNotNull(productDetailDto.getName());
            Assertions.assertNotNull(productDetailDto.getPrice());
            Assertions.assertNotNull(productDetailDto.getAvailability());
        });
    }

    @Test
    void getProductSimilarButSimilarProductNotFoundIntegrationTest() throws Exception {
        String existingProduct = "EXISTING_PRODUCT";
        String relatedProduct = "RELATED_PRODUCT";

        URI existingApiSimilarProductUri = UriComponentsBuilder.fromUriString(EXISTING_API_SIMILAR_PRODUCTS_URL).build(existingProduct);
        WireMock.stubFor(WireMock.get(urlEqualTo(existingApiSimilarProductUri.getPath()))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(objectMapper.writeValueAsString(Collections.singletonList(relatedProduct)))
                )
        );

        URI existingApiProductUri = UriComponentsBuilder.fromUriString(EXISTING_API_PRODUCT_URL).build(relatedProduct);
        WireMock.stubFor(WireMock.get(urlEqualTo(existingApiProductUri.getPath()))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.NOT_FOUND.value())
                )
        );

        URI myAppUri = UriComponentsBuilder.fromUriString(SIMILAR_PRODUCTS_URL).build(existingProduct);
        webTestClient.get().uri(myAppUri.toString())
                .exchange()
                .expectStatus().isNotFound();
    }

}

