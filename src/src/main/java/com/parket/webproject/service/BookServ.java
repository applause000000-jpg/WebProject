package com.parket.webproject.service;

import com.parket.webproject.domain.CrawlBook;
import com.parket.webproject.dto.BookDTO;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public interface BookServ {
    void CrawlBooks() throws IOException;
    List<BookDTO> CrawlSelectBook(String selectText) throws IOException;
    BookDTO findBookById(Long bno);

    default BookDTO entityToDTO(CrawlBook crawlBook){
        BookDTO boardDTO = BookDTO.builder()
                .imgUrl(crawlBook.getBookImage())
                .title(crawlBook.getTitle())
                .author(crawlBook.getAuthor())
                .publisher(crawlBook.getPublisher())
                .price(crawlBook.getPrice())
                .productUrl(crawlBook.getProductUrl())
                .build();
        return boardDTO;
    }
    default CrawlBook dtoToEntity(BookDTO bookDTO){
        CrawlBook crawlBook = CrawlBook.builder()
                .bookImage(bookDTO.getImgUrl())
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .publisher(bookDTO.getPublisher())
                .price(bookDTO.getPrice())
                .productUrl(bookDTO.getProductUrl())
                .build();
        return crawlBook;
    }

}
