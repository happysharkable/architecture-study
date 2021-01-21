package com.geekbrains.geek.market.services;

import com.geekbrains.geek.market.entities.Product;
import com.geekbrains.geek.market.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final Map<Long, Product> identityProductList;

    public Optional<Product> findById(Long id) {
        if (identityProductList.containsKey(id)) {
            return Optional.of(identityProductList.get(id));
        }
        Optional<Product> o = productRepository.findById(id);
        if (o.isPresent()) {
            Product p = o.get();
            identityProductList.put(p.getId(), p);
        }
        return o;
    }

    public void deleteById(Long id) {
        identityProductList.remove(id);
        productRepository.deleteById(id);
    }

    public void deleteAll() {
        identityProductList.clear();
        productRepository.deleteAll();
    }

    public Page<Product> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page, size));
    }

    public Product saveOrUpdate(Product product) {
        if (identityProductList.containsKey(product.getId())) {
            identityProductList.replace(product.getId(), product);
        }
        identityProductList.put(product.getId(), product);
        return productRepository.save(product);
    }
}
