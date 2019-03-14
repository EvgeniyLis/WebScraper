package com.evgen.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtil {
	
    private static Logger LOGGER = LoggerFactory.getLogger(HibernateUtil.class);

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                sessionFactory = new Configuration().configure()
                        .buildSessionFactory();
                LOGGER.trace("sessionFactory is created");
            } catch (Throwable ex) {
                LOGGER.error("Initial SessionFactory creation failed.", ex);
                throw new RuntimeException(ex);
            }
        }
        return sessionFactory;
    }


}
