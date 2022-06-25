package com.bondarenko.onlineshop.service;

import com.bondarenko.onlineshop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    void add(Product product);

    //
    Optional<Product> findById(int id);

    //
    void delete(int id);

    //
    Product update(int id, Product product);
//
//    List<Product> search(String searchText);
}
