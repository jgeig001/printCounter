package com.jgeig001.printcounter.model.api

import com.jgeig001.printcounter.model.domain.PrintJob
import com.jgeig001.printcounter.model.domain.Printer
import com.jgeig001.printcounter.model.domain.User
import com.jgeig001.printcounter.ui.main.print.viewObjects.PrinterWithOwner
import retrofit2.Response
import retrofit2.http.*

interface RestApi {

    @GET("/user")
    suspend fun getUser(@Query("id") id: Int): Response<User?>

    @FormUrlEncoded
    @POST("/newUser")
    suspend fun newUser(@Field("name") name: String): Response<User?>

    @FormUrlEncoded
    @POST("/newPrinter")
    suspend fun newPrinter(@Body payload: Map<String?, Any?>?): Response<Printer?>

    @GET("/newJob")
    suspend fun newPrintJob(
        @Query("uid") u_id: Int,
        @Query("pid") p_id: Int,
        @Query("pages") pages: Int,
        @Query("paper") paper: Int,
        @Query("quantity") quantity: Int,
        @Query("color") color: Int
    ): Response<PrintJob>

    @DELETE("/delUser")
    suspend fun deleteUser(@Query("id") user_id: Int): Response<Boolean>

    @DELETE("/delPrinter")
    suspend fun deletePrinter(@Query("id") printer_id: Int): Response<Boolean>

    @DELETE("/delJob")
    suspend fun deletePrintJob(@Query("id") id: Int): Response<Boolean>

    @GET("/jobsOf")
    suspend fun allPrintJobsOfUser(@Query("id") user_id: Int): Response<List<PrintJob?>>

    @PATCH("/username")
    suspend fun changeNameOfUser(
        @Query("id") user_id: Int,
        @Query("name") name: String?
    ): Response<Boolean>

    @PATCH("/printername")
    suspend fun changeNameOfPrinter(
        @Query("id") printer_id: Int,
        @Query("name") name: String?
    ): Response<Boolean>

    @GET("/allPrinters")
    suspend fun allPrinterOfUser(@Query("id") user_id: Int): Response<List<Printer?>>

    @GET("/patchCosts")
    suspend fun changeCostsOfPrinter(
        @Query("id") printer_id: Int,
        @Body payload: Map<String?, Any?>?
    ): Response<Boolean>

    @GET("/printer")
    suspend fun getPrinter(@Query("id") id: Int): Response<Printer>

    @GET("/allPrintersUsedBy")
    suspend fun allPrinterUsedByUser(@Query("id") id: Int): Response<List<PrinterWithOwner>>

    //@FormUrlEncoded
    @JvmSuppressWildcards
    @POST("/ownerOfPrinter")
    suspend fun ownerOfPrinter(@Body payload: Map<String?, Any?>?): Response<List<User>>

}