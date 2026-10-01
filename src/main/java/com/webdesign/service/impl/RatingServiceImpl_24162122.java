package com.webdesign.service.impl;

import com.webdesign.dao.IRatingDAO_24162122;
import com.webdesign.dao.impl.RatingDAOImpl_24162122;
import com.webdesign.service.IRatingService_24162122;

public class RatingServiceImpl_24162122 implements IRatingService_24162122 {

    private final IRatingDAO_24162122 ratingDAO = new RatingDAOImpl_24162122();

    @Override
    public java.util.List<com.webdesign.entity.Rating_24162122> getReviews(Integer bookid) {
        return ratingDAO.findByBookWithUser(bookid);
    }

    @Override
    public long countReviews(Integer bookid) {
        return ratingDAO.countByBook(bookid);
    }

    @Override
    public com.webdesign.entity.Rating_24162122 getReview(Integer userid, Integer bookid) {
        return ratingDAO.findByUserAndBook(userid, bookid);
    }

    @Override
    public void addReview(com.webdesign.entity.Rating_24162122 rating) {
        ratingDAO.saveOrUpdate(rating);
    }
}
