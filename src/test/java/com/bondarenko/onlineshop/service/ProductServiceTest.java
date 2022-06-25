package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.repositary.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;
    private Product savedProduct;
    private Product searchedProduct;

    @BeforeEach
    void setUp() {
        Product product =
                Product.builder()
                        .id(1)
                        .name("TV")
                        .price(3000)
                        .creationDate(LocalDateTime.now())
                        .build();

         searchedProduct = Product.builder()
                .id(2)
                .name("TV")
                .price(10000)
                .creationDate(LocalDateTime.now())
                .build();

        savedProduct = productRepository.save(product);
        searchedProduct = productRepository.save(searchedProduct);


    }

    @Test
    @DisplayName("when Save Product then Correct Product Parameters Return")
    public void whenSaveProduct_thenCorrectProductParametersReturn() {

        assertNotNull(savedProduct);
        assertEquals("TV", savedProduct.getName());
        assertEquals(3000, savedProduct.getPrice());
        assertEquals(6, savedProduct.getId());
    }

    @Test
    @DisplayName("when Save Product then Saved Product is Not Null")
    public void whenSaveProduct_thenSavedProduct_isNotNull() {
        product = Product.builder()
                .id(2)
                .name("TV")
                .price(3000)
                .creationDate(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getName());
        assertNotNull(savedProduct.getPrice());
        assertNotNull(savedProduct.getId());
    }

    @DisplayName("when FindAll then Correct Quantity Of ProductsReturn")
    @Test
    public void whenFindAll_thenCorrectQuantityOfProducts_Return() {
        List<Product> productList = productRepository.findAll();

        assertThat(productList).isNotNull();
        assertThat(productList.size()).isEqualTo(2);
    }

    @DisplayName("given Saved Product when FindById then Searched Product is Not Null")
    @Test
    public void givenSavedProduct_whenFindById_thenSearchedProduct_isNotNull() {

        Optional<Product> product = productRepository.findById(1);

        assertNotNull(product);
    }

    @DisplayName("given Saved Product when Update Product then Return Updated Product")
    @Test
    public void givenSavedProduct_whenUpdateProduct_thenReturnUpdatedProduct() {

        Product actualProduct = productRepository.findById(searchedProduct.getId()).get();
        actualProduct.setName("snowboard");
        actualProduct.setPrice(5000);
        Product updatedProduct = productRepository.save(actualProduct);

        assertEquals(5000, updatedProduct.getPrice());
        assertEquals("snowboard", updatedProduct.getName());
    }


}