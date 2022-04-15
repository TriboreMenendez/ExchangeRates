package tribore.exchangerates.presentation.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tribore.exchangerates.R
import tribore.exchangerates.databinding.ActivityMainBinding
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.presentation.adapter.CurrencyRatesAdapter
import tribore.exchangerates.presentation.viewodels.CurrencyViewModel
import tribore.exchangerates.presentation.viewodels.DownloadingStatus

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapterCurrencyRecycler = CurrencyRatesAdapter { item -> onClickRecyclerItem(item) }
    private var messageErrorConnection = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerViewCurrencyRates = binding.recyclerViewCurrencyRates
        messageErrorConnection = getString(R.string.message_error_connection)
        recyclerViewCurrencyRates.adapter = adapterCurrencyRecycler

        // Displaying data on the results of the download
        viewModel.downloadingStatus.observe(this) {
            when (it) {
                DownloadingStatus.DONE -> eventSuccessLoadingData()
                else -> eventErrorInternetConnection()
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

    private fun eventErrorInternetConnection() {
        Toast.makeText(this, messageErrorConnection, Toast.LENGTH_SHORT).show()
        binding.swipeRefreshData.isRefreshing = false
        if (binding.progressBarLoadingData.visibility == View.VISIBLE) {
            binding.progressBarLoadingData.visibility =
                View.INVISIBLE
        }
    }

    private fun eventSuccessLoadingData() {
        adapterCurrencyRecycler.submitList(viewModel.listRatesCurrency.value)
        binding.swipeRefreshData.isRefreshing = false

        if (binding.convertCode.text.isEmpty())
            binding.convertCode.text = viewModel.exchangeCurrency.charCode

        if (binding.progressBarLoadingData.visibility == View.VISIBLE) {
            binding.progressBarLoadingData.visibility =
                View.INVISIBLE
        }
    }

    // Click listener for recycler. Choice of currency for conversion
    private fun onClickRecyclerItem(itemCurrency: RatesCurrencyDomainModel) {
        binding.convertCode.text = itemCurrency.charCode
        viewModel.exchangeCurrency.apply {
            charCode = itemCurrency.charCode
            nominal = itemCurrency.nominal
            value = itemCurrency.value
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