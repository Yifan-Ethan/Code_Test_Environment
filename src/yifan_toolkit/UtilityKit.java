package yifan_toolkit;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JFileChooser;

/**
 * Java scripting tools, catered for utility functionalities
 * Gentle reminder: Code logic should always be simple and easy to read! No complicated logic is allowed in this class file!
 * This is to ensure that code logic can be easily understood, and easily transferred to a new language
 * Note: Methods with names starting P#_ are methods that work in conjunction with one another. Groups are denoted by #. Eg. P1_CreateDataPacket works with P1_ReceiveData
 * This class contains
 * 
 * String/Text/Output related functions
 * 1. Read all contents from a txt file, returns a string (readfile)
 * 2. Add contents of string to list by delimiter (stringtolistbydelimiter)
 * 3. Count number of lines in a string (countLines)
 * 4. Read all contents from a txt file, with window to choose file (choosereadfile)
 * 5. Generate a random string of characters with the specified length (randomstring)
 * 6. Converts elements in a list into string based on its existing order (ListToString)
 * 7. Checks if string contains all characters in substring (ContainsAllChars)
 * 
 * Number related functions
 * 1. Generate a random number from a given range (randomnumbergenerator)
 * 2. Generate a random BigInteger within the given range (RandomBigInteger)
 * 3. Shift most significant digit to least significant dight (RotateDigit)
 * 
 * Data conversion
 * 1. Converts a byte array to hexadecimal (BytesToHex)
 * 2. Converts any data type to byte array (P1_CreateDataPacket)
 * 3. Receives data converted from any type via socket (P1_ReceiveData)
 * 
 * Misc
 * 1. Calculates the first day of the next month (FirstDayOfNextMonth)
 * 2. Checks if year is leap year (IsLeapYear)
 * @author weiyifan
 *
 */
public class UtilityKit {
	
	public static int RotateDigit(int n) {
		int removefirstdigit = (int) (n%(Math.pow(10, (MathKit.Digits(n)-1))));
	    int upshift = removefirstdigit*10;
	    int rotation = (int) (upshift + (n/(Math.pow(10, (MathKit.Digits(n)-1)))));
	    if(MathKit.Digits(rotation)!=MathKit.Digits(n)) {	//handles 0s in the number. eg. 200314 -> 314200 instead of 3142
	    	rotation = (int) (rotation*Math.pow(10,MathKit.Digits(n)-MathKit.Digits(rotation)));
	    }
	    return rotation;
	}
	
	/**
	 * Convert any data into byte array
	 * @param data
	 * @return
	 */
	public static byte[] P1_CreateDataPacket(byte[] data){
		byte[] packet = null;
		try{
			byte[] initialize = new byte[1];
			initialize[0] = 2;
			byte[] separator = new byte[1];
			separator[0] = 4;
			byte[] data_length = String.valueOf(data.length).getBytes("UTF8");
			packet = new byte[initialize.length+separator.length+data_length.length+data.length];
				
			System.arraycopy(initialize, 0, packet, 0, initialize.length);
			System.arraycopy(data_length, 0, packet, initialize.length, data_length.length);
			System.arraycopy(separator, 0, packet, initialize.length+data_length.length, separator.length);
			System.arraycopy(data, 0, packet, initialize.length+data_length.length+separator.length, data.length);
		} catch (UnsupportedEncodingException ex){	//Logs if fail
			//Logger.getLogger(testground.class.getName()).log(Level.SEVERE,null,ex);
		}
		return packet;
	}
	
	/**
	 * Receive data converted from any type into byte array
	 * @param dis
	 * @return
	 * @throws IOException
	 */
	public static byte[] P1_ReceiveData(DataInputStream dis) throws IOException{
		byte[] initialize = new byte[1];
		dis.read(initialize, 0, initialize.length);
		if(initialize[0]==2){
			byte[] data_buff = null;
			try{
				int b = 0;
				String buff_length = "";
				while ((b = dis.read()) != 4) {
					buff_length += (char) b;
				}
				int data_length = Integer.parseInt(buff_length);
				data_buff = new byte[Integer.parseInt(buff_length)];
				int byte_read = 0;
				int byte_offset = 0;
				while(byte_offset<data_length){
					byte_read = dis.read(data_buff, byte_offset, data_length-byte_offset);
					byte_offset+=byte_read;
				}
			} catch (IOException ex){	//Logs if fail
				//Logger.getLogger(testground.class.getName()).log(Level.SEVERE,null,ex);
			}
			return data_buff;
		}
		return null;
	}
	
	//s is string to be checked, chars is string containing all characters that need to be in s
	public static boolean ContainsAllChars (String s, String chars) {
		
		Set<Character> sset = new HashSet<>();
	    for (char c : s.toCharArray()) {
	        sset.add(c);
	    }
	    
	    Set<Character> charsset = new HashSet<>();
	    for (char c : chars.toCharArray()) {
	    	charsset.add(c);
	    }
		
		return sset.containsAll(charsset);
	}
	
	public static String ListToString(@SuppressWarnings("rawtypes") List l){
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<l.size();i++){
			sb.append(l.get(i).toString());
		}
		return sb.toString();
	}
	
	/**
	 * Takes in first day of month and number of days in month to calculate first day of following month
	 * @param firstdayofmonth
	 * @param daysinmonth
	 * @return
	 */
	public static int FirstDayOfNextMonth(int firstdayofmonth, int daysinmonth){
		
		int day = daysinmonth%7+firstdayofmonth;
		
		if(day>7){
			return day - 7;
		}
		else{
			return day;
		}
	}
	
	/**
	 * Checks if year is a leap year
	 * @param year
	 * @return
	 */
	public static boolean IsLeapYear(int year){
		//If year is a century and is not divisible by 400
		if((year%100==0) && (year%400!=0)){
			return false;
		}
		//If year is a century and divisible by 400
		else if((year%100==0) && (year%400==0)){
			return true;
		}
		//If year is divisible by 4
		else if(year%4==0){
			return true;
		}
		//Else not leap year
		else{
			return false;
		}
	}
	
	/**
	 * Generate a random Big Integer within the range of min<n<(2^maxbits)-1
	 * @param minvalue
	 * @param maxbits
	 * @return
	 */
	public static BigInteger RandomBigInteger(BigInteger min, int maxbits){
		
		//Random generator
		Random random = new Random();
		
		//Calculate the upper limit
		BigInteger upperlimit = BigInteger.valueOf(2);
		upperlimit = upperlimit.pow(maxbits);
		upperlimit = upperlimit.subtract(BigInteger.ONE);
		
		//Loop continues until random BigInteger is lower than upperlimit
		while(true){
			BigInteger randomint = new BigInteger(maxbits, random);
			
			//Adds min range to random BigInteger as default lowest possible value is 0
			randomint = randomint.add(min);
			
			//Return random BigInteger and end loop if it is within specific range
			if(randomint.compareTo(upperlimit) != 1){
				return randomint;
			}
		}
	}
	
	public static String readfile(String filename) throws FileNotFoundException{
		
		Scanner entireFileText = new Scanner(new File(filename));
		StringBuilder content = new StringBuilder();
		
		while(entireFileText.hasNextLine()){
			content.append(entireFileText.nextLine() + "\n");
		}
		
		entireFileText.close();
		return content.toString();
	}
	
	//Due to issues on java 7, you might need to use  string.replaceAll("toreplace", "replaceto") to clear out as many unwanted symbols as possible before using delimiter;
	public static List<String> StringToListByDelimiter (String content, String delimiter){
		List<String> list = Arrays.asList(content.split(delimiter));
		return list;
	}
	
	public static int countLines(String str){
		   String[] lines = str.split("\r\n|\r|\n");
		   return  lines.length;
	}
	
	public static String choosereadfile() throws FileNotFoundException{
		
		String s = "";
		JFileChooser jfc = new JFileChooser();
		int returnValue = jfc.showOpenDialog(null);
		   
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			s = readfile(selectedFile.getAbsolutePath());
		}
		return s;
	}
	
	public static int randomnumbergenerator(int max, int min){
		Random rand = new Random();
		return rand.nextInt(max-min+1) + min;
	}
	
	public static String randomstring(int length){

	    String CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    StringBuilder string = new StringBuilder();
	    Random rand = new Random();
	    while (string.length() < length) { // length of the random string.
	        int index = rand.nextInt(CHAR.length());
	        string.append(CHAR.charAt(index));
	    }
	    String result = string.toString();
	    return result;
	}
	
	/**
	 * Converts byte array to hexadecimal
	 * @param bytes
	 * @return
	 */
	public static String BytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xFF) + 0x100, 16).substring(1));
        return result.toString();
    }
}
