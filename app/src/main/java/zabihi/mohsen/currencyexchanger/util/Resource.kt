package zabihi.mohsen.currencyexchanger.util


//attach message when there is no data ( for error)
sealed class Resource<T>(val data: T?, val message: String?) {
    class Success<T>(data: T) : Resource<T>(data, null)
    class Error<T>(message: String) : Resource<T>(null, message)
}