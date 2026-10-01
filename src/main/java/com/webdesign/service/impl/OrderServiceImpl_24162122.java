package com.webdesign.service.impl;

import com.webdesign.config.JPAUtil_24162122;
import com.webdesign.dao.ICartDAO_24162122;
import com.webdesign.dao.IOrderDAO_24162122;
import com.webdesign.dao.impl.CartDAOImpl_24162122;
import com.webdesign.dao.impl.OrderDAOImpl_24162122;
import com.webdesign.entity.CartItem_24162122;
import com.webdesign.entity.OrderDetail_24162122;
import com.webdesign.entity.Orders_24162122;
import com.webdesign.entity.Users_24162122;
import com.webdesign.service.IOrderService_24162122;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl_24162122 implements IOrderService_24162122 {

    private final IOrderDAO_24162122 orderDAO = new OrderDAOImpl_24162122();
    private final ICartDAO_24162122 cartDAO = new CartDAOImpl_24162122();

    @Override
    public Orders_24162122 checkout(Users_24162122 user, String receiverName, String receiverPhone,
                                    String receiverAddress, StringBuilder msg) {
        List<CartItem_24162122> cart = cartDAO.findByUser(user.getId());
        if (cart.isEmpty()) {
            msg.append("Giỏ hàng đang trống, không thể thanh toán!");
            return null;
        }
        if (receiverName == null || receiverName.trim().isEmpty()) {
            msg.append("Vui lòng nhập tên người nhận.");
            return null;
        }
        if (receiverPhone == null || !receiverPhone.matches("\\d{9,11}")) {
            msg.append("Số điện thoại không hợp lệ (9-11 chữ số).");
            return null;
        }
        if (receiverAddress == null || receiverAddress.trim().isEmpty()) {
            msg.append("Vui lòng nhập địa chỉ giao hàng.");
            return null;
        }

        // Kiểm tra tồn kho trước khi tạo đơn
        for (CartItem_24162122 item : cart) {
            int stock = (item.getBook().getQuantity() != null) ? item.getBook().getQuantity() : 0;
            if (item.getQuantity() > stock) {
                msg.append("Sách \"").append(item.getBook().getTitle())
                        .append("\" chỉ còn ").append(stock).append(" quyển, vui lòng cập nhật giỏ hàng.");
                return null;
            }
        }

        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();

            BigDecimal total = BigDecimal.ZERO;
            for (CartItem_24162122 item : cart) {
                // trừ tồn kho
                com.webdesign.entity.Books_24162122 book = em.find(
                        com.webdesign.entity.Books_24162122.class, item.getBookid());
                book.setQuantity(book.getQuantity() - item.getQuantity());
                total = total.add(item.getSubtotal());
            }

            Orders_24162122 order = new Orders_24162122();
            order.setUser(em.merge(user));
            order.setReceiverName(receiverName.trim());
            order.setReceiverPhone(receiverPhone.trim());
            order.setReceiverAddress(receiverAddress.trim());
            order.setTotal(total);
            order.setPaymentMethod("COD");
            order.setStatus(com.webdesign.entity.OrderStatus_24162122.NEW);
            order.setOrderDate(new Date());

            for (CartItem_24162122 item : cart) {
                OrderDetail_24162122 detail = new OrderDetail_24162122();
                detail.setOrder(order);
                detail.setBook(em.find(com.webdesign.entity.Books_24162122.class, item.getBookid()));
                detail.setQuantity(item.getQuantity());
                detail.setPrice(item.getBook().getPrice());
                order.getDetails().add(detail);
            }

            em.persist(order);
            em.getTransaction().commit();

            // thanh toán xong -> xóa giỏ hàng
            cartDAO.clear(user.getId());
            return order;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            msg.append("Có lỗi xảy ra khi tạo đơn hàng, vui lòng thử lại.");
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Orders_24162122> getOrdersByUser(Integer userid) {
        return getOrdersByUser(userid, null);
    }

    @Override
    public List<Orders_24162122> getOrdersByUser(Integer userid, String status) {
        if (status == null || status.trim().isEmpty()) {
            return orderDAO.findByUser(userid);
        }
        return orderDAO.findByUserAndStatus(userid, status.trim());
    }

    @Override
    public Orders_24162122 getOrder(Integer orderid) {
        return orderDAO.findById(orderid);
    }
}
