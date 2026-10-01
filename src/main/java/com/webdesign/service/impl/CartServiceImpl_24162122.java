package com.webdesign.service.impl;

import com.webdesign.dao.ICartDAO_24162122;
import com.webdesign.dao.IBooksDAO_24162122;
import com.webdesign.dao.impl.CartDAOImpl_24162122;
import com.webdesign.dao.impl.BooksDAOImpl_24162122;
import com.webdesign.entity.Books_24162122;
import com.webdesign.entity.CartItem_24162122;
import com.webdesign.entity.Users_24162122;
import com.webdesign.service.ICartService_24162122;

import java.math.BigDecimal;
import java.util.List;

public class CartServiceImpl_24162122 implements ICartService_24162122 {

    private final ICartDAO_24162122 cartDAO = new CartDAOImpl_24162122();
    private final IBooksDAO_24162122 booksDAO = new BooksDAOImpl_24162122();

    @Override
    public List<CartItem_24162122> getCart(Integer userid) {
        return cartDAO.findByUser(userid);
    }

    @Override
    public String addToCart(Users_24162122 user, Integer bookid, int quantity) {
        Books_24162122 book = booksDAO.findById(bookid);
        if (book == null) return "Sách không tồn tại!";
        if (quantity < 1) return "Số lượng phải ít nhất là 1.";

        CartItem_24162122 existing = cartDAO.find(user.getId(), bookid);
        int currentQty = (existing != null && existing.getQuantity() != null) ? existing.getQuantity() : 0;
        int newQty = currentQty + quantity;

        int stock = (book.getQuantity() != null) ? book.getQuantity() : 0;
        if (stock <= 0) return "Sách này đã hết hàng!";
        if (newQty > stock) {
            return "Chỉ còn " + stock + " quyển \"" + book.getTitle() + "\" trong kho"
                    + (currentQty > 0 ? " (giỏ hiện có " + currentQty + ")." : ".");
        }

        if (existing != null) {
            cartDAO.updateQuantity(user.getId(), bookid, newQty);
        } else {
            cartDAO.insert(new CartItem_24162122(user.getId(), bookid, newQty));
        }
        return null;
    }

    @Override
    public String updateQuantity(Integer userid, Integer bookid, int quantity) {
        Books_24162122 book = booksDAO.findById(bookid);
        if (book == null) return "Sách không tồn tại!";
        int stock = (book.getQuantity() != null) ? book.getQuantity() : 0;

        if (quantity > stock) {
            return "Số lượng vượt quá tồn kho (chỉ còn " + stock + " quyển).";
        }
        if (quantity < 1) {
            // cập nhật về 0 tương đương xóa khỏi giỏ
            cartDAO.delete(userid, bookid);
            return null;
        }
        cartDAO.updateQuantity(userid, bookid, quantity);
        return null;
    }

    @Override
    public boolean removeFromCart(Integer userid, Integer bookid) {
        return cartDAO.delete(userid, bookid);
    }

    @Override
    public void clearCart(Integer userid) {
        cartDAO.clear(userid);
    }

    @Override
    public BigDecimal getCartTotal(List<CartItem_24162122> items) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem_24162122 item : items) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    @Override
    public int getCartCount(Integer userid) {
        int count = 0;
        for (CartItem_24162122 item : cartDAO.findByUser(userid)) {
            count += (item.getQuantity() != null ? item.getQuantity() : 0);
        }
        return count;
    }
}
