# World Info Tool

This application runs in the command line and takes user input to drive the program.

The user can choose from the following:

- Weather in a city
- ISS location
- Weather in ISS location
- Cryptocurrency price

Uses the Spring Reactive Web dependency to connect to multiple REST APIs and retrieve JSON responses.

# Tools and technologies
### Created with Spring Initializr
- Spring Reactive Web dependency
### Mono
- For webclient API JSON responses

# APIs
### CoinAPI (https://www.coinapi.io/)
- Grabs information on current Cryptocurrency prices.
### OpenWeather API (https://openweathermap.org/api)
- Grabs the weather conditions in a location.
### Open Notify: Current ISS Location (http://open-notify.org/Open-Notify-API/)
- Sends the current location of the International Space Station in latitude and longitude.
