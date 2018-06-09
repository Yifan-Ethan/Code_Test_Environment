package yifan_toolkit;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import test_objects.object1;

/**
 * Support functions designed to run all other functions within the toolkit package
 * Each of these functions might need to be changed according to the problem
 * None of the functions in this class is meant to be run by itself
 * Gentle reminder: Code logic should always be simple and easy to read! No complicated logic is allowed in this class file!
 * 
 * @author weiyifan
 *
 */
public class Supportfunction {
	
	public Supportfunction(){
	}
	
	/**
	 * The following variables and functions are all problem specific. They may be removed after problem is solved
	 * Safety zone dictates the code that is safe to be removed after problem solving
	 */
	//-----------------Safety zone starts here. Safe to make edits.------------------------------
	static BigInteger rootn = new BigInteger("0");
	public void setrootn(BigInteger bigint){
		rootn = bigint;
	}
	//-----------------Safety zone ends here. Do not touch anything else.------------------------
	
	/**
	 * Genetic Algorithm specific mergesort algorithm.
	 * This algorithm applies to all implementations of genetic algorithm. No editting is necessary to cater to different versions of implementation
	 */
	public static List<List<object1>> geneticalgorithmmergesort(List<List<object1>> l) {
		if(l.size()>1){
			List<List<object1>> fronthalf = geneticalgorithmmergesort(l.subList(0, ((l.size()-1)/2)+1));
			List<List<object1>> backhalf = geneticalgorithmmergesort(l.subList((l.size()+1)/2, l.size()));
			
			int fronthalfindex = 0;
			int backhalfindex = 0;
			int fronthalfindexend = fronthalf.size()-1;
			int backhalfindexend = backhalf.size()-1;
			
			List<List<object1>> combinedlist = new ArrayList<List<object1>>();
			
			while(true){
				if(fronthalfindex > fronthalfindexend){		//if end of fronthalf list is reached
					if(backhalfindexend>=0){
						combinedlist.addAll(backhalf.subList(backhalfindex, backhalfindexend+1));
						return combinedlist;
					}
					else{
						return combinedlist;
					}
				}
				else if(backhalfindex > backhalfindexend){		//if end of backhalf list is reached
					if(fronthalfindexend>=0){
						combinedlist.addAll(fronthalf.subList(fronthalfindex, fronthalfindexend+1));
						return combinedlist;
					}
					else{
						return combinedlist;
					}
				}
				else if(fitnessfunction(fronthalf.get(fronthalfindex)).compareTo(fitnessfunction(backhalf.get(backhalfindex))) == 1 || 
						fitnessfunction(fronthalf.get(fronthalfindex)).compareTo(fitnessfunction(backhalf.get(backhalfindex))) == 0){	//if element in fronthalf list is smaller than or same as element in backhalf list
					combinedlist.add(fronthalf.get(fronthalfindex));
					fronthalfindex++;
				}
				else if(fitnessfunction(fronthalf.get(fronthalfindex)).compareTo(fitnessfunction(backhalf.get(backhalfindex))) == -1){	//if element in fronthalf list is bigger than element in backhalf list. Change 1 to -1 for descending order
					combinedlist.add(backhalf.get(backhalfindex));
					backhalfindex++;
				}
			}
		}
		else{	//returns the list if it has only 1 element
			return l;
		}
	}
	
	/**
	 * Function to identify an unique object in genetic algorithm
	 * Code might need to be changed according to the problem
	 * Works with geneticalgorithmcombination to extract unique gene from list of discreet objects
	 */
	public static object1 identifyuniquegene(List<object1> childpig, List<object1> fullgenelist){
		
		HashMap <BigInteger, object1> childpigmap = new HashMap <BigInteger, object1>();
		HashMap <Integer, BigInteger> fullgenelistmap = new HashMap <Integer, BigInteger>();
		
		//Copies all of child's genes into a map, key is index (like list index) and value is the value of object
		for(int i = 0;i<childpig.size();i++){		
			childpigmap.put(childpig.get(i).geti(), childpig.get(i));
		}
		
		//Copies the entire gene list into a map, key is value of object and value is the object itself
		//Value is later used for returning, and hence implemented this way.
		for(int i = 0;i<fullgenelist.size();i++){	
			fullgenelistmap.put(i, fullgenelist.get(i).geti());
		}
		
		//Checks for unique gene to execute mutation
		for(int i = 0;i<fullgenelistmap.size();i++){
			if(childpigmap.containsKey(fullgenelistmap.get(i))){}//Do nothing and skip to next gene
			else{	//Return the gene if it does not exist in child
				return fullgenelist.get(i);
			}
		}
		return null;
	}
	
	
	/**
	 * Fitnessfunction for algorithms in algorithms_toolkit
	 * Code always need to be changed according to the problem
	 * Fitness function meant to work with both genetic algorithms. Higher fitness value = great solution. Needs to be rewritten with respect to each new problem
	 */
	public static BigInteger fitnessfunction(List<object1> l){
		
		BigInteger result = new BigInteger("1");
		
		for(int i = 0; i<l.size();i++){
			BigInteger dump = l.get(i).geti();
			result = result.multiply(dump);
		}
		
		if(result.compareTo(rootn) == -1){
			return result;
		}
		else{
			return BigInteger.ONE;
		}
	}
}
