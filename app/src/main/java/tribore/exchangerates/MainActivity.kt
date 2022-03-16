package tribore.exchangerates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import tribore.exchangerates.databinding.ActivityMainBinding
import tribore.exchangerates.ui.CurrencyViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: CurrencyViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.networkStatus.observe(this) {
            binding.textView.text = viewModel.listRatesCurrency.value?.get(0)?.Name
        }
    }
}