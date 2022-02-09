package com.example.iesbcoinapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.iesbcoinapp.R
import com.example.iesbcoinapp.databinding.RecyclerviewCellIconTestBinding
import com.example.iesbcoinapp.domain.entities.CoinEntity
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject


class MainAdapter @Inject constructor(
    val diffUtil: DiffUtil.ItemCallback<CoinEntity>
) : ListAdapter<CoinEntity, MainAdapter.MainVh>(diffUtil) {

    class MainVh(val binding: RecyclerviewCellIconTestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CoinEntity, counter: Int) {
            binding.counterText.text = "${counter + 1}"
            binding.icon.load(model.icon) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)

                listener(
                    onSuccess = { request, data ->
                        println("Successo app")
                    },
                    onError = { request, throwable ->
                        println("Error app")
                        println(throwable.localizedMessage)
                        println(request)
                    }
                )
            }

            binding.coinTitle.text = model.name

            val formattedMarketCap = BigDecimal(model.marketCap).setScale(4, RoundingMode.HALF_EVEN)
            binding.marketcap.text = "$formattedMarketCap..."
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainVh {
        return MainVh(
            RecyclerviewCellIconTestBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainVh, position: Int) {
        holder.bind(currentList[position] as CoinEntity, position)
    }


}
