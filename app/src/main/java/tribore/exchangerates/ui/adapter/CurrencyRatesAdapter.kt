package tribore.exchangerates.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import tribore.exchangerates.databinding.ItemRecyclerViewCurrencyRatesBinding
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel

class CurrencyRatesAdapter :
    ListAdapter<RatesCurrencyDomainModel, CurrencyRatesAdapter.CurrencyViewHolder>(DiffCallback) {

    class CurrencyViewHolder(private val binding: ItemRecyclerViewCurrencyRatesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RatesCurrencyDomainModel) {
            binding.charCodeText.text = item.CharCode
            binding.currencyNameText.text = item.Name
            binding.currencyValueText.text = item.Value.toString()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<RatesCurrencyDomainModel>() {
        override fun areItemsTheSame(
            oldItem: RatesCurrencyDomainModel,
            newItem: RatesCurrencyDomainModel
        ) =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: RatesCurrencyDomainModel,
            newItem: RatesCurrencyDomainModel
        ) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            ItemRecyclerViewCurrencyRatesBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holderCurrency: CurrencyViewHolder, position: Int) {
        holderCurrency.bind(getItem(position))
    }
}