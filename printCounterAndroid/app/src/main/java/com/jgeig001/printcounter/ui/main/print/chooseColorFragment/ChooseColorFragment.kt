package com.jgeig001.printcounter.ui.main.print.chooseColorFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.jgeig001.printcounter.R
import com.jgeig001.printcounter.model.domain.printTypes.ColorType
import com.jgeig001.printcounter.ui.main.print.PrintSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseColorFragment : Fragment() {

    private val printViewModel: PrintSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.print_choose_color_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.button_choose_black).setOnClickListener {
            printViewModel.printJobData.colorType = ColorType.BLACK
            navToChoosePaperFragment()
        }
        view.findViewById<Button>(R.id.button_choose_colored).setOnClickListener {
            printViewModel.printJobData.colorType = ColorType.COLORED
            navToChoosePaperFragment()
        }
    }

    private fun navToChoosePaperFragment() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_print_choose_printer_fragment_to_print_choose_color_fragment)
    }

}