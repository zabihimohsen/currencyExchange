package zabihi.mohsen.currencyexchanger.data.models

import zabihi.mohsen.currencyexchanger.data.db.BalanceEntity

//Data class Used for passing in calculation functions and returning back
data class ExchangeRateCalculation(
    val source : BalanceEntity,
    val target : BalanceEntity,
)
data class ExchangeRateResult(
    val rates : ExchangeRateCalculation,
    val message : String,
    val success : Boolean,
)
