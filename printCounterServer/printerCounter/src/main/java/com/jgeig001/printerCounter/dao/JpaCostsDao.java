package com.jgeig001.printerCounter.dao;

import com.jgeig001.printerCounter.dao.daoInterfaces.CostDao;
import com.jgeig001.printerCounter.entities.CostEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class JpaCostsDao extends BaseJpa implements CostDao {
    @Override
    public Optional<CostEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(CostEntity.class, id));
    }

    @Override
    public List<CostEntity> getAll() {
        Query query = entityManager.createQuery("FROM CostEntity");
        return query.getResultList();
    }

    @Override
    public void save(CostEntity costEntity) {
        entityManager.persist(costEntity);
    }

    @Override
    public void update(CostEntity costEntity) {
        entityManager.merge(costEntity);
    }

    @Override
    public void delete(CostEntity costEntity) {
        entityManager.remove(costEntity);
    }

    @Override
    public List<CostEntity> getAllOfPrinter(int printerId) {
        String query_str = String.format("FROM CostEntity WHERE fPrinter = %d", printerId);
        Query query = entityManager.createQuery(query_str);
        return query.getResultList();
    }
}
