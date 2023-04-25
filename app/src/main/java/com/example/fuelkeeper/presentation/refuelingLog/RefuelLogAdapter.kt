package com.example.fuelkeeper.presentation.refuelingLog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.example.fuelkeeper.R
import com.example.fuelkeeper.databinding.ItemRvFuelLogBinding
import com.example.fuelkeeper.domain.models.RefuelingStatModel

class RefuelLogAdapter(val listener: OnItemClickListener) :
    RecyclerView.Adapter<RefuelLogAdapter.RefuelViewHolder>() {

    private val refuelingList = ArrayList<RefuelingStatModel>()
    var onDeleteBttClickListener: OnDeleteBttClickListener? = null

    inner class RefuelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRvFuelLogBinding.bind(itemView)


        @SuppressLint("SetTextI18n")
        fun bind(data: RefuelingStatModel) {
            binding.apply {
                tvCurrentMileage.text =
                    "${data.currentMileage}  ${itemView.context.getString(R.string.km)} "
                tvRefuelDate.text = data.refuelDate
                tvFuelAverage.text = data.fuelAverage
                tvMileageOnLastRefuel.text =
                    data.lastRefuelDistance.toString() + itemView.context.getString(R.string.km)
                tvLastRefuelPrice.text =
                    data.refuelPayment + itemView.context.getString(R.string.pln)

            }
            itemView.setOnClickListener {
                data.id.let { itemId ->
                    if (itemId != null) {
                        listener.onItemClick(itemId = itemId.toInt())
                    }

                }
            }
            binding.bttDelete.setOnClickListener {
                data.id.let { itemId ->
                    if (itemId != null)
                        onDeleteBttClickListener?.onDeleteClick( itemId)
                }
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

    interface OnItemClickListener {
        fun onItemClick(itemId: Int)
    }

    interface OnDeleteBttClickListener {
        fun onDeleteClick( itemId: Int)
    }
}