package com.example.endclothing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.endclothing.R
import com.example.endclothing.data.Product
import com.example.endclothing.databinding.ProductItemBinding

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var products: List<Product> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val withDataBinding: ProductItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), ProductViewHolder.LAYOUT,parent,false)
        return ProductViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.viewBinding.also {
            it.result = products[position]
        }
    }

    override fun getItemCount(): Int = products.size

    class ProductViewHolder(val viewBinding: ProductItemBinding): RecyclerView.ViewHolder(viewBinding.root) {
        companion object{
            @LayoutRes
            val LAYOUT = R.layout.product_item
        }

    }
}


