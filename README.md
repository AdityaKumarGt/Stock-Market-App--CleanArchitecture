<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/50458cc1-e88d-4861-a4f8-42baa000fc7d" alt="app icon" width="150"> 



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

<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/2b131f16-69b6-4f21-98c1-ed7ecb660288" alt="SplashScreen" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/968c95fd-b488-413b-8961-0778aeb9f468" alt="SplashScreen" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/5a1d4df9-bb5e-4669-96db-91da39ca98cb" alt="SplashScreen" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/AdityaKumarGt/Stock-Market-App--CleanArchitecture/assets/121026525/102c363a-5125-495f-a087-b769c1e729d0" alt="SplashScreen" width="200">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;


## Technologies and Architecture Used:

- Clean Architecture with MVVM
- Kotlin
- Jetpack Compose for UI
- Dagger Hilt for Dependency Injection
- Kotlin Coroutines & Flows
- Retrofit for Rest API integration
- Room Database for Local Caching
- Compose Destinations for Navigation
- Lottie animation

## Acknowledgments
- [Alpha Vantage API](https://www.alphavantage.co/) for providing an extensive array of stocks information.
- [Compose Destinations](https://github.com/raamcosta/compose-destinations) for providing a wonderful library which makes the navigation in jetpack compose much smoother.



