package com.example.fuelkeeper.presentation.refuelingLog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.ItemRvFuelLogBinding
import com.example.fuelkeeper.domain.models.RefuelingStatModel

class RefuelLogAdapter : RecyclerView.Adapter<RefuelLogAdapter.RefuelViewHolder>() {

    private val refuelingList = ArrayList<RefuelingStatModel>()

    inner class RefuelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvFuelLogBinding.bind(itemView)
        fun bind(data: RefuelingStatModel) {
            binding.apply {
                tvCurrentMileage.text = data.currentMileage.toString()
                tvRefuelDate.text = data.refuelDate
                tvFuelAverage.text = data.fuelAverage.toString()
                tvMileageOnLastRefuel.text = data.lastRefuelDistance.toString()
                tvLastRefuelPrice.text = data.refuelPayment.toString()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefuelViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_fuel_log, parent, false)
        return RefuelViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: RefuelViewHolder, position: Int) {
        holder.bind(refuelingList[position])
    }

    override fun getItemCount(): Int {
        return refuelingList.size
    }

    fun submitList(list: List<RefuelingStatModel>) {
        refuelingList.clear()
        refuelingList.addAll(list.asReversed())
        notifyDataSetChanged()
    }
}