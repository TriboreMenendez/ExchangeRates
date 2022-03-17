package tribore.exchangerates.ui.viewodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.domain.usecase.GetListCurrencyUseCase
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val useCaseGetCurrency: GetListCurrencyUseCase) : ViewModel() {

    private val _networkStatus = MutableLiveData<DownloadingStatus>(DownloadingStatus.LOADING)
    val downloadingStatus: LiveData<DownloadingStatus> = _networkStatus

    private val _listRatesCurrency = MutableLiveData<List<RatesCurrencyDomainModel>>()
    val listRatesCurrency: LiveData<List<RatesCurrencyDomainModel>> = _listRatesCurrency

    init {
        downloadData()
    }

    fun downloadData() {
        _listRatesCurrency.value = listOf()
        _networkStatus.value = DownloadingStatus.LOADING

        viewModelScope.launch {
            try {
                _listRatesCurrency.value = useCaseGetCurrency.getRatesCurrency()
                _networkStatus.value = DownloadingStatus.DONE
            } catch (e: Exception) {
                _networkStatus.value = DownloadingStatus.ERROR
            }
        }
    }
}

enum class DownloadingStatus {
    LOADING, ERROR, DONE
}