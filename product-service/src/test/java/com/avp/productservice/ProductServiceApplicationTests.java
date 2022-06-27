package com.avp.productservice;

import com.avp.productservice.dto.ProductRequest;
import com.avp.productservice.dto.ProductResponse;
import com.avp.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer container = new MongoDBContainer("mongo:4.4.14");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                )
                .andExpect(status().isCreated());
        assertEquals(1, productRepository.findAll().size());
    }

    @Test
    public void shouldGetAllProducts() throws Exception {
        List<ProductResponse> productResponseList = List.of(getProductResponse(1), getProductResponse(2));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productResponseList))
                )
                .andExpect(status().isOk());
        assertEquals(2, productResponseList.size());
    }

    private ProductResponse getProductResponse(int i) {
        return ProductResponse.builder()
                .name("Iphone" + i)
                .description("Iphone" + i)
                .price(BigDecimal.valueOf(i))
                .build();
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Iphone13")
                .description("iphone 13")
                .price(BigDecimal.valueOf(1000))
                .build();
    }

}
