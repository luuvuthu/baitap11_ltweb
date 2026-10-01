package com.webdesign.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.webdesign.entity.Books_24162122;
import com.webdesign.entity.Rating_24162122;
import com.webdesign.entity.Users_24162122;
import com.webdesign.service.IBooksService_24162122;
import com.webdesign.service.IRatingService_24162122;
import com.webdesign.service.impl.BooksServiceImpl_24162122;
import com.webdesign.service.impl.RatingServiceImpl_24162122;

import java.util.List;

@WebServlet(name = "ProductController_24162122", urlPatterns = {"/products", "/book"})
public class ProductController_24162122 extends HttpServlet {

    private final IBooksService_24162122 booksService = new BooksServiceImpl_24162122();
    private final IRatingService_24162122 ratingService = new RatingServiceImpl_24162122();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // /book?id=n -> trang chi tiết 1 cuốn sách
        if ("/book".equals(request.getServletPath())) {
            Integer id = null;
            try {
                id = Integer.valueOf(request.getParameter("id"));
            } catch (NumberFormatException | NullPointerException e) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
            Books_24162122 book = booksService.getBookDetail(id);
            if (book == null) {
                response.sendRedirect(request.getContextPath() + "/home");
                return;
            }
            List<Rating_24162122> reviews = ratingService.getReviews(id);
            long reviewCount = ratingService.countReviews(id);
            // điểm đánh giá trung bình
            double avg = 0;
            if (!reviews.isEmpty()) {
                int sum = 0;
                for (Rating_24162122 r : reviews) {
                    sum += (r.getRating() != null ? r.getRating() : 0);
                }
                avg = (double) sum / reviews.size();
            }
            request.setAttribute("book", book);
            request.setAttribute("reviews", reviews);
            request.setAttribute("reviewCount", reviewCount);
            // review hiện có của user (nếu đã review) để điền sẵn form
            Users_24162122 currentUser = (Users_24162122) session.getAttribute("user");
            request.setAttribute("myReview", ratingService.getReview(currentUser.getId(), id));
            request.setAttribute("avgRating", Math.round(avg * 10) / 10.0);
            request.getRequestDispatcher("/WEB-INF/views/book-detail.jsp").forward(request, response);
            return;
        }

        request.setAttribute("books", booksService.getAllBooks());
        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }

    // Form thêm reviews: submit -> lưu review rồi quay lại trang chi tiết
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Users_24162122 user = (session != null) ? (Users_24162122) session.getAttribute("user") : null;
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Integer bookid = null;
        try {
            bookid = Integer.valueOf(request.getParameter("bookid"));
        } catch (NumberFormatException | NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String reviewText = request.getParameter("review_text");
        Integer ratingValue = null;
        try {
            int r = Integer.parseInt(request.getParameter("rating"));
            if (r >= 1 && r <= 5) ratingValue = r;
        } catch (NumberFormatException ignored) {
        }

        if (reviewText != null && !reviewText.trim().isEmpty() && ratingValue != null) {
            Rating_24162122 existing = ratingService.getReview(user.getId(), bookid);
            Rating_24162122 r = (existing != null) ? existing : new Rating_24162122();
            r.setUser(user);
            r.setBook(booksService.getBookDetail(bookid));
            r.setRating(ratingValue);
            r.setReviewText(reviewText.trim());
            ratingService.addReview(r);
        }
        response.sendRedirect(request.getContextPath() + "/book?id=" + bookid);
    }
}
