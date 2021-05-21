package com.jgeig001.printerCounter.dao.daoInterfaces;

import com.jgeig001.printerCounter.entities.PrintJobEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PrintJobDao extends BaseDao<PrintJobEntity> {

    @Transactional
    void deleteWith(int id);

    @Transactional
    List<PrintJobEntity> getAllOf(int userId);

    @Transactional
    boolean jobDoneBy(int printerId);

}
