/**
 *  Logarithm Utility Class
 * <p>
 * This is a utility class that provides various static methods to compute logarithms
 * in different configurations: arbitrary bases, powers, and quotients.
 * <hr>
 *
 * <h3>⚙️ Capabilities</h3>
 * The methods allow, among others:
 * <ul style="margin-left: 15px;">
 *   <li>📌 Calculating the logarithm of a value in an arbitrary base</li>
 *   <li>📌 Applying powers to the value or the base</li>
 *   <li>📌 Handling quotients as values or bases</li>
 * </ul>
 * <p>
 * This class is <b>non-instantiable</b>.
 * <hr>
 *
 * <h3> Validation</h3>
 * <p>
 * All methods validate their arguments and throw
 * {@link IllegalArgumentException} if any mathematical precondition is violated:
 * <ul style="margin-left: 15px;">
 *   <li> Base must be strictly positive and not equal to 1</li>
 *   <li> Values must be strictly positive</li>
 * </ul>
 * In some cases, exceptions may be thrown directly by {@link Math Math}'s internal logic.
 * <hr>
 *
 * <h3> Numerical Precision</h3>
 * <p>
 * All computations rely on the IEEE 754 double-precision floating-point format,
 * as implemented in {@link Math#log}. Results are therefore subject to:
 * <ul style="margin-left: 15px;">
 *   <li>🔸 Rounding errors</li>
 *   <li>🔸 Precision loss for values near 0 or large magnitudes</li>
 *   <li>🔸 Instability for bases close to 1</li>
 * </ul>
 * For high-precision or scientific needs, external libraries may be required.
 * <hr>
 *
 * @implNote Passing {@code NaN}, infinity, or {@code null} (via autoboxing) may propagate
 * exceptions or yield IEEE 754 special values depending on {@link Math#log}.
 *
 * @author owl
 */
public final class Logarithm {


    /**
     * Private constructor to prevent instantiation
     */
    private Logarithm(){}

    /**
     * Computes the logarithm of {@code value} with a specified {@code base}
     * <p>
     * Formula:
     * <pre>
     *     log<sub>base</sub>(value) = ln(value) / ln(base)
     * </pre>
     *
     * @param value the value to compute the logarithm for (must be &gt; 0)
     * @param base the base of the logarithm (must be &gt; 0 and ≠ 1)
     *
     * @return the logarithm of {@code value} with base {@code base}
     *
     * @throws IllegalArgumentException if {@code base} ≤ 0, {@code base} = 1, or {@code value} ≤ 0
     *
     * @implNote Passing {@code NaN}, infinity, or null (via autoboxing) may propagate
     *          exceptions or return special IEEE 754 values depending on {@link Math#log}.
     */
    public static double logInBase(double value, double base){
        checkArgument(base > 0, "base must be > 0: " + base);
        checkArgument(base != 1, "base must not be equal to 1");
        checkArgument(value > 0, "value must be > 0: " + value);

        return Math.log(value) / Math.log(base);
    }

    /**
     * Computes the logarithm of {@code value} to the power of {@code power} with base {@code base}
     *
     * <p>
     * Formula:
     * <pre>
     *     log<sub>base</sub>(value<sup>power</sup>) = power * log<sub>base</sub>(value)
     * </pre>
     *
     * Value and base validation is delegated to logInBase(...)
     *
     * @param value the value inside the logarithm (must be &gt; 0)
     * @param power the multiplier applied to the logarithm
     * @param base the base of the logarithm (must be &gt; 0 and ≠ 1)
     *
     * @return {@code power} times the logarithm of {@code value} in base {@code base}
     *
     * @throws IllegalArgumentException if {@code base} ≤ 0, {@code base} = 1, or {@code value} ≤ 0
     */
    public static double logWithPoweredValue(double value, double power, double base){
        return power * logInBase(value, base);
    }

    /**
     * Computes the logarithm of {@code value} with the base
     * to the power {@code power}
     * <p>
     * Uses the identity:
     * <pre>
     *     log<sub>base<sup>power</sup></sub>(value) = (1 / power) * log<sub>base</sub>(value)
     * </pre>
     *
     * Value and base validation is delegated to logInBase(...)
     *
     * @param value the value inside the logarithm (must be &gt; 0)
     * @param base the base of the logarithm before exponentiation (must be &gt; 0 and ≠ 1)
     * @param power the exponent applied to the base (must not be 1)
     *
     * @return the logarithm of {@code value} in base {@code base^power}
     *
     * @throws IllegalArgumentException if {@code base} ≤ 0, {@code base} = 1,
     *                                  {@code value} ≤ 0, or {@code power} = 1
     */
    public static double logWithPoweredBase(double value, double base, double power){
        checkArgument(power != 1, "power must not be 1");
        return (1/power) * logInBase(value, base);
    }

    /**
     * Computes the logarithm of the quotient {@code valueNumerator / valueDenominator}
     * with the base {@code base}
     *
     * Value and base validation is delegated to logInBase(...)
     *
     * @param valueNumerator numerator of the value quotient
     * @param valueDenominator denominator of the value quotient (must not be 0)
     * @param base the base of the logarithm (must be &gt; 0 and ≠ 1)
     *
     * @return the logarithm of {@code valueNumerator / valueDenominator} in base {@code base}
     *
     * @throws IllegalArgumentException if {@code valueDenominator} = 0, or if
     *                                  the quotient or base violate {@link #logInBase} preconditions
     */
    public static double logOfQuotient(double valueNumerator, double valueDenominator, double base){
        checkArgument(valueDenominator != 0, "valueDenominator must not be zero");
        return logInBase(valueNumerator/valueDenominator, base);
    }

    /**
     * Computes the logarithm of {@code value} with the base
     * defined as the quotient {@code baseNumerator / baseDenominator}
     *
     * Value and base validation is delegated to logInBase(...)
     *
     * @param value the value inside the logarithm (must be &gt; 0)
     * @param baseNumerator numerator of the base quotient
     * @param baseDenominator denominator of the base quotient (must not be 0)
     *
     * @return the logarithm of {@code value} in base {@code baseNumerator / baseDenominator}
     *
     * @throws IllegalArgumentException if {@code baseDenominator} = 0 or
     *                                  if resulting base or value violate {@link #logInBase} preconditions
     */
    public static double logWithBaseQuotient(double value, double baseNumerator, double baseDenominator){
        checkArgument(baseDenominator != 0, "baseDenominator is zero");
        return logInBase(value, baseNumerator/baseDenominator);
    }

    /**
     * Computes the logarithm of the quotient {@code valueNumerator / valueDenominator}
     * with the base defined as the quotient {@code baseNumerator / baseDenominator}
     *
     * Value and base validation is delegated to logInBase(...)
     *
     * @param valueNumerator numerator of the value quotient
     * @param valueDenominator denominator of the value quotient (must not be 0)
     * @param baseNumerator numerator of the base quotient
     * @param baseDenominator denominator of the base quotient (must not be 0)
     *
     * @return the logarithm of {@code valueNumerator / valueDenominator} in base {@code baseNumerator / baseDenominator}
     *
     * @throws IllegalArgumentException if {@code valueDenominator} = 0,
     *                                  {@code baseDenominator} = 0,
     *                                  or if resulting values violate {@link #logInBase} preconditions
     */
    public static double logOfQuotientAndBaseQuotient(double valueNumerator, double valueDenominator, double baseNumerator, double baseDenominator){
        checkArgument(valueDenominator != 0, "Illegal given valueDenominator value");
        checkArgument(baseDenominator != 0, "baseDenominator must not be zero");
        return logInBase(valueNumerator/valueDenominator, baseNumerator/baseDenominator);
    }

    /**
     * Ensures the provided condition is {@code true}, otherwise throws {@link IllegalArgumentException}
     *
     * @param shouldBeTrue condition to validate
     * @param message exception message if validation fails
     * @throws IllegalArgumentException if {@code shouldBeTrue} is false
     */
    private static void checkArgument(boolean shouldBeTrue, String message){
        if(!shouldBeTrue) throw new IllegalArgumentException(message);
    }
}