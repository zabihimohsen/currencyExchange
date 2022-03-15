package zabihi.mohsen.currencyexchanger.mainactivity

import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse
import zabihi.mohsen.currencyexchanger.util.Resource

/*Interface for MainRepository, This interface is not needed and just coded for future
 mighty use cases*/
interface MainRepositoryInterface {
    suspend fun getExchangeRates(accessKey : String) : Resource<ExchangeRateResponse>
    fun getCommissionFee(response: ExchangeRateResponse, buy: String, sell: String):Double
}