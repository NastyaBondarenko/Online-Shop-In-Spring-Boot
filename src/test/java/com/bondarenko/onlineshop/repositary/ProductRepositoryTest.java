package com.bondarenko.onlineshop.repositary;

import com.bondarenko.onlineshop.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    void setUp() {
        Product product = Product.builder()
                .name("TV")
                .price(3000)
                .creationDate(LocalDateTime.now())
                .build();

        entityManager.persist(product);
    }

    @Test
    @DisplayName("when Find Product By Name Ignore Case then Correct Data Return")
    public void whenFindProductByNameIgnoreCase_thenCorrectDataReturn() {
        List<Product> list = productRepository.findProductByNameIgnoreCase("tv");
        assertEquals("TV", list.get(0).getName());
        assertEquals(3000, list.get(0).getPrice());
    }

    @Test
    @DisplayName("when Find By Id With Incorrect Id then InvalidDataAccessApiUsageException Return")
    public void whenFindByIdWithIncorrectId_thenInvalidDataAccessApiUsageExceptionReturn() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            Product product = productRepository.findById(null).get();
            assertNull(product.getName());
            assertNull(product.getPrice());
        });
    }
}