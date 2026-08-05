/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dg.util;

/**
 *
 * @author samih
 */


import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    public static void sendEmail(String toEmail, String username, String password) {

        final String fromEmail = "disasterguard220@gmail.com";     // 🔁 replace
        final String appPassword = "ghcw egqi wbol dntz";   // 🔁 replace (Gmail App Password)

        // SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Create session
        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, appPassword);
                    }
                });

        try {
            // Create message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(toEmail)
            );

            message.setSubject("Account Approved - Login Details");

            // Email body
            String content =
                    "Dear User,\n\n" +
                    "Congratulations! Your account has been approved.\n\n" +
                    "Here are your login credentials:\n\n" +
                    "Username: " + username + "\n" +
                    "Password: " + password + "\n\n" +
                    "Please login and change your password immediately.\n\n" +
                    "Regards,\nDisaster Management Team";

            message.setText(content);

            // Send email
            Transport.send(message);

            System.out.println(" Email sent successfully to " + toEmail);

        } catch (MessagingException e) {
            System.out.println(" Email sending failed!");
            e.printStackTrace();
        }
    }
}