import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import test_objects.node;
import test_objects.object1;
import yifan_toolkit.Supportfunction;
import yifan_toolkit.algorithms_toolkit;
import yifan_toolkit.math_toolkit;
import yifan_toolkit.utility_toolkit;

public class testground{
	
	//Personal tools library
	static math_toolkit k1 = new math_toolkit();
	static utility_toolkit k2 = new utility_toolkit();
	static algorithms_toolkit k3 = new algorithms_toolkit();
	static Supportfunction f = new Supportfunction();
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {		
		File authoritydatabase = new File("test.txt");
		try {
			authoritydatabase.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(authoritydatabase, true));
			writer.append("hello world");
			writer.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}
	

	
	public int randomnumbergenerator(int max, int min){
		Random rand = new Random();
		return rand.nextInt(max-min+1) + min;
	}
	

	//System.out.println(k2.randomnumbergenerator(613, 1));
}
