package tribore.exchangerates.ui.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import tribore.exchangerates.databinding.ActivityMainBinding
import tribore.exchangerates.ui.adapter.CurrencyRatesAdapter
import tribore.exchangerates.ui.viewodels.CurrencyViewModel
import tribore.exchangerates.ui.viewodels.DownloadingStatus

@AndroidEntryPoint
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

        viewModel.downloadingStatus.observe(this) {
            when(it) {
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
                    hideProgressBar()
                    hideNetworkError()
                }
            }
        }

        // Update currency rates by swipe
        binding.swipeRefreshData.setOnRefreshListener { viewModel.downloadData() }
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

}