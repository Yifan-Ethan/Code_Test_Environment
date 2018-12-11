import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import java.util.Set;

import test_objects.Fraction;
import test_objects.memoize;
import test_objects.node;
import test_programs.TSPBruteForceControl;
import test_programs.TSPOptimizer;
import yifan_toolkit.AlgoKit;
import yifan_toolkit.MathKit;
import yifan_toolkit.UtilityKit;

public class testground {

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {

		/**
		 * Problem 33: Digit cancelling fractions The fraction 49/98 is a curious
		 * fraction, as an inexperienced mathematician in attempting to simplify it may
		 * incorrectly believe that 49/98 = 4/8, which is correct, is obtained by
		 * cancelling the 9s. We shall consider fractions like, 30/50 = 3/5, to be
		 * trivial examples. There are exactly four non-trivial examples of this type of
		 * fraction, less than one in value, and containing two digits in the numerator
		 * and denominator. If the product of these four fractions is given in its
		 * lowest common terms, find the value of the denominator.
		 */

		/**
		 * Solution decription: Problem pointers: 1. Numerator and denominator both
		 * contain only 2 digits 2. There exists a total of 4 curious fractions
		 * 
		 * Matching conditions for curious fraction: 1. answer1 = cancellation of same
		 * digit in both numerator and denominator 2. answer2 = simplification of
		 * fraction 3. answer1 == answer2
		 * 
		 * Result 1. To find the product of these 4 fractions, and retrieve value of
		 * denominator
		 */

		// CONFIG
		double s1 = 2;
		double s2 = 3;

		List<Fraction> curiousfractions = new ArrayList<Fraction>();

		for (int d = 10; d < 100; d++) {
			for (int n = 10; n < d; n++) {
				if (n % 10 != 0 && d % 10 != 0 && n != d) {
					if (n / 10 == d % 10 || n % 10 == d / 10) {
						Fraction actual = new Fraction(n, d);
						Fraction c1 = new Fraction(n / 10, d % 10);
						Fraction c2 = new Fraction(n % 10, d / 10);
						if (actual.equals(c1) || actual.equals(c2)) {
							curiousfractions.add(actual);
						}
					}
				}
			}
		}

		System.out.println(curiousfractions);
	}
}
