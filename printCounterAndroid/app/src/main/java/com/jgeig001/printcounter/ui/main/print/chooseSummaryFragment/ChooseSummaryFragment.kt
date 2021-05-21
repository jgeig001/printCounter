package com.jgeig001.printcounter.ui.main.print.chooseSummaryFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.jgeig001.printcounter.R
import com.jgeig001.printcounter.databinding.PrintSummaryFragmentBinding
import com.jgeig001.printcounter.ui.main.ApplicationViewModel
import com.jgeig001.printcounter.ui.main.print.PrintSharedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.CancellationException

@AndroidEntryPoint
class ChooseSummaryFragment : Fragment() {

    private val viewModel: PrintSharedViewModel by activityViewModels()
    private val applicationViewModel: ApplicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Obtain binding
        val binding: PrintSummaryFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.print_summary_fragment, container, false)

        // Bind layout with ViewModel
        binding.printJobData = viewModel.printJobData

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.confirm_printjob_button).setOnClickListener {
            executePrintJob()
        }
    }

    private fun executePrintJob() {
        // exec db operation
        val userId = applicationViewModel.userLiveData.value
        val printerId = viewModel.printJobData.printerWithOwner?.printerId
        val pagesAmount = viewModel.printJobData.pagesAmount ?: 171717
        val paperType = viewModel.printJobData.paperType
        val quantityType = viewModel.printJobData.quantityType
        val colorType = viewModel.printJobData.colorType
        if (listOf(userId, printerId, pagesAmount, paperType, quantityType, colorType)
                .all { optional -> optional != null }
        ) {
            GlobalScope.launch(Dispatchers.Main) {
                viewModel.printJobRepository.newPrintJob(
                    userId!!.id,
                    printerId!!,
                    pagesAmount!!,
                    paperType!!.value,
                    quantityType!!.value,
                    colorType!!.value
                )
            }.invokeOnCompletion { throwable ->
                if (throwable is CancellationException) {
                    applicationViewModel.messageToUserLiveData.postValue("Something went wrong! Restart App!")
                } else {
                    navToMainScreen()
                }
            }
        } else {
            applicationViewModel.messageToUserLiveData.postValue("Something went wrong. Restart App!")
        }
    }

    private fun navToMainScreen() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_printSummaryFragment_to_mainFragment)
    }

}