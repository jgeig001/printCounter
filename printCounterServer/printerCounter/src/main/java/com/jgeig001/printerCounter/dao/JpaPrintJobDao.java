package com.jgeig001.printerCounter.dao;

import com.jgeig001.printerCounter.dao.daoInterfaces.PrintJobDao;
import com.jgeig001.printerCounter.entities.PrintJobEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class JpaPrintJobDao extends BaseJpa implements PrintJobDao {

    @Override
    public Optional<PrintJobEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(PrintJobEntity.class, id));
    }

    @Override
    public List<PrintJobEntity> getAll() {
        Query query = entityManager.createQuery("FROM PrintJobEntity");
        return query.getResultList();
    }

    @Override
    public void save(PrintJobEntity printerJobEntity) {
        entityManager.persist(printerJobEntity);
    }

    @Override
    public void update(PrintJobEntity printerJobEntity) {
        entityManager.merge(printerJobEntity);
    }

    @Override
    public void delete(PrintJobEntity printerJobEntity) {
        entityManager.remove(printerJobEntity);
    }

    @Override
    public void deleteWith(int id) {
        String query_str = String.format("DELETE FROM PrintJobEntity WHERE id = %d", id);
        Query query = entityManager.createQuery(query_str);
        query.executeUpdate();
    }

    @Override
    public List<PrintJobEntity> getAllOf(int userId) {
        String query_str = String.format("FROM PrintJobEntity WHERE fOrderer = %d", userId);
        Query query = entityManager.createQuery(query_str);
        return query.getResultList();
    }

    @Override
    public boolean jobDoneBy(int printerId) {
        String query_str = String.format("SELECT count(*) FROM PrintJobEntity WHERE fPrinter = %d", printerId);
        Query query = entityManager.createQuery(query_str);
        int jobsDoneByPrinter = ((Number) query.getSingleResult()).intValue();
        return jobsDoneByPrinter > 0;
    }


}
