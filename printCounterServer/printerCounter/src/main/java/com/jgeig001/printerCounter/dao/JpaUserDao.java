package com.jgeig001.printerCounter.dao;

import com.jgeig001.printerCounter.dao.daoInterfaces.PrintJobDao;
import com.jgeig001.printerCounter.dao.daoInterfaces.PrinterDao;
import com.jgeig001.printerCounter.dao.daoInterfaces.UserDao;
import com.jgeig001.printerCounter.entities.PrintJobEntity;
import com.jgeig001.printerCounter.entities.PrinterEntity;
import com.jgeig001.printerCounter.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDao extends BaseJpa implements UserDao {

    @Autowired
    PrinterDao printerDao;
    @Autowired
    PrintJobDao printJobDao;

    @Override
    public Optional<UserEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(UserEntity.class, id));
    }

    @Override
    public List<UserEntity> getAll() {
        Query query = entityManager.createQuery("FROM UserEntity");
        return query.getResultList();
    }

    @Override
    public void save(UserEntity user) {
        // Session session = entityManager.unwrap(Session.class);
        entityManager.persist(user);
    }

    @Override
    public void update(UserEntity user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(UserEntity user) {
        entityManager.remove(user);
    }

    @Deprecated
    @Override
    public List<PrinterEntity> getOwnPrinter(int id) {
        // TODO: delete, just debugging stuff
        String query_str = String.format("FROM PrinterEntity WHERE fOwner = %d", id);
        Query query = entityManager.createQuery(query_str);
        return query.getResultList();
    }

    @Override
    public void changeNameOf(int userId, String newUserName) {
        String query_str = String.format("UPDATE UserEntity SET name = '%s' WHERE id = %d", newUserName, userId);
        Query query = entityManager.createQuery(query_str);
        query.executeUpdate();
    }

    @Override
    public boolean deleteUser(int user_id) {
        // get user entity
        String query_str = String.format("FROM UserEntity WHERE id = %d", user_id);
        Query query = entityManager.createQuery(query_str);
        UserEntity userEntity = (UserEntity) query.getSingleResult();
        // delete print jobs of user
        for (PrintJobEntity printJobEntity : userEntity.getDonePrintJobs()) {
            printJobDao.delete(printJobEntity);
        }
        // delete printer of user
        for (PrinterEntity printerEntity : userEntity.getOwnedPrinterEntities()) {
            printerDao.deletePrinter(printerEntity.getId());
        }
        // delete user
        delete(userEntity);
        return true;
    }

}
