package com.tech.empedancemachinetask.adapters

import android.R
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.ShapeAppearanceModel
import com.tech.empedancemachinetask.common.AppUtils
import com.tech.empedancemachinetask.databinding.ServicesItemBinding
import com.tech.empedancemachinetask.models.Children
import javax.inject.Inject

class ServicesAdapter @Inject constructor(
    private val appUtils: AppUtils
) : ListAdapter<Children, ServicesAdapter.ServiceViewHolder>(ServiceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ServicesItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<Children>?) {
        val filteredList = list?.filter {
            val isIssue = it.name?.contains("issue", ignoreCase = true) ?: false
            val isHttps = it.iconUrl?.startsWith("https", ignoreCase = true) ?: false
            val isEndWithSvg = it.iconUrl?.endsWith(".svg", ignoreCase = true) ?: false
            !isEndWithSvg && !isIssue && isHttps
        }
        super.submitList(filteredList)
    }

    inner class ServiceViewHolder(val binding: ServicesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(service: Children) {
            binding.tvServiceName.text = service.name
            Glide.with(binding.ivService)
                .load(service.iconUrl)
                .placeholder(R.drawable.ic_menu_gallery)
                .error(R.drawable.ic_delete)
                .timeout(30000)
                .into(binding.ivService)
        }
    }

    class ServiceDiffCallback : DiffUtil.ItemCallback<Children>() {
        override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean {
            return oldItem == newItem
        }
    }
}
