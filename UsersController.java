package in.prasannaFarm.controller;


import in.prasannaFarm.entity.Users;
import in.prasannaFarm.exception.EmailServiceException;
import in.prasannaFarm.exception.UserValidationException;
import in.prasannaFarm.service.EmailService;
import in.prasannaFarm.service.OtpService;
import in.prasannaFarm.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UsersController {
	
    @Autowired
    private UsersService usersService;
    @Autowired
    private OtpService otpService;
    @Autowired
    private EmailService emailService;
    // Endpoint to generate OTP and send it via email
    @PostMapping("/generateOtp")
    public ResponseEntity<String> generateOtp(@RequestBody Users users) {
        if (users.getEmail().isEmpty()) {
            return ResponseEntity.badRequest().body("Email is required");
        }
        if (!usersService.emailVerification(users.getEmail())) {
            return ResponseEntity.badRequest().body("Invalid email format");
        }
        int otp = otpService.generateOtp(users.getEmail());
        emailService.sendOtpEmail(users.getEmail(), otp); // Send OTP to email
        return ResponseEntity.ok("OTP sent to your email");
    }
    // Endpoint to verify OTP and save user details
    @PostMapping("/verifyOtpAndSaveUser")
    public ResponseEntity<String> verifyOtpAndSaveUser(@RequestBody Map<String, Object> requestData) {
        String email = (String) requestData.get("email");
        String phoneObject = (String) requestData.get("phoneNumber"); // Handle phone number as a string
        System.out.println("Request Data: " + requestData);
        System.out.println("Phone Number from request: " + phoneObject);
        System.out.println(requestData.get("userName"));
        
        // Check for null or empty values
        if (email == null || email.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is missing or empty");
        }
        
        if (phoneObject == null || phoneObject.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number is missing");
        }
        // Parse OTP from the requestData map
        Integer otp;
        try {
            Object otpObject = requestData.get("otp");
            if (otpObject == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP is missing");
            }
            otp = Integer.parseInt(otpObject.toString());  // Convert to int from String
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP format");
        }
        Users users = new Users();
        users.setEmail(email);
        users.setUserName((String) requestData.get("userName"));
        users.setPassword((String) requestData.get("password"));
        users.setAddress((String) requestData.get("address"));  // Address field
        try {
            Long phoneNumber = Long.parseLong(phoneObject); // Convert phoneNumber to Long
            users.setPhoneNumber(phoneNumber);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number format");
        }
        // Validate OTP
        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
        // Save the user after OTP validation
        try {
            Users savedUser = usersService.saveUsers(users);
            return ResponseEntity.ok("User saved successfully after OTP verification");
        } catch (UserValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    // Get user details by verifying email and password
    @GetMapping
    public ResponseEntity<Users> getUserByEmailAndPassword(
            @RequestParam String email,
            @RequestParam String password) {
        if (!usersService.verifyUserCredentials(email, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        Optional<Users> user = usersService.getUserByEmailAndPassword(email, password);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
    // Update user details by verifying email, password, and OTP
    @PutMapping
    public ResponseEntity<String> updateUserByEmailAndPasswordAndOtp(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam int otp,
            @RequestBody Users users) {
        // Verify email and password
        if (!usersService.verifyUserCredentials(email, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        // Verify OTP
        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }
        try {
            Users updatedUser = usersService.updateUserByEmailAndPassword(email, password, users);
            return ResponseEntity.ok("User updated successfully");
        } catch (UserValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    // Delete user by verifying email and password
    @DeleteMapping
    public ResponseEntity<String> deleteUserByEmailAndPassword(
            @RequestParam String email,
            @RequestParam String password) {
        if (!usersService.verifyUserCredentials(email, password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
        usersService.deleteUserByEmailAndPassword(email, password);
        return ResponseEntity.ok("User deleted successfully");
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException ex) {
        String paramName = ex.getParameterName();
        if ("otp".equals(paramName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("OTP is required");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(paramName + " parameter is missing");
    }
    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<String> handleUserValidationException(UserValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
    @ExceptionHandler(EmailServiceException.class)
    public ResponseEntity<String> handleEmailServiceException(EmailServiceException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}