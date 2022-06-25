package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.repositary.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product =
                Product.builder()
                        .id(1)
                        .name("TV")
                        .price(3000)
                        .creationDate(LocalDateTime.now())
                        .build();

        productRepository.findProductByNameIgnoreCase("TV");

    }

    @Test
    @DisplayName("Get Data based on Valida Department Name")
    public void whenValidDepartmentName_thenDepartmentShouldFound() {
        String name = "TV";
        Product found = (Product) productService.findProductByName(name);
        assertEquals(name, found.getName());
    }

//    @Test
//    @DisplayName("Get Data based on Valida Department Name")
//    public void whenValidDepfartmentName_thenDepartmentShouldFound() {
//        productRepository.save(product);
//        List<Product> list = productService.findAll();
//        assertEquals(1, list.size());
//
//    }

}