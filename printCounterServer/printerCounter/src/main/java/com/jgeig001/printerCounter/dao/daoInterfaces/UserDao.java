package com.jgeig001.printerCounter.dao.daoInterfaces;

import com.jgeig001.printerCounter.entities.PrinterEntity;
import com.jgeig001.printerCounter.entities.UserEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserDao extends BaseDao<UserEntity> {

    @Transactional
    List<PrinterEntity> getOwnPrinter(int id);

    @Transactional
    void changeNameOf(int userId, String newUserName);

    @Transactional
    boolean deleteUser(int user_id);

}
