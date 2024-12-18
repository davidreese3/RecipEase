package com.thesis.recipease.util.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom("RecipEase <recipeasecommunication@gmail.com>");
            helper.setSubject(subject);
            helper.setText(body, true); // Set 'true' for HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }

    public void sendActivationEmail(String to, String activationLink, int activationCode) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom("RecipEase <recipeasecommunication@gmail.com>");
            helper.setSubject("Welcome to RecipEase! Activate Your Account");
            String body = """
                <html>
                <body>
                    <p>Dear User,</p>
                    <p>Welcome to <strong>RecipEase</strong>, your go-to platform for creating, sharing, and discovering recipes! We’re thrilled to have you join our community of food enthusiasts.</p>
                    <p>To get started, please activate your account by clicking the link below:</p>
                    <p><a href="%s" target="_blank" rel="noopener noreferrer" style="color: #06D2FF; text-decoration: none; font-weight: bold;">Activate My Account</a></p>
                    <p>Once you get to the activation page, use the following activation code:</p>
                    <p style="font-size: 1.5em; font-weight: bold; color: #333; border: 1px solid #ddd; padding: 10px; display: inline-block; background-color: #f9f9f9;">%s</p>
                    <p>Once your account is activated, you’ll be able to:</p>
                    <ul>
                        <li>Create your own recipes.</li>
                        <li>Discover others' recipes.</li>
                        <li>Access our built-in glossary and substitution tools.</li>
                    </ul>
                    <p>If you have any questions or need assistance, don’t hesitate to reach out to our support team at <a href="mailto:recipeasecommunication@gmail.com">recipeasecommunication@gmail.com</a>.</p>
                    <p>Thank you for joining RecipEase. Can’t wait to see what you'll create!</p>
                    <p><strong>Bon appétit,</strong><br>The RecipEase Team</p>
                </body>
                </html>
                """.formatted(activationLink, activationCode);

            helper.setText(body, true); // 'true' indicates HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }

    public void sendPasswordResetEmail(String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom("RecipEase <recipeasecommunication@gmail.com>");
            helper.setSubject("Your RecipEase Password Has Been Reset");

            String body = """
            <html>
            <body>
                <p>Dear User,</p>
                <p>We wanted to let you know that your password has been successfully reset.</p>
                <p>If you did not request this password reset, please contact our support team immediately at <a href="mailto:recipeasecommunication@gmail.com">recipeasecommunication@gmail.com</a>.</p>
                <p>For your security, we recommend that you:</p>
                <ul>
                    <li>Ensure your password is strong and unique.</li>
                    <li>Update your account settings if necessary.</li>
                    <li>Contact support if you notice any suspicious activity.</li>
                </ul>
                <p>You can now log in to your account using your new password:</p>
                <p style="text-align: center; margin-top: 20px;">
                    <a href="https://localhost:8080/login" target="_blank" rel="noopener noreferrer" 
                       style="background-color: #06D2FF; color: white; text-decoration: none; 
                              padding: 10px 20px; font-weight: bold; border-radius: 5px;">
                        Log In to RecipEase
                    </a>
                </p>
                <p>Thank you for being a valued member of the <strong>RecipEase</strong> community!</p>
                <p><strong>Bon appétit,</strong><br>The RecipEase Team</p>
            </body>
            </html>
            """;

            helper.setText(body, true); // 'true' indicates HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }

    public void sendAccountDeletionEmail(String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setFrom("RecipEase <recipeasecommunication@gmail.com>");
            helper.setSubject("Goodbye from RecipEase");

            String body = """
        <html>
        <body>
            <p>Dear User,</p>
            <p>We’re sorry to see you go. Your account with <strong>RecipEase</strong> has been successfully deleted.</p>
            <p>We’d like to take this opportunity to thank you for being a part of our community. It’s been a pleasure to help you explore, create, and share your culinary adventures.</p>
            <p>If there’s anything we could have done better or if you’d like to share your feedback, please don’t hesitate to reach out to us at <a href="mailto:recipeasecommunication@gmail.com">recipeasecommunication@gmail.com</a>.</p>
            <p>Remember, the door is always open if you wish to rejoin in the future. Until then, we wish you all the best in your cooking journey!</p>
            <p><strong>Bon appétit,</strong><br>The RecipEase Team</p>
        </body>
        </html>
        """;

            helper.setText(body, true); // 'true' indicates HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }



    public void sendResetPasswordEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // Set 'true' for HTML content
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(); // Log the error for debugging
        }
    }
}
