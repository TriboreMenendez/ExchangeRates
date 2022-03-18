package tribore.exchangerates.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import tribore.exchangerates.R
import tribore.exchangerates.databinding.ItemRecyclerViewCurrencyRatesBinding
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel

class CurrencyRatesAdapter(private val onClick: (RatesCurrencyDomainModel) -> Unit) :
    ListAdapter<RatesCurrencyDomainModel, CurrencyRatesAdapter.CurrencyViewHolder>(DiffCallback) {

    class CurrencyViewHolder(
        private val binding: ItemRecyclerViewCurrencyRatesBinding,
        val onClick: (RatesCurrencyDomainModel) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RatesCurrencyDomainModel) {
            binding.charCodeText.text = item.CharCode
            binding.currencyNameText.text = item.Name
            binding.currencyValueText.text = binding.root.context.getString(
                R.string.nominal,
                item.Nominal.toString(),
                item.Value.toString()
            )
            binding.root.setOnClickListener {
                onClick(item)
            }
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
            ), onClick
        )
    }

    override fun onBindViewHolder(holderCurrency: CurrencyViewHolder, position: Int) {
        holderCurrency.bind(getItem(position))
    }
}