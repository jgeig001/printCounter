package com.jgeig001.printerCounter;

import com.jgeig001.printerCounter.entities.PrintJobEntity;
import com.jgeig001.printerCounter.entities.PrinterEntity;
import com.jgeig001.printerCounter.entities.UserEntity;
import com.jgeig001.printerCounter.entities.specialEntities.PrinterWithOwner;

import java.util.List;
import java.util.Map;

public interface API {

    UserEntity newUser(String name);

    UserEntity getUser(int id);

    PrinterEntity newPrinter(Map<String, Object> payload);

    PrintJobEntity newPrintJob(int u_id, int p_id, int pages, int paper, int quantity, int color);

    boolean deleteUser(int user_id);

    boolean deletePrinter(int printer_id);

    boolean deletePrintJob(int id);

    /**
     * return a list of all print jobs which given user has ordered
     *
     * @param user_id
     */
    List<PrintJobEntity> allPrintJobsOfUser(int user_id);

    boolean changeNameOfUser(int user_id, String name);

    boolean changeNameOfPrinter(int printer_id, String name);

    /**
     * return a list of all printers which given user owns
     *
     * @param user_id
     */
    List<PrinterEntity> allPrinterOfUser(int user_id);

    /**
     * returns a list of printer objects which were used by user
     * sorted by usage, starting with most usage
     * not including own printers
     *
     * @param user_id
     */
    List<PrinterWithOwner> allPrinterUsedByUser(int user_id);

    /**
     * returns list of users matching the printers, considering the order
     *
     * @param payload
     */
    List<UserEntity> ownerOfPrinter(Map<String, Object> payload);

    boolean changeCostsOfPrinter(int printer_id, Map<String, Object> payload);

}