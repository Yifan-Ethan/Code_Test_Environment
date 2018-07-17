import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import test_programs.TSPBruteForceControl;
import test_programs.TSPOptimizer;
import yifan_toolkit.MathKit;


public class testground{
	
	public static void main(String[] args) throws IOException {		
		
		//Brute force technique deployed as a control
		long startTime = System.currentTimeMillis();
		TSPBruteForceControl.run();
		System.out.println("COMPLETE: Total time taken is " +(System.currentTimeMillis() - startTime)+" milliseconds");
		
		
		//TSP optimizer
		//long startTime = System.currentTimeMillis();
		//TSPOptimizer.run();
		//System.out.println("COMPLETE: Total time taken is " +(System.currentTimeMillis() - startTime)+" milliseconds");
	}
}
	
