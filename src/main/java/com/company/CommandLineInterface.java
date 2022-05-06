package com.company;

import com.company.api.CryptoResponseAPI;
import com.company.api.SpaceResponseAPI;
import com.company.api.WeatherResponseAPI;
import com.company.cryptocurrency.CryptoResponse;
import com.company.iss.SpaceResponse;
import com.company.weather.WeatherResponse;

import java.util.Scanner;

/**
 * The {@code CommandLineInterface} class contains methods that execute a command line interface
 * titled World Info Tool. This program uses multiple APIs that return information about the world.
 * When ran, the user is presented with a menu, and choices are made with keyboard input.
 */
public class CommandLineInterface {
    /**
     * This method is used to print the tool's title in ASCII art.
     */
    static void printToolTitle() {
        String title =
                "\n" +
                        "░█──░█ █▀▀█ █▀▀█ █── █▀▀▄ 　 ▀█▀ █▀▀▄ █▀▀ █▀▀█ 　 ▀▀█▀▀ █▀▀█ █▀▀█ █── \n" +
                        "░█░█░█ █──█ █▄▄▀ █── █──█ 　 ░█─ █──█ █▀▀ █──█ 　 ─░█── █──█ █──█ █── \n" +
                        "░█▄▀▄█ ▀▀▀▀ ▀─▀▀ ▀▀▀ ▀▀▀─ 　 ▄█▄ ▀──▀ ▀── ▀▀▀▀ 　 ─░█── ▀▀▀▀ ▀▀▀▀ ▀▀▀ \n" +
                        "by Jose Salgado Jr.";
        System.out.println(title + "\n");
    }

    /**
     * This method is used to neatly print and display the user their choices when the program is run.
     */
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

    /**
     * The main method contains the control loop of the program. It consists of a while loop
     * and a switch statement. Different response API methods are run when a user makes a selection.
     * This method exits when a user enters 6.
     */
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
