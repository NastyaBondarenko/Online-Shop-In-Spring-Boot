package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.exceptions.ProductNotFoundExceptions;
import com.bondarenko.onlineshop.repositary.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public void add(Product product) {
        product.setCreationDate(LocalDateTime.now());
        productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Optional<Product> findById(int id) throws ProductNotFoundExceptions {
        Optional<Product> product = productRepository.findById(id);
        if (!product.isPresent()) {
            throw new ProductNotFoundExceptions("Product is not Available");
        }
        return productRepository.findById(id);
    }

    @Override
    public Product update(int id, Product product) {
        Product productDB = productRepository.findById(id).get();

        if (Objects.nonNull(product.getName()) && !"".equalsIgnoreCase(product.getName())) {
            productDB.setName(product.getName());
        }

        productDB.setPrice(product.getPrice());

        if (Objects.nonNull(product.getCreationDate())) {
            productDB.setCreationDate(product.getCreationDate());
        }
        return productRepository.save(productDB);
    }

    @Override
    public List<Product> search(String name) {
        return productRepository.findProductByNameIgnoreCase(name);
    }
}
