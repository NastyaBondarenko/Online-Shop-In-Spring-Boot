package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerITest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .id(1)
                .name("TV")
                .price(3000)
                .creationDate(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("when Add Product with Correct Url And Http Method then Request Has Succeeded")
    void whenAddProduct_withCorrectUrlAndHttpMethod_thenRequestHasSucceeded() throws Exception {
        Product inputProduct = Product.builder()
                .id(1)
                .name("TV")
                .price(3000)
                .creationDate(LocalDateTime.now())
                .build();

        productService.add(inputProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"TV",
                                "price":"3000"
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Update Product with Correct Url And Http Method then Request Has Succeeded")
    void whenUpdateProduct_withCorrectUrlAndHttpMethod_thenRequestHasSucceeded() throws Exception {

        mockMvc.perform(put("/products/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"Snowboard",
                                "price":"2000"
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Find Product By Id with Correct Url And Http Method then Request Has Succeeded")
    void whenFindProductById_withCorrectUrlAndHttpMethod_thenRequestHasSucceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Search Product with Correct Url And Http Method then Request Has Succeeded")
    void whenSearchProduct_withCorrectUrlAndHttpMethod_thenRequestHasSucceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/TV")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Find All with Correct Url And HttpMethod then Request Has Succeeded")
    void whenFindAll_withCorrectUrlAndHttpMethod_thenRequestHasSucceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Delete Product with Correct Url And Http Method then Request Has Succeeded")
    void whenDeleteProduct_withCorrectUrlAndHttpMethod_thenRequestHasSucceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Find By Id with Correct Url And Http Method then Searched Product Return")
    void whenFindById_withCorrectUrlAndHttpMethod_thenSearchedProductReturn() throws Exception {
        Mockito.when(productService.findById(1))
                .thenReturn(Optional.ofNullable(product));
        mockMvc.perform(get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(product.getName()))
                .andExpect(jsonPath("$.price")
                        .value(product.getPrice()));
    }

    @Test
    @DisplayName("when Add Product with Incorrect Url then Method Not Allowed Return")
    void whenAddProduct_withIncorrectUrl_thenMethodNotAllowedReturn() throws Exception {
        Product inputProduct = Product.builder()
                .id(1)
                .name("TV")
                .price(3000)
                .creationDate(LocalDateTime.now())
                .build();

        productService.add(inputProduct);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/IncorrectUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"TV",
                                "price":"3000"
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("when Update Product with Incorrect Url then Method Not Allowed Return")
    void whenUpdateProduct_withIncorrectUrl_thenMethodNotAllowedReturn() throws Exception {
        mockMvc.perform(put("/products/IncorrectUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"Snowboard",
                                "price":"2000"
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("when Find Product By Id with Incorrect Id then Bad Request Return")
    void whenFindProductById_withIncorrectId_thenBadRequestReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/IncorrectId")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("when Update Product with Incorrect Url then Method Not Found Return")
    void whenUpdateProduct_withIncorrectUrl_thenMethodNotFoundReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/IncorrectUrl/TV")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("when Find All with Incorrect Url then Not Found Return")
    void whenFindAll_withIncorrectUrl_thenNotFoundReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/IncorrectUrl")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("when Delete Product with Incorrect Url then Not Found Return")
    void whenDeleteProduct_withIncorrectUrl_thenNotFoundReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/IncorrectUrl/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("when Delete Product with Incorrect Id then Bad Request Return")
    void whenDeleteProduct_withIncorrectId_thenBadRequestReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/incorrectId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("when Search Product with Incorrect Url then Bad Request Return")
    void whenSearchProduct_withIncorrectUrl_thenBadRequestReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search//")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("when Add Product with Incorrect Http Method then Method Not Allowed Return")
    void whenAddProduct_withIncorrectHttpMethod_thenMethodNotAllowedReturn() throws Exception {
        Product inputProduct = Product.builder()
                .id(1)
                .name("TV")
                .price(3000)
                .creationDate(LocalDateTime.now())
                .build();

        productService.add(inputProduct);

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"TV",
                                "price":"3000"
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("when Update Product with Incorrect Http Method then Method Not Allowed Return")
    void whenUpdateProduct_withIncorrectHttpMethod_thenMethodNotAllowedReturn() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/products/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"Snowboard",
                                "price":"2000"
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("when Find Product By Id with Incorrect Http Method then Method Not Allowed Return")
    void whenFindProductById_withIncorrectHttpMethod_thenMethodNotAllowedReturn() throws Exception {
        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("when Search ProductBy Name with Incorrect Http Method then Method Not Allowed Return")
    void whenSearchProductByName_withIncorrectHttpMethod_thenMethodNotAllowedReturn() throws Exception {
        mockMvc.perform(put("/products/search/TV")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("when Find All with Incorrect Http Method then Method Not Allowed Return")
    void whenFindAll_withIncorrectHttpMethod_thenMethodNotAllowedReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }


    @Test
    @DisplayName("when Delete Product with Incorrect Http Method then Method Not Allowed Return")
    void whenDeleteProduct_withIncorrectHttpMethod_thenMethodNotAllowedReturn() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("when Add Product with Empty Url then IllegalArgumentException Return")
    void whenAddProduct_withEmptyUrl_thenIllegalArgumentExceptionReturn() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            mockMvc.perform(MockMvcRequestBuilders.get(null)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        });
    }
}
