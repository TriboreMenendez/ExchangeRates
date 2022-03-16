package tribore.exchangerates.ui.viewodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tribore.exchangerates.data.models.ResponseApiModel
import tribore.exchangerates.data.network.CurrencyApi
import tribore.exchangerates.data.repository.CurrencyRepositoryImpl
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.domain.usecase.GetListCurrencyUseCase

class CurrencyViewModel : ViewModel() {

    // Удалить переменные после добавления DI
    private val repo = CurrencyRepositoryImpl(CurrencyApi.retrofitService)
    private val useCase = GetListCurrencyUseCase(repo)

    private val _networkStatus = MutableLiveData<NetworkStatus>(NetworkStatus.LOADING)
    val networkStatus: LiveData<NetworkStatus> = _networkStatus

    private val _listRatesCurrency = MutableLiveData<List<RatesCurrencyDomainModel>>()
    val listRatesCurrency: LiveData<List<RatesCurrencyDomainModel>> = _listRatesCurrency

    init {
        dataDownloading()
    }

    //Убрать приватность функции, когда потребуется обновление через UI
    private fun dataDownloading() {

        viewModelScope.launch {
            try {
                _listRatesCurrency.value = useCase.getRatesCurrency()
                _networkStatus.value = NetworkStatus.DONE
            } catch (e: Exception) {
                _networkStatus.value = NetworkStatus.ERROR
            }
        }
    }
}

enum class NetworkStatus {
    LOADING, ERROR, DONE
}