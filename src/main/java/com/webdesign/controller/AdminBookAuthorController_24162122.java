package com.webdesign.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.webdesign.entity.Author_24162122;
import com.webdesign.entity.Books_24162122;
import com.webdesign.entity.Users_24162122;
import com.webdesign.service.IAuthorService_24162122;
import com.webdesign.service.IBooksService_24162122;
import com.webdesign.service.impl.AuthorServiceImpl_24162122;
import com.webdesign.service.impl.BooksServiceImpl_24162122;

/**
 * CRUD (Create / Read / Update / Delete) có phân trang cho bảng Books và Authors.
 */
@WebServlet(name = "AdminBookAuthorController_24162122", urlPatterns = {
        "/admin/books", "/admin/book-view", "/admin/book-form", "/admin/book-save", "/admin/book-delete",
        "/admin/authors", "/admin/author-form", "/admin/author-save", "/admin/author-delete"
})
public class AdminBookAuthorController_24162122 extends HttpServlet {

    private static final int PAGE_SIZE = 5;

    private final IBooksService_24162122 booksService = new BooksServiceImpl_24162122();
    private final IAuthorService_24162122 authorService = new AuthorServiceImpl_24162122();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkAdmin(request, response)) return;

        switch (request.getServletPath()) {
            case "/admin/books":     listBooks(request, response); break;
            case "/admin/book-view": viewBook(request, response); break;
            case "/admin/book-form": bookForm(request, response); break;
            case "/admin/book-delete": deleteBook(request, response); break;
            case "/admin/authors":     listAuthors(request, response); break;
            case "/admin/author-form": authorForm(request, response); break;
            case "/admin/author-delete": deleteAuthor(request, response); break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!checkAdmin(request, response)) return;

        switch (request.getServletPath()) {
            case "/admin/book-save":   saveBook(request, response); break;
            case "/admin/author-save": saveAuthor(request, response); break;
            default:
                response.sendRedirect(request.getContextPath() + "/admin");
        }
    }

    // ==================== BOOKS ====================

    /** READ: danh sách sách có phân trang. */
    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = parseInt(request.getParameter("page"), 1);
        request.setAttribute("books", booksService.getBooksPage(page, PAGE_SIZE));
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages(booksService.countAllBooks(), PAGE_SIZE));
        request.getRequestDispatcher("/WEB-INF/views/admin/books.jsp").forward(request, response);
    }

    /** READ: xem chi tiết 1 cuốn sách. */
    private void viewBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Books_24162122 book = booksService.getBookDetail(parseInt(request.getParameter("id"), null));
        if (book == null) {
            response.sendRedirect(request.getContextPath() + "/admin/books");
            return;
        }
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/views/admin/book-view.jsp").forward(request, response);
    }

    /** CREATE + UPDATE: hiển thị form thêm (id rỗng) hoặc sửa (id có giá trị). */
    private void bookForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = parseInt(request.getParameter("id"), null);
        request.setAttribute("book", (id == null) ? new Books_24162122() : booksService.getBookDetail(id));
        request.setAttribute("allAuthors", authorService.getAllAuthors());
        request.getRequestDispatcher("/WEB-INF/views/admin/book-form.jsp").forward(request, response);
    }

    /** CREATE + UPDATE: lưu sách (mới hoặc sửa). */
    private void saveBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = parseInt(request.getParameter("bookid"), null);

        Books_24162122 book = (id == null) ? new Books_24162122() : booksService.getBook(id);
        if (book == null) {
            response.sendRedirect(request.getContextPath() + "/admin/books");
            return;
        }
        book.setIsbn(parseInt(request.getParameter("isbn"), null));
        book.setTitle(request.getParameter("title"));
        book.setPublisher(request.getParameter("publisher"));
        String price = request.getParameter("price");
        try {
            book.setPrice((price == null || price.isEmpty()) ? null : new BigDecimal(price));
        } catch (NumberFormatException ignored) {
            book.setPrice(null);
        }
        book.setDescription(request.getParameter("description"));
        book.setPublishDate(parseDate(request.getParameter("publishDate")));
        book.setCoverImage(request.getParameter("coverImage"));
        book.setQuantity(parseInt(request.getParameter("quantity"), 0));

        // nhiều-nhiều: sách <-> tác giả
        Set<Author_24162122> authors = new HashSet<>();
        String[] authorIds = request.getParameterValues("authors");
        if (authorIds != null) {
            for (String aid : authorIds) {
                Author_24162122 a = authorService.getAuthor(parseInt(aid, null));
                if (a != null) authors.add(a);
            }
        }
        book.setAuthors(authors);

        if (id == null) {
            booksService.addBook(book);
        } else {
            booksService.updateBook(book);
        }
        response.sendRedirect(request.getContextPath() + "/admin/books?page=" +
                parseInt(request.getParameter("page"), 1));
    }

    /** DELETE: xóa sách. */
    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = parseInt(request.getParameter("id"), null);
        if (id != null) booksService.deleteBook(id);
        response.sendRedirect(request.getContextPath() + "/admin/books?page=" +
                parseInt(request.getParameter("page"), 1));
    }

    // ==================== AUTHORS ====================

    /** READ: danh sách tác giả có phân trang. */
    private void listAuthors(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = parseInt(request.getParameter("page"), 1);
        request.setAttribute("authors", authorService.getAuthorsPage(page, PAGE_SIZE));
        request.setAttribute("bookCounts", authorService.countBooksPerAuthor());
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages(authorService.countAllAuthors(), PAGE_SIZE));
        request.getRequestDispatcher("/WEB-INF/views/admin/authors.jsp").forward(request, response);
    }

    /** CREATE + UPDATE: form thêm/sửa tác giả. */
    private void authorForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = parseInt(request.getParameter("id"), null);
        request.setAttribute("author", (id == null) ? new Author_24162122() : authorService.getAuthor(id));
        request.getRequestDispatcher("/WEB-INF/views/admin/author-form.jsp").forward(request, response);
    }

    /** CREATE + UPDATE: lưu tác giả. */
    private void saveAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Integer id = parseInt(request.getParameter("authorId"), null);

        Author_24162122 author = (id == null) ? new Author_24162122() : authorService.getAuthor(id);
        if (author == null) {
            response.sendRedirect(request.getContextPath() + "/admin/authors");
            return;
        }
        author.setAuthorName(request.getParameter("authorName"));
        author.setDateOfBirth(parseDate(request.getParameter("dateOfBirth")));

        if (id == null) {
            authorService.addAuthor(author);
        } else {
            authorService.updateAuthor(author);
        }
        response.sendRedirect(request.getContextPath() + "/admin/authors?page=" +
                parseInt(request.getParameter("page"), 1));
    }

    /** DELETE: xóa tác giả. */
    private void deleteAuthor(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer id = parseInt(request.getParameter("id"), null);
        if (id != null) authorService.deleteAuthor(id);
        response.sendRedirect(request.getContextPath() + "/admin/authors?page=" +
                parseInt(request.getParameter("page"), 1));
    }

    // ==================== helpers ====================

    private boolean checkAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        Users_24162122 user = (session == null) ? null : (Users_24162122) session.getAttribute("user");
        if (user == null || !user.isAdmin()) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập trang này!");
            return false;
        }
        return true;
    }

    private Integer parseInt(String value, Integer fallback) {
        if (value == null || value.isEmpty()) return fallback;
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private java.util.Date parseDate(String value) {
        if (value == null || value.isEmpty()) return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            return sdf.parse(value);
        } catch (ParseException e) {
            return null;
        }
    }

    private int totalPages(long total, int size) {
        return (int) Math.max(1, (total + size - 1) / size);
    }
}
