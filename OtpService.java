package in.prasannaFarm.service;


import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
    private Map<String, Integer> otpData = new HashMap<>();
    // Generate OTP and store it with the email
    public int generateOtp(String email) {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000); // 4 digit OTP
        otpData.put(email, otp);
        System.out.println("Generated OTP for " + email + ": " + otp);
        return otp;
    }
    // Validate the OTP with the email
    public boolean validateOtp(String email, int otp) {
        Integer storedOtp = otpData.get(email);
        System.out.println("Validating OTP for " + email + ". Stored OTP: " + storedOtp + ", Input OTP: " + otp);
        
        if (storedOtp != null && storedOtp == otp) {
            otpData.remove(email); // OTP verified, remove it
            return true;
        }
        return false;
    }
}