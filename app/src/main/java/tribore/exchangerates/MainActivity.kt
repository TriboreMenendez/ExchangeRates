package tribore.exchangerates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import tribore.exchangerates.data.model.valute.USD
import tribore.exchangerates.databinding.ActivityMainBinding
import tribore.exchangerates.ui.CurrencyViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this, Observer {
            binding.textView.text = viewModel.listRates.value?.Valute?.USD?.Value.toString()
        })
    }
}