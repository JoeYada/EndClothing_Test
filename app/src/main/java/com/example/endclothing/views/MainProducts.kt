package com.example.endclothing.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.endclothing.R
import com.example.endclothing.adapters.ProductAdapter
import com.example.endclothing.databinding.FragmentMainProductsBinding
import com.example.endclothing.utils.LoadState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainProducts : Fragment() {

    private var viewModelAdapter: ProductAdapter? = null

    //Create instance of the viewModel by viewModel
    private val viewModel by viewModel<MainProductViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMainProductsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_products, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        viewModelAdapter = ProductAdapter()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = viewModelAdapter
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObserver()
    }

    private fun setUpObserver(){
        viewModel.productResults.observe(viewLifecycleOwner) { product ->
            product?.let {
                Log.d("List Size", it.size.toString())
                viewModelAdapter?.products = it
            }
        }

        viewModel.loadState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LoadState.Status.LOADING -> {
                    view?.findViewById<ProgressBar>(R.id.progressbar)?.visibility = View.VISIBLE
                }
                LoadState.Status.SUCCESS -> {
                    view?.findViewById<ProgressBar>(R.id.progressbar)?.visibility = View.GONE
                }
                LoadState.Status.FAILED -> {
                    view?.findViewById<ProgressBar>(R.id.progressbar)?.visibility = View.GONE
                }
            }
        })
    }
}