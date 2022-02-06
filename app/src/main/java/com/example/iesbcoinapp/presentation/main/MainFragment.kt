package com.example.iesbcoinapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.example.iesbcoinapp.presentation.base.BaseFragment
import com.example.iesbcoinapp.R
import com.example.iesbcoinapp.core.utils.toggleVisibility
import com.example.iesbcoinapp.databinding.MainFragmentBinding
import com.example.iesbcoinapp.domain.entities.CoinEntity
import com.example.iesbcoinapp.presentation.base.GenericListSkeleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment :
    BaseFragment<MainFragmentBinding>() {

    private val viewModel: MainViewModel by viewModels()

    // Adapter
    //private lateinit var genericAdapter: GenericAdapter<CoinEntity>

    private lateinit var skeleton: GenericListSkeleton

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> MainFragmentBinding
        get() = MainFragmentBinding::inflate

    override fun setupFragment() {
        setupRecyclerView()

        setupSkeleton()

        setupObservers()
    }

    private fun setupSkeleton() {
        skeleton = GenericListSkeleton(binding.recyclerview, R.layout.recyclerview_cell_icon)
        skeleton.showSkeletons()
    }

    private fun setupObservers() {
        viewModel.coinsData.observe(viewLifecycleOwner) { data ->
            if (data.isEmpty()) {
                binding.placeholder.placeholderContainer.toggleVisibility(true)

                skeleton.hideSkeletons()
                return@observe
            }
            binding.placeholder.placeholderContainer.toggleVisibility(false)

            skeleton.hideSkeletons()
            binding.lastUpdated.text = getString(R.string.last_updated_str, data.first().update_at)

            //genericAdapter.submitList(data)
        }
    }

    private fun setupRecyclerView() {
//        binding.recyclerview.apply {
//            adapter = adapter
//        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<CoinEntity>() {
        override fun areItemsTheSame(
            oldItem: CoinEntity,
            newItem: CoinEntity
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(
            oldItem: CoinEntity,
            newItem: CoinEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
