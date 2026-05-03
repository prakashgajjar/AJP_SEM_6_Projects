package com.hostelmart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HostelMartClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- HostelMart Search Client ---");

        while (true) {
            System.out.print("\nEnter a product to search (or type 'exit' to quit): ");
            String searchString = scanner.nextLine();

            if ("exit".equalsIgnoreCase(searchString.trim())) {
                System.out.println("Exiting client...");
                break;
            }

            if (searchString.trim().isEmpty()) {
                System.out.println("Search string cannot be empty.");
                continue;
            }

            searchProductOnServer(searchString);
        }
        scanner.close();
    }

    private static void searchProductOnServer(String searchString) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send search string to server
            out.println(searchString);

            // Read response from server
            System.out.println("\n--- Server Response ---");
            String responseLine;
            boolean receivedData = false;
            
            while ((responseLine = in.readLine()) != null) {
                if ("END_OF_RESPONSE".equals(responseLine)) {
                    break;
                }
                System.out.println(responseLine);
                receivedData = true;
            }
            
            if (!receivedData) {
                System.out.println("No response from server.");
            }
            System.out.println("-----------------------\n");

        } catch (IOException e) {
            System.err.println("Connection error: Could not connect to the server. Is it running? (" + e.getMessage() + ")");
        }
    }
}
