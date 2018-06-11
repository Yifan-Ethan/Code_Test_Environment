package yifan_toolkit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a list of archived implementations that has been made obsolete due to better implementations
 * These implementations are archived rather than deleted as their code can be useful in solving other problems. Read comments of each code for more details
 * Gentle reminder: Code logic should always be simple and easy to read! No complicated logic is allowed in this class file!
 * This is to ensure that code logic can be easily understood, and easily transferred to a new language
 * @author weiyifan
 *
 */
public class ARCHIVED_implementations {
	
	/**
	 * Computation of number partitioning following the standard infinite polynomial series
	 * Logic in code deals with multiplying of 2 or more polynomial equations, and produce the final result as a single polynomial equation
	 * Logic can be utilized to write code specialized in handling multiplication of polynomial equations
	 */
	public static BigInteger NumberPartitionGeneratingFunction(int k){
		List<BigInteger> polynomial = new ArrayList<BigInteger>();
		polynomial.add(BigInteger.ONE);	//To skip index 0
		List<BigInteger> dump = new ArrayList<BigInteger>();
		
		//Specifically for the first polynomial series (1 + X1 + X2 + X3 + X4 + ...)
		for(int n=1;n<=k;n++){
			polynomial.add(BigInteger.ONE);
		}
		
		//Loop to increment the power of indeterminate for each polynomial
		for(int n=2;n<=k;n++){
			//dump map to save initial values of computed polynomial to prevent logic failure resulting from compounding increase of co-efficient during computation
			dump.addAll(polynomial); 	
			//Loop to move from one indeterminate to the next in the polynomial series
			for(int j=n;j<=k;j=j+n){		
				//Loop for moving through the current, computed polynomial stored in the map
				for(int key=1;key<=k;key++){		
					/**
					 * The following if(key==1) condition is to account for the first participant in the computed polynomial, which is the 1.
					 * During multiplication, the 1 will always add a new copy of each indeterminate from the new polynomial. 
					 * Mathematically speaking, this should be done when key=0 (not key=1, which means x^1), but this code does not include logic for key=0 to cut computation cost.
					 * Hence, the determinate with corresponding power will simply be incremented at the start of multiplying each new indeterminate of the new polynomial, which happens when key=1
					 */
					if(key==1){		
						polynomial.set(j, polynomial.get(j).add(BigInteger.ONE));
					}
					if((key+j)<=k){
						polynomial.set(key+j, dump.get(key).add(polynomial.get(key+j)));
					}
				}
			}
			dump.clear();	//clears dump list for next iteration
		}
		return polynomial.get(k);
	}
}
