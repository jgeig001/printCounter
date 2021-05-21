package com.jgeig001.printcounter.ui.main.print.choosePrinterFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jgeig001.printcounter.R
import com.jgeig001.printcounter.databinding.PrintChoosePrinterFragmentBinding
import com.jgeig001.printcounter.model.domain.printTypes.ColorType
import com.jgeig001.printcounter.ui.main.ApplicationViewModel
import com.jgeig001.printcounter.ui.main.print.PrintSharedViewModel
import com.jgeig001.printcounter.ui.main.print.viewObjects.PrinterWithOwner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoosePrinterFragment : Fragment() {

    private lateinit var binding: PrintChoosePrinterFragmentBinding

    private val printViewModel: PrintSharedViewModel by activityViewModels()
    private val applicationViewModel: ApplicationViewModel by activityViewModels()

    private lateinit var recyclerViewPrinterList: RecyclerView
    private lateinit var printerListAdapter: ChoosePrinterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = DataBindingUtil.inflate(
            inflater,
            R.layout.print_choose_printer_fragment,
            container,
            false
        )

        this.binding.lifecycleOwner = this
        this.binding.choosePrinterViewModel = printViewModel
        this.binding.applicationViewModel = applicationViewModel

        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        applicationViewModel.isLoadingLiveData.postValue(true)

        applicationViewModel.userLiveData.value?.let { user ->
            val userID = user.id
            printViewModel.loadPrinter(userID)
        }

        // setup recycler view
        this.recyclerViewPrinterList = view.findViewById(R.id.printChoosePrinterRecyclerview)
        this.recyclerViewPrinterList.layoutManager = LinearLayoutManager(this.requireContext())
        this.printerListAdapter = ChoosePrinterListAdapter(this::onPrinterCardClick)
        this.recyclerViewPrinterList.adapter = printerListAdapter

        // supply recycler view with data
        printViewModel.printerLivaDataList.observe(viewLifecycleOwner, { newPrinterList ->
            this.printerListAdapter.updateData(newPrinterList)
            applicationViewModel.isLoadingLiveData.postValue(false)
        })

    }

    private fun onPrinterCardClick(printerWithOwner: PrinterWithOwner) {
        printViewModel.printJobData.printerWithOwner = printerWithOwner
        if (printerWithOwner.hasColor) {
            navigateToChooseColor()
        }
        printViewModel.printJobData.colorType = ColorType.BLACK
        skipChooseColor()
    }

    private fun navigateToChooseColor() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_print_choose_printer_fragment_to_print_choose_color_fragment)
    }

    private fun skipChooseColor() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_printChoosePrinterFragment_to_printChoosePaperFragment)
    }

}