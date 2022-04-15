package tribore.exchangerates.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
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
            val resultMovement = item.value - item.previous
            val formatCurrencyMovement = String.format("%.4f", resultMovement)

            binding.charCodeText.text = item.charCode
            binding.currencyNameText.text = item.name
            binding.currencyValueText.text = binding.root.context.getString(
                R.string.nominal,
                item.nominal.toString(),
                item.value.toString()
            )
            binding.root.setOnClickListener {
                onClick(item)
            }

            if (item.value < item.previous) {
                binding.currencyMovementText.text = binding.root.context.getString(
                    R.string.depreciation, formatCurrencyMovement)
                            binding . currencyMovementText . setTextColor (
                            binding.root.context.resources.getColor(R.color.red)
                            )
            } else {
                binding.currencyMovementText.text = binding.root.context.getString(
                    R.string.growth, formatCurrencyMovement)
                binding.currencyMovementText.setTextColor(
                    binding.root.context.resources.getColor(R.color.green)
                )
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