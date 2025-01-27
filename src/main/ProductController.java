package in.prasannaFarm.controller;


	
	import java.util.List;
	import java.util.Optional;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.http.HttpStatus;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;
	import in.prasannaFarm.entity.Product;
	import in.prasannaFarm.service.ProductService;
	
	@CrossOrigin(origins = "http://localhost:3000")
	@RestController
	@RequestMapping("/products")
	public class ProductController {
	    @Autowired
	    private ProductService productService;
	    // Get all products
	    @GetMapping
	    public List<Product> getAllProducts() {
	        return productService.getAllProducts();
	    }
	    // Get product by name
	    @GetMapping("/{id}")
	    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
	        Optional<Product> product = productService.getProductById(id);
	        return product.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	    }
	    // Create or update a product
	    @PostMapping
	    public Product createOrUpdateProduct(@RequestBody Product product) {
	        return productService.saveProduct(product);
	    }
	    // Update product by id
	    @PutMapping("/{id}")
	    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
	        Optional<Product> product = productService.getProductById(id);
	        if (product.isPresent()) {
	            Product updatedProduct = product.get();
	            updatedProduct.setName(productDetails.getName());
	            updatedProduct.setCategory(productDetails.getCategory());
	            updatedProduct.setDescription(productDetails.getDescription());
	            updatedProduct.setPrice(productDetails.getPrice());
	            updatedProduct.setImageUrl(productDetails.getImageUrl());
	            productService.saveProduct(updatedProduct);
	            return ResponseEntity.ok(updatedProduct);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    // Delete a product by id
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
	        Optional<Product> product = productService.getProductById(id);
	        if (product.isPresent()) {
	            productService.deleteProductById(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	}

