package in.prasannaFarm.repository;


import in.prasannaFarm.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsersRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(Long phoneNumber);
    boolean existsByPassword(String password);
    Optional<Users> findByEmail(String email); // To find users by email for verification
}