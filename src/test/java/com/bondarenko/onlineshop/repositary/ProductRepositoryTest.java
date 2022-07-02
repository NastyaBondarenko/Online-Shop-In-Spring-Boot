package com.bondarenko.onlineshop.repositary;

import com.bondarenko.onlineshop.entity.Product;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DBRider
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DataSet("products.yml")
    @DisplayName("when Find All then Correct Size Of Products List Return")
    public void whenFindAll_thenCorrectSizeOfProductsListReturn() {
        productRepository.findAll();
        assertEquals(3, productRepository.findAll().size());
    }

    @Test
    @DataSet("products.yml")
    @DisplayName("when Find All then Correct Product Return")
    public void whenFindAll_thenCorrectProductReturn() {

        String actualName = productRepository.findAll().get(0).getName();
        double actualPrice = productRepository.findAll().get(0).getPrice();

        LocalDateTime actualCreationDate = productRepository.findAll().get(0).getCreationDate();
        LocalDateTime expectedCreationDate = Timestamp.valueOf("2021-01-01 00:00:00.000000").toLocalDateTime();

        assertEquals("TV", actualName);
        assertEquals(3000.0, actualPrice);
        assertEquals(expectedCreationDate, actualCreationDate);
    }

    @Test
    @DataSet("products.yml")
    @DisplayName("when Delete ById then Product Is Not Exist")
    public void whenDeleteById_thenProductIsNotExist() {

        productRepository.deleteById(2);

        assertFalse(productRepository.existsById(2));
    }

    @Test
    @DataSet("products.yml")
    @DisplayName("when Delete not Existing Product By Id then EmptyResultDataAccessException Return")
    public void whenDelete_notExistingProductById_thenEmptyResultDataAccessExceptionReturn() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            productRepository.deleteById(100);
        });
    }

    @Test
    @DataSet("products.yml")
    @DisplayName("when Delete Existing Product then Size Of Products List will Decrease By One")
    public void whenDeleteExistingProduct_thenSizeOfProductsList_willDecreaseByOne() {

        assertEquals(3, productRepository.findAll().size());

        productRepository.deleteById(2);

        assertEquals(2, productRepository.findAll().size());
    }

    @Test
    @DataSet("products.yml")
    @DisplayName("when Search Product then Correct Quantity Of Products Return")
    public void whenSearchProduct_thenCorrectQuantityOfProductsReturn() {

        int actualSizeOfProductsList = productRepository.findProductByNameIgnoreCase("TV").size();

        assertEquals(2, actualSizeOfProductsList);
    }

    @Test
    @DataSet("products.yml")
    @DisplayName("when Search Not Existing Product By Name then Quantity Of Searched Products is Null")
    public void whenSearchNotExistingProductByName_thenQuantityOfSearchedProducts_isNull() {

        int actualSizeOfProductsList = productRepository.findProductByNameIgnoreCase("NotExistingProduct").size();

        assertEquals(0, actualSizeOfProductsList);
    }

    @Test
    @DataSet("products.yml")
    @DisplayName("when Search Product With Lower Case then Correct Quantity Of Products Return")
    public void whenSearchProductWithLowerCase_thenCorrectQuantityOfProductsReturn() {

        int actualSizeOfProductsList = productRepository.findProductByNameIgnoreCase("tv").size();

        assertEquals(2, actualSizeOfProductsList);
    }

    @Test
    @DataSet("update_product.yml")
    @DisplayName("when Update Product then Updated Product Return")
    public void whenUpdateProduct_thenUpdatedProductReturn() {
        LocalDateTime creationDate = Timestamp.valueOf("2021-01-01 00:00:00.000000").toLocalDateTime();
        Product previousProduct = productRepository.findAll().get(0);

        assertEquals("Snowboard", previousProduct.getName());
        assertEquals(7000, previousProduct.getPrice());

        productRepository.save(new Product(1, "TV", 4000.0, creationDate));

        Product updatedProduct = productRepository.findAll().get(0);

        assertEquals("TV", updatedProduct.getName());
        assertEquals(4000, updatedProduct.getPrice());
    }

    @Test
    @DataSet("update_product.yml")
    @DisplayName("when Update Product then Size Of Product List Does Not Change")
    public void whenUpdateProduct_thenSizeOfProductListDoesNotChange() {
        LocalDateTime creationDate = Timestamp.valueOf("2021-01-01 00:00:00.000000").toLocalDateTime();
        int expectedSizeOfProductList = productRepository.findAll().size();

        productRepository.save(new Product(1, "TV", 4000.0, creationDate));

        int actualSizeOfProductList = productRepository.findAll().size();

        assertEquals(expectedSizeOfProductList, actualSizeOfProductList);
    }

    @Test
    @DataSet("products.yml")
    @ExpectedDataSet("products.yml")
    @DisplayName("when FindBAll then Return Correct List Of Product")
    public void whenFindAllThenReturnCorrectListOfProduct() {
        productRepository.findAll();
    }

    @Test
    @DataSet("empty_products.yml")
    @DisplayName("when Search Product By Name With Lower Case then Correct Quantity Of Products Return")
    public void whenSearchProduct_inEmptyList_thenCorrectQuantityOfProductsReturn() {

        int actualSizeOfProductsList = productRepository.findProductByNameIgnoreCase("car").size();

        assertEquals(0, actualSizeOfProductsList);
    }
}
