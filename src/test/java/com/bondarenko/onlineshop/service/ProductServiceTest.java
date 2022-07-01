package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.exceptions.ProductNotFoundExceptions;
import com.bondarenko.onlineshop.repositary.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product firstProduct;
    private Product secondProduct;

    @BeforeEach
    void setUp() {
        firstProduct = Product.builder()
                .name("TV")
                .price(100)
                .creationDate(LocalDateTime.MIN)
                .id(1)
                .build();

        secondProduct = Product.builder()
                .name("snowboard")
                .price(100)
                .creationDate(LocalDateTime.MIN)
                .id(1)
                .build();
    }

    @Test
    @DisplayName("when Find All then Correct Data Return")
    public void whenFindAll_thenCorrectDataReturn() {
        List<Product> productList = Arrays.asList(firstProduct, secondProduct);
        given(this.productRepository.findAll())
                .willReturn(productList);

        Product actualProduct = productService.findAll().get(0);

        assertEquals(firstProduct, actualProduct);
        assertEquals("TV", actualProduct.getName());
        assertEquals(100, actualProduct.getPrice());
    }

    @Test
    @DisplayName("when Find All then Correct Quantity Of Products Return")
    public void whenFindAll_thenCorrectQuantityOfProductsReturn() {
        List<Product> productList = Arrays.asList(firstProduct, secondProduct);
        given(this.productRepository.findAll())
                .willReturn(productList);

        int actualSize = productService.findAll().size();
        assertEquals(2, actualSize);
    }

    @Test
    @DisplayName("when Find By Id then Correct Product Return")
    public void whenFindById_thenCorrectProductReturn() throws ProductNotFoundExceptions {
        given(this.productRepository.findById(1))
                .willReturn(Optional.ofNullable(secondProduct));

        Product searchedProduct = productService.findById(1).get();

        assertEquals("snowboard", searchedProduct.getName());
        assertEquals(100, searchedProduct.getPrice());
    }

    @Test
    @DisplayName("when Find By Name then Correct Name Of Product Return")
    public void whenFindByName_thenCorrectNameOfProductReturn() {
        given(this.productRepository.findProductByNameIgnoreCase("TV"))
                .willReturn(List.of(firstProduct));

        Product actualProduct = productService.search("TV").get(0);

        assertEquals("TV", actualProduct.getName());
    }

    @Test
    @DisplayName("when Find By Name With Ignore Case then Correct Name Of Product Return")
    public void whenFindByNameWithIgnoreCase_thenCorrectNameOfProductReturn() {
        given(this.productRepository.findProductByNameIgnoreCase("tv"))
                .willReturn(List.of(firstProduct));

        Product actualProduct = productService.search("tv").get(0);

        assertEquals("TV", actualProduct.getName());
    }

    @Test
    @DisplayName("when Find All in Empty List then Then Size Of List isNull")
    public void whenFindAllInEmptyList_thenThenSizeOfListIsNull() {
        List<Product> productList = List.of();
        given(this.productRepository.findAll())
                .willReturn(productList);

        int actualSize = productService.findAll().size();
        assertEquals(0, actualSize);
    }

    @Test
    @DisplayName("when Search then Find Product By Name Ignore Case Called")
    public void whenSearch_thenFindProductByNameIgnoreCaseCalled() {
        productService.search("TV");

        verify(productRepository).findProductByNameIgnoreCase("TV");
    }
}