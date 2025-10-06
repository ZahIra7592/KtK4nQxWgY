// 代码生成时间: 2025-10-06 22:12:44
package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * A math tools controller that provides various mathematical operations.
 */
public class MathTools extends Controller {

    // Define a MathContext for precise arithmetic operations.
    private static final MathContext MATH_CONTEXT = new MathContext(10, RoundingMode.HALF_UP);

    /**
     * Handles the addition operation.
     * @param operand1 The first number to add.
     * @param operand2 The second number to add.
     * @return A JSON result with the sum of the two numbers.
     */
    public Result add(String operand1, String operand2) {
        try {
            BigDecimal result = new BigDecimal(operand1).add(new BigDecimal(operand2), MATH_CONTEXT);
            return ok(result.toString());
        } catch (NumberFormatException e) {
            return badRequest("Invalid number format");
        }
    }

    /**
     * Handles the subtraction operation.
     * @param operand1 The first number (minuend).
     * @param operand2 The second number (subtrahend).
     * @return A JSON result with the difference of the two numbers.
     */
    public Result subtract(String operand1, String operand2) {
        try {
            BigDecimal result = new BigDecimal(operand1).subtract(new BigDecimal(operand2), MATH_CONTEXT);
            return ok(result.toString());
        } catch (NumberFormatException e) {
            return badRequest("Invalid number format");
        }
    }

    /**
     * Handles the multiplication operation.
     * @param operand1 The first number.
     * @param operand2 The second number.
     * @return A JSON result with the product of the two numbers.
     */
    public Result multiply(String operand1, String operand2) {
        try {
            BigDecimal result = new BigDecimal(operand1).multiply(new BigDecimal(operand2), MATH_CONTEXT);
            return ok(result.toString());
        } catch (NumberFormatException e) {
            return badRequest("Invalid number format");
        }
    }

    /**
     * Handles the division operation.
     * @param operand1 The dividend.
     * @param operand2 The divisor.
     * @return A JSON result with the quotient of the two numbers.
     */
    public Result divide(String operand1, String operand2) {
        try {
            BigDecimal dividend = new BigDecimal(operand1);
            BigDecimal divisor = new BigDecimal(operand2);
            if (divisor.compareTo(BigDecimal.ZERO) == 0) {
                return badRequest("Division by zero is not allowed");
            }

            BigDecimal result = dividend.divide(divisor, MATH_CONTEXT);
            return ok(result.toString());
        } catch (NumberFormatException e) {
            return badRequest("Invalid number format");
        }
    }

    /**
     * Handles the request to calculate the power of a number.
     * @param base The base number.
     * @param exponent The exponent.
     * @return A JSON result with the result of the power operation.
     */
    public Result power(String base, String exponent) {
        try {
            BigDecimal baseNumber = new BigDecimal(base);
            BigDecimal exponentNumber = new BigDecimal(exponent);
            BigDecimal result = baseNumber.pow(exponentNumber.intValue(), MATH_CONTEXT);
            return ok(result.toString());
        } catch (NumberFormatException e) {
            return badRequest("Invalid number format");
        }
    }

    // Additional mathematical operations can be added here following the same pattern.
}
