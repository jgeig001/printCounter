package com.jgeig001.printerCounter;

import com.jgeig001.printerCounter.Exceptions.NoColorPrinterException;
import com.jgeig001.printerCounter.dao.JpaCostsDao;
import com.jgeig001.printerCounter.dao.JpaPrintJobDao;
import com.jgeig001.printerCounter.dao.JpaPrinterDao;
import com.jgeig001.printerCounter.dao.JpaUserDao;
import com.jgeig001.printerCounter.entities.CostEntity;
import com.jgeig001.printerCounter.entities.PrintJobEntity;
import com.jgeig001.printerCounter.entities.PrinterEntity;
import com.jgeig001.printerCounter.entities.UserEntity;
import com.jgeig001.printerCounter.entities.specialEntities.PrinterWithOwner;
import com.jgeig001.printerCounter.model.types.PrintType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping
public class MainController implements API {

    final String DEFAULT_ID_STRING = "-1";
    final int DEFAULT_ID_INT = -1;


    @Autowired
    private JpaUserDao userDao;
    @Autowired
    private JpaPrinterDao printerDao;
    @Autowired
    private JpaPrintJobDao printJobDao;
    @Autowired
    private JpaCostsDao costsDao;

    @GetMapping("/printer")
    public PrinterEntity test(@RequestParam(name = "id", defaultValue = "-1") int id) {
        return printerDao.get(id).get();
    }

    // tested ✔
    @PostMapping("/newUser")
    public UserEntity newUser(@RequestParam(name = "name", defaultValue = "") String name) {
        UserEntity user;
        try {
            if (name.equals(""))
                return null;

            user = new UserEntity(name);
            userDao.save(user);

        } catch (Exception e) {
            return null;
        }
        return user;
    }

    @GetMapping("/user")
    public UserEntity getUser(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int id) {
        Optional<UserEntity> user = userDao.get(id);
        return user.orElse(null);
    }

    // tested ✔
    @PostMapping(value = "/newPrinter", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public PrinterEntity newPrinter(@RequestBody Map<String, Object> payload) {
        PrinterEntity printer;
        try {
            String name = (String) payload.get("name");

            int userId = (int) payload.get("fOwner");
            UserEntity owner = userDao.get(userId).get();

            printer = new PrinterEntity(owner, name);

            PrintType[] printTypes = PrintType.values();
            for (PrintType type : printTypes) {
                String valueAsString = String.valueOf(type.getValue());
                // check if printer can print colors
                Object colorCosts = payload.get(valueAsString);
                if (colorCosts == null && type == PrintType.COLORED) {
                    printer.setHasColor(false);
                    continue;
                }
                float cent = ((Double) payload.get(valueAsString)).floatValue();
                CostEntity costEntity = new CostEntity(printer, type, cent);
                printer.addCostEntity(costEntity);
            }
            printer.updateHasColor();
            printerDao.save(printer);

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        return printer;
    }

    // tested ✔cpu
    @GetMapping("/newJob")
    public PrintJobEntity newPrintJob(@RequestParam(name = "uid", defaultValue = DEFAULT_ID_STRING) int u_id,
                                      @RequestParam(name = "pid", defaultValue = DEFAULT_ID_STRING) int p_id,
                                      @RequestParam(name = "pages") int pages,
                                      @RequestParam(name = "paper") int paper,
                                      @RequestParam(name = "quantity") int quantity,
                                      @RequestParam(name = "color") int color) {
        System.out.println("asjldfg2lsigflisu324gföilsuegföiu5sgeföabwäo9dfqwjdaj+wpdhqwlikueöiwqh3lwsr4s3w364eewe4qakiwqgi7gdaksjgbedliu332");
        PrintJobEntity newPrintJob;
        try {
            if (u_id == DEFAULT_ID_INT || p_id == DEFAULT_ID_INT)
                return null;
            UserEntity user = userDao.get(u_id).get();
            PrinterEntity printer = printerDao.get(p_id).get();
            newPrintJob = new PrintJobEntity(
                    pages,
                    PrintType.getTypeBy(quantity),
                    PrintType.getTypeBy(paper),
                    PrintType.getTypeBy(color),
                    user,
                    printer);
            printJobDao.save(newPrintJob);
        } catch (NoColorPrinterException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "invalid printjob");
        } catch (Exception ex) {
            return null;
        }
        return newPrintJob;
    }

    // tested ✔
    @DeleteMapping("/delUser")
    public boolean deleteUser(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int user_id) {
        try {
            if (user_id == DEFAULT_ID_INT)
                return false;
            UserEntity userEntity = userDao.get(user_id).get();
            boolean noJobs = userEntity.hasNoPrintJobsDone();
            boolean hasNoPrinter = userEntity.hasNoPrinter();
            if (hasNoPrinter && noJobs) {
                userDao.delete(userEntity);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // tested ✔
    @DeleteMapping("/delPrinter")
    public boolean deletePrinter(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int printer_id) {
        try {
            if (printer_id == DEFAULT_ID_INT)
                return false;
            printerDao.deletePrinter(printer_id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // tested ✔
    @DeleteMapping("/delJob")
    public boolean deletePrintJob(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int id) {
        try {
            if (id == DEFAULT_ID_INT)
                return false;
            printJobDao.deleteWith(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Returns a list of all PrintJobs of the user with this id.
     * The list will be empty if no PrintJobs were done.
     * Returns null if an exception occurs or the id is missing.
     *
     * @param user_id
     * @return null if error
     */
    @GetMapping("/jobsOf")
    public List<PrintJobEntity> allPrintJobsOfUser(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int user_id) {
        List<PrintJobEntity> list;
        try {
            if (user_id == -1)
                return null;
            list = printJobDao.getAllOf(user_id);
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    // tested ✔
    @PatchMapping("/username")
    public boolean changeNameOfUser(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int user_id,
                                    @RequestParam(name = "name", defaultValue = "") String name) {
        try {
            if (user_id == DEFAULT_ID_INT || name.equals(""))
                return false;
            userDao.changeNameOf(user_id, name);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // tested ✔
    @PatchMapping("/printername")
    public boolean changeNameOfPrinter(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int printer_id,
                                       @RequestParam(name = "name", defaultValue = "") String name) {
        try {
            if (printer_id == DEFAULT_ID_INT || name.equals(""))
                return false;
            printerDao.changeNameOf(printer_id, name);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    // tested ✔
    @GetMapping("/allPrinters")
    public List<PrinterEntity> allPrinterOfUser(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int user_id) {
        List<PrinterEntity> list;
        try {
            if (user_id == DEFAULT_ID_INT)
                return null;
            list = printerDao.getAllPrinterOwnedBy(user_id);
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    // (tested ✔)
    @GetMapping("/allPrintersUsedBy")
    public List<PrinterWithOwner> allPrinterUsedByUser(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int user_id) {
        List<PrinterEntity> printerList;
        List<PrinterWithOwner> returnList = new ArrayList<>();
        try {
            if (user_id == DEFAULT_ID_INT)
                return null;
            printerList = printerDao.allPrinterUsedByUser(user_id);
            for (PrinterEntity printer : printerList) {
                String ownerName = printer.getfOwner().getName();
                PrinterWithOwner printerWithOwner = new PrinterWithOwner(ownerName, printer);
                System.out.println("slkidufgslidugflisgdflisgdfligsfiu### " + printerWithOwner.getPrinterId());
                returnList.add(printerWithOwner);
            }
        } catch (Exception e) {
            return null;
        }
        return returnList;
    }

    @PostMapping(value = "/ownerOfPrinter", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserEntity> ownerOfPrinter(@RequestBody Map<String, Object> payload) {
        List<Integer> lis = (List<Integer>) payload.get("printerIdList");
        List<UserEntity> list = new ArrayList<>();
        try {
            if (lis.isEmpty())
                return null;
            //list = printerDao.ownerOfPrinter(printer_IdList);
        } catch (Exception e) {
            return null;
        }
        return list;
    }

    // tested ✔
    @PatchMapping(value = "/patchCosts", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public boolean changeCostsOfPrinter(@RequestParam(name = "id", defaultValue = DEFAULT_ID_STRING) int printer_id,
                                        @RequestBody Map<String, Object> payload) {
        try {
            if (printer_id == DEFAULT_ID_INT)
                return false;
            PrinterEntity printerEntity = printerDao.get(printer_id).get();
            List<CostEntity> costs = costsDao.getAllOfPrinter(printer_id);
            for (Map.Entry<String, Object> entry : payload.entrySet()) {
                int key = Integer.parseInt(entry.getKey());
                Object value = entry.getValue();
                Optional<CostEntity> curCostEntity = costs.stream().filter(costEntity -> costEntity.getPrintType() == key).findFirst();
                if (value == null) {
                    // delete cost entity
                    curCostEntity.ifPresent(costEntity -> {
                        costsDao.delete(costEntity);
                        if (costEntity.getPrintType() == PrintType.COLORED.getValue()) {
                            printerEntity.setHasColor(false);
                            printerDao.update(printerEntity);
                        }
                    });
                } else {
                    // update value
                    float newValue = ((Double) value).floatValue();
                    curCostEntity.ifPresent(costEntity -> {
                        costEntity.setCent(newValue);
                        costsDao.update(costEntity);
                    });
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

