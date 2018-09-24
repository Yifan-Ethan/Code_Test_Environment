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

import test_objects.memoize;
import test_objects.node;
import test_programs.TSPBruteForceControl;
import test_programs.TSPOptimizer;
import yifan_toolkit.AlgoKit;
import yifan_toolkit.MathKit;
import yifan_toolkit.UtilityKit;


public class testground{
	
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InterruptedException {		
		
		long maxprime = 0;
		double answer = 0;
		
		System.out.println(CountPrime(-999,-999));
		
		for(int a=-999;a<1000;a++){
			for(int b=-1000;b<=1000;b++){
				long totalprimes = CountPrime(a,b);
				long totalprimesneg = CountPrimeNegativeN(a,b);
				if(totalprimes<totalprimesneg){
					totalprimes = totalprimesneg;
				}
				if(totalprimes>maxprime){
					maxprime = totalprimes;
					answer = a*b;
					System.out.println("Highest number of consecutive primes found: "+maxprime);
					System.out.println("a is: "+a);
					System.out.println("b is: "+b);
					System.out.println("Current answer: "+answer);
				}
			}
		}
		
		System.out.println("Final answer: "+answer);
	}
	
	public static long CountPrime(int x, int y){
		
		long count = 0;
		
		//Initial run
		BigInteger n2 = BigInteger.valueOf(count*count);
		BigInteger an = BigInteger.valueOf(count*x);
		BigInteger b = BigInteger.valueOf(y);
		
		while(MathKit.IsPrime(n2.add(an).add(b))){
			count++;
			n2 = BigInteger.valueOf(count*count);
			an = BigInteger.valueOf(count*x);
			b = BigInteger.valueOf(y);
		}
		
		return count;
	}
	
public static long CountPrimeNegativeN(int x, int y){
		
		long count = 0;
		
		//Initial run
		BigInteger n2 = BigInteger.valueOf(count*count);
		BigInteger an = BigInteger.valueOf(count*x);
		BigInteger b = BigInteger.valueOf(y);
		
		while(MathKit.IsPrime(n2.add(an).add(b))){
			count--;
			n2 = BigInteger.valueOf(count*count);
			an = BigInteger.valueOf(count*x);
			b = BigInteger.valueOf(y);
		}

		return -count;
	}
}
	
