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
import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals("TV", savedProduct.getName());
        assertEquals(3000, savedProduct.getPrice());
    }

    @Test
    @DisplayName("when Save Product then Saved Product is Not Null")
    public void whenSaveProduct_thenSavedProductIsNotNull() {
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getName());
        assertNotNull(savedProduct.getPrice());
        assertNotNull(savedProduct.getId());
    }

    @DisplayName("when FindAll then Correct Quantity Of ProductsReturn")
    @Test
    public void whenFindAll_thenCorrectQuantityOfProducts_Return() {
        List<Product> productList = productRepository.findAll();
        assertEquals(2, productList.size());
    }

    @Test
    @DisplayName("when FindAll then All Elements Contains Of List")
    public void whenFindAll_thenAllElementsContainsOfList() {
        List<Product> productList = productRepository.findAll();

        assertNotNull(productList);
        assertTrue(productList.contains(savedProduct));
        assertTrue(productList.contains(searchedProduct));
    }

    @Test
    @DisplayName("when FindById Existing Product then Searched Product is Not Null")
    public void whenFindById_ExistingProduct_thenSearchedProduct_isNotNull() {

        Optional<Product> existingProduct = productRepository.findById(1);
        assertNotNull(existingProduct);
    }

    @Test
    @DisplayName("when FindById Not Existing Product then Searched Product is Empty")
    public void whenFindById_NotExistingProduct_thenSearchedProduct_isEmpty() {
        Optional<Product> notExistingProduct = productRepository.findById(101);
        assertThat(notExistingProduct).isEmpty();
    }

    @Test
    @DisplayName("when Update Product then Return Updated Product")
    public void whenUpdateProduct_thenUpdatedProductReturn() {
        Product actualProduct = productRepository.findById(searchedProduct.getId()).get();

        actualProduct.setName("snowboard");
        actualProduct.setPrice(5000);

        Product updatedProduct = productRepository.save(actualProduct);

        assertEquals(5000, updatedProduct.getPrice());
        assertEquals("snowboard", updatedProduct.getName());
    }

    @Test
    @DisplayName("when Update Product then Quantity Of Products Does Not Changes")
    public void whenUpdateProduct_thenQuantityOfProducts_DoesNotChanges() {
        List<Product> listOfProducts = productRepository.findAll();
        assertEquals(2, listOfProducts.size());
        Product actualProduct = productRepository.findById(searchedProduct.getId()).get();

        actualProduct.setName("snowboard");
        actualProduct.setPrice(5000);
        productRepository.save(actualProduct);

        assertEquals(2, listOfProducts.size());
    }

    @Test
    @DisplayName("when Delete Product then Removed Product is Empty")
    public void whenDeleteProduct_thenRemovedProduct_isEmpty() {
        Product newProduct = Product.builder()
                .id(3)
                .name("board")
                .price(9000)
                .creationDate(LocalDateTime.now())
                .build();

        Product savedNewProduct = productRepository.save(newProduct);

        productRepository.deleteById(savedNewProduct.getId());
        Optional<Product> deletedProduct = productRepository.findById(3);

        assertThat(deletedProduct).isEmpty();
    }


}