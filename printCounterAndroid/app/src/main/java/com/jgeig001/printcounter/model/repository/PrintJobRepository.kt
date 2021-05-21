package com.jgeig001.printcounter.model.repository

import com.jgeig001.printcounter.model.api.DataLoaderService
import com.jgeig001.printcounter.model.domain.PrintJob
import retrofit2.Response
import javax.inject.Inject

class PrintJobRepository @Inject constructor(private val dataLoaderService: DataLoaderService) {

    suspend fun newPrintJob(
        u_id: Int,
        p_id: Int,
        pages: Int,
        paper: Int,
        quantity: Int,
        color: Int
    ): Response<PrintJob> {
        return dataLoaderService.newPrintJob(u_id, p_id, pages, paper, quantity, color)
    }

    suspend fun allPrintJobsOfUser(id: Int): Response<List<PrintJob?>> {
        return dataLoaderService.allPrintJobsOfUser(id)
    }

}