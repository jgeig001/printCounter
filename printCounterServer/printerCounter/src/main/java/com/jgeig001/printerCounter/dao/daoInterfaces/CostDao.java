package com.jgeig001.printerCounter.dao.daoInterfaces;

import com.jgeig001.printerCounter.entities.CostEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CostDao extends BaseDao<CostEntity> {

    @Transactional
    List<CostEntity> getAllOfPrinter(int printerId);

}
