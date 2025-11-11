package com.parket.webproject.repository;

import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<CrawlBook, Long> {
//    Object findAll();
    Optional<CrawlBook> findByTitleAndAuthor(String title, String author);

    @Query("SELECT b FROM CrawlBook b WHERE b.category LIKE :bookType AND b.title LIKE :keyword ORDER BY b.bno ASC")
    List<CrawlBook> findByTitleSearchAll(String bookType,String keyword);
    @Query("SELECT b FROM CrawlBook b WHERE b.category LIKE :bookType AND b.author LIKE :keyword ORDER BY b.bno ASC")
    List<CrawlBook> findByAuthorSearchAll(String bookType,String keyword);
    @Query("SELECT b FROM CrawlBook b WHERE b.category LIKE :bookType AND (b.title LIKE :keyword OR b.author LIKE :keyword) ORDER BY b.bno ASC")
    List<CrawlBook> findByALLSearchAll(String bookType,String keyword);

    @Query("SELECT b FROM CrawlBook b WHERE b.category LIKE '%국베%' ORDER BY b.bno ASC")
    List<CrawlBook> findByDomesticAll();
    @Query("SELECT b FROM CrawlBook b WHERE b.category LIKE '%외베%' ORDER BY b.bno ASC")
    List<CrawlBook> findByForeignAll();
    @Query("SELECT b FROM CrawlBook b WHERE b.category LIKE '%신간%' ORDER BY b.bno ASC")
    List<CrawlBook> findByBookNewAll();


    @Query("SELECT b FROM CrawlBook b WHERE b.category LIKE :keyword ORDER BY b.bno ASC")
    List<CrawlBook> findByCategoryContaining(@Param("keyword") String keyword);


    //product 레파지토리로 가야하긴함
    @Query("SELECT p FROM Product p WHERE p.title = :title AND p.author = :author")
    List<Product> detailProduct(@Param("title") String title, @Param("author") String author);
}
