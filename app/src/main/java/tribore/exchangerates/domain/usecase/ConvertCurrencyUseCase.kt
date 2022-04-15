package tribore.exchangerates.domain.usecase

import tribore.exchangerates.domain.model.ExchangeCurrency
import java.lang.IllegalArgumentException

class ConvertCurrencyUseCase {

    fun execute(amountRuble: Int, exchangeCurrency: ExchangeCurrency): Double {

        val nominal = exchangeCurrency.nominal
        val value = exchangeCurrency.value

        return if (value == 0.0) throw IllegalArgumentException("Divisor is 0")
        else (amountRuble / value) * nominal
    }

}