package com.tech.empedancemachinetask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tech.empedancemachinetask.R
import com.tech.empedancemachinetask.common.AppUtils
import com.tech.empedancemachinetask.databinding.AcServicesListItemBinding
import com.tech.empedancemachinetask.models.AcService
import javax.inject.Inject

class AcServicesAdapter :
    ListAdapter<AcService, AcServicesAdapter.AcServiceViewHolder>(AcServiceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcServiceViewHolder {
        val binding = AcServicesListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AcServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AcServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AcServiceViewHolder(val binding: AcServicesListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(service: AcService) {
            binding.tvServiceName.text = service.name
            binding.tvRating.text = service.rating
            binding.tvReviews.text = "(${service.reviews} reviews)"

            Glide.with(binding.ivService)
                .load(service.iconUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(binding.ivService)
        }
    }

    class AcServiceDiffCallback : DiffUtil.ItemCallback<AcService>() {
        override fun areItemsTheSame(oldItem: AcService, newItem: AcService): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AcService, newItem: AcService): Boolean {
            return oldItem == newItem
        }
    }
}
