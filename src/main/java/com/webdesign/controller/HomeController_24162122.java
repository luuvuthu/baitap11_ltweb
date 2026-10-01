package com.webdesign.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.webdesign.entity.Books_24162122;
import com.webdesign.service.IRatingService_24162122;
import com.webdesign.service.impl.RatingServiceImpl_24162122;

@WebServlet(name = "HomeController_24162122", urlPatterns = {"/home", ""})
public class HomeController_24162122 extends HttpServlet {

    private static final int PAGE_SIZE = 6; // 6 sách / 1 trang

    private final com.webdesign.service.IBooksService_24162122 booksService =
            new com.webdesign.service.impl.BooksServiceImpl_24162122();
    private final IRatingService_24162122 ratingService =
            new RatingServiceImpl_24162122();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Chưa đăng nhập thì quay lại trang đăng nhập
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Trang hiện tại (mặc định trang 1)
        int page = 1;
        try {
            page = Integer.parseInt(request.getParameter("page"));
        } catch (NumberFormatException | NullPointerException e) {
            page = 1;
        }
        long totalBooks = booksService.countAllBooks();
        int totalPages = (int) Math.ceil(totalBooks / (double) PAGE_SIZE);
        if (page < 1) page = 1;
        if (page > totalPages && totalPages > 0) page = totalPages;

        List<Books_24162122> books = booksService.getBooksPage(page, PAGE_SIZE);

        // Đếm số lượt review của từng sách để hiển thị "Review (n)"
        Map<Integer, Long> reviewCounts = new HashMap<>();
        for (Books_24162122 b : books) {
            reviewCounts.put(b.getBookid(), ratingService.countReviews(b.getBookid()));
        }

        request.setAttribute("books", books);
        request.setAttribute("totalBooks", totalBooks);
        request.setAttribute("reviewCounts", reviewCounts);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}
