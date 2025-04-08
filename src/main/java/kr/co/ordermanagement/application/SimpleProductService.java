package kr.co.ordermanagement.application;

import kr.co.ordermanagement.domain.product.Product;
import kr.co.ordermanagement.domain.product.ProductRepository;
import kr.co.ordermanagement.presentation.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleProductService {

    private ProductRepository productRepository;

    @Autowired
    SimpleProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<presentation.dto.ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<presentation.dto.ProductDto> productDtos = products.stream()
                .map(product -> presentation.dto.ProductDto.toDto(product))
                .toList();
        return productDtos;
    }

}
