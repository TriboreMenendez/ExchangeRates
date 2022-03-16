package tribore.exchangerates.ui.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import tribore.exchangerates.databinding.ActivityMainBinding
import tribore.exchangerates.ui.adapter.CurrencyRatesAdapter
import tribore.exchangerates.ui.viewodels.CurrencyViewModel
import tribore.exchangerates.ui.viewodels.NetworkStatus

class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private val adapterCurrencyRecycler = CurrencyRatesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val recyclerViewCurrencyRates = binding.recyclerViewCurrencyRates
        recyclerViewCurrencyRates.adapter = adapterCurrencyRecycler

        viewModel.networkStatus.observe(this) {
            when(it) {
                NetworkStatus.LOADING -> {
                    showProgressBar()
                    hideNetworkError()
                }
                NetworkStatus.ERROR -> {
                    hideProgressBar()
                    showNetworkError()
                }
                else -> {
                    adapterCurrencyRecycler.submitList(viewModel.listRatesCurrency.value)
                    hideProgressBar()
                    hideNetworkError()
                }
            }
        }


    }

    private fun showNetworkError() {
        binding.imageNetworkError.visibility = View.VISIBLE
        binding.textNetworkError.visibility = View.VISIBLE
    }

    private fun hideNetworkError() {
        binding.imageNetworkError.visibility = View.INVISIBLE
        binding.textNetworkError.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBarLoadingData.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBarLoadingData.visibility = View.INVISIBLE
    }
}