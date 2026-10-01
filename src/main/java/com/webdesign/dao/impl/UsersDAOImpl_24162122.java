package com.webdesign.dao.impl;

import com.webdesign.config.JPAUtil_24162122;
import com.webdesign.dao.IUsersDAO_24162122;
import com.webdesign.entity.Users_24162122;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.Date;

public class UsersDAOImpl_24162122 implements IUsersDAO_24162122 {

    @Override
    public Users_24162122 findByUsername(String email) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            TypedQuery<Users_24162122> query = em.createQuery(
                    "SELECT u FROM Users_24162122 u WHERE u.email = :email", Users_24162122.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public Users_24162122 findById(Integer id) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        try {
            return em.find(Users_24162122.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void updateLastLogin(Integer id) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Users_24162122 u = em.find(Users_24162122.class, id);
            if (u != null) {
                u.setLastLogin(new Date());
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Users_24162122 user) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Users_24162122 user) {
        EntityManager em = JPAUtil_24162122.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(user);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}