# Currency Exchanger
- Impelemented exchangeratesapi.io free API.
- Calling API each 5 seconds to ensure prices.
- Done with least third party libraries.
- Add currency easily.
- Optimized for Android dark theme.
- Handled all possible balance and calculation errors.
## Libraries & Technologies Used
	LiveData
	Retrofit
	ViewBinding
	Room
	Coroutines
	StateFlow
	MVVM
##Add Currency
Just add desired currency in `strings.xml` like :
```xml
<item>AMD</item>
```
## Change API Interval
API is called every 5 seconds by default which is configured by `apiCallInterval` in `Constants`.
```kotlin
const val apiCallInterval : Long = 5000 //  Milliseconds.
```
##Screenshots

| Light Theme|Dark Theme(Night)|
| ------------ | ------------ |
|![](https://i.ibb.co/BPJbyhT/1.png)  |  ![](https://i.ibb.co/tsqRYhq/dark-1.png)|
|![](https://i.ibb.co/zHp76GH/2.png) |  ![](https://i.ibb.co/HCm0kmH/dark-2.png)|
