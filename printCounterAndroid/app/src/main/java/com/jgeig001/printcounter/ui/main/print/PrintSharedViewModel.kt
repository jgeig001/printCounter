package com.jgeig001.printcounter.ui.main.print

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgeig001.printcounter.model.repository.PrintJobRepository
import com.jgeig001.printcounter.model.repository.PrinterRepository
import com.jgeig001.printcounter.ui.main.print.viewObjects.PrintJobData
import com.jgeig001.printcounter.ui.main.print.viewObjects.PrinterWithOwner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PrintSharedViewModel @Inject constructor(
    private val printerRepository: PrinterRepository,
    val printJobRepository: PrintJobRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val printerLivaDataList = MutableLiveData<List<PrinterWithOwner>>()

    val printJobData: PrintJobData = PrintJobData()

    fun loadPrinter(userId: Int) {
        viewModelScope.launch {
            coroutineScope {
                val response = printerRepository.allPrinterUsedByUser(userId)
                if (response.isSuccessful) {
                    val printerList = response.body()
                    printerLivaDataList.postValue(printerList ?: emptyList())
                }
            }
        }
    }

}