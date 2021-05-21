package com.jgeig001.printcounter.model.api

import com.google.gson.GsonBuilder
import com.jgeig001.printcounter.model.domain.PrintJob
import com.jgeig001.printcounter.model.domain.Printer
import com.jgeig001.printcounter.model.domain.User
import com.jgeig001.printcounter.ui.main.print.viewObjects.PrinterWithOwner
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataLoaderService {

    private val BASE_URL = "http://192.168.2.105:8080/"
    private val BASE_URL_LAN = "http://192.168.2.106:8080/"

    private val api: RestApi

    init {
        val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .setPrettyPrinting()
            .create()

        api = Retrofit.Builder()
            .baseUrl(BASE_URL_LAN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RestApi::class.java)
    }

    suspend fun getUser(id: Int): Response<User?> {
        return api.getUser(id)
    }

    suspend fun newUser(name: String): Response<User?> {
        return api.newUser(name)
    }

    suspend fun deleteUser(id: Int): Response<Boolean> {
        return api.deleteUser(id)
    }

    suspend fun allPrintJobsOfUser(id: Int): Response<List<PrintJob?>> {
        return api.allPrintJobsOfUser(id)
    }

    suspend fun changeNameOfUser(id: Int, name: String): Response<Boolean> {
        return api.changeNameOfUser(id, name)
    }

    suspend fun allPrinterOfUser(id: Int): Response<List<Printer?>> {
        return api.allPrinterOfUser(id)
    }

    suspend fun newPrintJob(
        u_id: Int,
        p_id: Int,
        pages: Int,
        paper: Int,
        quantity: Int,
        color: Int
    ): Response<PrintJob> {
        return api.newPrintJob(u_id, p_id, pages, paper, quantity, color)
    }

    suspend fun newPrinter(payload: Map<String?, Any?>?): Response<Printer?> {
        return api.newPrinter(payload)
    }

    suspend fun deletePrinter(printer_id: Int): Response<Boolean> {
        return api.deletePrinter(printer_id)
    }

    suspend fun changeNameOfPrinter(printer_id: Int, name: String?): Response<Boolean> {
        return api.changeNameOfPrinter(printer_id, name)
    }

    suspend fun changeCostsOfPrinter(
        printer_id: Int,
        payload: Map<String?, Any?>?
    ): Response<Boolean> {
        return api.changeCostsOfPrinter(printer_id, payload)
    }

    suspend fun getPrinter(id: Int): Response<Printer> {
        return api.getPrinter(id)
    }

    suspend fun allPrinterUsedByUser(id: Int): Response<List<PrinterWithOwner>> {
        return api.allPrinterUsedByUser(id)
    }

    suspend fun ownerOfPrinter(printerIdList: List<Int>?): Response<List<User>> {
        val fakeMap = HashMap<String?, Any?>()
        fakeMap["printerIdList"] = printerIdList
        return api.ownerOfPrinter(fakeMap)
    }

}