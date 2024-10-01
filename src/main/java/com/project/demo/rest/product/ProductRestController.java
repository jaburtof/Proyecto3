package com.project.demo.rest.product;

import com.project.demo.logic.entity.product.Product;
import com.project.demo.logic.entity.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductRestController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'USER')")
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return productRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(product.getName());
                    existingCategory.setDescription(product.getDescription());
                    existingCategory.setPrice(product.getPrice());
                    existingCategory.setStockQuantity(product.getStockQuantity());
                    existingCategory.setCategory(product.getCategory());
                    return productRepository.save(existingCategory);
                })
                .orElseGet(() -> {
                    product.setId(id);
                    return productRepository.save(product);
                });
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    public Product addProduct(@RequestBody Product product) {
        return  productRepository.save(product);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteProduct (@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
