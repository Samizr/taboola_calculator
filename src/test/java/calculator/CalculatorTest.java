package test.java.calculator;

import main.java.calculator.Calculator;
import main.java.calculator.CalculatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    Calculator calculator;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
    }

    @Test
    public void testBasicSuccess() {
        assertParseDoesNotThrow( "i = 0");
        assertParseDoesNotThrow( "j = ++i");
        assertParseDoesNotThrow( "x = i++ + 5");
        assertParseDoesNotThrow( "y = (5 + 3) * 10");
        assertParseDoesNotThrow( "i += y");

        assertStateEquals("(i=82,j=1,x=6,y=80)");
    }

    @Disabled
    public void testNegativesSuccess() {
        assertParseDoesNotThrow( "i = -3");
        assertParseDoesNotThrow( "j = i-3");
        assertParseDoesNotThrow( "k = -3 + i");

        assertStateEquals("(i=-3,j=-6,k=-6");
    }

    @Test
    public void testBasicIncrementSuccess() {
        assertParseDoesNotThrow( "x = 10");
        assertParseDoesNotThrow( "y = ++x");
        assertParseDoesNotThrow( "z = y++");
        assertParseDoesNotThrow( "i = y++ + 6");
        assertParseDoesNotThrow( "z = ++i - 6");

        assertStateEquals("(x=11,y=13,z=13,i=19)");
    }

    @Test
    public void testBasicBracketSuccess() {
        assertParseDoesNotThrow("x = 5 * (3 + 2)");
        assertParseDoesNotThrow("y = (2 * (3 + 2))");
        assertParseDoesNotThrow("z = (2 + 5) * (3 + 2)");
        assertParseDoesNotThrow("a = (2)");
        assertParseDoesNotThrow("a = ((3))");
        assertParseDoesNotThrow("b = ((5 - 3) + 2 * (19-4 + 2))");


        assertStateEquals("(x=25,y=10,z=35,a=3,b=36)");
    }

    @Test
    public void testMissingVariableFailure() {
        String expectedString = "Line must start with a variable";
        assertParseThrowsCalculatorException("1", expectedString);
        assertParseThrowsCalculatorException("30", expectedString);
        assertParseThrowsCalculatorException("= 30 + 1", expectedString);
        assertParseThrowsCalculatorException("+10", expectedString);
        assertParseThrowsCalculatorException("i++", expectedString);
    }

    @Test
    public void testMissingAssignFailure() {
        assertParseThrowsCalculatorException("x");
        assertParseThrowsCalculatorException("x =+ 10");
        assertParseThrowsCalculatorException("x + 10");
    }


    @Test
    public void testMissingTerminalsAfterAssignFailure() {
        assertParseThrowsCalculatorException("x=");
        assertParseThrowsCalculatorException("x +=");
    }

    private void assertParseDoesNotThrow(String line) {
        assertDoesNotThrow(() -> calculator.calculate(line));
    }

    private void assertStateEquals(String line) {
        assertEquals(line, calculator.getState());
    }

    private void assertParseThrowsCalculatorException(String line) {
        assertThrows(CalculatorException.class, () -> calculator.calculate(line));
    }

    private void assertParseThrowsCalculatorException(String line, String expectedMessage) {
        Exception exception = assertThrows(CalculatorException.class, () -> calculator.calculate(line));
        assertEquals(exception.getMessage(), expectedMessage);
    }

}

