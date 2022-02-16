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


    inner class MainVh(val binding: RecyclerviewCellIconTestBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: CoinEntity, counter: Int) {
            binding.counterText.text = "${counter + 1}"
            binding.icon.load(model.icon) {
                crossfade(true)
                placeholder(R.drawable.ic_launcher_background)
                error(R.drawable.ic_launcher_background)
            }

            binding.coinTitle.text = model.name

            val formattedMarketCap = BigDecimal(model.marketCap).setScale(4, RoundingMode.HALF_EVEN)
            binding.marketcap.text = "$formattedMarketCap..."

            val img = if (model.isFavourite) R.drawable.ic_baseline_star_rate_24
            else R.drawable.ic_baseline_star_outline_24
            binding.starIcon.load(img)

            binding.starIcon.setOnClickListener {
                starCb?.invoke(model.id, model.isFavourite.not())
            }
        }
    }

    private var starCb: ((id: Int, status: Boolean) -> Unit)? = null
    fun setOnStarClick(cb: (id: Int, status: Boolean) -> Unit) {
        starCb = cb
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
