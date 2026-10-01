package com.webdesign.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.webdesign.entity.Users_24162122;
import com.webdesign.service.IUsersService_24162122;
import com.webdesign.service.impl.UsersServiceImpl_24162122;

@WebServlet(name = "LoginController_24162122", urlPatterns = {"/login", "/logout"})
public class LoginController_24162122 extends HttpServlet {

    private final IUsersService_24162122 usersService = new UsersServiceImpl_24162122();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if ("/logout".equals(request.getServletPath())) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        // Đã đăng nhập thì không hiện lại trang đăng nhập
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Users_24162122 user = usersService.login(username, password);
        if (user == null) {
            if (username != null && !usersService.isActivated(username)) {
                request.setAttribute("error", "Tài khoản chưa kích hoạt! Vui lòng kiểm tra email OTP.");
                request.getSession().setAttribute("registerEmail", username.trim().toLowerCase());
            } else {
                request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu!");
            }
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("isAdmin", user.isAdmin());
        response.sendRedirect(request.getContextPath() + "/home");
    }
}
