package com.webdesign.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.webdesign.entity.Books_24162122;
import com.webdesign.entity.Users_24162122;
import com.webdesign.service.IBooksService_24162122;
import com.webdesign.service.impl.BooksServiceImpl_24162122;

@WebServlet(name = "AdminController_24162122", urlPatterns = {"/admin"})
public class AdminController_24162122 extends HttpServlet {

    private final IBooksService_24162122 booksService = new BooksServiceImpl_24162122();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Users_24162122 user = (session == null) ? null : (Users_24162122) session.getAttribute("user");

        // Chỉ admin mới được vào trang quản trị
        if (user == null || !user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập trang này!");
            return;
        }

        request.setAttribute("books", booksService.getAllBooks());
        request.getRequestDispatcher("/WEB-INF/views/admin/dashboard.jsp").forward(request, response);
    }
}
