package com.jgeig001.printcounter.ui.main.print.chooseQuantityFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.jgeig001.printcounter.R
import com.jgeig001.printcounter.model.domain.printTypes.PaperType
import com.jgeig001.printcounter.model.domain.printTypes.QuantityType
import com.jgeig001.printcounter.ui.main.print.PrintSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseQuantityFragment : Fragment() {

    private val printSharedViewModel: PrintSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.print_choose_quantity_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_choose_quantity_much).setOnClickListener {
            printSharedViewModel.printJobData.quantityType = QuantityType.QUANTITY_MUCH
            navToOverviewFragment()
        }
        view.findViewById<Button>(R.id.button_choose_quantity_average).setOnClickListener {
            printSharedViewModel.printJobData.quantityType = QuantityType.QUANTITY_AVERAGE
            navToOverviewFragment()
        }
        view.findViewById<Button>(R.id.button_choose_quantity_little).setOnClickListener {
            printSharedViewModel.printJobData.quantityType = QuantityType.QUANTITY_LITTLE
            navToOverviewFragment()
        }
    }

    private fun navToOverviewFragment() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_printChooseQuantityFragment_to_printSummaryFragment)
    }

}