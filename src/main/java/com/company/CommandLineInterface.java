package com.company;

import com.company.cryptocurrency.CoinResponse;
import com.company.iss.IssResponse;
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
                "Select from the following options: (Enter digit 1-5)\n" +
                "\t1. Weather in a City\n" +
                "\t2. Location of the International Space Station (ISS)\n" +
                "\t3. Weather in the Location of the ISS\n" +
                "\t4. Current Cryptocurrency Prices\n" +
                "\t5. Exit\n";
        System.out.println(menu);
    }


    public static void main(String[] args) {
        WeatherResponseAPI weatherResponseAPI = new WeatherResponseAPI();
        IssResponseAPI issResponseAPI = new IssResponseAPI();
        CoinResponseAPI coinResponseAPI = new CoinResponseAPI();

        Scanner scanner = new Scanner(System.in);
        printToolTitle();
        printMenu();

        int userIn;
        boolean running = true;
        while (running) {
            // TODO: handle incorrect input exceptions
            System.out.print("world-info-tool:$~ ");
            userIn = Integer.parseInt(scanner.nextLine().trim());

            switch (userIn) {
                case 1:
                    // TODO: try to break
                    System.out.print("Enter location: ");
                    String citySelection = scanner.nextLine().trim();
                    WeatherResponse cityData = weatherResponseAPI.getWeatherResponse(citySelection);
                    if (cityData != null) {
                        System.out.println("\t" + cityData.main.temp);
                        System.out.println("\t" + cityData.weather.get(0).description);
                    }
                    break;
                case 2:
                    System.out.println("The current location of the ISS:");
                    IssResponse issData = issResponseAPI.getIssResponse();
                    if (issData != null) {
                        WeatherResponse issLocation = weatherResponseAPI.getWeatherResponse(issData);
                        System.out.println("\t" + issData.iss_position.latitude + ", " + issData.iss_position.longitude);
                        if (issLocation.sys.country == null) {
                            System.out.println("\tISS is not currently in a country.");
                        } else {
                            System.out.println("\t" + issLocation.name + ", " + issLocation.sys.country);
                        }
                    }
                    break;
                case 3:
                    System.out.println("Weather in the location of the ISS:");
                    IssResponse issLocation = issResponseAPI.getIssResponse();
                    if (issLocation != null) {
                        WeatherResponse issWeather = weatherResponseAPI.getWeatherResponse(issLocation);
                        System.out.println("\t" + issLocation.iss_position.latitude + ", " + issLocation.iss_position.longitude);
                        if (issWeather.sys.country == null) {
                            System.out.println("\tISS is not currently in a country.");
                        } else {
                            System.out.println("\t" + issWeather.name + ", " + issWeather.sys.country);
                        }
                        System.out.println("\t" + issWeather.main.temp);
                        System.out.println("\t" + issWeather.weather.get(0).description);
                    }
                    break;
                case 4:
                    // TODO: try to break
                    System.out.print("Enter cryptocurrency symbol: ");
                    String symbol = scanner.nextLine().toUpperCase().trim();
                    CoinResponse coinData = coinResponseAPI.getCoinResponse(symbol);
                    if (coinData != null) {
                        System.out.println("\tName: " + coinData.name);
                        System.out.println("\tSymbol: " + coinData.asset_id);
                        System.out.format("\tPrice (USD): $%.2f\n", coinData.price_usd);
                    }
                    else {
                        System.out.println("\tCryptocurrency with symbol '" + symbol + "' not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting world-info-tool...");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid Selection");
                    printMenu();
            }
        }
    }
}
