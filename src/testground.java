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

		// CONFIG
		int count = 4;
		
		for(int i=1;i<1000000;i++) {
			if(MathKit.DistinctFactors(BigInteger.valueOf(i)).size()==count) {
				for(int c=1;c<=count-1;c++) {
					if(MathKit.DistinctFactors(BigInteger.valueOf(i+c)).size()!=count) {
						c = count+1;
					}
					if(c==count-1) {
						System.out.println(i);
						i = 1000000;
					}
				}
			}
		}
	}
}
