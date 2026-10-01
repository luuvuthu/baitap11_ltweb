package com.webdesign.dao;

import com.webdesign.entity.CartItem_24162122;

import java.util.List;

public interface ICartDAO_24162122 {

    /** Lấy toàn bộ giỏ hàng của 1 user (kèm sách). */
    List<CartItem_24162122> findByUser(Integer userid);

    /** Tìm 1 dòng giỏ hàng. */
    CartItem_24162122 find(Integer userid, Integer bookid);

    /** Thêm mới 1 dòng (chưa có trong giỏ). */
    CartItem_24162122 insert(CartItem_24162122 item);

    /** Cập nhật số lượng của 1 dòng. */
    boolean updateQuantity(Integer userid, Integer bookid, int quantity);

    /** Xóa 1 dòng khỏi giỏ. */
    boolean delete(Integer userid, Integer bookid);

    /** Xóa sạch giỏ của user (sau khi thanh toán). */
    void clear(Integer userid);
}
