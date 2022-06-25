package com.bondarenko.onlineshop.repositary;

import com.bondarenko.onlineshop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
//    public List<Product> findByProductName(String departmentName);

    public Product findProductByName(String name);
}
