package tribore.exchangerates.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tribore.exchangerates.data.model.ResponseApiModel
import tribore.exchangerates.data.network.CurrencyApi

class CurrencyViewModel : ViewModel() {

    private val _status = MutableLiveData<Boolean>(false)
    val status: LiveData<Boolean> = _status

    private val _listRates = MutableLiveData<ResponseApiModel>()
    val listRates: LiveData<ResponseApiModel> = _listRates

    init {
        refreshData()
    }

    //Убрать приватность функции, когда потребуется обновление через UI
    private fun refreshData() {

        viewModelScope.launch {
            try {
                _listRates.value = CurrencyApi.retrofitService.getRatesCurrency()
                _status.value = true
            } catch (e: Exception) {
                _status.value = false
            }
        }
    }
}