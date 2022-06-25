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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    @DisplayName("add Product")
    void addProduct() throws Exception {
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
    @DisplayName("update Product")
    void updateProduct() throws Exception {
        Product inputProduct = Product.builder()
                .id(1)
                .name("TV")
                .price(3000)
                .creationDate(LocalDateTime.now())
                .build();

        productService.update(1,inputProduct);

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
    @DisplayName("delete Product")
    void deleteProduct() throws Exception {
        productService.delete(1);

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
    @DisplayName("when Find Product By Id then Searched Product Return ")
    void whenFindById_thenSearchedPro8duct_Return() throws Exception {
        productService.delete(1);
        mockMvc.perform(delete("/products/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertNull(product);

    }
}
