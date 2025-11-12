package com.parket.webproject.repository;

import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.user.id = :userId ORDER BY p.isSold ASC, p.updatedAt DESC, p.createdAt DESC")
    List<Product> findByUserId(Long userId);

    @Query("SELECT b FROM Product b WHERE b.title LIKE :keyword ORDER BY b.productId ASC")
    List<Product> findByTitleSearchAll(String keyword);
    @Query("SELECT b FROM Product b WHERE b.author LIKE :keyword ORDER BY b.productId ASC")
    List<Product> findByAuthorSearchAll(String keyword);
    @Query("SELECT b FROM Product b WHERE b.title LIKE :keyword OR b.author LIKE :keyword ORDER BY b.productId ASC")
    List<Product> findByALLSearchAll(String keyword);
}
