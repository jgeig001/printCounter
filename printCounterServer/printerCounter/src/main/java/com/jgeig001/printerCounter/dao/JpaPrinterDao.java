package com.jgeig001.printerCounter.dao;

import com.jgeig001.printerCounter.dao.daoInterfaces.PrinterDao;
import com.jgeig001.printerCounter.entities.CostEntity;
import com.jgeig001.printerCounter.entities.PrintJobEntity;
import com.jgeig001.printerCounter.entities.PrinterEntity;
import com.jgeig001.printerCounter.entities.specialEntities.PrinterWithOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.format.Printer;
import org.springframework.stereotype.Service;
import reactor.util.function.Tuple2;

import javax.persistence.Query;
import java.util.*;

@Service
public class JpaPrinterDao extends BaseJpa implements PrinterDao {

    @Autowired
    private JpaCostsDao jpaCostsDao;
    @Autowired
    private JpaPrintJobDao jpaPrintJobDao;

    @Override
    public Optional<PrinterEntity> get(int id) {
        return Optional.ofNullable(entityManager.find(PrinterEntity.class, id));
    }

    @Override
    public List<PrinterEntity> getAll() {
        Query query = entityManager.createQuery("FROM PrinterEntity");
        return query.getResultList();
    }

    @Override
    public void save(PrinterEntity printerEntity) {
        entityManager.persist(printerEntity);
        for (CostEntity costEntity : printerEntity.getCosts()) {
            jpaCostsDao.save(costEntity);
        }
    }

    @Override
    public void update(PrinterEntity printerEntity) {
        entityManager.merge(printerEntity);
        for (CostEntity costEntity : printerEntity.getCosts()) {
            jpaCostsDao.update(costEntity);
        }
    }

    @Override
    public void delete(PrinterEntity printerEntity) {
        entityManager.remove(printerEntity);
    }


    @Override
    public void changeNameOf(int printerId, String newPrinterName) {
        String query_str = String.format("UPDATE PrinterEntity SET name = '%s' WHERE id = %d", newPrinterName, printerId);
        Query query = entityManager.createQuery(query_str);
        query.executeUpdate();
    }

    @Override
    public List<PrinterEntity> getAllPrinterOwnedBy(int userId) {
        String query_str = String.format("FROM PrinterEntity WHERE fOwner = %d", userId);
        Query query = entityManager.createQuery(query_str);
        return query.getResultList();
    }

    @Override
    public void deletePrinter(int printerId) {
        if (anyPrinterReferencesInPrintJobs(printerId)) {
            // deactivate printer
            String query_str = String.format("UPDATE PrinterEntity SET alive = false WHERE id = %d", printerId);
            Query query = entityManager.createQuery(query_str);
            query.executeUpdate();
        } else {
            // incl. costs
            String query_str = String.format("DELETE FROM CostEntity WHERE fPrinter = %d", printerId);
            Query query = entityManager.createQuery(query_str);
            query.executeUpdate();
            // complete deletion
            query_str = String.format("DELETE FROM PrinterEntity WHERE id = %d", printerId);
            query = entityManager.createQuery(query_str);
            query.executeUpdate();
        }
    }

    private boolean anyPrinterReferencesInPrintJobs(int printerId) {
        return jpaPrintJobDao.jobDoneBy(printerId);
    }

    public List<PrinterEntity> allPrinterUsedByUser(int user_id) {
        String query_str = String.format("SELECT job.fPrinter FROM PrintJobEntity job WHERE job.fOrderer = %d", user_id);
        Query query = entityManager.createQuery(query_str);
        List<PrinterEntity> listOfPrinters = query.getResultList();
        // hide done printJobs for less data traffic
        for (PrinterEntity printer : listOfPrinters) {
            printer.setPrintJobs(Collections.emptyList());
        }
        // sort by amount of uses
        HashMap<PrinterEntity, Integer> printerAmountMap = new HashMap<>();
        for (PrinterEntity printer : listOfPrinters) {
            if (printerAmountMap.containsKey(printer)) {
                printerAmountMap.merge(printer, 1, Integer::sum);
            } else {
                printerAmountMap.put(printer, 1);
            }
        }
        List<Pair<PrinterEntity, Integer>> sortedPrinterPairs = new ArrayList<>();
        for (Map.Entry<PrinterEntity, Integer> entry : printerAmountMap.entrySet()) {
            sortedPrinterPairs.add(Pair.of(entry.getKey(), entry.getValue()));
        }
        sortedPrinterPairs.sort(Comparator.comparing(Pair::getSecond));
        listOfPrinters.clear();
        for (Pair<PrinterEntity, Integer> pair : sortedPrinterPairs) {
            listOfPrinters.add(pair.getFirst());
        }
        // remove own printers
        listOfPrinters.removeIf(printer -> printer.getfOwner().getId() == user_id);
        return listOfPrinters;
    }
}
