import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        // Read lines in a loop
        // Run lexical / regex interpreter on line
        // Run backend on that line
        Calculator calculator = new Calculator();
        System.out.println("Please type calculator operations:");
        BufferedReader inputReader =  new BufferedReader(new InputStreamReader(System.in));
        inputReader.lines().forEachOrdered(calculator::parse);   //TODO: Do we need the ordered? can reduce performance.
            // Calculator.render line
                // Calculator has interpreter section and backend section
}}

// Test cases:
    // Whatever in doc
    // Case of double enters then another line.

// Future considerations:
    // consider asynchronous operations on lines. But probably bad because sequantial functions matter.