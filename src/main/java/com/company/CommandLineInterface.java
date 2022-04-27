package com.company;

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

        Scanner scanner = new Scanner(System.in);
        printToolTitle();
        printMenu();

        int userIn;
        boolean running = true;
        while (running) {
            System.out.print("world-info-tool:$~ ");
            userIn = Integer.parseInt(scanner.nextLine().trim());

            switch (userIn) {
                case 1:
                    System.out.print("Enter location: ");
                    String citySelection = scanner.nextLine().trim();
                    WeatherResponse cityData = weatherResponseAPI.getWeatherResponse(citySelection);
                    if (cityData != null) {
                        System.out.println(cityData.main.temp);
                    }
                    break;
                case 2:
                    System.out.println("The current location of the ISS:");
                    IssResponse issData = issResponseAPI.getIssResponse();
                    if (issData != null) {
                        System.out.println(issData.iss_position.latitude + ", " + issData.iss_position.longitude);
                    }
                    break;
                case 3:
                    System.out.println("ISS weather");
                    break;
                case 4:
                    System.out.println("Crypto");
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
