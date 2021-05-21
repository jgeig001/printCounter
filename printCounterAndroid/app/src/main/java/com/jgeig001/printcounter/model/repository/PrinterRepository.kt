package com.jgeig001.printcounter.model.repository

import com.jgeig001.printcounter.model.api.DataLoaderService
import com.jgeig001.printcounter.model.domain.Printer
import com.jgeig001.printcounter.ui.main.print.viewObjects.PrinterWithOwner
import retrofit2.Response
import javax.inject.Inject

class PrinterRepository @Inject constructor(private val dataLoaderService: DataLoaderService) {

    suspend fun newPrinter(payload: Map<String?, Any?>?): Response<Printer?> {
        return dataLoaderService.newPrinter(payload)
    }

    suspend fun deletePrinter(printer_id: Int): Response<Boolean> {
        return dataLoaderService.deletePrinter(printer_id)
    }

    suspend fun changeNameOfPrinter(printer_id: Int, name: String?): Response<Boolean> {
        return dataLoaderService.changeNameOfPrinter(printer_id, name)
    }

    suspend fun changeCostsOfPrinter(
        printer_id: Int,
        payload: Map<String?, Any?>?
    ): Response<Boolean> {
        return dataLoaderService.changeCostsOfPrinter(printer_id, payload)
    }

    suspend fun getPrinter(id: Int): Response<Printer> {
        return dataLoaderService.getPrinter(id)
    }

    suspend fun allPrinterUsedByUser(id: Int): Response<List<PrinterWithOwner>> {
        return dataLoaderService.allPrinterUsedByUser(id)
    }

}