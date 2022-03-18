package tribore.exchangerates.ui.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tribore.exchangerates.R
import tribore.exchangerates.databinding.ActivityMainBinding
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.ui.adapter.CurrencyRatesAdapter
import tribore.exchangerates.ui.viewodels.CurrencyViewModel
import tribore.exchangerates.ui.viewodels.DownloadingStatus

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapterCurrencyRecycler = CurrencyRatesAdapter { item -> onClickRecyclerItem(item) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerViewCurrencyRates = binding.recyclerViewCurrencyRates
        recyclerViewCurrencyRates.adapter = adapterCurrencyRecycler

        // Displaying data on the results of the download
        viewModel.downloadingStatus.observe(this) {
            when (it) {
                DownloadingStatus.LOADING -> {
                    hideNetworkError()
                }
                DownloadingStatus.ERROR -> {
                    hideProgressBar()
                    showNetworkError()
                }
                else -> {
                    adapterCurrencyRecycler.submitList(viewModel.listRatesCurrency.value)
                    binding.recyclerViewCurrencyRates.visibility = View.VISIBLE
                    binding.swipeRefreshData.isRefreshing = false
                    binding.convertCode.text = viewModel.listRatesCurrency.value!![0].CharCode
                    hideProgressBar()
                    hideNetworkError()
                }
            }
        }

        // Update currency rates by swipe
        binding.swipeRefreshData.setOnRefreshListener { viewModel.downloadData() }

        // Currency conversion event
        binding.buttonConversion.setOnClickListener { conversionCurrency() }
        viewModel.resultConversion.observe(this) {
            val formattedResult = String.format("%.2f", it)
            binding.convertResult.text = getString(R.string.textValueConversion, formattedResult)
        }

        // Hide keyboard
        binding.inputAmountText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(view, keyCode)
        }

    }

    private fun showNetworkError() {
        binding.imageNetworkError.visibility = View.VISIBLE
        binding.textNetworkError.visibility = View.VISIBLE
        binding.recyclerViewCurrencyRates.visibility = View.INVISIBLE
        binding.swipeRefreshData.isRefreshing = false
    }

    private fun hideNetworkError() {
        binding.imageNetworkError.visibility = View.INVISIBLE
        binding.textNetworkError.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBarLoadingData.visibility = View.INVISIBLE
    }

    // click listener for recycler. Choice of currency for conversion
    private fun onClickRecyclerItem(itemCurrency: RatesCurrencyDomainModel) {
        binding.convertCode.text = itemCurrency.CharCode
        viewModel.exchangeCurrency.apply {
            nominalExchangeCurrency = itemCurrency.Nominal
            valueExchangeCurrency = itemCurrency.Value
        }
    }

    private fun conversionCurrency() {
        val stringInTextField = binding.inputAmountText.text.toString()
        val amount = stringInTextField.toIntOrNull()

        if (amount == null) {
            binding.convertResult.text = ""
        } else {
            viewModel.amountRubleForExchange = amount
            viewModel.convertCurrency()
        }
    }

    // Hide keyboard after conversion
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {

            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }

}