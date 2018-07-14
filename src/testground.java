import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import test_programs.TSPBruteForceControl;
import test_programs.TSPOptimizer;
import yifan_toolkit.MathKit;


public class testground{
	
	public static void main(String[] args) throws IOException {		
		
		//Brute force technique deployed as a control
		//TSPBruteForceControl.run();
		
		//TSP optimizer
		//TSPOptimizer.run();
		
		//Inputs
		double ax = 3;
		double ay = 4;
		double bx = 7;
		double by = 5;
		double px = 2;
		double py = 3;
		boolean accuracy = false;
		
		System.out.println(MathKit.PerpendicularDistDouble(ax,ay,bx,by,px,py,accuracy));
	}
}
	
