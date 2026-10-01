package com.webdesign.dao;

import com.webdesign.entity.Orders_24162122;

import java.util.List;

public interface IOrderDAO_24162122 {

    /** Lưu đơn hàng + chi tiết trong 1 transaction. */
    Orders_24162122 save(Orders_24162122 order);

    /** Đơn hàng của 1 user (mới nhất trước). */
    List<Orders_24162122> findByUser(Integer userid);

    /** Đơn hàng của 1 user lọc theo trạng thái (status null = tất cả). */
    List<Orders_24162122> findByUserAndStatus(Integer userid, String status);

    /** Lấy 1 đơn hàng theo id (kèm chi tiết). */
    Orders_24162122 findById(Integer orderid);
}
