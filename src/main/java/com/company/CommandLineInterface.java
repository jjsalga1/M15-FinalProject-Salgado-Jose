package com.company;

import com.company.cryptocurrency.CryptoResponse;
import com.company.iss.SpaceResponse;
import com.company.weather.WeatherResponse;

import java.util.Scanner;

public class CommandLineInterface {
    static void printToolTitle() {
        String title =
                "\n" +
                        "░█──░█ █▀▀█ █▀▀█ █── █▀▀▄ 　 ▀█▀ █▀▀▄ █▀▀ █▀▀█ 　 ▀▀█▀▀ █▀▀█ █▀▀█ █── \n" +
                        "░█░█░█ █──█ █▄▄▀ █── █──█ 　 ░█─ █──█ █▀▀ █──█ 　 ─░█── █──█ █──█ █── \n" +
                        "░█▄▀▄█ ▀▀▀▀ ▀─▀▀ ▀▀▀ ▀▀▀─ 　 ▄█▄ ▀──▀ ▀── ▀▀▀▀ 　 ─░█── ▀▀▀▀ ▀▀▀▀ ▀▀▀ \n" +
                        "by Jose Salgado Jr.";
        System.out.println(title + "\n");
    }

    static void printMenu() {
        String menu =
                "Select from the following options: (Enter digit 1-6)\n" +
                "\t1. Weather in a City\n" +
                "\t2. Location of the International Space Station (ISS)\n" +
                "\t3. Weather in the Location of the ISS\n" +
                "\t4. Current Cryptocurrency Prices\n" +
                "\t5. Display Option Menu\n" +
                "\t6. Exit\n" +
                "Select option 5 or type 'help' to display option menu.\n";
        System.out.println(menu);
    }


    public static void main(String[] args) {
        WeatherResponseAPI weatherResponseAPI = new WeatherResponseAPI();
        SpaceResponseAPI spaceResponseAPI = new SpaceResponseAPI();
        CryptoResponseAPI cryptoResponseAPI = new CryptoResponseAPI();

        Scanner scanner = new Scanner(System.in);
        printToolTitle();
        printMenu();

        String userIn;
        boolean running = true;
        while (running) {
            System.out.print("world-info-tool:$~ ");
            try {
                userIn = scanner.nextLine();
                if (userIn.equals("help")) {
                    userIn = "5";
                }

                int userChoice = Integer.parseInt(userIn);
                switch (userChoice) {
                    case 1:
                        System.out.println("For US cities, enter 'City, State, Country'.");
                        System.out.println("For international cities, enter 'City, Country'.");
                        System.out.print("Enter city: ");
                        String citySelection = scanner.nextLine().trim();
                        WeatherResponse cityData = weatherResponseAPI.getWeatherResponse(citySelection);
                        if (cityData != null) {
                            System.out.println("The weather in " + cityData.name + ", " + cityData.sys.country + " is:");
                            System.out.println("\t" + "Temperature: " + cityData.main.temp + "°F");
                            System.out.println("\t" + "Description: " + cityData.weather.get(0).description);
                        }
                        break;
                    case 2:
                        System.out.println("The current location of the ISS:");
                        SpaceResponse issData = spaceResponseAPI.getIssResponse();
                        if (issData != null) {
                            WeatherResponse issLocation = weatherResponseAPI.getWeatherResponse(issData);
                            System.out.println("\tCoordinates: " + issData.iss_position.latitude + ", " + issData.iss_position.longitude);
                            if (issLocation.sys.country == null) {
                                System.out.println("\tISS is not currently in a country.");
                            } else {
                                System.out.println("\tCity: " + issLocation.name + ", " + issLocation.sys.country);
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Weather in the location of the ISS:");
                        SpaceResponse issLocation = spaceResponseAPI.getIssResponse();
                        if (issLocation != null) {
                            WeatherResponse issWeather = weatherResponseAPI.getWeatherResponse(issLocation);
                            System.out.println("\tCoordinates: " + issLocation.iss_position.latitude + ", " + issLocation.iss_position.longitude);
                            if (issWeather.sys.country == null) {
                                System.out.println("\tISS is not currently in a country.");
                            } else {
                                System.out.println("\tCity: " + issWeather.name + ", " + issWeather.sys.country);
                            }
                            System.out.println("\tTemperature: " + issWeather.main.temp + "°F");
                            System.out.println("\tDescription: " + issWeather.weather.get(0).description);
                        }
                        break;
                    case 4:
                        System.out.print("Enter cryptocurrency symbol: ");
                        String symbol = scanner.nextLine().toUpperCase().trim();
                        CryptoResponse coinData = cryptoResponseAPI.getCoinResponse(symbol);
                        if (coinData != null) {
                            System.out.println("\tName: " + coinData.name);
                            System.out.println("\tSymbol: " + coinData.asset_id);
                            System.out.format("\tPrice (USD): $%.2f\n", coinData.price_usd);
                        } else {
                            System.out.println("\tCryptocurrency with symbol '" + symbol + "' not found.");
                        }
                        break;
                    case 5:
                        printMenu();
                        break;
                    case 6:
                        System.out.println("Exiting world-info-tool...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid Selection. (Enter digit 1-6 or type 'help')");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Selection. (Enter digit 1-6 or type 'help')");
            }
        }
    }
}
