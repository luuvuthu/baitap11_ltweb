package com.webdesign.dao;

import com.webdesign.entity.Rating_24162122;

import java.util.List;

public interface IRatingDAO_24162122 {

    List<Rating_24162122> findByBook(Integer bookid);

    /** Lấy review kèm luôn thông tin người review (JOIN FETCH). */
    List<Rating_24162122> findByBookWithUser(Integer bookid);

    /** Đếm số lượt review của 1 sách. */
    long countByBook(Integer bookid);

    /** Tìm review của 1 user cho 1 sách (khóa chính kép). */
    Rating_24162122 findByUserAndBook(Integer userid, Integer bookid);

    /** Thêm mới hoặc cập nhật review của 1 user cho 1 sách. */
    Rating_24162122 saveOrUpdate(Rating_24162122 rating);
}
