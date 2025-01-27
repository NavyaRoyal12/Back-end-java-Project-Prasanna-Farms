package in.prasannaFarm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import in.prasannaFarm.entity.Product;
public interface ProductRepository extends JpaRepository<Product, Long> {
    // No need to add custom methods for findById and deleteById, 
    // JpaRepository already provides them by default.
}
