package com.webdesign.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.webdesign.entity.Users_24162122;
import com.webdesign.service.ICartService_24162122;
import com.webdesign.service.impl.CartServiceImpl_24162122;

import java.util.List;

@WebServlet(name = "CartController_24162122", urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/clear"})
public class CartController_24162122 extends HttpServlet {

    private final ICartService_24162122 cartService = new CartServiceImpl_24162122();

    private Users_24162122 currentUser(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        Users_24162122 user = (session != null) ? (Users_24162122) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        return user;
    }

    /** GET /cart -> trang giỏ hàng. */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users_24162122 user = currentUser(request, response);
        if (user == null) return;

        List<com.webdesign.entity.CartItem_24162122> items = cartService.getCart(user.getId());
        request.setAttribute("cartItems", items);
        request.setAttribute("cartTotal", cartService.getCartTotal(items));
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    /** POST: /cart/add, /cart/update, /cart/remove, /cart/clear. */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users_24162122 user = currentUser(request, response);
        if (user == null) return;

        String path = request.getServletPath();
        String message = null;

        switch (path) {
            case "/cart/add": {
                Integer bookid = parseInt(request.getParameter("bookid"));
                int qty = parseIntOrDefault(request.getParameter("quantity"), 1);
                if (bookid != null) {
                    message = cartService.addToCart(user, bookid, qty);
                }
                // quay lại trang đang đứng (nếu có) để mua tiếp
                String back = request.getParameter("back");
                if (message != null) {
                    request.getSession().setAttribute("cartMsg", message);
                } else {
                    request.getSession().setAttribute("cartOk", "Đã thêm vào giỏ hàng!");
                }
                response.sendRedirect(request.getContextPath()
                        + (back != null && back.startsWith("/") ? back : "/cart"));
                return;
            }
            case "/cart/update": {
                Integer bookid = parseInt(request.getParameter("bookid"));
                int qty = parseIntOrDefault(request.getParameter("quantity"), -1);
                if (bookid != null) {
                    message = cartService.updateQuantity(user.getId(), bookid, qty);
                }
                if (message != null) request.getSession().setAttribute("cartMsg", message);
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            case "/cart/remove": {
                Integer bookid = parseInt(request.getParameter("bookid"));
                if (bookid != null) {
                    cartService.removeFromCart(user.getId(), bookid);
                }
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            case "/cart/clear": {
                cartService.clearCart(user.getId());
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }
            default:
                response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    private Integer parseInt(String s) {
        try {
            return Integer.valueOf(s);
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    private int parseIntOrDefault(String s, int def) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException | NullPointerException e) {
            return def;
        }
    }
}
