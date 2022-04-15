package tribore.exchangerates.domain.usecase

import org.junit.Test
import tribore.exchangerates.domain.model.ExchangeCurrency
import java.lang.IllegalArgumentException

class ConvertCurrencyUseCaseTestException {

    @Test(expected = IllegalArgumentException::class)
    fun `should return an error when divided by zero`() {
        val testExchangeCurrency =
            ExchangeCurrency("", 1, 0.0)

        val useCase = ConvertCurrencyUseCase()
        useCase.execute(100, testExchangeCurrency)

    }
}