package com.jgeig001.printcounter.model.repository

import com.jgeig001.printcounter.model.api.DataLoaderService
import com.jgeig001.printcounter.model.domain.Printer
import com.jgeig001.printcounter.model.domain.User
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val dataLoaderService: DataLoaderService) {

    suspend fun getUser(id: Int): Response<User?> {
        return dataLoaderService.getUser(id);
    }

    suspend fun newUser(name: String): Response<User?> {
        return dataLoaderService.newUser(name)
    }

    suspend fun deleteUser(id: Int): Response<Boolean> {
        return dataLoaderService.deleteUser(id)
    }

    suspend fun changeNameOfUser(id: Int, name: String) {
        dataLoaderService.changeNameOfUser(id, name)
    }

    suspend fun allPrinterOfUser(id: Int): Response<List<Printer?>> {
        return dataLoaderService.allPrinterOfUser(id)
    }

    suspend fun ownerOfPrinter(printerIdList: List<Int>?): Response<List<User>> {
        return dataLoaderService.ownerOfPrinter(printerIdList)
    }

}