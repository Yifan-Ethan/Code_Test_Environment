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

import test_objects.memoize;
import test_objects.node;
import test_programs.TSPBruteForceControl;
import test_programs.TSPOptimizer;
import yifan_toolkit.AlgoKit;
import yifan_toolkit.MathKit;
import yifan_toolkit.UtilityKit;


public class testground{
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {		
		
		/**
		 * Problem 32: Pandigital products
		 * Create a list of elements, ranging from 1 to 9
		 * Each permutation of possible multiplicand/multiplier/product identity consists of 3 sequences
		 * A lot of sequences will be invalid as a 1 digit number multiplied by a 2 digit number will never result in a 6 digit number
		 * There is only 1 valid set of permutations: 2 digits * 3 digits = 4 digits
		 */
		
		//Configuration
		double s1 = 2;
		double s2 = 3;
		
		long answer = 0;
		HashMap<Long, Boolean> trackanswers = new HashMap<Long, Boolean>();
		List<Long> e = new ArrayList<Long>();
		
		//Add elements to list
		for(long i=1;i<=9;i++){
			e.add(i);
		}
		
		//2 * 3 = 4 digits
		List<List<Long>> firstmultiplicand = AlgoKit.permutations(2, e);
		List<List<Long>> secondmultiplicand = AlgoKit.permutations(3, e);
		List<List<Long>> answers = AlgoKit.permutations(4, e);

		HashMap<Long, Boolean> indexanswers = new HashMap<Long, Boolean>();
		
		for(int i=0;i<answers.size();i++){
			indexanswers.put(Long.parseLong(UtilityKit.ListToString(answers.get(i))), true);
		}
		
		for(int i=0;i<firstmultiplicand.size();i++){
			for(int j=0;j<secondmultiplicand.size();j++){
				
				long m1 = Long.parseLong(UtilityKit.ListToString(firstmultiplicand.get(i)));
				long m2 = Long.parseLong(UtilityKit.ListToString(secondmultiplicand.get(j)));
				
				//Check if 1st multiplicand * 2nd multiplicand == one of the answers
				if(indexanswers.get(m1*m2)!=null){
					
					String m1string = String.valueOf(m1);
					String m2string = String.valueOf(m2);
					String ansstring = String.valueOf(m1*m2);
					String Pandigitalcheck = m1string + m2string + ansstring;		
					if(UtilityKit.ContainsAllChars(Pandigitalcheck,"123456789"))
				    {
						if(trackanswers.get(m1*m2) == null){
				        	answer = answer + (m1*m2);
				        	trackanswers.put(m1*m2, true);
				        }
				    }
				}
			}
		}
		
		
		//1 * 4 = 4 digits
		firstmultiplicand = AlgoKit.permutations(1, e);
		secondmultiplicand = (List<List<Long>>) ((ArrayList) answers).clone();
		
		for(int i=0;i<firstmultiplicand.size();i++){
			for(int j=0;j<secondmultiplicand.size();j++){
				
				long m1 = Long.parseLong(UtilityKit.ListToString(firstmultiplicand.get(i)));
				long m2 = Long.parseLong(UtilityKit.ListToString(secondmultiplicand.get(j)));
				
				//Check if 1st multiplicand * 2nd multiplicand == one of the answers
				if(indexanswers.get(m1*m2)!=null){
					
					String m1string = String.valueOf(m1);
					String m2string = String.valueOf(m2);
					String ansstring = String.valueOf(m1*m2);
					String Pandigitalcheck = m1string + m2string + ansstring;		
					if(UtilityKit.ContainsAllChars(Pandigitalcheck,"123456789"))
				    {
						if(trackanswers.get(m1*m2) == null){
				        	answer = answer + (m1*m2);
				        	trackanswers.put(m1*m2, true);
				        }
				    }
				}
			}
		}
		
		
		System.out.println(answer);
	}
}
	
