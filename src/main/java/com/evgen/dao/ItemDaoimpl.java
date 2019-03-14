package com.evgen.dao;

import com.evgen.model.Item;
import com.evgen.util.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemDaoimpl implements ItemDao {
	
	private static Logger LOGGER = LoggerFactory
            .getLogger(ItemDaoimpl.class);
	
	private SessionFactory factory = HibernateUtil.getSessionFactory();

	public void saveItem(Item item) {
		Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
            LOGGER.trace("transaction is commited to database");
        } catch (Exception e) {
            LOGGER.error("An error was occured : ", e);
            transactionRollback(transaction);
            throw new RuntimeException(e);
        } finally {
            session.close();
            LOGGER.trace("session close");
        }
	}
	
    private static void transactionRollback(Transaction tr) {
        if (tr != null) {
            tr.rollback();
            LOGGER.trace("Successfully rolled back changes from the database");
        }
    }

}
