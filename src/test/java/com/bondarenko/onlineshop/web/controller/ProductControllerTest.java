package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.service.ProductService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

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
    @DisplayName("when Add Product then Request Has Succeeded and Product Added")
    void whenAddProduct_thenRequestHasSucceeded_andProductAdded() throws Exception {
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
    @DisplayName("when Update Product then Request Has Succeeded and Product Updated")
    void whenUpdateProduct_thenRequestHasSucceeded_andProductUpdated() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.put("/products/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                "name":"Snowboard",
                                "price":"2000"
                                }"""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Find Product By Id then Request Has Succeeded")
    void whenFindProductById_thenRequestHasSucceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Search Product then Request Has Succeeded")
    void whenSearchProduct_thenRequestHasSucceeded() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products/search/TV")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("when Find All then Request Has Succeeded")
    void whenFindAll_thenRequestHasSucceeded() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("when Delete Product then Request Has Succeeded")
    void whenDeleteProduct_thenRequestHasSucceeded() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.delete("/products/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    @DisplayName("when Find Product By Id then Searched Product Return ")
    void whenFindById_thenSearchedProduct_Return() throws Exception {
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
        mockMvc.perform(MockMvcRequestBuilders.put("/products/IncorrectUrl")
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

}
