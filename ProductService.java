package in.prasannaFarm.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import in.prasannaFarm.entity.Product;
import in.prasannaFarm.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    // Get a product by id
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    // Create or update a product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
    // Delete a product by id
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}