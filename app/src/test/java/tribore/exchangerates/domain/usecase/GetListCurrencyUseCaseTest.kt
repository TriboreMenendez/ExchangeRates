package tribore.exchangerates.domain.usecase

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel
import tribore.exchangerates.domain.repository.CurrencyRepository

class GetListCurrencyUseCaseTest {

    private val testRepository = mock<CurrencyRepository>()

    @Test
    fun `should return the data as in repository`() = runBlocking {

        val testDataCurrency = listOf(RatesCurrencyDomainModel(
            charCode = "test char code",
            id = "test id",
            name = "test RUB",
            nominal = 1,
            numCode = "test",
            previous = 2.0,
            value = 80.0
        ))
        Mockito.`when`(testRepository.getRatesCurrency()).thenReturn(testDataCurrency)
        val useCase = GetListCurrencyUseCase(currencyRepository = testRepository)

        val actual = useCase.execute()
        val expected = listOf(
            RatesCurrencyDomainModel(
                charCode = "test char code",
                id = "test id",
                name = "test RUB",
                nominal = 1,
                numCode = "test",
                previous = 2.0,
                value = 80.0
            )
        )

        assertEquals(expected, actual)
    }
}