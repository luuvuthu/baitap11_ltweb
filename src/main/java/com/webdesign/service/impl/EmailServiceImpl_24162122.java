package com.webdesign.service.impl;

import com.webdesign.service.IEmailService_24162122;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

/**
 * Gửi mail OTP bằng Gmail SMTP (jakarta.mail).
 * Lưu ý: dùng App Password của Gmail (2 bước xác thực), không dùng mật khẩu thường.
 */
public class EmailServiceImpl_24162122 implements IEmailService_24162122 {

    // Chế độ dev: SMTP ảo chạy tại localhost:1025 (tools/fake_smtp.py), mail lưu ra file.
    // Khi nộp bài/chạy thật: đổi lại smtp.gmail.com + app password của bạn.
    private static final String SMTP_HOST = "127.0.0.1";
    private static final String SMTP_PORT = "1025";
    private static final String FROM_EMAIL = "no-reply@bookstore.local";
    private static final String FROM_PASSWORD = "not-needed";

    @Override
    public boolean sendOtpEmail(String toEmail, String fullname, String otp) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Ma OTP kich hoat tai khoan - Bookstore 24162122");
            message.setText("Xin chao " + (fullname == null ? "" : fullname) + ",\n\n"
                    + "Ma OTP kich hoat tai khoan cua ban la: " + otp + "\n"
                    + "Ma co hieu luc trong 5 phut.\n\n"
                    + " neu ban khong dang ky, hay bo qua email nay.");
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
