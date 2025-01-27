package in.prasannaFarm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import in.prasannaFarm.exception.EmailServiceException;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;
    public void sendOtpEmail(String toEmail, int otp) {
        try {
           
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + otp + ". Please use this to verify your email.");
            message.setFrom("noreplayimdigipro@gmail.com");
            mailSender.send(message);
        } catch (MailException e) {
            // Log and throw a custom exception
            throw new EmailServiceException("Failed to send OTP email. Please check the email address and try again.", e);
        }
    }
}