package yifan_toolkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * Java scripting tools, catered for utility functionalities
 * Gentle reminder: Code logic should always be simple and easy to read! No complicated logic is allowed in this class file!
 * This is to ensure that code logic can be easily understood, and easily transferred to a new language
 * This class contains
 * 
 * 1. Read all contents from a txt file, returns a string (readfile)
 * 2. Add contents of string to list by delimiter (stringtolistbydelimiter)
 * 3. Count number of lines in a string (countLines)
 * 4. Read all contents from a txt file, with window to choose file (choosereadfile)
 * 5. Generate a random number from a given range (randomnumbergenerator)
 * 6. Generate a random string of characters with the specified length (randomstring)
 * 7. Converts a byte array to hexadecimal (BytesToHex)
 * 8. Generate a random BigInteger within the given range (RandomBigInteger)
 * 9. Calculates the first day of the next month (FirstDayOfNextMonth)
 * 10. Checks if year is leap year (IsLeapYear)
 * @author weiyifan
 *
 */
public class UtilityKit {
	
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
