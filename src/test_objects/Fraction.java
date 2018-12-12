package test_objects;

import java.math.BigInteger;

import yifan_toolkit.MathKit;;

public class Fraction {

	// Paramaters Numerator / Denominator
	public BigInteger Numerator;
	public BigInteger Denominator;

	// CONSTRUCTORS

	public Fraction(BigInteger num, BigInteger den) {
		Numerator = num;
		Denominator = den;
		Simplify();
	}

	public Fraction(BigInteger num) {
		Numerator = num;
		Denominator = BigInteger.ONE;
		Simplify();
	}

	public Fraction(int i) {
		Numerator = BigInteger.valueOf(i);
		Denominator = BigInteger.ONE;
		Simplify();
	}

	public Fraction(int i, int n) {
		Numerator = BigInteger.valueOf(i);
		Denominator = BigInteger.valueOf(n);
		Simplify();
	}

	public Fraction(double i, double n) {
		
		i = i * MathKit.DecimalPlaces(Double.toString(i));
		n = n * MathKit.DecimalPlaces(Double.toString(n));
		
		Numerator = new BigInteger(Double.toString(i));
		Denominator = new BigInteger(Double.toString(n));
		Simplify();
	}
	
	// Override ToString
	public String toString() {
		return Numerator.toString() + "/" + Denominator.toString();
	}
	
	// Override equals
	public boolean equals(Fraction o) {
		if(Numerator.equals(o.Numerator) && Denominator.equals(o.Denominator)) {
			return true;
		}
		return false; 
	}

	// MISC
	public void Simplify() {
		BigInteger quotient = Numerator.divide(Denominator); // Separate quotient from the number for faster calculation
		BigInteger remainder = Numerator.mod(Denominator);
		BigInteger gcd = GreatestCommonDivisor(remainder, Denominator);
		remainder = remainder.divide(gcd);

		Denominator = Denominator.divide(gcd);
		Numerator = (quotient.multiply(Denominator)).add(remainder);
	}

	// NOTE: ALWAYS use this method when converting from BigFraction to BigInteger.
	public BigInteger ToBigInteger() {
		return Numerator.divide(Denominator);
	}

	/**
	 * Returns the greatest common multiplier of 2 numbers a and b
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static BigInteger GreatestCommonDivisor(BigInteger a, BigInteger b) {
		return b.equals(BigInteger.ZERO) ? a : GreatestCommonDivisor(b, a.mod(b));
	}
}
