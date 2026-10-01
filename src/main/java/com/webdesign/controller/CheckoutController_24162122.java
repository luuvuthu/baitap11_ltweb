package com.webdesign.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.webdesign.entity.Orders_24162122;
import com.webdesign.entity.Users_24162122;
import com.webdesign.service.ICartService_24162122;
import com.webdesign.service.IOrderService_24162122;
import com.webdesign.service.impl.CartServiceImpl_24162122;
import com.webdesign.service.impl.OrderServiceImpl_24162122;

import java.util.List;

@WebServlet(name = "CheckoutController_24162122", urlPatterns = {"/checkout", "/orders"})
public class CheckoutController_24162122 extends HttpServlet {

    private final ICartService_24162122 cartService = new CartServiceImpl_24162122();
    private final IOrderService_24162122 orderService = new OrderServiceImpl_24162122();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Users_24162122 user = (session != null) ? (Users_24162122) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String path = request.getServletPath();

        if ("/orders".equals(path)) {
            // lịch sử đơn hàng của user, lọc theo trạng thái (nếu có)
            String statusFilter = request.getParameter("status");
            request.setAttribute("statusFilter", statusFilter);
            request.setAttribute("statusList", com.webdesign.entity.OrderStatus_24162122.ALL);
            request.setAttribute("orders", orderService.getOrdersByUser(user.getId(), statusFilter));
            request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
            return;
        }

        // /checkout -> trang xác nhận + nhập thông tin giao hàng (COD)
        List<com.webdesign.entity.CartItem_24162122> items = cartService.getCart(user.getId());
        if (items.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        request.setAttribute("cartItems", items);
        request.setAttribute("cartTotal", cartService.getCartTotal(items));
        // điền sẵn tên/sđt từ tài khoản
        request.setAttribute("defaultName", user.getFullname());
        request.setAttribute("defaultPhone", user.getPhone() != null ? String.valueOf(user.getPhone()) : "");
        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }

    /** POST /checkout -> tạo đơn hàng COD. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Users_24162122 user = (session != null) ? (Users_24162122) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String name = request.getParameter("receiver_name");
        String phone = request.getParameter("receiver_phone");
        String address = request.getParameter("receiver_address");

        StringBuilder msg = new StringBuilder();
        Orders_24162122 order = orderService.checkout(user, name, phone, address, msg);

        if (order == null) {
            request.getSession().setAttribute("cartMsg", msg.toString());
            response.sendRedirect(request.getContextPath() + "/checkout");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/orders?success=" + order.getOrderid());
    }
}
