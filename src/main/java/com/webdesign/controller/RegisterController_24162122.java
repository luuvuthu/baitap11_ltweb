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

/**
 * Đăng ký tài khoản + kích hoạt bằng OTP gửi qua email.
 * URL: /register (form đăng ký), /verify (nhập OTP), /resend (gửi lại OTP).
 */
@WebServlet(name = "RegisterController_24162122", urlPatterns = {"/register", "/verify", "/resend"})
public class RegisterController_24162122 extends HttpServlet {

    private final IUsersService_24162122 usersService = new UsersServiceImpl_24162122();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        if ("/verify".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();

        // ==== Gửi lại OTP ====
        if ("/resend".equals(path)) {
            String email = (String) request.getSession().getAttribute("registerEmail");
            boolean sent = email != null && usersService.resendOtp(email);
            request.setAttribute("message", sent
                    ? "Đã gửi lại mã OTP mới tới email của bạn."
                    : "Gửi lại OTP thất bại, thử lại sau!");
            request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
            return;
        }

        // ==== Xác thực OTP ====
        if ("/verify".equals(path)) {
            String email = (String) request.getSession().getAttribute("registerEmail");
            String otp = request.getParameter("otp");
            if (email == null) {
                response.sendRedirect(request.getContextPath() + "/register");
                return;
            }
            if (usersService.activateByOtp(email, otp)) {
                request.getSession().removeAttribute("registerEmail");
                request.setAttribute("success", "Kích hoạt tài khoản thành công! Bạn có thể đăng nhập.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Mã OTP không đúng hoặc đã hết hạn!");
                request.getRequestDispatcher("/WEB-INF/views/verify.jsp").forward(request, response);
            }
            return;
        }

        // ==== Đăng ký ====
        String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        if (password == null || !password.equals(confirm)) {
            request.setAttribute("error", "Mật khẩu nhập lại không khớp!");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        Users_24162122 user = usersService.register(email, fullname, phone, password);
        if (user == null) {
            request.setAttribute("error",
                    "Đăng ký thất bại! Email có thể đã tồn tại hoặc gửi email OTP không thành công.");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("registerEmail", user.getEmail());
        response.sendRedirect(request.getContextPath() + "/verify");
    }
}
