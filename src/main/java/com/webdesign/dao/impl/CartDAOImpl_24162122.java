package com.webdesign.dao.impl;

import com.webdesign.config.JPAUtil_24162122;
import com.webdesign.dao.ICartDAO_24162122;
import com.webdesign.entity.CartItem_24162122;

import jakarta.persistence.EntityManager;
import java.util.List;

public class CartDAOImpl_24162122 implements ICartDAO_24162122 {

    @Override
    public List<CartItem_24162122> findByUser(Integer userid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM CartItem_24162122 c JOIN FETCH c.book WHERE c.userid = :uid ORDER BY c.bookid",
                    CartItem_24162122.class)
                    .setParameter("uid", userid)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public CartItem_24162122 find(Integer userid, Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.find(CartItem_24162122.class, new com.webdesign.entity.CartItemId_24162122(userid, bookid));
        } finally {
            em.close();
        }
    }

    @Override
    public CartItem_24162122 insert(CartItem_24162122 item) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(item);
            em.getTransaction().commit();
            return item;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean updateQuantity(Integer userid, Integer bookid, int quantity) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            int updated = em.createQuery(
                    "UPDATE CartItem_24162122 c SET c.quantity = :qty WHERE c.userid = :uid AND c.bookid = :bid")
                    .setParameter("qty", quantity)
                    .setParameter("uid", userid)
                    .setParameter("bid", bookid)
                    .executeUpdate();
            em.getTransaction().commit();
            return updated > 0;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(Integer userid, Integer bookid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            CartItem_24162122 item = em.find(CartItem_24162122.class,
                    new com.webdesign.entity.CartItemId_24162122(userid, bookid));
            if (item == null) {
                em.getTransaction().rollback();
                return false;
            }
            em.remove(item);
            em.getTransaction().commit();
            return true;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void clear(Integer userid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM CartItem_24162122 c WHERE c.userid = :uid")
                    .setParameter("uid", userid)
                    .executeUpdate();
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
