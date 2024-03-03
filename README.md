<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/7fa86859-8c91-443f-8924-3573fd287a80" alt="app icon" width="150"> 

# Stock Market App 

## Overview 

This Android application provides users with access to stock market information using the Alpha Vantage API. Users can view a list of listed stocks, along with company details and intraday information for each stock.

## Features

 - **Listed Stocks:**
    - Fetches a large list of listed stocks from the Alpha Vantage API.
    - Caches the list of all listed stocks for faster searching.
  
- **Pull-to-Refresh:**
    - Users can refresh the listed stock list by swiping down, ensuring the latest data is readily available.

- **Stock Details:**
   - Clicking on any stock allows users to view detailed company information and intraday information of that stock for the previous day.
   - **Note**: If users view intraday information on Monday, the app will display Saturday's intraday information due to the market being closed on Sundays.

- **Refreshing the Intraday Information:**
  - Users can manually refresh the intraday information for the selected stock by clicking on the refresh icon.

- **Local Caching:**
  - Caches the list of all listed stocks for faster searching.
  - Viewed stock details like the Intraday information and company information are saved into local cache, enabling offline access for subsequent views.


## Screenshots

<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/c62db1b2-395c-4299-a57e-b33a456fc88a" alt="SplashScreen" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/92a98a45-0571-40e9-b6fa-ada4fb727c4b" alt="SplashScreen" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/6f371030-0ff2-48b6-9a60-1b7aa6b6477d" alt="SplashScreen" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/230b6393-4035-4e20-991c-400c08102c23" alt="SplashScreen" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;





## Technologies and Architecture Used:

- Clean Architecture with MVVM
- Kotlin
- Jetpack Compose for UI
- Dagger Hilt for Dependency Injection
- Kotlin Coroutines & Flows
- Retrofit for Rest API integration
- Room Database for Local Caching
- Compose Destinations for Navigation

## Acknowledgments
- [Alpha Vantage API](https://www.alphavantage.co/) for providing an extensive array of stocks information.
- [Compose Destinations](https://github.com/raamcosta/compose-destinations) for providing a wonderful library which makes the navigation in jetpack compose much smoother.



