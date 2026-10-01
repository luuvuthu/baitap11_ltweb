package com.webdesign.dao;

import com.webdesign.entity.Author_24162122;

import java.util.List;

public interface IAuthorDAO_24162122 {

    List<Author_24162122> findAll();

    Author_24162122 findById(Integer authorId);

    /** Lấy 1 trang tác giả (page bắt đầu từ 1). */
    List<Author_24162122> findPage(int page, int size);

    /** Tổng số tác giả trong CSDL. */
    long countAll();

    /** Đếm số sách của từng tác giả (tránh lazy init trong JSP). */
    java.util.Map<Integer, Long> countBooksPerAuthor();

    /** Thêm mới tác giả. */
    Author_24162122 insert(Author_24162122 author);

    /** Cập nhật tác giả. */
    Author_24162122 update(Author_24162122 author);

    /** Xóa tác giả theo id (book_author sẽ bị xóa cascade). */
    boolean delete(Integer authorId);
}
