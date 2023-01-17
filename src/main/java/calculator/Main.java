package main.java.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read lines in a loop
        // Run lexical / regex interpreter on line
        // Run backend on that line
        Calculator calculator = new Calculator();
        System.out.println("Please type main.calculator operations:");
        BufferedReader inputReader =  new BufferedReader(new InputStreamReader(System.in));
            inputReader.lines().forEachOrdered(i -> {
                try {
                    calculator.parse(i);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                    //TODO: Do we need the ordered? can reduce performance.
                }
            });
        System.out.println(calculator.getState());
}}
