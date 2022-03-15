package zabihi.mohsen.currencyexchanger.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import zabihi.mohsen.currencyexchanger.data.models.ExchangeRateResponse

interface ExchangeApi {
    @GET(Constants.latestEndPoint)
    suspend fun getConversionRates(
        @Query("access_key")  accessKey : String
    ):Response<ExchangeRateResponse>
}