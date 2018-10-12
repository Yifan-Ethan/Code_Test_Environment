package yifan_toolkit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Java scripting tools, catered for mathematics
 * Gentle reminder: Code logic should always be simple and easy to read! No complicated logic is allowed in this class file!
 * This is to ensure that code logic can be easily understood, and easily transferred to a new language
 * This class contains
 * 
 * General Mathematics
 * 1. Fraction of two number (Fraction)
 * 
 * Permuations and combinations
 * 1. Combinations calculator (ncr)
 * 2. Permutations without repetition for numerals (nprnorepeatnumeric)
 * 3. Number partitions, algorithm follows generating function (NumberPartitionGeneratingFunction)
 * 4. Permutations calculator (npr)
 * 
 * Large calculations
 * 1. Sum of all BigIntegers in a list (SumList)
 * 2. Square root function for BigDecimal (BigDecimalsqrt) 
 * 3. Factorial (Factorial)
 * 
 * Graphs
 * 1. Calculates 2 dimensional Euclidean distance for big integer (BigIntTwoDEuclideanDist)
 * 2. Calculates 2 dimensional Euclidean distance for double (DoubleTwoDEuclideanDist)
 * 3. Formulate the gradient and y-intercept of linear equation based on 2 points on graph for BigInteger (GraphLinearEquationMNCBigInt)
 * 4. Formulate the gradient and y-intercept of linear equation based on 2 points on graph for double (GraphLinearEquationMNCDouble)
 * 5. Calculate the perpendicular distance between an edge represented by 2 points to a certain point. Inputs and outputs are of type Double (PerpendicularDistDouble)
 * 6. Checks if 2 edges cross intersect (IfEdgesCrossIntersect)
 * 7. Checks if 2 edges parallel intersect (IfEdgesParallelIntersect)
 * 
 * Investigation of a number
 * 1. Checks if number is prime (IsPrime)
 * 2. Finds the largest prime factor of a number (largestprime)
 * 3. Finds the number of decimal places of a number (decimalplaces)
 * 4. Checks the number of digits in a number (digits)
 * 5. Finds all factors of a number (AllFactors)
 * 6. Greatest common multiplier of 2 numbers (GreatestCommonMultiplier)
 * 
 * Calculation for scientific purposes
 * 1. Cantor pairing function, uniquely encode 2 natural numbers into a single natural number (pairingfunction)
 * 
 * Number Theory
 * 1. Brute force Amicable Pair generator (AmicableNumbers)
 * 
 * @author weiyifan
 */
public class MathKit {
	
	static HashMap <BigInteger, Boolean> memoize = new HashMap <BigInteger, Boolean>();
	
	/**
	 * Summation of all BigIntegers in list
	 * @param l
	 * @return
	 */
	public static BigInteger SumList(List<BigInteger> l){
		int count = 0;
		BigInteger sum = new BigInteger("0");
		while(count<l.size()){
			sum = sum.add(l.get(count));
			count++;
		}
		return sum;
	}
	
	public static boolean IsPrime(BigInteger n) {
		//Return false if n is less than 1
		if(n.compareTo(BigInteger.ONE)==-1){
			return false;
		}
	    //check if n is a multiple of 2, and also not equals to 2
	    if (n.mod(new BigInteger("2")).equals(new BigInteger("0")) && !n.equals(new BigInteger("2"))) {
	    	return false;
	    }
	    //if not, then just check the odds	    
	    BigInteger i = new BigInteger("3");
	    while(i.multiply(i).compareTo(n) == -1 || i.multiply(i).compareTo(n) == 0){
	    	if(n.mod(i).equals(new BigInteger("0"))){
	    		return false;
	    	}
	    	i = i.add(new BigInteger("2"));
	    }
	    return true;
	}
	
	public int decimalplaces(String n){
		int decimalplacebegin = 0;
		boolean check = true;
		for(int i = n.length()-1; i>-1;i--){
			if(n.charAt(i) == '0' && check){	//caters to situation where all decimal digits are 0 eg 123.000, ans should be 0
				decimalplacebegin++;
			}
			if(Character.isDigit(n.charAt(i)) && n.charAt(i) != '0' && check){	//caters to situation where 0 is the last digit in decimal place eg 123.120, ans should be 2
				check = false;
				decimalplacebegin = n.length()-1-i;
			}
			else if(n.charAt(i) == '.'){	//returns number of decimal places
				return n.length()-1-i-decimalplacebegin;
			}
		}
		return 0;	//Returns 0 if no decimal places are found
	}
	
	//Please note that the inputs are x1, y1, x2 and y2
	//Please refer to BigDecimalsqrt function to adjust precision of decimal places if code efficiency needs to be increased
	//This function is specifically designed for big integers
	public static BigDecimal BigIntTwoDEuclideanDist(BigInteger ax, BigInteger ay, BigInteger bx, BigInteger by){
		BigInteger xdifference = ax.subtract(bx);
		BigInteger ydifference = ay.subtract(by);
		BigInteger xdistance = xdifference.pow(2);
		BigInteger ydistance = ydifference.pow(2);
		BigInteger distancetotal = xdistance.add(ydistance);
		return BigDecimalsqrt(new BigDecimal(distancetotal));
	}
	
	//Please note that the inputs are x1, y1, x2 and y2
	//This function is specifically designed for doubles
	public static double DoubleTwoDEuclideanDist(double ax, double ay, double bx, double by){
		double xdifference = ax-bx;
		double ydifference = ay-by;
		double xdistance = Math.pow(xdifference, 2);
		double ydistance = Math.pow(ydifference, 2);
		double distancetotal = xdistance+ydistance;
		return Math.sqrt(distancetotal);
	}
	
	/**
	 * Logic of this code revolves around divide and conquer, and testing of odd numbers for prime factors
	 */
	public BigInteger largestprime(BigInteger input){
		//Break down the number until it becomes an odd number
		while(input.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO)==0){
			input = input.divide(BigInteger.valueOf(2));
		}
		//divider is used to test for prime factors. largestprimefactor is used to store the largest prime factor found
		BigInteger divider = BigInteger.valueOf(3);
		BigInteger largestprimefactor = BigInteger.ZERO;
		//test for prime factors, while breaking down the number for each prime factor found
		while(divider.compareTo(input) != 1){
			if(input.mod(divider).compareTo(BigInteger.ZERO)==0){
				input = input.divide(divider);
				largestprimefactor = divider;
			}
			else{	//divider only increment if number cannot be divided by divisor anymore. This is to maximize divide and conquer by going through all powers of primes
				divider = divider.add(BigInteger.valueOf(2));
			}
		}
		
		return largestprimefactor;
	}
	
	public int digits(double d){
		if(d == 0){
			return 1;
		}
		if(d < 0){
			d = -d;
		}
		return (int)(Math.log10(d)+1);
	}
	
	/**
	 * Uses Newton Raphson to compute the square root of a BigDecimal.
	 * 
	 * @author Luciano Culacciatti 
	 * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
	 */
	public static BigDecimal BigDecimalsqrt(BigDecimal c){
		BigDecimal SQRT_DIG = new BigDecimal(150);
		BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());
	    return sqrtNewtonRaphson(c,new BigDecimal(1),new BigDecimal(1).divide(SQRT_PRE), SQRT_DIG);
	}
	private static BigDecimal sqrtNewtonRaphson  (BigDecimal c, BigDecimal xn, BigDecimal precision, BigDecimal SQRT_DIG){
	    BigDecimal fx = xn.pow(2).add(c.negate());
	    BigDecimal fpx = xn.multiply(new BigDecimal(2));
	    BigDecimal xn1 = fx.divide(fpx,2*SQRT_DIG.intValue(),RoundingMode.HALF_DOWN);
	    xn1 = xn.add(xn1.negate());
	    BigDecimal currentSquare = xn1.pow(2);
	    BigDecimal currentPrecision = currentSquare.subtract(c);
	    currentPrecision = currentPrecision.abs();
	    if (currentPrecision.compareTo(precision) <= -1){
	        return xn1;
	    }
	    return sqrtNewtonRaphson(c, xn1, precision, SQRT_DIG);
	}
	
	public static BigInteger pairingfunction(BigInteger a, BigInteger b){
		BigInteger result = BigInteger.ZERO;
		result = a.add(b);
		result = result.add(BigInteger.ONE);
		result = result.multiply(result.subtract(BigInteger.ONE));
		result = result.divide(new BigInteger("2"));
		result = result.add(b);
		return result;
	}
	
	public static BigInteger Factorial(BigInteger n){
		BigInteger answer = BigInteger.ONE;
		while(n.compareTo(BigInteger.ONE) == 1){
			answer = answer.multiply(n);
			n = n.subtract(BigInteger.ONE);
		}
		return answer;
	}
	
	public BigInteger ncr(int n, int r){
		BigInteger nfactorial = (Factorial(BigInteger.valueOf(n)));
		BigInteger rfactorial = (Factorial(BigInteger.valueOf(r)));
		BigInteger nminusrfactorial = (Factorial(BigInteger.valueOf(n-r)));
		return nfactorial.divide(rfactorial.multiply(nminusrfactorial));
	}
	
	//List can be any numeric types. It does not work for BigInteger or objects. Refer to comment in code on how to edit for those cases
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public BigInteger nprnorepeatnumeric(List list){
		BigInteger nfactorial = Factorial(BigInteger.valueOf(list.size()));
		List<BigInteger> listofdivisors = new ArrayList();
		for(int i=0;i<list.size();i++){
			BigInteger divisor = BigInteger.ONE;
			for(int c=i+1;c<list.size();c++){
				if(list.get(i)==list.get(c)){	//Change == to comparable function for BigIntegers and objects
					divisor = divisor.add(BigInteger.ONE);
					list.remove(c);
					c--;
				}
			}
			listofdivisors.add(Factorial(divisor));
		}
		BigInteger answer = nfactorial;
		for(int i=0;i<listofdivisors.size();i++){
			answer = answer.divide(listofdivisors.get(i));
		}
		return answer;
	}
	
	//Returns a list where index 0 is gradient of graph, and index 1 is y intercept of graph
	//Code itself is not as clear as I intended to reduce computation time in solving a problem. Please refer to comments for the meaning of each line
	//Please note that both gradient m and y-intercept c are arraylists containing the numerator and its denominator. It is to represent a fraction in order to keep accuracy
	//As such, the returned values cannot be utilised immediately with primitive types such as int. You will have to write another computation separately for them to work together
	public static List<List<BigInteger>> GraphLinearEquationMNCBigInt(BigInteger ax, BigInteger ay, BigInteger bx, BigInteger by){
		List<List<BigInteger>> mnc = new ArrayList<List<BigInteger>>();
		
		//Calculate fraction for the gradient
		List<BigInteger> m = new ArrayList<BigInteger>();
		m.add((ay.subtract(by)));		//This is Y1 - Y2 in gradient calculation
		m.add(ax.subtract(bx));			//This is X1 - X2 in gradient calculation
		
		//Calculate fraction for the y-intercept
		List<BigInteger> c = new ArrayList<BigInteger>();
		c.add(((ax.subtract(bx)).multiply(ay)).subtract((ay.subtract(by)).multiply(ax)));	//This is the y-intercept value (c value) in linear equation
		c.add((ax.subtract(bx)));															//This is X1 - X2 in gradient calculation
		
		//Add both gradient and y-intercept to list that will be returned
		mnc.add(m);
		mnc.add(c);
		
		return mnc;
	}
	
	//Returns a list where index 0 is gradient of graph, and index 1 is y intercept of graph
	//Code itself is not as clear as I intended to reduce computation time in solving a problem. Please refer to comments for the meaning of each line
	//Please note that both gradient m and y-intercept c are arraylists containing the numerator and its denominator. It is to represent a fraction in order to keep accuracy
	//As such, the returned values cannot be utilised immediately with primitive types such as int. You will have to write another computation separately for them to work together
	public static List<List<Double>> GraphLinearEquationMNCDouble(double ax, double ay, double bx, double by){
		List<List<Double>> mnc = new ArrayList<List<Double>>();
			
		//Calculate fraction for the gradient
		List<Double> m = new ArrayList<Double>();
		String fraction = Fraction(ay-by,ax-bx);	//Simplifies the fraction
		String[] dump = fraction.split("/");
		m.add(Double.parseDouble(dump[0]));		//This is Y1 - Y2 in gradient calculation
		m.add(Double.parseDouble(dump[1]));		//This is X1 - X2 in gradient calculation
			
		//Calculate fraction for the y-intercept
		List<Double> c = new ArrayList<Double>();
		fraction = Fraction(((ax-bx)*ay)-((ay-by)*ax),ax-bx);	//Simplifies the fraction
		dump = fraction.split("/");
		c.add(Double.parseDouble(dump[0]));		//This is the y-intercept value (c value) in linear equation
		c.add(Double.parseDouble(dump[1]));		//This is X1 - X2 in gradient calculation
			
		//Add both gradient and y-intercept to list that will be returned
		mnc.add(m);
		mnc.add(c);
			
		return mnc;
	}
	
	/**
	 * Calculates the perpendicular distance from edge with points (ax,ay) and (bx,by) to point (px,py)
	 * Accuracy is used to decide whether absolute accuracy of answer should be kept. True to keep accuracy and false to ignore.
	 * This function follows the formula of perpendicular dist: (Ax + By + C)/((A^2 + B^2)^(1/2))
	 * @param ax
	 * @param ay
	 * @param bx
	 * @param by
	 * @param px
	 * @param py
	 * @param accuracy
	 * @return
	 */
	public static List<String> PerpendicularDistDouble(double ax, double ay, double bx, double by, double px, double py, boolean accuracy){
		
		List<List<Double>> mnc = MathKit.GraphLinearEquationMNCDouble(ax, ay, bx, by);

		//If edge is vertical, and hence has no gradient or y intercept values
		if(mnc.get(0).get(1)==0){
			List<String> answer = new ArrayList<String>();
			answer.add(String.valueOf(Math.abs(ax-px)));
			return answer;
		}
		else{
			//a is coefficient of x (numerator of gradient for now), b is coefficient of y, and c is y intercept (just the numerator for now)
			double a = mnc.get(0).get(0);
			double b = 1;
			double c = mnc.get(1).get(0);
			
			//Align sign of a with that of gradient
			if((a<0 && (mnc.get(0).get(0)/mnc.get(0).get(1))>0) ||
			   (a>0 && (mnc.get(0).get(0)/mnc.get(0).get(1))<0)	){
				a = -a;
			}
			
			//Align sign of c with that of y intercept
			if((c<0 && (mnc.get(1).get(0)/mnc.get(1).get(1))>0) ||
			   (c>0 && (mnc.get(1).get(0)/mnc.get(1).get(1))<0)	){
				c = -c;
			}
			
			//Multiply each coefficient by all available denominators to eliminate fractions
			a = a*Math.abs(mnc.get(1).get(1));
			b = b*Math.abs(mnc.get(1).get(1))*Math.abs(mnc.get(0).get(1));
			c = c*Math.abs(mnc.get(0).get(1));
			
			//Bring x and c to the side of y so equation becomes AX+BY+C=0
			a = -a;
			c = -c;
			
			//Numerator of perpendicular distance
			double numerator = (a*px) + (b*py) + c;
			
			//Return depends on whether user wants to keep accuracy of answer
			//If accuracy is true, function will return a list with 2 items. Index 0 is the numerator. Index 1 is the denominator, whose value have yet to be square rooted
			//If accuracy is false, function will return a list with 1 item. Index 0 contains the numerical distance in string format.
			if(accuracy){
				List<String> answer = new ArrayList<String>();
				answer.add(String.valueOf(numerator));
				double denominator = Math.pow(a, 2)+Math.pow(b, 2);
				answer.add("Square root of "+String.valueOf(denominator));
				return answer;
			}
			else{
				//Denominator of perpendicular distance
				double denominator = Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
				List<String> answer = new ArrayList<String>();
				answer.add(String.valueOf(Math.abs(numerator/denominator)));
				return answer;
			}
		}
	}
	
	public static BigInteger NumberPartitionGeneratingFunction(int n){
		
		//Initialize variables. P(0), P(1) and P(2) are hard-coded. P(0) is given the value of 1
		List<BigInteger> AllPartitions = new ArrayList<BigInteger>();
		AllPartitions.add(BigInteger.ONE);
		AllPartitions.add(BigInteger.ONE);
		AllPartitions.add(BigInteger.valueOf(2));
		
		//Computation begins from P(3)
		for(int Pcurrent=3;Pcurrent<=n;Pcurrent++){
			
			int index = 1;
			int k = 1;
			List<Integer> IndexToBeSummed = new ArrayList<Integer>();
			
			//Computation derived from generating function: P(n) = P(n-1) + P(n-2) - P(n-5) - P(n-7) + P(n-12) +...
			while((Pcurrent-index)>=0){
				index = (k*((3*k)-1))/2;	//Index is the 1 in P(n-1). Calculation is from generalized pentagonal numbers; Pk = k(3k-1)/2 for k=1,-1,2,-2,3,-3,...
				if((Pcurrent-index)>=0){
					IndexToBeSummed.add(Pcurrent-index);
				}
				if(k<0){
					k = -k;
					k++;
				}
				else{
					k = -k;
				}
			}
		
			//Computes the value of P(n) and add it to grand list so as to compute P(n+1)
			boolean add = true;
			int count = 0;
			BigInteger total = BigInteger.ZERO;
			for(int i=0;i<IndexToBeSummed.size();i++){
				if(count==2){
					add = !add;
					count = 0;
				}
				if(add){total = total.add(AllPartitions.get(IndexToBeSummed.get(i)));}
				else{total = total.subtract(AllPartitions.get(IndexToBeSummed.get(i)));}
				count++;
			}
			AllPartitions.add(total);
		}
		return AllPartitions.get(AllPartitions.size()-1);		//Returns the value of P(n)
	}
	
	/**
	 * Logic of this code is as follows:
	 * 1. Finds all possible number of base divisors (etc 2s and 3s) while breaking down number by said divisors (divide and conquer)
	 * 2. Finds all possible combinations of these base divisors, whose product will be the factor of input number (eg. 28 = [1],[2],[2,2],[7],[2,7],[2,2,7])
	 * Algorithm for combinations is taken from yifan's algorithm library: combinationsnorepeat
	 * @param input: Number whose factors are to be found
	 * @return
	 */
	public static List<BigInteger> AllFactors(BigInteger input){
		
		//subdivisors stores all base divisor values into a list (eg 64=2,2,2,2,2,2)
		List<BigInteger> subdivisors = new ArrayList<BigInteger>();
		BigInteger product = BigInteger.ONE;		
		
		//test for sub-divisors of 2
		while(input.mod(BigInteger.valueOf(2)).compareTo(BigInteger.ZERO)==0){
			subdivisors.add(BigInteger.valueOf(2));
			input = input.divide(BigInteger.valueOf(2));
		}
		
		//divider will begin at 3 and increment by 2 each time.
		BigInteger divider = BigInteger.valueOf(3);
		//test for prime sub-divisors, while breaking down the number for each prime sub-divisors found
		while(divider.compareTo(input) != 1){
			if(input.mod(divider).compareTo(BigInteger.ZERO)==0){
				subdivisors.add(divider);
				input = input.divide(divider);
			}
			else{	//divider only increment if number cannot be divided by divisor anymore. This is to maximize divide and conquer by going through all powers of primes
				divider = divider.add(BigInteger.valueOf(2));
			}
		}
				
		//Finds all possible combinations of sub-divisors, using which to calculate a list of all factors
		List<BigInteger> products = new ArrayList<BigInteger>();
		products.add(BigInteger.ONE);
		for(int r=1;r<=subdivisors.size();r++){		//Loop to generate all possible number of elements in combination of sub-divisors
			@SuppressWarnings("unchecked")
			List<List<BigInteger>> dump = AlgoKit.combinationsnorepeat(r, subdivisors);	//dump is a list of combinations with a specified number of elements
			for(int i=0;i<dump.size();i++){
				List<BigInteger> subdump = dump.get(i);			//subdump is a singular combination from dump
				for(int isub=0;isub<subdump.size();isub++){		//calculate product from elements in subdump
					product = product.multiply(subdump.get(isub));		
				}
				products.add(product);
				product = BigInteger.ONE;
			}
		}
		
		return products;
	}
	
	public static BigInteger npr(int n, int r){
		BigInteger nfactorial = (Factorial(BigInteger.valueOf(n)));
		BigInteger nminusrfactorial = (Factorial(BigInteger.valueOf(n-r)));
		return nfactorial.divide(nminusrfactorial);
	}
	
	/**
	 * Returns the greatest common multiplier of 2 numbers a and b
	 * @param a
	 * @param b
	 * @return
	 */
	public static double GreatestCommonMultiplier(double a, double b) {
	    return b == 0 ? a : GreatestCommonMultiplier(b, a % b); 
	}
	
	/**
	 * Provides and simplifies the fraction of 2 numbers
	 * @param a
	 * @param b
	 * @return
	 */
	public static String Fraction(double a, double b) {
		double GCM = GreatestCommonMultiplier(a, b);
		a = a / GCM;
		b = b / GCM;
		if(Math.abs(b)==0){
			b = 0;
		}
	    return a + "/" + b;
	}
	
	/**
	 * This function checks if 2 edges intersect by running along one another. Note that intersect is NOT counted if edge 1 touches edge 2 with its tip only.
	 * e1 stands for edge 1, and e2 stands for edge 2
	 * Inputs are start and end points of both edges, with x and y representing their coordinates
	 * @param e1x1
	 * @param e1y1
	 * @param e1x2
	 * @param e1y2
	 * @param e2x1
	 * @param e2y1
	 * @param e2x2
	 * @param e2y2
	 * @return
	 */
	public static boolean IfEdgesParallelIntersect(double e1x1, double e1y1, double e1x2, double e1y2, double e2x1, double e2y1, double e2x2, double e2y2){
		List<List<Double>> e1MNC = MathKit.GraphLinearEquationMNCDouble(e1x1, e1y1, e1x2, e1y2);
		List<List<Double>> e2MNC = MathKit.GraphLinearEquationMNCDouble(e2x1, e2y1, e2x2, e2y2);
		
		//If both edges have the same linear equation
		if(
		   (e1MNC.get(0).get(0).equals(e2MNC.get(0).get(0))) &&
		   (e1MNC.get(0).get(1).equals(e2MNC.get(0).get(1))) &&
		   (e1MNC.get(1).get(0).equals(e2MNC.get(1).get(0))) &&
		   (e1MNC.get(1).get(1).equals(e2MNC.get(1).get(1)))
		  ){
			//If one point of either edge lies between (both vertically and horizontally) both points on the other edge
			if(
			    (
				(((e2x1<e1x1) && (e1x1<e2x2)) && ((e2y1<e1y1) && (e1y1<e2y2))) ||
			    (((e2x1<e1x1) && (e1x1<e2x2)) && ((e2y1>e1y1) && (e1y1>e2y2))) ||	
			    (((e2x1>e1x1) && (e1x1>e2x2)) && ((e2y1<e1y1) && (e1y1<e2y2))) ||
			    (((e2x1>e1x1) && (e1x1>e2x2)) && ((e2y1>e1y1) && (e1y1>e2y2)))
			    ) || (
			    (((e2x1<e1x2) && (e1x2<e2x2)) && ((e2y1<e1y2) && (e1y2<e2y2))) ||
			    (((e2x1<e1x2) && (e1x2<e2x2)) && ((e2y1>e1y2) && (e1y2>e2y2))) ||	
			    (((e2x1>e1x2) && (e1x2>e2x2)) && ((e2y1<e1y2) && (e1y2<e2y2))) ||
			    (((e2x1>e1x2) && (e1x2>e2x2)) && ((e2y1>e1y2) && (e1y2>e2y2)))
			    )
			    )
				{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function checks if 2 edges cross intersect. Note that intersect is NOT counted if edge 1 touches edge 2 with its tip only.
	 * e1 stands for edge 1, and e2 stands for edge 2
	 * Inputs are start and end points of both edges, with x and y representing their coordinates
	 * @param e1x1
	 * @param e1y1
	 * @param e1x2
	 * @param e1y2
	 * @param e2x1
	 * @param e2y1
	 * @param e2x2
	 * @param e2y2
	 * @return
	 */
	public static boolean IfEdgesCrossIntersect(double e1x1, double e1y1, double e1x2, double e1y2, double e2x1, double e2y1, double e2x2, double e2y2){
		List<List<Double>> e1MNC = MathKit.GraphLinearEquationMNCDouble(e1x1, e1y1, e1x2, e1y2);
		List<List<Double>> e2MNC = MathKit.GraphLinearEquationMNCDouble(e2x1, e2y1, e2x2, e2y2);

		//If edges are parallel
		if(
		   (e1MNC.get(0).get(0).equals(e2MNC.get(0).get(0))) &&
		   (e1MNC.get(0).get(1).equals(e2MNC.get(0).get(1)))
		   ){
			return false;
		}
		else{
			
			double x = 0;
			double y = 0;
			
			//If edge 1 is vertical but edge 2 is not
			if((e1MNC.get(1).get(1) == 0) && (e2MNC.get(1).get(1) != 0)){
				x = e1x1;
				double e2m = e2MNC.get(0).get(0)/e2MNC.get(0).get(1);
				double e2c = (e2MNC.get(1).get(0)/e2MNC.get(1).get(1));
				y = (e2m*x)+e2c;
				//Checks if intersection of 2 edges lies on both edges
				if(
				   (((e1x1<=x) && (x<=e1x2)) || ((e1x1>=x)&&(x>=e1x2))) &&
				   (((e1y1<y) && (y<e1y2)) || ((e1y1>y)&&(y>e1y2))) &&
				   (((e2x1<x) && (x<e2x2)) || ((e2x1>x)&&(x>e2x2))) &&
				   (((e2y1<y) && (y<e2y2)) || ((e2y1>y)&&(y>e2y2)))
				   ){
					return true;
				}
			}
			//If edge 2 is vertical but edge 1 is not
			else if((e2MNC.get(1).get(1) == 0) && (e1MNC.get(1).get(1) != 0)){
				x = e2x1;
				double e1m = e1MNC.get(0).get(0)/e1MNC.get(0).get(1);
				double e1c = (e1MNC.get(1).get(0)/e1MNC.get(1).get(1));
				y = (e1m*x)+e1c;
				//Checks if intersection of 2 edges lies on both edges
				if(
				   (((e1x1<x) && (x<e1x2)) || ((e1x1>x)&&(x>e1x2))) &&
				   (((e1y1<y) && (y<e1y2)) || ((e1y1>y)&&(y>e1y2))) &&
				   (((e2x1<=x) && (x<=e2x2)) || ((e2x1>=x)&&(x>=e2x2))) &&
				   (((e2y1<y) && (y<e2y2)) || ((e2y1>y)&&(y>e2y2)))
				   ){
					return true;
				}
			}
			//If none of the 2 edges are vertical
			else{
				double e2c = (e2MNC.get(1).get(0)/e2MNC.get(1).get(1));
				double e1c = (e1MNC.get(1).get(0)/e1MNC.get(1).get(1));
				double xnum = e2c-e1c;
				double e1m = e1MNC.get(0).get(0)/e1MNC.get(0).get(1);
				double e2m = e2MNC.get(0).get(0)/e2MNC.get(0).get(1);
				double xdenom = e1m-e2m;
				x = xnum/xdenom;
				y = (e1m*x)+e1c;

				//If edge 1 is horizontal
				if(e1MNC.get(0).get(0) == 0){
					if(
					   (((e1x1<x) && (x<e1x2)) || ((e1x1>x)&&(x>e1x2))) &&
					   (((e1y1<=y) && (y<=e1y2)) || ((e1y1>=y)&&(y>=e1y2))) &&
					   (((e2x1<x) && (x<e2x2)) || ((e2x1>x)&&(x>e2x2))) &&
					   (((e2y1<y) && (y<e2y2)) || ((e2y1>y)&&(y>e2y2)))
					   ){
							return true;
					}
				}
				//If edge 2 is horizontal
				else if(e2MNC.get(0).get(0) == 0){
					if(
					   (((e1x1<x) && (x<e1x2)) || ((e1x1>x)&&(x>e1x2))) &&
					   (((e1y1<y) && (y<e1y2)) || ((e1y1>y)&&(y>e1y2))) &&
					   (((e2x1<x) && (x<e2x2)) || ((e2x1>x)&&(x>e2x2))) &&
					   (((e2y1<=y) && (y<=e2y2)) || ((e2y1>=y)&&(y>=e2y2)))
					   ){
							return true;
					}
				}
				//Checks if intersection of 2 edges lies on both edges
				else{
					if(
					   (((e1x1<x) && (x<e1x2)) || ((e1x1>x)&&(x>e1x2))) &&
					   (((e1y1<y) && (y<e1y2)) || ((e1y1>y)&&(y>e1y2))) &&
					   (((e2x1<x) && (x<e2x2)) || ((e2x1>x)&&(x>e2x2))) &&
					   (((e2y1<y) && (y<e2y2)) || ((e2y1>y)&&(y>e2y2)))
					   ){
							return true;
					}
				}
			}
			
			return false;
		}
	}
	
	/**
	 * Returns a list of amicable numbers, where both numbers are within the specified upper limit
	 * @param limit
	 * @return
	 */
	public static List<String> AmicableNumbers(BigInteger limit){

		//Clears memoize list
		memoize.clear();
		
		List<String> pairs = new ArrayList<String>();
		
		BigInteger i = BigInteger.valueOf(2);
		
		//While number to be checked is below the limit
		while (i.compareTo(limit)==-1){
			
			//If number has yet to be checked
			if(memoize.get(i) == null){
				
				//Finds all factors of the number
				List<BigInteger> firstintfactors = MathKit.AllFactors(i);
	        
	        	//Number removes itself from its list of factors
	        	firstintfactors.remove(firstintfactors.size()-1);
	        
	        	//Finds the sum of factors of the first number
	        	BigInteger firstintfactorssum = MathKit.SumList(firstintfactors);
	        
	        	//Finds all factors of the sum of the number's factors
	        	List<BigInteger> secondintfactors = MathKit.AllFactors(firstintfactorssum);

	        	//Number removes itself from its list of factors
	        	secondintfactors.remove(secondintfactors.size()-1);
	        
	        	//Finds the sum of factors of the number's factors
	        	BigInteger secondintfactorssum = MathKit.SumList(secondintfactors);
	        
	        	//If SumOfFactors(A) = B and SumOfFactors(B) = A (Condition 1)
	        	//If sum of factors of number does not equal to itself (Condition 2)
	        	if(secondintfactorssum.compareTo(i)==0 && i.compareTo(firstintfactorssum) != 0){
	        		memoize.put(i, true);
	        		memoize.put(firstintfactorssum, true);
	        		String pair = i + " " + firstintfactorssum;
	        		pairs.add(pair);
	        	}
			}
			
	        //Increment number by one
	        i = i.add(BigInteger.ONE);
		} 
		
		return pairs;
	}
}
