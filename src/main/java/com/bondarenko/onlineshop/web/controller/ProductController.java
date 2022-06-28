package com.bondarenko.onlineshop.web.controller;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.exceptions.ProductNotFoundExceptions;
import com.bondarenko.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ProductController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public List<Product> findAll() {
        List<Product> products = productService.findAll();
        logger.info("products {}", products);

        return products;
    }

    @PostMapping("products/add")
    public void addProduct(@RequestBody Product product) {
        logger.info("add product {}", product);
        productService.add(product);
    }

    @DeleteMapping("/products/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        logger.info("delete product by id {}", id);
        productService.delete(id);
    }

    @GetMapping("/products/{id}")
    public Optional<Product> findById(@PathVariable("id") int id) throws ProductNotFoundExceptions {
        logger.info("find product by id {}", id);
        return productService.findById(id);
    }

    @PutMapping("/products/update/{id}")
    public Product update(@PathVariable("id") int id,
                          @RequestBody Product product) {
        logger.info("update product by id {}", id);
        return productService.update(id, product);
    }

    @GetMapping("/products/search/{name}")
    public List<Product> search(@PathVariable("name") String searchText) {
        logger.info("search product {}", searchText);
        return productService.search(searchText);
    }
}