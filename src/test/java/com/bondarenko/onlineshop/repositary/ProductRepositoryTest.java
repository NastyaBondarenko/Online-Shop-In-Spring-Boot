package com.bondarenko.onlineshop.repositary;

import com.bondarenko.onlineshop.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product savedProduct;
    private Product anotherSavedProduct;

    @BeforeEach
    void setUp() {
        Product firstProduct =
                Product.builder()
                        .id(1)
                        .name("TV")
                        .price(3000)
                        .creationDate(LocalDateTime.now())
                        .build();

        Product secondProduct = Product.builder()
                .id(2)
                .name("TV")
                .price(10000)
                .creationDate(LocalDateTime.now())
                .build();

        savedProduct = productRepository.save(firstProduct);
        anotherSavedProduct = productRepository.save(secondProduct);
    }

    @Test
    @DisplayName("when Save Product then Saved Product is Not Null")
    public void whenSaveProduct_thenSavedProductIsNotNull() {
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getName());
        assertNotNull(savedProduct.getPrice());
        assertNotNull(savedProduct.getId());
    }

    @Test
    @DisplayName("when FindAll then Correct Quantity Of ProductsReturn")
    public void whenFindAll_thenCorrectQuantityOfProductsReturn() {
        List<Product> productList = productRepository.findAll();
        assertEquals(2, productList.size());
    }

    @Test
    @DisplayName("given Saved Products when Find All then List Of Products Return")
    public void givenSavedProducts_whenFindAll_thenListOfProductsReturn() {
        List<Product> productList = productRepository.findAll();

        assertNotNull(productList);
        assertTrue(productList.contains(savedProduct));
        assertTrue(productList.contains(anotherSavedProduct));
    }

    @Test
    @DisplayName("when Find By Id Existing Product then Searched Product is Not Null")
    public void whenFindById_ExistingProduct_thenSearchedProduct_isNotNull() {

        Optional<Product> existingProduct = productRepository.findById(1);
        assertNotNull(existingProduct);
    }

    @Test
    @DisplayName("when Find By Id Not Existing Product then Searched Product is Empty")
    public void whenFindById_NotExistingProduct_thenSearchedProduct_isEmpty() {
        Optional<Product> notExistingProduct = productRepository.findById(101);
        assertThat(notExistingProduct).isEmpty();
    }

    @Test
    @DisplayName("when Update Product then Updated Product Return")
    public void whenUpdateProduct_thenUpdatedProductReturn() {
        Product actualProduct = productRepository.findById(anotherSavedProduct.getId()).get();

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
        Product actualProduct = productRepository.findById(anotherSavedProduct.getId()).get();

        actualProduct.setName("snowboard");
        actualProduct.setPrice(5000);
        productRepository.save(actualProduct);

        assertEquals(2, listOfProducts.size());
    }

    @Test
    @DisplayName("when Delete Product then Removed Product is Empty")
    public void whenDeleteProduct_thenRemovedProductIsEmpty() {
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

    @Test
    @DisplayName("when Search Product By Name then Quantity Of Searched Products Is Correct")
    public void whenSearchProductsByName_thenQuantityOfSearchedProducts_isCorrect() {

        List<Product> listOfSearchedProducts = productRepository.findProductByNameIgnoreCase("TV");

        assertEquals(2, listOfSearchedProducts.size());
    }

    @Test
    @DisplayName("when Search Product By Name then Appropriate Product Return")
    public void whenSearchProductByName_thenAppropriateProductReturn() {

        List<Product> listOfSearchedProducts = productRepository.findProductByNameIgnoreCase("TV");

        assertEquals("TV", savedProduct.getName());
        assertTrue(listOfSearchedProducts.contains(savedProduct));

        assertEquals("TV", anotherSavedProduct.getName());
        assertTrue(listOfSearchedProducts.contains(anotherSavedProduct));
    }

    @Test
    @DisplayName("when Search By Name Not Existing Product then List Of Searched Products is Empty")
    public void whenSearchByName_NotExistingProduct_thenListOfSearchedProducts_isEmpty() {

        List<Product> listOfSearchedProducts = productRepository.findProductByNameIgnoreCase("boll");
        listOfSearchedProducts.isEmpty();
    }

    @Test
    @DisplayName("when Find By Id Not Existing Product then No Such Element Exception Return")
    public void whenFindByIdNotExistingProduct_thenNoSuchElementExceptionReturn() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            Product product = productRepository.findById(5).get();
            assertEquals(product.getName(), "TV");
            assertEquals(product.getPrice(), 10000);
        });
    }

    @Test
    @DisplayName("when Find By Null Id then InvalidDataAccessApiUsageException Return")
    public void whenFindByNullId_thenInvalidDataAccessApiUsageExceptionReturn() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> {
            productRepository.findById(null).get();
        });
    }

    @Test
    @DisplayName("when Find Product By Name Ignore Case then Correct Data Return")
    public void whenFindProductByNameIgnoreCase_thenCorrectDataReturn() {
        List<Product> listOfProducts = productRepository.findProductByNameIgnoreCase("tv");
        assertEquals("TV", listOfProducts.get(0).getName());
        assertEquals(3000, listOfProducts.get(0).getPrice());
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