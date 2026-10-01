package com.webdesign.service;

/**
 * Giao diện dịch vụ gửi mail (dùng cho việc gửi mã OTP kích hoạt tài khoản).
 */
public interface IEmailService_24162122 {

    /**
     * Gửi mail chứa mã OTP kích hoạt tài khoản tới địa chỉ email người dùng.
     * @return true nếu gửi thành công, false nếu gửi thất bại.
     */
    boolean sendOtpEmail(String toEmail, String fullname, String otp);
}
