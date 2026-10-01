package com.webdesign.dao;

import com.webdesign.entity.Books_24162122;

import java.util.List;

public interface IBooksDAO_24162122 {

    List<Books_24162122> findAll();

    Books_24162122 findById(Integer bookid);

    /** Lấy sách kèm luôn tác giả (JOIN FETCH, tránh Lazy ngoài session). */
    Books_24162122 findByIdWithAuthors(Integer bookid);

    /** Lấy 1 trang sách (page bắt đầu từ 1). */
    List<Books_24162122> findPage(int page, int size);

    /** Tổng số sách trong CSDL. */
    long countAll();

    /** Thêm mới sách. */
    Books_24162122 insert(Books_24162122 book);

    /** Cập nhật sách. */
    Books_24162122 update(Books_24162122 book);

    /** Xóa sách theo id (book_author sẽ bị xóa cascade). */
    boolean delete(Integer bookid);
}
