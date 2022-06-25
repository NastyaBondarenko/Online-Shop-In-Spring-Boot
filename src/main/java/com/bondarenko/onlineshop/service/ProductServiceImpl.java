package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.Product;
import com.bondarenko.onlineshop.repositary.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public Optional<Product> findById(int id) {
        return productRepository.findById(id);
    }
//
//    @Override
//    public String update(Product product) {
//        productRepository.update(product);
//        return null;
//    }
//
//    @Override
//    public List<Product> search(String searchText) {
//        return productRepository.search(searchText);
//    }
}
