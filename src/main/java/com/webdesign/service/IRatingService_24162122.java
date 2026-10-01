package com.webdesign.service;

/**
 * Dịch vụ đánh giá/review của sách.
 */
public interface IRatingService_24162122 {

    /** Đếm số lượt review của 1 sách. */
    long countReviews(Integer bookid);

    /** Danh sách review (kèm người review) của 1 sách. */
    java.util.List<com.webdesign.entity.Rating_24162122> getReviews(Integer bookid);

    /** Review của 1 user cho 1 sách (null nếu chưa review). */
    com.webdesign.entity.Rating_24162122 getReview(Integer userid, Integer bookid);

    /** Thêm mới/cập nhật review (rating 1-5 + nội dung) của user cho 1 sách. */
    void addReview(com.webdesign.entity.Rating_24162122 rating);
}
