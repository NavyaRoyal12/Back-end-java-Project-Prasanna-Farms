package in.prasannaFarm.service;

import in.prasannaFarm.entity.Users;
import in.prasannaFarm.exception.UserValidationException;
import in.prasannaFarm.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.regex.Pattern;
@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    // Validate that the phone number is exactly 10 digits long
    public boolean phoneNumberVerification(long phoneNumber) {
        return phoneNumber >= 1_000_000_000L && phoneNumber <= 9_999_999_999L;
    }
    // Validate that the password contains at least one special character, one digit, and is at least 8 characters long
    public boolean passwordVerification(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[!@#$%^&*]).{8,}$";
        return password != null && Pattern.matches(passwordPattern, password);
    }
    // Validate that the email is in a correct format using regex
    public boolean emailVerification(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && Pattern.matches(emailPattern, email);
    }
    // Save the user if all validations pass
    public Users saveUsers(Users users) {
        if (!phoneNumberVerification(users.getPhoneNumber())) {
            throw new UserValidationException("Invalid phone number format");
        }
        if (!passwordVerification(users.getPassword())) {
            throw new UserValidationException("Password must contain at least one special character, one digit, and be at least 8 characters long");
        }
        if (!emailVerification(users.getEmail())) {
            throw new UserValidationException("Invalid email format");
        }
        // Check for uniqueness constraints
        if (usersRepository.existsByEmail(users.getEmail())) {
            throw new UserValidationException("Email already exists");
        }
        if (usersRepository.existsByPhoneNumber(users.getPhoneNumber())) {
            throw new UserValidationException("Phone number already exists");
        }
        if (usersRepository.existsByPassword(users.getPassword())) {
            throw new UserValidationException("Password already exists");
        }
        return usersRepository.save(users);
    }
    // Verify user credentials (email and password)
    public boolean verifyUserCredentials(String email, String password) {
        Optional<Users> user = usersRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }
    // Get user by email and password
    public Optional<Users> getUserByEmailAndPassword(String email, String password) {
        return usersRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }
    // Update user details by email and password
    public Users updateUserByEmailAndPassword(String email, String password, Users updatedUser) {
        Optional<Users> existingUser = usersRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
        if (existingUser.isPresent()) {
            Users user = existingUser.get();
            user.setUserName(updatedUser.getUserName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setPhoneNumber(updatedUser.getPhoneNumber());
            user.setAddress(updatedUser.getAddress());
            return usersRepository.save(user);
        } else {
            throw new UserValidationException("Invalid email or password");
        }
    }
    // Delete user by email and password
    public void deleteUserByEmailAndPassword(String email, String password) {
        Optional<Users> existingUser = usersRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
        if (existingUser.isPresent()) {
            usersRepository.delete(existingUser.get());
        } else {
            throw new UserValidationException("Invalid email or password");
        }
    }
}