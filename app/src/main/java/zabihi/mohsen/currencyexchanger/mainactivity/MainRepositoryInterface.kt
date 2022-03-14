package zabihi.mohsen.currencyexchanger.mainactivity

import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse
import zabihi.mohsen.currencyexchanger.util.Resource

interface MainRepositoryInterface {
    suspend fun getExchangeRates(accessKey : String) : Resource<ExchangeRateResponse>
}