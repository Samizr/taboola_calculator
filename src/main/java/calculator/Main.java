package main.java.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        Calculator calculator = new Calculator();
        System.out.println("Please type main.calculator operations:");
        BufferedReader inputReader =  new BufferedReader(new InputStreamReader(System.in));
            inputReader.lines().forEachOrdered(line -> {
                try {
                    calculator.calculate(line);
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            });
        System.out.println(calculator.getState());
}}
