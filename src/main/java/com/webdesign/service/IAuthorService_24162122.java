package com.webdesign.service;

import com.webdesign.entity.Author_24162122;

import java.util.List;

public interface IAuthorService_24162122 {

    List<Author_24162122> getAllAuthors();

    Author_24162122 getAuthor(Integer authorId);

    /** Lấy 1 trang tác giả (page bắt đầu từ 1). */
    List<Author_24162122> getAuthorsPage(int page, int size);

    /** Tổng số tác giả (để tính số trang). */
    long countAllAuthors();

    /** Đếm số sách của từng tác giả (tránh lazy init trong JSP). */
    java.util.Map<Integer, Long> countBooksPerAuthor();

    /** Thêm mới tác giả. */
    Author_24162122 addAuthor(Author_24162122 author);

    /** Cập nhật tác giả. */
    Author_24162122 updateAuthor(Author_24162122 author);

    /** Xóa tác giả theo id. */
    boolean deleteAuthor(Integer authorId);
}
