package test_programs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import test_objects.TSPtestnode;
import yifan_toolkit.AlgoKit;
import yifan_toolkit.MathKit;
import yifan_toolkit.UtilityKit;

public class TSPBruteForceControl {

	public static void run() throws FileNotFoundException{
		String data = UtilityKit.readfile("test.txt");
		
		List<String> rows = UtilityKit.stringtolistbydelimiter(data, "\n");
		List<TSPtestnode> nodes = new ArrayList<TSPtestnode>();
		
		//Create nodes, add value to them and add nodes to list
		for(int i=0;i<rows.size();i++){
			List<String> dump = UtilityKit.stringtolistbydelimiter(rows.get(i), " ");
			TSPtestnode node = new TSPtestnode(i,Double.valueOf(dump.get(1)),Double.valueOf(dump.get(2)));
			nodes.add(node);
		}
		
		//Remove dups
		for(int i=0;i<nodes.size();i++){
			for(int j=i+1;j<nodes.size();j++){
				if((nodes.get(i).getX()==nodes.get(j).getX()) &&
				  (nodes.get(i).getY()==nodes.get(j).getY())
				  ){
					nodes.remove(j);
					j--;
				}
			}
		}
		
		@SuppressWarnings("unchecked")
		List<List<TSPtestnode>> allpossibleroutes = AlgoKit.permutations(nodes.size(), nodes);
		double mindist = 999999999;
		List<TSPtestnode> bestroute = new ArrayList<TSPtestnode>();
		
		for(int i=0;i<allpossibleroutes.size();i++){
			double routedist = 0;
			List<TSPtestnode> route = allpossibleroutes.get(i);
			for(int n=0;n<route.size()-1;n++){
				TSPtestnode n1 = route.get(n);
				TSPtestnode n2 = route.get(n+1);
				routedist = routedist + MathKit.DoubleTwoDEuclideanDist(n1.getX(), n1.getY(), n2.getX(), n2.getY());
			}
			//Adds dist from last node back to first node
			routedist = routedist + MathKit.DoubleTwoDEuclideanDist(route.get(0).getX(), route.get(0).getY(), route.get(route.size()-1).getX(), route.get(route.size()-1).getY());
			if(routedist<mindist){
				mindist = routedist;
				bestroute = allpossibleroutes.get(i);
			}
		}
		
		System.out.println(mindist);
		System.out.println(bestroute);
	}
	
}
