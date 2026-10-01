package com.webdesign.dao;

import com.webdesign.entity.Users_24162122;

public interface IUsersDAO_24162122 {

    Users_24162122 findByUsername(String email);

    Users_24162122 findById(Integer id);

    void updateLastLogin(Integer id);

    /** Thêm user mới (đăng ký, chưa kích hoạt - có otp). */
    void save(Users_24162122 user);

    /** Cập nhật user (dùng để xoá otp khi kích hoạt / lưu otp mới). */
    void update(Users_24162122 user);
}
