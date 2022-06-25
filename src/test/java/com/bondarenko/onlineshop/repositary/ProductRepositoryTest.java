package com.bondarenko.onlineshop.repositary;

import com.bondarenko.onlineshop.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
      Product  product = Product.builder()
                .name("TV")
                .price(3000)
                .creationDate(LocalDateTime.now())
                .build();

        entityManager.persist(product);
    }

    @Test
    public void whenFindById_thenReturn() {
        Product product = productRepository.findById(1).get();
        assertEquals(product.getName(), "TV");
    }

}