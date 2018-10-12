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
		
		//Configuration
		double pence = 200;
		
		List<Double> l = new ArrayList<Double>();
		
		//1 pence
		for(int i=0;i<(pence/1);i++){
			l.add((double) 1);
		}
		
		//2 pence
		for(int i=0;i<(pence/2);i++){
			l.add((double) 2);
		}
		
		//5 pence
		for(int i=0;i<(pence/5);i++){
			l.add((double) 5);
		}
		
		//10 pence
		for(int i=0;i<(pence/10);i++){
			l.add((double) 10);
		}
		
		//20 pence
		for(int i=0;i<(pence/20);i++){
			l.add((double) 20);
		}
		
		//50 pence
		for(int i=0;i<(pence/50);i++){
			l.add((double) 50);
		}
		
		//100 pence
		for(int i=0;i<(pence/100);i++){
			l.add((double) 100);
		}
		
		//200 pence
		for(int i=0;i<(pence/200);i++){
			l.add((double) 200);
		}
		
		//Algorithm: Given the total sum of all elements, find all combinations that satisfy total sum
		//STACK OVERFLOW!
		List answer = AlgoKit.CombinationSum(pence, l);

		//Output all combis to console screen
		for(int i=0;i<answer.size();i++){
			System.out.println(answer.get(i));
		}
		
		System.out.println(answer.size());
	}
}
	
