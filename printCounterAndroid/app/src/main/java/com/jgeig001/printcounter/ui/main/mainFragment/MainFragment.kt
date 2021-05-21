package com.jgeig001.printcounter.ui.main.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.jgeig001.printcounter.R
import com.jgeig001.printcounter.databinding.MainFragmentBinding
import com.jgeig001.printcounter.ui.main.ApplicationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: ApplicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Obtain binding
        val binding: MainFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)

        // Bind layout with ViewModel
        binding.applicationViewModel = viewModel

        // LiveData needs the lifecycle owner
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ImageView>(R.id.button_print)?.setOnClickListener {
            navToPrinting()
        }
    }

    fun navToPrinting() {
        NavHostFragment.findNavController(this)
            .navigate(R.id.action_mainFragment_to_print_choose_printer_fragment)
    }

}