package zabihi.mohsen.currencyexchanger.mainactivity

import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse
import zabihi.mohsen.currencyexchanger.util.Resource

interface MainRepositoryInterface {
    suspend fun getExchangeRates(accessKey : String) : Resource<ExchangeRateResponse>
    fun getRateOfPair(response : ExchangeRateResponse,buy:String,sell:String,sellAMount:Float) : String
}