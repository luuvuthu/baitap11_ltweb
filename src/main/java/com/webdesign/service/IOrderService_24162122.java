package com.webdesign.service;

import com.webdesign.entity.Orders_24162122;
import com.webdesign.entity.Users_24162122;

import java.util.List;

public interface IOrderService_24162122 {

    /**
     * Thanh toán giỏ hàng bằng COD: tạo đơn hàng + chi tiết, trừ tồn kho, xóa giỏ.
     * Trả về đơn hàng đã tạo, hoặc null + message lỗi qua tham số msg.
     */
    Orders_24162122 checkout(Users_24162122 user, String receiverName, String receiverPhone,
                             String receiverAddress, StringBuilder msg);

    /** Danh sách đơn hàng của user. */
    List<Orders_24162122> getOrdersByUser(Integer userid);

    /** Danh sách đơn hàng của user, lọc theo trạng thái (null/rỗng = tất cả). */
    List<Orders_24162122> getOrdersByUser(Integer userid, String status);

    /** Lấy 1 đơn hàng kèm chi tiết. */
    Orders_24162122 getOrder(Integer orderid);
}
