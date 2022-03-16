package tribore.exchangerates.data.repository

import tribore.exchangerates.data.models.CurrencyApiModel
import tribore.exchangerates.domain.model.RatesCurrencyDomainModel

fun CurrencyApiModel.toDomain(): RatesCurrencyDomainModel {

    return RatesCurrencyDomainModel(
        CharCode = this.CharCode ,
        ID = this.ID,
        Name = this.Name,
        Nominal = this.Nominal,
        NumCode = this.NumCode,
        Previous = this.Previous,
        Value = this.Value
    )
}