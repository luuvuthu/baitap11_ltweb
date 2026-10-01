package com.webdesign.service;

import com.webdesign.entity.CartItem_24162122;
import com.webdesign.entity.Users_24162122;

import java.util.List;

public interface ICartService_24162122 {

    /** Giỏ hàng hiện tại của user. */
    List<CartItem_24162122> getCart(Integer userid);

    /** Thêm sách vào giỏ (nếu đã có thì cộng dồn). Trả về thông báo lỗi, null nếu OK. */
    String addToCart(Users_24162122 user, Integer bookid, int quantity);

    /** Cập nhật số lượng (giới hạn 1..tồn kho). Trả về thông báo lỗi, null nếu OK. */
    String updateQuantity(Integer userid, Integer bookid, int quantity);

    /** Xóa 1 dòng khỏi giỏ. */
    boolean removeFromCart(Integer userid, Integer bookid);

    /** Xóa sạch giỏ hàng. */
    void clearCart(Integer userid);

    /** Tổng tiền giỏ hàng. */
    java.math.BigDecimal getCartTotal(List<CartItem_24162122> items);

    /** Tổng số quyển trong giỏ. */
    int getCartCount(Integer userid);
}
