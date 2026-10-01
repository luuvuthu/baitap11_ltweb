package com.webdesign.dao.impl;

import com.webdesign.config.JPAUtil_24162122;
import com.webdesign.dao.IBooksDAO_24162122;
import com.webdesign.entity.Books_24162122;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class BooksDAOImpl_24162122 implements IBooksDAO_24162122 {

    @Override
    public List<Books_24162122> findAll() {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            TypedQuery<Books_24162122> query = em.createQuery(
                    "SELECT b FROM Books_24162122 b", Books_24162122.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Books_24162122> findPage(int page, int size) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            // JOIN FETCH để lấy luôn tác giả (tránh LazyInitializationException trong JSP)
            return em.createQuery(
                    "SELECT DISTINCT b FROM Books_24162122 b LEFT JOIN FETCH b.authors ORDER BY b.bookid",
                    Books_24162122.class)
                    .setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public long countAll() {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.createQuery("SELECT COUNT(b) FROM Books_24162122 b", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public Books_24162122 findByIdWithAuthors(Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            var list = em.createQuery(
                    "SELECT DISTINCT b FROM Books_24162122 b LEFT JOIN FETCH b.authors WHERE b.bookid = :id",
                    Books_24162122.class)
                    .setParameter("id", bookid)
                    .getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public Books_24162122 findById(Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.find(Books_24162122.class, bookid);
        } finally {
            em.close();
        }
    }

    @Override
    public Books_24162122 insert(Books_24162122 book) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(book);
            em.getTransaction().commit();
            return book;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Books_24162122 update(Books_24162122 book) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            Books_24162122 saved = em.merge(book);
            em.getTransaction().commit();
            return saved;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            Books_24162122 book = em.find(Books_24162122.class, bookid);
            if (book == null) {
                em.getTransaction().rollback();
                return false;
            }
            em.remove(book);
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
