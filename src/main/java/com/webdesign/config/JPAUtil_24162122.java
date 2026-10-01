package com.webdesign.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil_24162122 {

    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("webdeignPU");

    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    public static void shutdown() {
        EMF.close();
    }
}
