package com.parket.webproject.service;

import com.parket.webproject.domain.Product;
import com.parket.webproject.domain.User;
import com.parket.webproject.dto.ProductDTO;
import com.parket.webproject.repository.BookRepository;
import com.parket.webproject.repository.CartRepository;
import com.parket.webproject.repository.member.MemberRepository;
import com.parket.webproject.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Long insertProduct(ProductDTO productDTO) {
        Product product = dtoToEntity(productDTO);

        // ✅ Optional<User>에서 실제 User 꺼내기 (없으면 null)
        User user = memberRepository.findByUsername(productDTO.getUsername())
                .orElse(null);

        // null 체크 (예외 방지)
        if (user == null) {
            log.error("⚠️ 등록하려는 사용자를 찾을 수 없습니다: {}", productDTO.getUsername());
            throw new RuntimeException("해당 사용자를 찾을 수 없습니다.");
        }

        product.setUser(user);
        Product saved = productRepository.save(product);
        return saved.getProductId();
    }

    @Override
    public List<ProductDTO> findAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(entityToDto(product));
        }
        return dtos;
    }

    @Override
    public List<ProductDTO> findProductsByUserId(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product product : products) {
            dtos.add(entityToDto(product));
        }

        return dtos;
    }

    @Override
    public List<ProductDTO> findSoldProductsByUserId(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);
        List<ProductDTO> dtos = new ArrayList<>();
        for (Product product : products) {
            if(product.getIsSold() == true){
                dtos.add(entityToDto(product));
            }
        }

        return dtos;
    }

    @Override
    public ProductDTO findProductById(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        ProductDTO dto = entityToDto(product);
        return dto;
    }

    @Override
    public void updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getProductId()).orElse(null);
        if (product != null) {
            product.change(productDTO.getPrice(), productDTO.getConditions());
            productRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        cartRepository.deleteByProduct_ProductId(productId);
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        }
    }
}
