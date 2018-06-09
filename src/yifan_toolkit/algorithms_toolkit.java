package yifan_toolkit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import test_objects.memoize;
import test_objects.node;
import test_objects.object1;

/**
 * Java scripting tools, catered for algorithms
 * Gentle reminder: Code logic should always be simple and easy to read! No complicated logic is allowed in this class file!
 * This is to ensure that code logic can be easily understood, and easily transferred to a new language
 * This class contains
 * 
 * Optimization Algorithms
 * 1. Dual parent Genetic algorithm, catered for permutation optimization with gene repetition (geneticalgorithmpermuation)
 * 2. Single parent Genetic algorithm, catered for permutation optimization without gene repetition (geneticalgorithmsolopermuation)
 * 3. Search tree to optimize multiplication with Dynamic Programming using memoize (CombinationalMultiplicationOptimizationDynamicProgramming) clearmemoizelist() can be used to clear hashmap before using this algorithm
 * 4. Search tree to optimize multiplication for a limited number of products, with Dynamic Programming using memoize (CombinationalMultiplicationOptimizationLimitedElementsDynamicProgramming)
 * 5. Single parent Genetic algorithm, catered for combination optimization without gene repetition (geneticalgorithmcombination)
 *
 * Sorting Algorithms
 * 1. Bubblesort (bubblesort)
 * 2. Mergesort (mergesort)
 * 
 * Combination and Permutation Algorithms
 * 1. Find all combinations within a set of elements (combinations)
 * 2. Find all combinations within a set of elements, same element can be repeated (combinationswithrepeat)
 * 3. Find all combinations within a set of elements, while excluding repeated combinations due to repeated elements (combinationsnorepeat)
 * 4. Find all permutations within a set of elements (permutations)
 * 5. Find all permutations within a set of elements, while excluding repeated elements (permutationsnorepeat)
 * 
 * Path Finding
 * 1. Given a specified distance, find all possible routes from start point to end point (numberofpaths)
 * @author weiyifan
 */

public class algorithms_toolkit {
	
	//Fixed variables, DO NOT TOUCH THESE EXCEPT FOR DEBUGGING
	static math_toolkit k1 = new math_toolkit();
	static utility_toolkit k2 = new utility_toolkit();
	static HashMap <BigInteger, BigInteger> memoizelist = new HashMap <BigInteger, BigInteger>();
	@SuppressWarnings("rawtypes")
	static HashMap <String, List> permutelist = new HashMap <String, List>();
	@SuppressWarnings("rawtypes")
	static HashMap <String, List> combilist = new HashMap <String, List>();
	static memoize memoize = new memoize();
	
	public algorithms_toolkit(){
	}
	
	/**
	 * The standard genetic algorithm with 2 parents
	 * Uses fitnessfunction to calculate fitness level of solution
	 * You need to write your own code for: The object(object1) involved and the fitnessfunction
	 * Optimizes permutation
	 * @param l: The list of items to be optimized
	 * @return List of items representing the near optimal solution
	 * @Runtime O(N1 X N2), N1 and N2 are separate input values, N1 for population and N2 for generation
	 */
	@SuppressWarnings("unchecked")
	public static List<object1> geneticalgorithmpermuation(List<object1> l){
		
		int population = 50; //CHANGABLE INPUT VALUE: to increase or decrease population
		int generation = 1000; //CHANGABLE INPUT VALUE: to increase or decrease the number of generations
		List<List<object1>> piggies = new ArrayList<List<object1>>();
		
		for(int i = 0;i<population;i++){	//create random solutions based on population size
			Collections.shuffle(l);
			List<object1> newpig = ((List<object1>) ((ArrayList<object1>) l).clone());
			piggies.add(newpig);
		}
		
		//Advance the population based on the number of generations
		for(int g = 0;g<generation;g++){
			
			piggies = Supportfunction.geneticalgorithmmergesort(piggies);	//Sort solutions in descending order		
		
			int mate = population/2;	//selects half of the strongest solutions
		
			for(int i = population-1;i>=mate;i--){	//removes weak solutions
				piggies.remove(i);
			}
		
			for(int i = 0;i<((mate+1)/2);i++){	//gives birth to child and add to population
			
				//Parents
				int parentindex1 = i;
				int parentindex2 = mate-1-i;
				List<object1> parentpig1 = ((List<object1>) ((ArrayList<object1>) piggies.get(parentindex1)).clone());
				List<object1> parentpig2 = ((List<object1>) ((ArrayList<object1>) piggies.get(parentindex2)).clone());
			
				for(int n = 0;n<2;n++){		//Each pair of parents give birth twice
					//Child
					int pivot = k2.randomnumbergenerator(l.size()-1, 0);
					List<object1> childpig = new ArrayList<object1>();
			
					//Inherit from first parent
					for(int j = 0;j<pivot;j++){
						childpig.add(parentpig1.get(j));
					}
			
					//Inherit from second parent
					for(int k = pivot;k<l.size();k++){
						childpig.add(parentpig2.get(k));
					}
			
					//child mutation
					int mutategeneindex1 = k2.randomnumbergenerator(childpig.size()-1, 0);
					int mutategeneindex2 = k2.randomnumbergenerator(childpig.size()-1, 0);
					object1 mutategene1 = childpig.get(mutategeneindex1);
					object1 mutategene2 = childpig.get(mutategeneindex2);
					childpig.set(mutategeneindex1, mutategene2);
					childpig.set(mutategeneindex2, mutategene1);
			
					//Add child to population
					piggies.add(childpig);
				}
			}
		}
		return piggies.get(0);
	}
	
	
	/**
	 * Genetic algorithm with only 1 parent
	 * Useful for problems such as travelling salesman, where genetics of second parent cannot be directly copied to child, which will result in data corruption
	 * Uses fitnessfunction to calculate fitness level of solution
	 * You need to write your own code for: The object(object1) involved and the fitnessfunction
	 * Optimizes permutation
	 * @param l: The list of items to be optimized
	 * @return List of items representing the near optimal solution
	 * @Runtime O(N1 X N2), N1 and N2 are separate input values, N1 for population and N2 for generation
	 */
	public List<object1> geneticalgorithmsolopermuation(List<object1> l){
		
		int population = 100; //CHANGABLE INPUT VALUE: to increase or decrease population
		int generation = 10000; //CHANGABLE INPUT VALUE: to increase or decrease the number of generations
		List<List<object1>> piggies = new ArrayList<List<object1>>();
		
		for(int i = 0;i<population;i++){	//create random solutions based on population size
			Collections.shuffle(l);
			@SuppressWarnings("unchecked")
			List<object1> newpig = ((List<object1>) ((ArrayList<object1>) l).clone());
			piggies.add(newpig);
		}
		
		//Advance the population based on the number of generations
		for(int g = 0;g<generation;g++){
			
			piggies = Supportfunction.geneticalgorithmmergesort(piggies);	//Sort solutions in descending order		
		
			int mate = population/2;	//selects half of the strongest solutions
		
			for(int i = population-1;i>=mate;i--){	//removes weak solutions
				piggies.remove(i);
			}
		
			for(int i = 0;i<mate;i++){	//gives birth to child and add to population
			
				//Parents
				int parentindex = i;
				@SuppressWarnings("unchecked")
				List<object1> parentpig = ((List<object1>) ((ArrayList<object1>) piggies.get(parentindex)  ).clone());;
			
				//Child
				int pivot = k2.randomnumbergenerator(l.size()-1, 0);
				List<object1> childpig = new ArrayList<object1>();
			
				//Inherit from parent
				for(int j = 0;j<=pivot;j++){
					childpig.add(parentpig.get(j));
				}
			
				//Randomize the rest of parent genes, ONLY PORTION OF CODE DIFFERENT FROM DUAL PARENT GENETIC ALGORITHM
				for(int k = pivot+1;k<l.size();k++){
					int randomindex = k2.randomnumbergenerator(parentpig.size()-1, pivot+1);
					childpig.add(parentpig.get(randomindex));
					parentpig.remove(randomindex);
				}
			
				//child mutation
				int mutategeneindex1 = k2.randomnumbergenerator(childpig.size()-1, 0);
				int mutategeneindex2 = k2.randomnumbergenerator(childpig.size()-1, 0);
				object1 mutategene1 = childpig.get(mutategeneindex1);
				object1 mutategene2 = childpig.get(mutategeneindex2);
				childpig.set(mutategeneindex1, mutategene2);
				childpig.set(mutategeneindex2, mutategene1);
			
				//Add child to population
				piggies.add(childpig);
			}
		}
		return piggies.get(0);
	}
	
	/**
	 * Combinational optimization dynamic programming, catered specifically towards multiplication
	 * @param l: List of factors to be used for multiplication
	 * @param capacity: Value at which product does not exceed
	 * @return Largest product found from a certain combination of factors, and is below capacity value
	 * @Runtime O(nCr)/O(n), O(nCr) is computation complexity for calculating combination, O(n) is complexity reduced from dynamic programming
	 * */
	
	//Initialization
	//Run this function to begin the algorithm. Do not run algorithm using the "recursion" version of the function
	public static BigInteger CombinationalMultiplicationOptimizationDynamicProgramming(List<BigInteger> l, BigInteger capacity){
		return CombinationalMultiplicationOptimizationDynamicProgramming(l, 0, capacity, BigInteger.ONE);
	}
	
	//Recursion
	public static BigInteger CombinationalMultiplicationOptimizationDynamicProgramming(List<BigInteger> l, int index, BigInteger capacity, BigInteger weight){
		if(memoizelist.get(k1.pairingfunction(weight, BigInteger.valueOf(index))) != null){		//Skip computation if results have already been computed during previous recursion
			if(weight.multiply(memoizelist.get(k1.pairingfunction(weight, BigInteger.valueOf(index)))).compareTo(capacity) == 1){	//Skip current index if weight exceeds capacity
				return BigInteger.ONE;
			}
			else{
				return memoizelist.get(k1.pairingfunction(weight, BigInteger.valueOf(index)));
			}
		} 
		else{
			BigInteger result = BigInteger.ZERO;	//Initialization of return value, starting value is not important
			if(index == l.size()){		//Return 1 if end of list reached
				return BigInteger.ONE;
			}
			else if(l.get(index).multiply(weight).compareTo(capacity) == 1){	//Skip current index if weight exceeds capacity
				result = CombinationalMultiplicationOptimizationDynamicProgramming(l,index+1,capacity,weight);
			}
			else{	//return the greater value
				BigInteger dump1 = CombinationalMultiplicationOptimizationDynamicProgramming(l,index+1,capacity, weight);
				BigInteger dump2 = l.get(index).multiply(CombinationalMultiplicationOptimizationDynamicProgramming(l,index+1,capacity, l.get(index).multiply(weight)));
				if(dump1.compareTo(dump2) == 1 || dump1.compareTo(dump2) == 0){
					result = dump1;
				}
				else{
					result = dump2;
				}
				memoizelist.put(k1.pairingfunction(weight, BigInteger.valueOf(index)), result);
			}
			return result;
		}
	}
	
	/**
	 * Combinational optimization dynamic programming, catered specifically towards multiplication
	 * @param l: List of factors to be used for multiplication
	 * @param capacity: Value at which multiplication do not exceed
	 * @param r: Value at which number of factors used for multiplication do not exceed (eg 3=3X4X5)
	 * @return Largest product found from a certain combination of factors, and is below capacity value and is composed of r factors
	 * @Runtime O(nCr)/O(n), O(nCr) is computation complexity for calculating combination, O(n) is complexity reduced from dynamic programming
	 * */
	//Initialization
		//Run this function to begin the algorithm. Do not run algorithm using the "recursion" version of the function
		public static BigInteger CombinationalMultiplicationOptimizationLimitedElementsDynamicProgramming(List<BigInteger> l, BigInteger capacity, int r){
			memoizelist.clear();
			return CombinationalMultiplicationOptimizationLimitedElementsDynamicProgramming(l, 0, capacity, BigInteger.ONE, r, 0);
		}
		
		//Recursion
		public static BigInteger CombinationalMultiplicationOptimizationLimitedElementsDynamicProgramming(List<BigInteger> l, int index, BigInteger capacity, BigInteger weight, int r, int numberofelements){
			if(memoizelist.get(k1.pairingfunction(weight, BigInteger.valueOf(index))) != null){		//Skip computation if results have already been computed during previous recursion
				if(weight.multiply(memoizelist.get(k1.pairingfunction(weight, BigInteger.valueOf(index)))).compareTo(capacity) == 1){	//Skip current index if weight exceeds capacity
					return BigInteger.ONE;
				}
				else{
					return memoizelist.get(k1.pairingfunction(weight, BigInteger.valueOf(index)));
				}
			} 
			else{
				BigInteger result = BigInteger.ZERO;	//Initialization of return value, starting value is not important
				if(index == l.size()){		//Return 1 if end of list reached
					return BigInteger.ONE;
				}
				else if(numberofelements >= r){
					return BigInteger.ONE;  //Return 1 if maximum number of elements in combination is reached
				}
				else if(l.get(index).multiply(weight).compareTo(capacity) == 1){	//Skip current index if weight exceeds capacity
					result = CombinationalMultiplicationOptimizationLimitedElementsDynamicProgramming(l,index+1,capacity,weight, r, numberofelements);
				}
				else{	//return the greater value
					BigInteger dump1 = CombinationalMultiplicationOptimizationLimitedElementsDynamicProgramming(l,index+1,capacity, weight, r, numberofelements);													//Does not pick current element in list
					BigInteger dump2 = l.get(index).multiply(CombinationalMultiplicationOptimizationLimitedElementsDynamicProgramming(l,index+1,capacity, l.get(index).multiply(weight), r, numberofelements+1));	//Pick current element in list
					if(dump1.compareTo(dump2) == 1 || dump1.compareTo(dump2) == 0){
						result = dump1;
					}
					else{
						result = dump2;
					}
					memoizelist.put(k1.pairingfunction(weight, BigInteger.valueOf(index)), result);
				}
				return result;
			}
		}
	
	/**
	 * Genetic algorithm with 1 parent
	 * Can use runthroughallnumberofelementsforgeneticalgorithmcombination to test through all possible number of elements in combination to find best combination
	 * Uses fitnessfunction to calculate fitness level of solution
	 * Uses identifyuniquegene to find new gene for mutation
	 * Optimizes combination
	 * @Runtime O(N1 X N2), N1 and N2 are separate input values, N1 for population and N2 for generation
	 */
	@SuppressWarnings("unchecked")
	public static List<object1> geneticalgorithmcombination(List<object1> l, int e){
			
		int population = 200; //CHANGABLE INPUT VALUE: to increase or decrease population
		int generation = 3000; //CHANGABLE INPUT VALUE: to increase or decrease the number of generations
		int mutations = 5; //CHANGABLE INPUT VALUE: to increase or decrease the number of mutations
		
		List<List<object1>> piggies = new ArrayList<List<object1>>();
			
		for(int i = 0;i<population;i++){	//create random solutions based on population size
			Collections.shuffle(l);
			List<object1> dump = ((List<object1>) ((ArrayList<object1>) l).clone());
			List<object1> newpig = new ArrayList<object1>();
			for(int j = 0;j<e;j++){
				newpig.add(dump.get(j));
			}
			piggies.add(newpig);
		}
			
		//Advance the population based on the number of generations
		for(int g = 0;g<generation;g++){
			
			piggies = Supportfunction.geneticalgorithmmergesort(piggies);	//Sort solutions in descending order	
			
			int mate = population/2;	//selects half of the strongest solutions

			for(int i = population-1;i>=mate;i--){	//removes weak solutions
				piggies.remove(i);
			}
			
			for(int i = 0;i<mate;i++){	//gives birth to child and add to population
				
				//Parents
				int parentindex = i;
				List<object1> parentpig = ((List<object1>) ((ArrayList<object1>) piggies.get(parentindex)).clone());
				
				//Child
				int pivot = k2.randomnumbergenerator(e-1, 0);
				List<object1> childpig = new ArrayList<object1>();
				
				//Inherit from parent
				for(int j = 0;j<=pivot;j++){
					childpig.add(parentpig.get(j));
				}
				
				//Randomize the rest of parent genes
				Collections.shuffle(l);
				List<object1> dump = ((List<object1>) ((ArrayList<object1>) l).clone());
				for(int k = pivot+1;k<e;k++){
					object1 birthgene = Supportfunction.identifyuniquegene(childpig,dump);
					childpig.add(birthgene);
				}

				//child mutation 
				for(int m=0;m<mutations;m++){
					int mutategeneindex = k2.randomnumbergenerator(childpig.size()-1, 0);
					Collections.shuffle(dump);
					if(Supportfunction.identifyuniquegene(childpig,dump) != null){		//Only assign the unique gene if it is not equals to null. Equals to null means that number of elements in combination = total number of elements in scope, which is a pointless computation
						object1 mutategene = Supportfunction.identifyuniquegene(childpig,dump);
						childpig.set(mutategeneindex, mutategene);
					}
				}

				//Add child to population
				piggies.add(childpig);
			}
		}
		return piggies.get(0);
	}
	
	
	/**
	 * Works with geneticalgorithmcombination to run through different numbers of elements for combination
	 * Params: l is the full list of elements, n is the maximum number of elements to be used for the combination
	 * @Runtime O(N)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<object1> runthroughallnumberofelementsforgeneticalgorithmcombination(List l, int minrange, int maxrange){
		List<object1> bestsolution = new ArrayList<object1>();
		for(int i = minrange; i<=maxrange;i++){
			List<object1> newsolution = geneticalgorithmcombination(l, i);
			if(Supportfunction.fitnessfunction(newsolution).compareTo(Supportfunction.fitnessfunction(bestsolution)) == 1){		//Set the new solution as best solution if it is better
				bestsolution = ((List<object1>) ((ArrayList<object1>) newsolution).clone());
			}
		}
		return bestsolution;
	}
	
	/**
	 * Bubblesort
	 * This implementation is only applicable for BigIntegers. Code has to be edited to cater to other data types 
	 * Currently in: Descending order
	 * Changes necessary to change from descending to ascending order, or vice vesa is stated in comments of the algorithm
	 * @Runtime O(N^2)
	 */
	
	public static List<BigInteger> bubblesort(List<BigInteger> l){
		for(int i = 1;i<l.size();i++){	
			for(int j = 0;j<l.size()-i;j++){
				BigInteger extract1 = l.get(j);
				BigInteger extract2 = l.get(j+1);
				if(extract1.compareTo(extract2) == -1){		//-1 for descending order, 1 for ascending order
					l.set(j, extract2);
					l.set(j+1, extract1);
				}
			}
		}
		//Sort the last element in place
		BigInteger extract1 = l.get(0);
		BigInteger extract2 = l.get(1);
		if(extract1.compareTo(extract2) == -1){		//-1 for descending order, 1 for ascending order
			l.set(0, extract2);
			l.set(1, extract1);
		}
		return l;
	}
	
	/**
	 * Mergesort
	 * This implementation is only applicable for BigIntegers. Code has to be edited to cater to other data types 
	 * Currently in: Ascending order
	 * Changes necessary to change from descending to ascending order, or vice vesa is stated in comments of the algorithm
	 * @Runtime O(NLogN)
	 */
	public static List<BigInteger> mergesort(List<BigInteger> l) {
		if(l.size()>1){
			List<BigInteger> fronthalf = mergesort(l.subList(0, ((l.size()-1)/2)+1));
			List<BigInteger> backhalf = mergesort(l.subList((l.size()+1)/2, l.size()));
			
			int fronthalfindex = 0;
			int backhalfindex = 0;
			int fronthalfindexend = fronthalf.size()-1;
			int backhalfindexend = backhalf.size()-1;
			
			List<BigInteger> combinedlist = new ArrayList<BigInteger>();
			
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
				else if(fronthalf.get(fronthalfindex).compareTo(backhalf.get(backhalfindex)) == -1 || //Change -1 to 1 for descending order
						fronthalf.get(fronthalfindex).compareTo(backhalf.get(backhalfindex)) == 0){	//if element in fronthalf list is smaller than or same as element in backhalf list
					combinedlist.add(fronthalf.get(fronthalfindex));
					fronthalfindex++;
				}
				else if(fronthalf.get(fronthalfindex).compareTo(backhalf.get(backhalfindex)) == 1){	//if element in fronthalf list is bigger than element in backhalf list. Change 1 to -1 for descending order
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
	 * The logic of this code is based entirely on the naive, iterative method of finding all combinations of a set, by using loop within a loop
	 * @param index: Starting element of new loop
	 * @param r: Number of elements in combination
	 * @param listofelements: All elements of set
	 * @param combi: A unique combination
	 * @param allcombi: The full list of all combinations
	 * @return
	 * @Runtime O(nCr), nCr is the formula for calculating combination
	 */
	//Initiation
	@SuppressWarnings("rawtypes")
	public static List combinations(int r, List listofelements){
		List dump1 = new ArrayList();
		List dump2 = new ArrayList();
		List allcombi = combinations(0,r,listofelements,dump1,dump2);
		return allcombi;
	}
	//Recursion
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List combinations(int index, int r, List listofelements, List combi, List allcombi){
		
		//Base case: last element of the subset
		if(r==1){	
			for(int i=index;i<listofelements.size()-r+1;i++){			//Iterate through each element within current loop
				List newcombi = ((List) ((ArrayList) combi).clone());	//creates a new subset based on the subset within the current loop
				newcombi.add(listofelements.get(i));					//add new element of current loop to subset
				allcombi.add(newcombi);									//add completed subset to list of combinations
			}
			return allcombi;
		}
		
		//Each recursion behaves like a loop of its own, and adds the element within its own loop before opening the next loop
		for(int i=index;i<listofelements.size()-r+1;i++){							//Iterate through each element within current loop
			List newcombi = ((List) ((ArrayList) combi).clone());					//creates a new subset based on the subset within the current loop
			newcombi.add(listofelements.get(i));									//add new element of current loop to subset
			allcombi = combinations(i+1,r-1,listofelements,newcombi,allcombi);		//executes the next loop
		}
		return allcombi;
	}
	
	/**
	 * The logic of this code is based entirely on the naive, iterative method of finding all combinations of a set, by using loop within a loop
	 * @param index: Starting element of new loop
	 * @param r: Number of elements in combination
	 * @param listofelements: All elements of set
	 * @param combi: A unique combination
	 * @param allcombi: The full list of all combinations
	 * @return
	 * @Runtime O(nCr), nCr is the formula for calculating combination
	 */
	//Initiation
	@SuppressWarnings("rawtypes")
	public static List combinationswithrepeat(int r, List listofelements){
		List dump1 = new ArrayList();
		List dump2 = new ArrayList();
		List allcombi = combinationswithrepeat(0,r,listofelements,dump1,dump2);
		return allcombi;
	}
	//Recursion
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List combinationswithrepeat(int index, int r, List listofelements, List combi, List allcombi){
		
		//Base case: last element of the subset
		if(r==1){	
			for(int i=index;i<listofelements.size();i++){			//Iterate through each element within current loop
				List newcombi = ((List) ((ArrayList) combi).clone());	//creates a new subset based on the subset within the current loop
				newcombi.add(listofelements.get(i));					//add new element of current loop to subset
				allcombi.add(newcombi);									//add completed subset to list of combinations
			}
			return allcombi;
		}
		
		//Each recursion behaves like a loop of its own, and adds the element within its own loop before opening the next loop
		for(int i=index;i<listofelements.size();i++){							//Iterate through each element within current loop
			List newcombi = ((List) ((ArrayList) combi).clone());					//creates a new subset based on the subset within the current loop
			newcombi.add(listofelements.get(i));									//add new element of current loop to subset
			allcombi = combinationswithrepeat(i,r-1,listofelements,newcombi,allcombi);		//executes the next loop
		}
		return allcombi;
	}
	
	/**
	 * The logic of this code is based entirely on the naive, iterative method of finding all combinations of a set, by using loop within a loop
	 * Borrows concept from dynamic programming to exclude repeated combinations
	 * NOTE: This function requires the objects (of the combinations) to have a toString function that identifies its value in order to work.
	 * @param index: Starting element of new loop
	 * @param r: Number of elements in combination
	 * @param listofelements: All elements of set
	 * @param combi: A unique combination
	 * @param allcombi: The full list of all combinations
	 * @return
	 * @Runtime O(nCr), nCr is the formula for calculating combination
	 */
	//Initiation
	@SuppressWarnings("rawtypes")
	public static List combinationsnorepeat(int r, List listofelements){
		combilist.clear();
		List dump1 = new ArrayList();
		List dump2 = new ArrayList();
		List allcombi = combinationsnorepeat(0,r,listofelements,dump1,dump2);
		return allcombi;
	}
	//Recursion
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List combinationsnorepeat(int index, int r, List listofelements, List combi, List allcombi){
		
		//Base case: last element of the subset
		if(r==1){	
			for(int i=index;i<listofelements.size()-r+1;i++){			//Iterate through each element within current loop
				List newcombi = ((List) ((ArrayList) combi).clone());	//creates a new subset based on the subset within the current loop
				newcombi.add(listofelements.get(i));					//add new element of current loop to subset
				
				//Convert permutation to string
				String key = "";
				for(int n=0;n<newcombi.size();n++){
					key = key + newcombi.get(n);
				}
				//Add permutation to list if it does not yet exist
				if(combilist.get(key) == null){
					allcombi.add(newcombi);									//add completed subset to list of permutations
					combilist.put(key, newcombi);
				}
			}
			return allcombi;
		}
		
		//Each recursion behaves like a loop of its own, and adds the element within its own loop before opening the next loop
		for(int i=index;i<listofelements.size()-r+1;i++){							//Iterate through each element within current loop
			List newcombi = ((List) ((ArrayList) combi).clone());					//creates a new subset based on the subset within the current loop
			newcombi.add(listofelements.get(i));									//add new element of current loop to subset
			allcombi = combinationsnorepeat(i+1,r-1,listofelements,newcombi,allcombi);		//executes the next loop
		}
		return allcombi;
	}
	
	/**
	 * The logic of this code is as follows:
	 * 1. Level of recursion will dictate the position of element in subset. Eg. Initial call = index 0, 1st recurse = index 1, ..., last recurse = index n-1 and index n
	 * 2. Each recursion will receive a sublist of the whole list of elements. The sublist does not contain any elements that has already been added to the subset in previous call
	 * 3. Each recursion then adds its own element from the sublist to its given subset and pass it down to the next recursion. Each recursion will repeat this process of adding elements to new subsets until all elements within current sublist has been added.
	 * 4. Base case is reached when only 2 elements are left in received subset. Function will then iteratively duplicate received subset and add all unadded elements into duplicated subset as last 2 elements and add them to grand list of permutations
	 * @Runtime O(nPr), nPr is the formula for calculating permutation
	 */
	//Initiation
	@SuppressWarnings("rawtypes")
	public static List permutations(int r, List listofelements){
		List dump1 = new ArrayList();
		List dump2 = new ArrayList();
		int loops = listofelements.size()-r;
		List allpermu = permutations(r,listofelements,dump1,dump2,loops);
		return allpermu;
	}
	//Recursion
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List permutations(int r, List listofelements, List permu, List allpermu, int loops){
		
		//Base case: last 2 elements of the subset
		if(r==2){				
			for(int i=0;i<listofelements.size()-1;i++){		//This dual loop adds every single possible permutation of 2 elements to the subset
				for(int j=i+1;j<listofelements.size();j++){
				
				List newpermu = ((List) ((ArrayList) permu).clone());	
				newpermu.add(listofelements.get(i));					
				newpermu.add(listofelements.get(j));
				allpermu.add(newpermu);									//add completed subset to list of permutations
				
				List newpermutwo = ((List) ((ArrayList) permu).clone());	
				newpermutwo.add(listofelements.get(j));					
				newpermutwo.add(listofelements.get(i));
				allpermu.add(newpermutwo);									//add completed subset to list of permutations
				}
			}
			return allpermu;
		}
		
		//Each recursion behaves like a loop of its own, and adds the element within its own loop before opening the next loop
		for(int m=0;m<=loops;m++){		//Value of m is used to help with hopping to next index within current loop that has yet to be permuted
			for(int i=0;i<r;i++){
				List newpermu = ((List) ((ArrayList) permu).clone());
				List iteratedlistofelements = ((List) ((ArrayList) listofelements).clone());
				if(i+(m*r)>=iteratedlistofelements.size()){		//End loop if index exceeded size of sublist
					return allpermu;
				}
				newpermu.add(iteratedlistofelements.remove(i+(m*r)));	//Add element of current loop to subset
				allpermu = permutations(r-1,iteratedlistofelements,newpermu,allpermu,loops);		//executes the next loop
			}
		}
		return allpermu;
	}
	
	/**
	 * The logic of this code is as follows:
	 * 1. Level of recursion will dictate the position of element in subset. Eg. Initial call = index 0, 1st recurse = index 1, ..., last recurse = index n-1 and index n
	 * 2. Each recursion will receive a sublist of the whole list of elements. The sublist does not contain any elements that has already been added to the subset in previous call
	 * 3. Each recursion then adds its own element from the sublist to its given subset and pass it down to the next recursion. Each recursion will repeat this process of adding elements to new subsets until all elements within current sublist has been added.
	 * 4. Base case is reached when only 2 elements are left in received subset. Function will then iteratively duplicate received subset and add all unadded elements into duplicated subset as last 2 elements and add them to grand list of permutations
	 * NOTE: This function requires the objects (of the permutations) to have a toString function that identifies its value in order to work.
	 * @Runtime O(nPr), nPr is the formula for calculating permutation
	 */
	//Initiation
	@SuppressWarnings("rawtypes")
	public static List permutationsnorepeat(int r, List listofelements){
		permutelist.clear();
		List dump1 = new ArrayList();
		List dump2 = new ArrayList();
		int loops = listofelements.size()-r;
		List allpermu = permutationsnorepeat(r,listofelements,dump1,dump2,loops);
		return allpermu;
	}
	//Recursion
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List permutationsnorepeat(int r, List listofelements, List permu, List allpermu, int loops){
		
		//Base case: last 2 elements of the subset
		if(r==2){				
			for(int i=0;i<listofelements.size()-1;i++){		//This dual loop adds every single possible permutation of 2 elements to the subset
				for(int j=i+1;j<listofelements.size();j++){
				
					List newpermu = ((List) ((ArrayList) permu).clone());	
					newpermu.add(listofelements.get(i));					
					newpermu.add(listofelements.get(j));
				
					//Convert permutation to string
					String key = "";
					for(int n=0;n<newpermu.size();n++){
						key = key + newpermu.get(n);
					}
					//Add permutation to list if it does not yet exist
					if(permutelist.get(key) == null){
						allpermu.add(newpermu);									//add completed subset to list of permutations
						permutelist.put(key, newpermu);
					}
				
					List newpermutwo = ((List) ((ArrayList) permu).clone());	
					newpermutwo.add(listofelements.get(j));					
					newpermutwo.add(listofelements.get(i));

					//Convert permutation to string
					key = "";
					for(int n=0;n<newpermutwo.size();n++){
						key = key + newpermutwo.get(n);
					}
					//Add permutation to list if it does not yet exist
					if(permutelist.get(key) == null){
						allpermu.add(newpermutwo);									//add completed subset to list of permutations
						permutelist.put(key, newpermutwo);
					}
				}
			}
			return allpermu;
		}
		
		//Each recursion behaves like a loop of its own, and adds the element within its own loop before opening the next loop
		for(int m=0;m<=loops;m++){		//Value of m is used to help with hopping to next index within current loop that has yet to be permuted
			for(int i=0;i<r;i++){
				List newpermu = ((List) ((ArrayList) permu).clone());
				List iteratedlistofelements = ((List) ((ArrayList) listofelements).clone());
				if(i+(m*r)>=iteratedlistofelements.size()){		//End loop if index exceeded size of sublist
					return allpermu;
				}
				newpermu.add(iteratedlistofelements.remove(i+(m*r)));	//Add element of current loop to subset
				allpermu = permutationsnorepeat(r-1,iteratedlistofelements,newpermu,allpermu,loops);		//executes the next loop
			}
		}
		return allpermu;
	}
	
	/**
	 * Given a defined distance, counts the number of possible routes from start to end
	 * Please use the initialization function to start the algorithm. Recursive function is strictly for recursion only.
	 * This algorithm uses the node.java object found in objects package.
	 * @param start: node which defines the starting point
	 * @param end: nodes which defines the ending point
	 * @param distance: The specified distance
	 * @return List of paths that can lead from start to end, given the specified distance
	 * ALERT: Current implementation overloads memory resources if number of nodes > 22, this is due to the creating of excessive pointers and objects. Interesting fact: Runtime for node<=22 and node>22 largely differs, its as if a lot of computing power is wasted on garbage cleaning by the compiler
	 * ALERT: Directional search has been set to true by default. Please develop path-finding algorithm to be used in conjunction with the activation of directional search. This is because with directional search switched on algorithm cannot deal with obstacles along the way.
	 */
	//Initialization function
	public static List<List<node>> numberofpaths(node start, node end, BigDecimal distance){
		memoize.setMemoizelist(new HashMap<String, List<node>>());			//clears memoize list for use later
		List<node> beginpath = new ArrayList<node>();
		beginpath.add(start);
		List<List<node>> dumptwo = new ArrayList<List<node>>();
		return numberofpaths(start, end, distance, start, beginpath, dumptwo, true);
	}
	/**
	 * Description of params for recursive function
	 * @param currentnode: current node at which the search engine is on
	 * @param candidatepath: the path being tested
	 * @param allpaths: paths that have passed the test
	 * @param directionalsearch: search according to direction to prevent exhaustive search
	 * @return List of paths that can lead from start to end, given the specified distance
	 */
	//Recursive function
	public static List<List<node>> numberofpaths(node start, node end, BigDecimal distance, node currentnode, List<node> candidatepath, List<List<node>> allpaths, Boolean directionalsearch){

		//Path taken has overshot specified distance
		if(distance.compareTo(BigDecimal.ZERO)==-1){
			return allpaths;
		}
		//Base case. Path taken = distance specified
		if(distance.compareTo(BigDecimal.ZERO)==0){
			boolean endreached = false;
			for(int i=0;i<candidatepath.size();i++){	//Checks if end of path has been reached
				if(candidatepath.get(i).toString().compareTo(end.toString())==0){
					endreached = true;
				}
			}
			if(endreached){	
				allpaths.add(candidatepath);
				return allpaths;
			}
			else{
				return allpaths;
			}
		}
		
		//Avoids recursion if computation has already been performed for the current node
		if(memoize.getMemoizelist().get(currentnode.toString()+distance)!=null){
			@SuppressWarnings("unchecked")
			List<List<node>> subpaths = (List<List<node>>) memoize.getMemoizelist().get(currentnode.toString()+distance);	//retrieves list of available paths from current position
			for(int i=0;i<subpaths.size();i++){		//for each available path from next node to end node, add all nodes from starting node to current node to said path, which makes one complete path, which is added to grand list
				@SuppressWarnings("unchecked")
				List<node> dump = ((List<node>) ((ArrayList<node>) candidatepath).clone());
				dump.addAll((Collection<node>) subpaths.get(i));
				allpaths.add(dump);
			}
			return allpaths;
		}
		
		//List for memoizing of values
		List<node> nextnodes = new ArrayList<node>();
		List<BigDecimal> rootpathdistance = new ArrayList<BigDecimal>();
		
		//For each new position in path, the list of new points is scanned all over again
		for(int i=0;i<currentnode.getNeighbournode().size();i++){
			
			node nextnode = currentnode.getNeighbournode().get(i);
			
			/**
			 * This "if" condition checks for constraints and directional search
			 * Any of the following conditions in this "if" statement can be switched off simply by commenting out the line
			 */
			if(
			  ((directionalsearch && nextnode.getX().compareTo(currentnode.getX())!=-1 && nextnode.getY().compareTo(currentnode.getY())!=-1) || !directionalsearch)	//Condition to handle smartsearch. Either smartsearch is on and checks for direction or smartsearch is off
			){
				@SuppressWarnings("unchecked")
				List<node> newcandidatepath = ((List<node>) ((ArrayList<node>) candidatepath).clone());
				newcandidatepath.add(nextnode);
				
				BigDecimal currenttonextpointdistance = k1.twodimensioneuclideandistance(nextnode.getX(), nextnode.getY(), currentnode.getX(), currentnode.getY());	//calculate distance between current point to next point
				BigDecimal leftoverdistance = distance.subtract(currenttonextpointdistance);	//updates the distance travelled up to new point
				
				int size = allpaths.size();
				allpaths = numberofpaths(start, end, leftoverdistance, nextnode, newcandidatepath, allpaths, directionalsearch);	//recurse
				int sizeafter = allpaths.size();
				if((sizeafter-size)>0){	//only memoize paths that can lead to end node
					//memoize, tostring value of node and leftoverdistance will be combined to form the key value
					nextnodes.add(nextnode);
					rootpathdistance.add(leftoverdistance);	
				}
			}
		}
		
		//This part of the code deals with memoization of computed values
		List<List<node>> subpaths = new ArrayList<List<node>>();
		for(int i=0;i<nextnodes.size();i++){		//for every new viable node found that can lead to end point
			@SuppressWarnings("unchecked")
			List<List<node>> subpathroot = (List<List<node>>) memoize.getMemoizelist().get(nextnodes.get(i).toString()+rootpathdistance.get(i));	//retrieve all paths that viable node leads to with the amount of distance left
			if(subpathroot==null){		//no path is found for next node; next node = end point
				List<node> dump = new ArrayList<node>();
				dump.add(nextnodes.get(i));
				subpaths.add(dump);
			}
			else{
				for(int j=0;j<subpathroot.size();j++){		//else add new nodes to all paths that can lead to end point
					@SuppressWarnings("unchecked")
					List<node> dump = ((List<node>) ((ArrayList<node>) subpathroot.get(j)).clone());
					dump.add(0,nextnodes.get(i));		//new node is added to the front of the list, as it nearer to the starting point (logic of code adds items in bottom up fashion, largely due to nature of recursion)
					subpaths.add(dump);
				}
			}
		}
		//Once all new nodes have been updated with their possible paths, memoize current node and its paths, and return the list of found paths
		memoize.addkeyvaluepair(currentnode.toString()+distance, subpaths);
		return allpaths;
	}
	
	//---------------------------------------------------------TEST ZONE-------------------------------------------------------------------------
	//-------------------------------------------------------END OF TEST ZONE--------------------------------------------------------------------
}
