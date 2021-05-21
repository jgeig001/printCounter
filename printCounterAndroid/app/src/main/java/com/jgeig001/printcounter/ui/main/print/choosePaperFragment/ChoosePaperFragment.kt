package com.jgeig001.printcounter.ui.main.print.choosePaperFragment

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
import com.jgeig001.printcounter.ui.main.print.PrintSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChoosePaperFragment : Fragment() {

    private val printViewModel: PrintSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.print_choose_paper_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_choose_paper_high).setOnClickListener {
            printViewModel.printJobData.paperType = PaperType.PAPER_HIGH_QUALITY
            navToChooseQuantityFragment()
        }
        view.findViewById<Button>(R.id.button_choose_paper_normal).setOnClickListener {
            printViewModel.printJobData.paperType = PaperType.PAPER_NORMAL
            navToChooseQuantityFragment()
        }
        view.findViewById<Button>(R.id.button_choose_paper_eco).setOnClickListener {
            printViewModel.printJobData.paperType = PaperType.PAPER_ECO
            navToChooseQuantityFragment()
        }
    }

    private fun navToChooseQuantityFragment() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_print_choose_paper_fragment_to_print_choose_quantity_fragment)
    }

}