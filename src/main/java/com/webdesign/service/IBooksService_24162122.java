package com.webdesign.service;

import com.webdesign.entity.Books_24162122;

import java.util.List;

public interface IBooksService_24162122 {

    List<Books_24162122> getAllBooks();

    Books_24162122 getBook(Integer bookid);

    /** Lấy sách kèm tác giả (EAGER) cho trang chi tiết. */
    Books_24162122 getBookDetail(Integer bookid);

    /** Lấy 1 trang sách (page bắt đầu từ 1). */
    List<Books_24162122> getBooksPage(int page, int size);

    /** Tổng số sách (để tính số trang). */
    long countAllBooks();

    /** Thêm mới sách. */
    Books_24162122 addBook(Books_24162122 book);

    /** Cập nhật sách. */
    Books_24162122 updateBook(Books_24162122 book);

    /** Xóa sách theo id. */
    boolean deleteBook(Integer bookid);
}
