package tribore.exchangerates.ui.viewodels

import android.os.CountDownTimer
import android.widget.Toast
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
    val exchangeCurrency: ExchangeCurrency = ExchangeCurrency(0, 1.00)
    private val timerDelay = 10000L // 10 second

    private val _resultConversion = MutableLiveData<Double>()
    val resultConversion: LiveData<Double> = _resultConversion

    private val _networkStatus = MutableLiveData<DownloadingStatus>(DownloadingStatus.LOADING)
    val downloadingStatus: LiveData<DownloadingStatus> = _networkStatus

    private val _listRatesCurrency = MutableLiveData<List<RatesCurrencyDomainModel>>()
    val listRatesCurrency: LiveData<List<RatesCurrencyDomainModel>> = _listRatesCurrency

    init {
        startUpdateTimer()
    }

    fun downloadData() {
        _listRatesCurrency.value = listOf()
        _networkStatus.value = DownloadingStatus.LOADING

        viewModelScope.launch {
            try {
                _listRatesCurrency.value = useCaseGetCurrency.execute()
                _networkStatus.value = DownloadingStatus.DONE
                setBaseExchangeCurrency()
            } catch (e: Exception) {
                _networkStatus.value = DownloadingStatus.ERROR
            }
        }
    }

    fun convertCurrency() {
        _resultConversion.value =
            useCaseConvertCurrency.execute(amountRubleForExchange, exchangeCurrency)
    }

    private fun setBaseExchangeCurrency() {
        if (!_listRatesCurrency.value.isNullOrEmpty()) {
            exchangeCurrency.valueExchangeCurrency = _listRatesCurrency.value!![0].Value
            exchangeCurrency.nominalExchangeCurrency = _listRatesCurrency.value!![0].Nominal
        }
    }

    // data update every $timerDelay milliseconds
    private fun startUpdateTimer() {
        object : CountDownTimer(timerDelay,9999) {
            override fun onTick(p0: Long) {
                downloadData()
            }
            override fun onFinish() {
                startUpdateTimer()
            }
        }.start()
    }
}

enum class DownloadingStatus {
    LOADING, ERROR, DONE
}