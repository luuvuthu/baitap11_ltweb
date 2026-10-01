package com.webdesign.dao.impl;

import com.webdesign.config.JPAUtil_24162122;
import com.webdesign.dao.IAuthorDAO_24162122;
import com.webdesign.entity.Author_24162122;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class AuthorDAOImpl_24162122 implements IAuthorDAO_24162122 {

    @Override
    public List<Author_24162122> findAll() {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            TypedQuery<Author_24162122> query = em.createQuery(
                    "SELECT a FROM Author_24162122 a ORDER BY a.authorId", Author_24162122.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24162122 findById(Integer authorId) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.find(Author_24162122.class, authorId);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author_24162122> findPage(int page, int size) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT a FROM Author_24162122 a ORDER BY a.authorId", Author_24162122.class)
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
            return em.createQuery("SELECT COUNT(a) FROM Author_24162122 a", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public java.util.Map<Integer, Long> countBooksPerAuthor() {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            java.util.Map<Integer, Long> result = new java.util.HashMap<>();
            em.createQuery(
                    "SELECT a.authorId, COUNT(b) FROM Author_24162122 a JOIN a.books b GROUP BY a.authorId",
                    Object[].class)
                    .getResultList()
                    .forEach(row -> result.put((Integer) row[0], (Long) row[1]));
            return result;
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24162122 insert(Author_24162122 author) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(author);
            em.getTransaction().commit();
            return author;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24162122 update(Author_24162122 author) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            Author_24162122 saved = em.merge(author);
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
    public boolean delete(Integer authorId) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            Author_24162122 author = em.find(Author_24162122.class, authorId);
            if (author == null) {
                em.getTransaction().rollback();
                return false;
            }
            // gỡ liên kết book_author để xóa sạch trước khi xóa tác giả
            author.getBooks().size(); // kích hoạt lazy set
            author.getBooks().forEach(b -> b.getAuthors().remove(author));
            em.remove(author);
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
