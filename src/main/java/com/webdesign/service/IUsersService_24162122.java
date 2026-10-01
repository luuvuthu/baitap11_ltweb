package com.webdesign.service;

import com.webdesign.entity.Users_24162122;

public interface IUsersService_24162122 {

    /**
     * Đăng nhập: kiểm tra email + mật khẩu (MD5).
     * @return Users nếu hợp lệ, null nếu sai thông tin đăng nhập.
     */
    Users_24162122 login(String email, String password);

    /**
     * Đăng ký tài khoản mới: tạo user có otp và gửi mail OTP kích hoạt.
     * Nếu gửi mail thất bại vẫn lưu user và in OTP ra console (chế độ dev).
     * @return null nếu email đã tồn tại.
     */
    Users_24162122 register(String email, String fullname, String phone, String password);

    /**
     * Kích hoạt tài khoản bằng OTP (hết hạn sau 5 phút).
     * @return true nếu OTP đúng và còn hiệu lực.
     */
    boolean activateByOtp(String email, String otp);

    /** Gửi lại OTP cho tài khoản chưa kích hoạt. */
    boolean resendOtp(String email);

    /** Kiểm tra tài khoản đã kích hoạt chưa (otp == NULL). Email không tồn tại -> true. */
    boolean isActivated(String email);
}
