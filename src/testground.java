import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.instrument.Instrumentation;
import java.math.BigDecimal;
import java.math.BigInteger;
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
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import test_programs.TSPBruteForceControl;
import test_programs.TSPOptimizer;
import yifan_toolkit.MathKit;
import yifan_toolkit.UtilityKit;


public class testground{
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {		
		
		//Brute force technique deployed as a control
		//long startTime = System.currentTimeMillis();
		//TSPBruteForceControl.run();
		//System.out.println("COMPLETE: Total time taken is " +(System.currentTimeMillis() - startTime)+" milliseconds");
		
		//TSP optimizer
		//long startTime = System.currentTimeMillis();
		//TSPOptimizer.run();
		//System.out.println("COMPLETE: Total time taken is " +(System.currentTimeMillis() - startTime)+" milliseconds");
		
		/**
		 * Project euler qns23, Non-Abundant Sums
		 */
		
		List<Integer> AbundantNumbers = new ArrayList<Integer>();
		
		//Generate all abundant numbers from 1 to 28124
		for(int i = 2;i<28124;i++){
			
			//Generate all factors of the number
			List<BigInteger> factors = MathKit.AllFactors(BigInteger.valueOf(i));
			
			//Remove the number itself from the list of its factors
			factors.remove(factors.size()-1);
			
			//Calculate the sum of the factors
			BigInteger factorsum = MathKit.SumList(factors);
			
			//Check if number is an abundant number
			if(factorsum.intValue() > i){
				//If yes, add number to list of abundant numbers
				AbundantNumbers.add(i);
			}
		}
		
		//Sort abundant numbers in ascending order
		Collections.sort(AbundantNumbers);
		
		List<BigInteger> antiabundantsum = new ArrayList<BigInteger>();
		
		boolean pass = true;
		
		for(int i = 1; i < 28124;i++){
			for(int n = 0; n < AbundantNumbers.size(); n++){
				if(n>i){
					n = AbundantNumbers.size();
				}
				for(int m = n; m < AbundantNumbers.size(); m++){
					int sum = AbundantNumbers.get(n) + AbundantNumbers.get(m);
					if(sum == i){
						pass = false;
						m = AbundantNumbers.size();
					}
					if(sum > i){
						m = AbundantNumbers.size();
					}
				}
				if(pass == false){
					n = AbundantNumbers.size();
				}
			}
			if(pass){
				antiabundantsum.add(BigInteger.valueOf(i));
			}
			pass = true;
		}
		
		System.out.println(AbundantNumbers);
		System.out.println(antiabundantsum);
		System.out.println(MathKit.SumList(antiabundantsum));
	}
}
	
