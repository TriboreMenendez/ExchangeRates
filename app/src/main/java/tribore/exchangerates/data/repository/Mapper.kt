package tribore.exchangerates.data.repository

import tribore.exchangerates.data.models.CurrencyApiModel
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel

fun CurrencyApiModel.toDomain(): RatesCurrencyDomainModel {

    return RatesCurrencyDomainModel(
        charCode = this.CharCode ,
        id = this.ID,
        name = this.Name,
        nominal = this.Nominal,
        numCode = this.NumCode,
        previous = this.Previous,
        value = this.Value
    )
}