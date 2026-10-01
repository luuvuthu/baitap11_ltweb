package com.webdesign.dao.impl;

import com.webdesign.config.JPAUtil_24162122;
import com.webdesign.dao.IOrderDAO_24162122;
import com.webdesign.entity.Orders_24162122;

import jakarta.persistence.EntityManager;
import java.util.List;

public class OrderDAOImpl_24162122 implements IOrderDAO_24162122 {

    @Override
    public Orders_24162122 save(Orders_24162122 order) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            // cascade = ALL nên chi tiết đơn được lưu cùng transaction
            em.getTransaction().commit();
            return order;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Orders_24162122> findByUser(Integer userid) {
        return findByUserAndStatus(userid, null);
    }

    @Override
    public List<Orders_24162122> findByUserAndStatus(Integer userid, String status) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            StringBuilder jpql = new StringBuilder(
                    "SELECT o FROM Orders_24162122 o WHERE o.user.id = :uid");
            if (status != null && !status.isEmpty()) {
                jpql.append(" AND o.status = :status");
            }
            jpql.append(" ORDER BY o.orderid DESC");
            var query = em.createQuery(jpql.toString(), Orders_24162122.class)
                    .setParameter("uid", userid);
            if (status != null && !status.isEmpty()) {
                query.setParameter("status", status);
            }
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Orders_24162122 findById(Integer orderid) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.find(Orders_24162122.class, orderid);
        } finally {
            em.close();
        }
    }
}
