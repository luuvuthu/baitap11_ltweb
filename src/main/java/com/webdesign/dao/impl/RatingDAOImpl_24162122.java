package com.webdesign.dao.impl;

import com.webdesign.config.JPAUtil_24162122;
import com.webdesign.dao.IRatingDAO_24162122;
import com.webdesign.entity.Books_24162122;
import com.webdesign.entity.Rating_24162122;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class RatingDAOImpl_24162122 implements IRatingDAO_24162122 {

    @Override
    public List<Rating_24162122> findByBook(Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            TypedQuery<Rating_24162122> query = em.createQuery(
                    "SELECT r FROM Rating_24162122 r WHERE r.book.bookid = :bookid",
                    Rating_24162122.class);
            query.setParameter("bookid", bookid);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Rating_24162122> findByBookWithUser(Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT r FROM Rating_24162122 r LEFT JOIN FETCH r.user WHERE r.book.bookid = :bookid ORDER BY r.user.id",
                    Rating_24162122.class)
                    .setParameter("bookid", bookid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countByBook(Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT COUNT(r) FROM Rating_24162122 r WHERE r.book.bookid = :bookid", Long.class)
                    .setParameter("bookid", bookid)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Rating_24162122 findByUserAndBook(Integer userid, Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.find(Rating_24162122.class,
                    new com.webdesign.entity.RatingId_24162122(userid, bookid));
        } finally {
            em.close();
        }
    }

    @Override
    public Rating_24162122 saveOrUpdate(Rating_24162122 rating) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            Rating_24162122 saved = em.merge(rating);
            em.getTransaction().commit();
            return saved;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
