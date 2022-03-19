package tribore.exchangerates.ui.viewodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tribore.exchangerates.domain.model.ExchangeCurrency
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.domain.usecase.ConvertCurrencyUseCase
import tribore.exchangerates.domain.usecase.GetListCurrencyUseCase
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val useCaseGetCurrency: GetListCurrencyUseCase,
    private val useCaseConvertCurrency: ConvertCurrencyUseCase
) :
    ViewModel() {

    var amountRubleForExchange = 100
    val exchangeCurrency: ExchangeCurrency = ExchangeCurrency()
    private val timerDelay = 10000L // 10 second
    private var flagInit = true

    private val _resultConversion = MutableLiveData<Double>()
    val resultConversion: LiveData<Double> = _resultConversion

    private val _networkStatus = MutableLiveData<DownloadingStatus>()
    val downloadingStatus: LiveData<DownloadingStatus> = _networkStatus

    private val _listRatesCurrency = MutableLiveData<List<RatesCurrencyDomainModel>>()
    val listRatesCurrency: LiveData<List<RatesCurrencyDomainModel>> = _listRatesCurrency

    init {
        startUpdateTimer()
    }

    // Update data every $timerDelay milliseconds
    private fun startUpdateTimer() {
        object : CountDownTimer(timerDelay, 9999) {
            override fun onTick(p0: Long) {
                downloadData()
            }

            override fun onFinish() {
                startUpdateTimer()
            }
        }.start()
    }

    fun downloadData() {
        //_listRatesCurrency.value = listOf()

        viewModelScope.launch {
            try {
                _listRatesCurrency.value = useCaseGetCurrency.execute()
                if (flagInit) setBaseExchangeCurrency()
                _networkStatus.value = DownloadingStatus.DONE
            } catch (e: Exception) {
                _networkStatus.value = DownloadingStatus.ERROR
            }
        }
    }

    private fun setBaseExchangeCurrency() {
        exchangeCurrency.apply {
            charCode = _listRatesCurrency.value!![0].CharCode
            value = _listRatesCurrency.value!![0].Value
            nominal = _listRatesCurrency.value!![0].Nominal
        }
        flagInit = false
    }

    fun convertCurrency() {
        _resultConversion.value =
            useCaseConvertCurrency.execute(amountRubleForExchange, exchangeCurrency)
    }
}

enum class DownloadingStatus {
    ERROR, DONE
}