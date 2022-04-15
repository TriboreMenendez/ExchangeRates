package tribore.exchangerates.domain.usecase

import org.junit.Test
import junit.framework.Assert.assertEquals
import tribore.exchangerates.domain.model.ExchangeCurrency
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ConvertCurrencyUseCaseTest(
    private val amountRubleTest: Int,
    private val exchangeCurrencyTest: ExchangeCurrency,
    private val expected: Double
) {

    companion object {

        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(100, ExchangeCurrency("", 1, 1.0), 100.0),
            arrayOf(100, ExchangeCurrency("", 100, 1.0), 10000.0),
            arrayOf(100, ExchangeCurrency("", 10, 100.0), 10.0),
        )
    }

    @Test()
    fun `should return the amount of currency exchanged to rubles`() {

        val useCase = ConvertCurrencyUseCase()
        val actual = useCase.execute(amountRubleTest,exchangeCurrencyTest)

        assertEquals(expected, actual)
    }

}