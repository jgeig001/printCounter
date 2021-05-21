package com.jgeig001.printerCounter.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

public class BaseJpa {

    @Autowired
    protected EntityManager entityManager;

    // TODO: save delete
    @Transactional
    protected void executeInsideTransaction(Consumer<EntityManager> action) {
        final EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

}
