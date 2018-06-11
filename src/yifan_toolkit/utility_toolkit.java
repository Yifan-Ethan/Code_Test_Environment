package yifan_toolkit;

import java.io.File;
import java.io.FileNotFoundException;
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
 * @author weiyifan
 *
 */
public class utility_toolkit {
	public utility_toolkit(){
	}
	
	public String readfile(String filename) throws FileNotFoundException{
		
		Scanner entireFileText = new Scanner(new File(filename));
		String content = "";
		
		while(entireFileText.hasNextLine()){
			content = content + entireFileText.nextLine() + "\n";
		}
		
		entireFileText.close();
		return content;
	}
	
	//Due to issues on java 7, you might need to use  string.replaceAll("toreplace", "replaceto") to clear out as many unwanted symbols as possible before using delimiter;
	public List<String> stringtolistbydelimiter (String content, String delimiter){
		List<String> list = Arrays.asList(content.split(delimiter));
		return list;
	}
	
	public static int countLines(String str){
		   String[] lines = str.split("\r\n|\r|\n");
		   return  lines.length;
	}
	
	public String choosereadfile() throws FileNotFoundException{
		
		String s = "";
		JFileChooser jfc = new JFileChooser();
		int returnValue = jfc.showOpenDialog(null);
		   
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			s = readfile(selectedFile.getAbsolutePath());
		}
		return s;
	}
	
	public int randomnumbergenerator(int max, int min){
		Random rand = new Random();
		return rand.nextInt(max-min+1) + min;
	}
	
	public String randomstring(int length){

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
}
