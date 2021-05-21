package com.jgeig001.printerCounter.dao.daoInterfaces;

import com.jgeig001.printerCounter.entities.PrinterEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PrinterDao extends BaseDao<PrinterEntity> {

    @Transactional
    void changeNameOf(int printerId, String newPrinterName);

    @Transactional
    List<PrinterEntity> getAllPrinterOwnedBy(int userId);

    @Transactional
    void deletePrinter(int printerId);
}
