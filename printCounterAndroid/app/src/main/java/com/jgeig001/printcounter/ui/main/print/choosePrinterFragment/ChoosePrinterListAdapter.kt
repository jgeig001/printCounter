package com.jgeig001.printcounter.ui.main.print.choosePrinterFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgeig001.printcounter.databinding.ChoosablePrinterCardBinding
import com.jgeig001.printcounter.model.domain.Printer
import com.jgeig001.printcounter.ui.main.print.viewObjects.PrinterWithOwner

// holder class to hold reference
class PrinterCardViewHolder(var binding: ChoosablePrinterCardBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(printer: PrinterWithOwner) {
        binding.printer = printer
    }
}

class ChoosePrinterListAdapter(
    private val itemClickCallback: (PrinterWithOwner) -> Unit
) : RecyclerView.Adapter<PrinterCardViewHolder>() {

    private var printerList: List<PrinterWithOwner> = emptyList()

    fun updateData(newData: List<PrinterWithOwner>) {
        printerList = newData
        notifyDataSetChanged()
    }

    private var parent: ViewGroup? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrinterCardViewHolder {
        this.parent = parent
        val layoutInfater = LayoutInflater.from(parent.context)
        val binding = ChoosablePrinterCardBinding.inflate(layoutInfater, parent, false)
        return PrinterCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PrinterCardViewHolder, position: Int) {
        val printer = printerList[position]
        holder.bind(printer)
        holder.itemView.setOnClickListener {
            itemClickCallback(printer)
        }
    }

    override fun getItemCount(): Int {
        return printerList.size
    }

}
