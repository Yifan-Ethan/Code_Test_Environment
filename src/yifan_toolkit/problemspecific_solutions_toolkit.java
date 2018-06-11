package yifan_toolkit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Java scripting tools, catered for difficult solutions that deal with a specific problem
 * Gentle reminder: Code logic should always be simple and easy to read! No complicated logic is allowed in this class file!
 * This is to ensure that code logic can be easily understood, and easily transferred to a new language
 * This class contains
 * 
 * 1. Algorithm to find the number of triangles which has the specified point within them. (pointintriangle)
 * All vertices of triangle and the specified point are defined by X and Y coordinates in 2 dimensional plane
 * 
 * @author weiyifan
 */

public class problemspecific_solutions_toolkit {

	static math_toolkit k1 = new math_toolkit();
	
	/**
	 * Important: You need to write your own code to generate the set of coordinates for vertices within the function itself. 
	 * Please refer to blue comments in code on where to do this.
	 * @param n is the number of coordinates that will be generated
	 * @param inputx is the x coordinate of point to be tested whether it is in triangle
	 * @param inputy is the y coordinate of point to be tested whether it is in triangle
	 * @param xmod is used if modulus is needed during generation of x coordinates of vertices. Please input this value as null if it is not needed or you fail to understand how to use it properly
	 * @param ymod is used if modulus is needed during generation of y coordinates of vertices. Please input this value as null if it is not needed or you fail to understand how to use it properly
	 * The primary objective of having xmod and ymod is to increase code efficiency and save memory. Please refer to comments in recursetrianglesearch on why is this so.
	 */
	public static void pointintriangle(int n, BigInteger inputx, BigInteger inputy, BigInteger xmod, BigInteger ymod){		
		
		//Removes the effect of modulus x or y if they are not needed
		if(xmod==null){
			xmod = BigInteger.valueOf(n);
		}
		if(ymod==null){
			ymod = BigInteger.valueOf(n);
		}
		
		//Internal variables
		List<List<BigInteger>> Pn = new ArrayList<List<BigInteger>>();
		List<BigInteger> xcoordinates = new ArrayList<BigInteger>();
		List<BigInteger> ycoordinates = new ArrayList<BigInteger>();
		
		//Adds coordinates to list
		for(int i=1;i<=n;i++){
			/**
			 * Write your own code to generate x coordinates for vertices here and replace line BigInteger x = BigInteger.ZERO;
			 * If computation of coordinate requires the modulus (eg x = 1248mod10), you can use your input xmod (xmod=10) here
			 * When using xmod, remember to limit the number of elements stored in xcoordinates list to i<xmod.
			 * This is because elements computed from i==xmod onwards are repeated elements, and this code has been specifically designed to handle that so as to increase efficiency
			 * It is completely fine to ignore the xmod input parameter, at the cost of code efficiency when modulus are used during computation of coordinates
			 */
			BigInteger x = BigInteger.ZERO;
			x = x.subtract(inputx);
			xcoordinates.add(x);
			
			/**
			 * Write your own code to generate y coordinates for vertices here and replace line BigInteger y = BigInteger.ZERO;
			 * If computation of coordinate requires the modulus (eg y = 1248mod10), you can use your input ymod (ymod=10) here
			 * When using ymod, remember to limit the number of elements stored in ycoordinates list to i<ymod, as elements computed from i==ymod onwards are repeated elements
			 * This is because elements computed from i==ymod onwards are repeated elements, and this code has been specifically designed to handle that so as to increase efficiency
			 * It is completely fine to ignore the ymod input parameter, at the cost of code efficiency when modulus are used during computation of coordinates
			 */
			BigInteger y = BigInteger.ZERO;
			y = y.subtract(inputy);
			ycoordinates.add(y);
		}
		Pn.add(xcoordinates);
		Pn.add(ycoordinates);
		
		//Initiate triangle search sequence
		BigInteger totalcount = initiatetrianglesearch(3, Pn, n, xmod, ymod);
		
		System.out.println("C("+n+"): "+totalcount);
	}
	
	//Initiation
	public static BigInteger initiatetrianglesearch(int r, List<List<BigInteger>> listofelements, int pn, BigInteger xmod, BigInteger ymod){
		BigInteger totalcount = BigInteger.ZERO;
		List<BigInteger> dump1 = new ArrayList<BigInteger>();
		totalcount = recursetrianglesearch(0,r,listofelements,dump1,pn,totalcount, xmod, ymod);
		return totalcount;
	}
		//New shortened version of recursion to cut computation time
		@SuppressWarnings("unchecked")
		public static BigInteger recursetrianglesearch(int index, int r, List<List<BigInteger>> listofelements, List<BigInteger> combi, int pn,BigInteger totalcount, BigInteger xmod, BigInteger ymod){
			//Base case: last element of the subset
			if(r==1){	
				for(int i=index;i<pn-r+1;i++){			//Iterate through each element within current loop
					
					//First vertice			
					BigInteger firstx = listofelements.get(0).get(BigInteger.valueOf(i).mod(BigInteger.valueOf(32322)).intValue());
					BigInteger firsty = listofelements.get(1).get(BigInteger.valueOf(i).mod(BigInteger.valueOf(30102)).intValue());
					
					//Second vertice
					BigInteger secondx = combi.get(0);
					BigInteger secondy = combi.get(1);
					
					//Third vertice
					BigInteger thirdx = combi.get(2);
					BigInteger thirdy = combi.get(3);
					
					//Linear equation of the 3 sides of triangle
					List<List<BigInteger>> firstsecondmnc = k1.graphlinearequationmnc(firstx, firsty, secondx, secondy);
					List<List<BigInteger>> firstthirdmnc = k1.graphlinearequationmnc(firstx, firsty, thirdx, thirdy);
					List<List<BigInteger>> secondthirdmnc = k1.graphlinearequationmnc(secondx, secondy, thirdx, thirdy);
					
					//Preparing of variables to generate axis intercept for each linear equation
					List<List<BigInteger>> axisintercepts = new ArrayList<List<BigInteger>>();
					BigInteger xinterceptnumerator = BigInteger.ZERO;
					BigInteger xinterceptdenominator = BigInteger.ZERO;
					BigInteger yinterceptnumerator = BigInteger.ZERO;
					BigInteger yinterceptdenominator = BigInteger.ZERO;
					
					//Record the highest and lowest values of x and y coordinates for this side of the triangle. This will act as triangle boundary constraint
					BigInteger firstsecondmaxx = firstx;
					BigInteger firstsecondminx = firstx;
					BigInteger firstsecondmaxy = firsty;
					BigInteger firstsecondminy = firsty;
					if(secondx.compareTo(firstsecondmaxx)==1){
						firstsecondmaxx = secondx;
					}
					if(secondx.compareTo(firstsecondminx)==-1){
						firstsecondminx = secondx;
					}
					if(secondy.compareTo(firstsecondmaxy)==1){
						firstsecondmaxy = secondy;
					}
					if(secondy.compareTo(firstsecondminy)==-1){
						firstsecondminy = secondy;
					}
					
					//Generate axis intercept for first side of triangle
					xinterceptnumerator = firstsecondmnc.get(1).get(0);
					xinterceptdenominator = firstsecondmnc.get(0).get(0);
					BigInteger firstsecondxintercept;
					if(xinterceptdenominator.compareTo(BigInteger.ZERO)==0){	//If line has no x intercept
						firstsecondxintercept = firstsecondmaxx.add(BigInteger.ONE);
					}
					else{
						firstsecondxintercept = xinterceptnumerator.divide(xinterceptdenominator).multiply(BigInteger.valueOf(-1));
					}
					yinterceptnumerator = firstsecondmnc.get(1).get(0);
					yinterceptdenominator = firstsecondmnc.get(1).get(1);
					BigInteger firstsecondyintercept;
					if(yinterceptdenominator.compareTo(BigInteger.ZERO)==0){	//If line has no y intercept
						firstsecondyintercept = firstsecondmaxy.add(BigInteger.ONE);
					}
					else{
						firstsecondyintercept = yinterceptnumerator.divide(yinterceptdenominator);
					}
					
					//Important fix for conditional logic to deal with fractions in intercept values
					if(xinterceptdenominator.compareTo(BigInteger.ZERO) != 0){
						if(xinterceptnumerator.mod(xinterceptdenominator.abs()).compareTo(BigInteger.ZERO)==1){
							if(firstsecondxintercept.compareTo(BigInteger.ZERO) == 1 && firstsecondmaxx.compareTo(BigInteger.ZERO)!=-1){
								firstsecondxintercept = firstsecondxintercept.add(BigInteger.ONE);
							}
							if(firstsecondxintercept.compareTo(BigInteger.ZERO) == -1 && firstsecondminx.compareTo(BigInteger.ZERO)!=1){
								firstsecondxintercept = firstsecondxintercept.subtract(BigInteger.ONE);
							}
						}
					}
					if(yinterceptdenominator.compareTo(BigInteger.ZERO) != 0){
						if(yinterceptnumerator.mod(yinterceptdenominator.abs()).compareTo(BigInteger.ZERO)==1){
							if(firstsecondyintercept.compareTo(BigInteger.ZERO) == 1 && firstsecondmaxy.compareTo(BigInteger.ZERO)!=-1){
								firstsecondyintercept = firstsecondyintercept.add(BigInteger.ONE);
							}
							if(firstsecondyintercept.compareTo(BigInteger.ZERO) == -1 && firstsecondminy.compareTo(BigInteger.ZERO)!=1){
								firstsecondyintercept = firstsecondyintercept.subtract(BigInteger.ONE);
							}
						}
					}
					
					//Important fix for conditional logic to deal with fractions in intercept values, where absolute value of intercept is 1<c<0
					if(xinterceptnumerator.compareTo(BigInteger.ZERO)!=0 && firstsecondxintercept.compareTo(BigInteger.ZERO)==0){
						if((xinterceptnumerator.compareTo(BigInteger.ZERO)==1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==1) ||
						   (xinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==-1)){
							firstsecondxintercept = BigInteger.valueOf(-1);
						}
						if((xinterceptnumerator.compareTo(BigInteger.ZERO)==1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==-1) ||
						   (xinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==1)){
							firstsecondxintercept = BigInteger.valueOf(1);
						}
					}
					if(yinterceptnumerator.compareTo(BigInteger.ZERO)!=0 && firstsecondyintercept.compareTo(BigInteger.ZERO)==0){
						if((yinterceptnumerator.compareTo(BigInteger.ZERO)==1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==1) ||
						   (yinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==-1)){
							firstsecondyintercept = BigInteger.valueOf(1);
						}
						if((yinterceptnumerator.compareTo(BigInteger.ZERO)==1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==-1) ||
						   (yinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==1)){
							firstsecondyintercept = BigInteger.valueOf(-1);
						}
					}
					
					//Save axis intercepts that are within lines of triangle, and discard those that are not
					if((firstsecondxintercept.compareTo(firstsecondmaxx)==-1 || firstsecondxintercept.compareTo(firstsecondmaxx)==0) && (firstsecondxintercept.compareTo(firstsecondminx)==1 || firstsecondxintercept.compareTo(firstsecondminx)==0)){
						List<BigInteger> dump = new ArrayList<BigInteger>();
						dump.add(firstsecondxintercept);
						dump.add(BigInteger.ZERO);
						axisintercepts.add(dump);
					}
					if((firstsecondyintercept.compareTo(firstsecondmaxy)==-1 || firstsecondyintercept.compareTo(firstsecondmaxy)==0) && (firstsecondyintercept.compareTo(firstsecondminy)==1 || firstsecondyintercept.compareTo(firstsecondminy)==0)){
						List<BigInteger> dump = new ArrayList<BigInteger>();
						dump.add(BigInteger.ZERO);
						dump.add(firstsecondyintercept);
						axisintercepts.add(dump);
					}
					
					//Record the highest and lowest values of x and y coordinates for this side of the triangle. This will act as triangle boundary constraint
					BigInteger firstthirdmaxx = firstx;
					BigInteger firstthirdminx = firstx;
					BigInteger firstthirdmaxy = firsty;
					BigInteger firstthirdminy = firsty;
					if(thirdx.compareTo(firstthirdmaxx)==1){
						firstthirdmaxx = thirdx;
					}
					if(thirdx.compareTo(firstthirdminx)==-1){
						firstthirdminx = thirdx;
					}
					if(thirdy.compareTo(firstthirdmaxy)==1){
						firstthirdmaxy = thirdy;
					}
					if(thirdy.compareTo(firstthirdminy)==-1){
						firstthirdminy = thirdy;
					}
					
					//Generate axis intercept for second side of triangle
					xinterceptnumerator = firstthirdmnc.get(1).get(0);
					xinterceptdenominator = firstthirdmnc.get(0).get(0);
					BigInteger firstthirdxintercept;
					if(xinterceptdenominator.compareTo(BigInteger.ZERO)==0){	//If line has no x intercept
						firstthirdxintercept = firstthirdmaxx.add(BigInteger.ONE);
					}
					else{
						firstthirdxintercept = xinterceptnumerator.divide(xinterceptdenominator).multiply(BigInteger.valueOf(-1));
					}
					yinterceptnumerator = firstthirdmnc.get(1).get(0);
					yinterceptdenominator = firstthirdmnc.get(1).get(1);
					BigInteger firstthirdyintercept;
					if(yinterceptdenominator.compareTo(BigInteger.ZERO)==0){	//If line has no y intercept
						firstthirdyintercept = firstthirdmaxy.add(BigInteger.ONE);
					}
					else{
						firstthirdyintercept = yinterceptnumerator.divide(yinterceptdenominator);
					}
					
					//Important fix for conditional logic to deal with fractions in intercept values
					if(xinterceptdenominator.compareTo(BigInteger.ZERO) != 0){
						if(xinterceptnumerator.mod(xinterceptdenominator.abs()).compareTo(BigInteger.ZERO)==1){
							if(firstthirdxintercept.compareTo(BigInteger.ZERO) == 1 && firstthirdmaxx.compareTo(BigInteger.ZERO)!=-1){
								firstthirdxintercept = firstthirdxintercept.add(BigInteger.ONE);
							}
							if(firstthirdxintercept.compareTo(BigInteger.ZERO) == -1 && firstthirdminx.compareTo(BigInteger.ZERO)!=1){
								firstthirdxintercept = firstthirdxintercept.subtract(BigInteger.ONE);
							}
						}
					}
					if(yinterceptdenominator.compareTo(BigInteger.ZERO) != 0){
						if(yinterceptnumerator.mod(yinterceptdenominator.abs()).compareTo(BigInteger.ZERO)==1){
							if(firstthirdyintercept.compareTo(BigInteger.ZERO) == 1 && firstthirdmaxy.compareTo(BigInteger.ZERO)!=-1){
								firstthirdyintercept = firstthirdyintercept.add(BigInteger.ONE);
							}
							if(firstthirdyintercept.compareTo(BigInteger.ZERO) == -1 && firstthirdminy.compareTo(BigInteger.ZERO)!=1){
								firstthirdyintercept = firstthirdyintercept.subtract(BigInteger.ONE);
							}
						}
					}
					
					//Important fix for conditional logic to deal with fractions in intercept values, where absolute value of intercept is 1<c<0
					if(xinterceptnumerator.compareTo(BigInteger.ZERO)!=0 && firstthirdxintercept.compareTo(BigInteger.ZERO)==0){
						if((xinterceptnumerator.compareTo(BigInteger.ZERO)==1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==1) ||
						   (xinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==-1)){
							firstthirdxintercept = BigInteger.valueOf(-1);
						}
						if((xinterceptnumerator.compareTo(BigInteger.ZERO)==1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==-1) ||
						   (xinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==1)){
							firstthirdxintercept = BigInteger.valueOf(1);
						}
					}
					if(yinterceptnumerator.compareTo(BigInteger.ZERO)!=0 && firstthirdyintercept.compareTo(BigInteger.ZERO)==0){
						if((yinterceptnumerator.compareTo(BigInteger.ZERO)==1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==1) ||
						   (yinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==-1)){
							firstthirdyintercept = BigInteger.valueOf(1);
						}
						if((yinterceptnumerator.compareTo(BigInteger.ZERO)==1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==-1) ||
						   (yinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==1)){
							firstthirdyintercept = BigInteger.valueOf(-1);
						}
					}
					
					//Save axis intercepts that are within lines of triangle, and discard those that are not
					if((firstthirdxintercept.compareTo(firstthirdmaxx)==-1 || firstthirdxintercept.compareTo(firstthirdmaxx)==0) && (firstthirdxintercept.compareTo(firstthirdminx)==1 || firstthirdxintercept.compareTo(firstthirdminx)==0)){
						List<BigInteger> dump = new ArrayList<BigInteger>();
						dump.add(firstthirdxintercept);
						dump.add(BigInteger.ZERO);
						axisintercepts.add(dump);
					}
					if((firstthirdyintercept.compareTo(firstthirdmaxy)==-1 || firstthirdyintercept.compareTo(firstthirdmaxy)==0) && (firstthirdyintercept.compareTo(firstthirdminy)==1 || firstthirdyintercept.compareTo(firstthirdminy)==0)){
						List<BigInteger> dump = new ArrayList<BigInteger>();
						dump.add(BigInteger.ZERO);
						dump.add(firstthirdyintercept);
						axisintercepts.add(dump);
					}
					
					//Record the highest and lowest values of x and y coordinates for this side of the triangle. This will act as triangle boundary constraint
					BigInteger secondthirdmaxx = secondx;
					BigInteger secondthirdminx = secondx;
					BigInteger secondthirdmaxy = secondy;
					BigInteger secondthirdminy = secondy;
					if(thirdx.compareTo(secondthirdmaxx)==1){
						secondthirdmaxx = thirdx;
					}
					if(thirdx.compareTo(secondthirdminx)==-1){
						secondthirdminx = thirdx;
					}
					if(thirdy.compareTo(secondthirdmaxy)==1){
						secondthirdmaxy = thirdy;
					}
					if(thirdy.compareTo(secondthirdminy)==-1){
						secondthirdminy = thirdy;
					}
					
					//Generate axis intercept for third side of triangle
					xinterceptnumerator = secondthirdmnc.get(1).get(0);
					xinterceptdenominator = secondthirdmnc.get(0).get(0);
					BigInteger secondthirdxintercept;
					if(xinterceptdenominator.compareTo(BigInteger.ZERO)==0){	//If line has no x intercept
						secondthirdxintercept = secondthirdmaxx.add(BigInteger.ONE);
					}
					else{
						secondthirdxintercept = xinterceptnumerator.divide(xinterceptdenominator).multiply(BigInteger.valueOf(-1));
					}
					yinterceptnumerator = secondthirdmnc.get(1).get(0);
					yinterceptdenominator = secondthirdmnc.get(1).get(1);
					BigInteger secondthirdyintercept;
					if(yinterceptdenominator.compareTo(BigInteger.ZERO)==0){	//If line has no y intercept
						secondthirdyintercept = secondthirdmaxy.add(BigInteger.ONE);
					}
					else{
						secondthirdyintercept = yinterceptnumerator.divide(yinterceptdenominator);
					}

					//Important fix for conditional logic to deal with fractions in intercept values
					if(xinterceptdenominator.compareTo(BigInteger.ZERO) != 0){
						if(xinterceptnumerator.mod(xinterceptdenominator.abs()).compareTo(BigInteger.ZERO)==1){
							if(secondthirdxintercept.compareTo(BigInteger.ZERO) == 1 && secondthirdmaxx.compareTo(BigInteger.ZERO)!=-1){
								secondthirdxintercept = secondthirdxintercept.add(BigInteger.ONE);
							}
							if(secondthirdxintercept.compareTo(BigInteger.ZERO) == -1 && secondthirdminx.compareTo(BigInteger.ZERO)!=1){
								secondthirdxintercept = secondthirdxintercept.subtract(BigInteger.ONE);
							}
						}
					}
					if(yinterceptdenominator.compareTo(BigInteger.ZERO) != 0){
						if(yinterceptnumerator.mod(yinterceptdenominator.abs()).compareTo(BigInteger.ZERO)==1){
							if(secondthirdyintercept.compareTo(BigInteger.ZERO) == 1 && secondthirdmaxy.compareTo(BigInteger.ZERO)!=-1){
								secondthirdyintercept = secondthirdyintercept.add(BigInteger.ONE);
							}
							if(secondthirdyintercept.compareTo(BigInteger.ZERO) == -1 && secondthirdminy.compareTo(BigInteger.ZERO)!=1){
								secondthirdyintercept = secondthirdyintercept.subtract(BigInteger.ONE);
							}
						}
					}
					
					//Important fix for conditional logic to deal with fractions in intercept values, where absolute value of intercept is 1<c<0
					if(xinterceptnumerator.compareTo(BigInteger.ZERO)!=0 && secondthirdxintercept.compareTo(BigInteger.ZERO)==0){
						if((xinterceptnumerator.compareTo(BigInteger.ZERO)==1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==1) ||
						   (xinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==-1)){
							secondthirdxintercept = BigInteger.valueOf(-1);
						}
						if((xinterceptnumerator.compareTo(BigInteger.ZERO)==1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==-1) ||
						   (xinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && xinterceptdenominator.compareTo(BigInteger.ZERO)==1)){
							secondthirdxintercept = BigInteger.valueOf(1);
						}
					}
					if(yinterceptnumerator.compareTo(BigInteger.ZERO)!=0 && secondthirdyintercept.compareTo(BigInteger.ZERO)==0){
						if((yinterceptnumerator.compareTo(BigInteger.ZERO)==1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==1) ||
						   (yinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==-1)){
							secondthirdyintercept = BigInteger.valueOf(1);
						}
						if((yinterceptnumerator.compareTo(BigInteger.ZERO)==1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==-1) ||
						   (yinterceptnumerator.compareTo(BigInteger.ZERO)==-1 && yinterceptdenominator.compareTo(BigInteger.ZERO)==1)){
							secondthirdyintercept = BigInteger.valueOf(-1);
						}
					}
					
					//Save axis intercepts that are within lines of triangle, and discard those that are not
					if((secondthirdxintercept.compareTo(secondthirdmaxx)==-1 || secondthirdxintercept.compareTo(secondthirdmaxx)==0) && (secondthirdxintercept.compareTo(secondthirdminx)==1 || secondthirdxintercept.compareTo(secondthirdminx)==0)){
						List<BigInteger> dump = new ArrayList<BigInteger>();
						dump.add(secondthirdxintercept);
						dump.add(BigInteger.ZERO);
						axisintercepts.add(dump);
					}
					if((secondthirdyintercept.compareTo(secondthirdmaxy)==-1 || secondthirdyintercept.compareTo(secondthirdmaxy)==0) && (secondthirdyintercept.compareTo(secondthirdminy)==1 || secondthirdyintercept.compareTo(secondthirdminy)==0)){
						List<BigInteger> dump = new ArrayList<BigInteger>();
						dump.add(BigInteger.ZERO);
						dump.add(secondthirdyintercept);
						axisintercepts.add(dump);
					}
					
					//Only checks triangle is its sides intercepted axis of graph 4 times
					if(axisintercepts.size()>=4){

						boolean highery = false;
						boolean higherx = false;
						boolean lowery = false;
						boolean lowerx = false;
						
						//Ensures that all 4 conditions are met
						for(int n=0;n<axisintercepts.size();n++){
							if(axisintercepts.get(n).get(0).compareTo(BigInteger.ZERO) == 1){
								higherx = true;
							}
							if(axisintercepts.get(n).get(0).compareTo(BigInteger.ZERO) == -1){
								lowerx = true;
							}
							if(axisintercepts.get(n).get(1).compareTo(BigInteger.ZERO) == 1){
								highery = true;
							}
							if(axisintercepts.get(n).get(1).compareTo(BigInteger.ZERO) == -1){
								lowery = true;
							}
						}
						
						if(highery && higherx && lowery && lowerx){
							totalcount = totalcount.add(BigInteger.ONE);
						}
					}
					//Caters for the situation where origin is on the side or vertice of triangle
					else if(axisintercepts.size()<4){
						boolean found = false;
						for(int n=0;n<axisintercepts.size();n++){
							if(axisintercepts.get(n).get(0).compareTo(BigInteger.ZERO) == 0 && axisintercepts.get(n).get(1).compareTo(BigInteger.ZERO) == 0){
								found = true;
							}
						}
						if(found){
							totalcount = totalcount.add(BigInteger.ONE);
						}
					}
				}
				return totalcount;
			}

			//Each recursion behaves like a loop of its own, and adds the element within its own loop before opening the next loop
			for(int i=index;i<pn-r+1;i++){							//Iterate through each element within current loop
				List<BigInteger> newcombi = (List<BigInteger>) ((ArrayList<BigInteger>) combi).clone();						//creates a new subset based on the subset within the current loop
				/**
				 * This is where xmod/ymod shines. It reduces the number of elements needed to be stored in list to save memory space
				 */
				newcombi.add(listofelements.get(0).get(BigInteger.valueOf(i).mod(xmod).intValue()));
				newcombi.add(listofelements.get(1).get(BigInteger.valueOf(i).mod(ymod).intValue()));	//add new element of current loop to subset
				totalcount = recursetrianglesearch(i+1,r-1,listofelements,newcombi,pn,totalcount, xmod, ymod);																//executes the next loop
			}
			return totalcount;
		}
}
