package in.prasannaFarm.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import in.prasannaFarm.entity.CartTable;
@Repository
public interface CartRepository extends JpaRepository<CartTable, Long> {
    // Find all cart items for a specific user by userId
    List<CartTable> findByUserId(long userId);
    // Delete all cart items for a specific user
    void deleteByUserId(long userId);
}