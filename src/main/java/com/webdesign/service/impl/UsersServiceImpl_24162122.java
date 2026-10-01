package com.webdesign.service.impl;

import com.webdesign.dao.IUsersDAO_24162122;
import com.webdesign.dao.impl.UsersDAOImpl_24162122;
import com.webdesign.entity.Users_24162122;
import com.webdesign.service.IEmailService_24162122;
import com.webdesign.service.IUsersService_24162122;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UsersServiceImpl_24162122 implements IUsersService_24162122 {

    private final IUsersDAO_24162122 usersDAO = new UsersDAOImpl_24162122();
    private final IEmailService_24162122 emailService = new EmailServiceImpl_24162122();

    @Override
    public Users_24162122 login(String email, String password) {
        if (email == null || password == null || email.trim().isEmpty() || password.isEmpty()) {
            return null;
        }
        Users_24162122 user = usersDAO.findByUsername(email.trim());
        // Chặn các tài khoản chưa kích hoạt (otp != null)
        if (user != null && user.isActivated() && user.getPasswd().equals(md5(password))) {
            usersDAO.updateLastLogin(user.getId());
            return user;
        }
        return null;
    }

    @Override
    public Users_24162122 register(String email, String fullname, String phone, String password) {
        if (email == null || password == null
                || email.trim().isEmpty() || password.isEmpty()) {
            return null;
        }
        String key = email.trim().toLowerCase();
        // Email đã tồn tại (dù đã kích hoạt hay chưa) -> không cho đăng ký lại
        if (usersDAO.findByUsername(key) != null) {
            return null;
        }

        Users_24162122 user = new Users_24162122();
        user.setEmail(key);
        user.setFullname(fullname);
        if (phone != null && !phone.trim().isEmpty()) {
            user.setPhone(Integer.valueOf(phone.trim()));
        }
        user.setPasswd(md5(password));
        user.setSignupDate(new Date());
        user.setIsAdmin(false);

        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(expiry());

        if (!emailService.sendOtpEmail(key, fullname, otp)) {
            // Chưa cấu hình SMTP thật: không chặn đăng ký, in OTP ra console để test
            System.out.println("[DEV] Gửi mail OTP thất bại - OTP cho " + key + " la: " + otp);
        }
        usersDAO.save(user);
        return user;
    }

    @Override
    public boolean activateByOtp(String email, String otp) {
        if (email == null || otp == null) {
            return false;
        }
        Users_24162122 user = usersDAO.findByUsername(email.trim().toLowerCase());
        if (user == null || user.isActivated()) {
            return false;
        }
        if (!user.getOtp().equals(otp.trim()) || new Date().after(user.getOtpExpiry())) {
            return false;
        }
        user.setOtp(null);
        user.setOtpExpiry(null);
        usersDAO.update(user);
        return true;
    }

    @Override
    public boolean resendOtp(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        Users_24162122 user = usersDAO.findByUsername(email.trim().toLowerCase());
        if (user == null || user.isActivated()) {
            return false;
        }
        String otp = generateOtp();
        user.setOtp(otp);
        user.setOtpExpiry(expiry());
        boolean sent = emailService.sendOtpEmail(user.getEmail(), user.getFullname(), otp);
        if (sent) {
            usersDAO.update(user);
        }
        return sent;
    }

    @Override
    public boolean isActivated(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true;
        }
        Users_24162122 user = usersDAO.findByUsername(email.trim().toLowerCase());
        return user == null || user.isActivated();
    }

    private String generateOtp() {
        return String.format("%06d", new Random().nextInt(1_000_000));
    }

    private Date expiry() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, 5);
        return c.getTime();
    }

    /**
     * Băm MD5 - khớp với cách sinh passwd trong database.sql (hàm MD5 của MySQL).
     */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 not available", e);
        }
    }
}
