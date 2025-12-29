package com.tech.empedancemachinetask.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tech.empedancemachinetask.R
import com.tech.empedancemachinetask.common.AppUtils
import com.tech.empedancemachinetask.databinding.RootItemBinding
import com.tech.empedancemachinetask.models.Data
import javax.inject.Inject

class CategoriesAdapter @Inject constructor(
    private val appUtils: AppUtils
) : ListAdapter<Data, CategoriesAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = RootItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CategoryViewHolder(val binding: RootItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Data) {
            binding.tvCategoryName.text = category.name

            appUtils.loadSvgOrImage(
                context = binding.root.context,
                imageView = binding.ivCategory,
                url = category.iconUrl,
                placeholder = R.drawable.ic_drill
            )
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }
}
