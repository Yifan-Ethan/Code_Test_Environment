import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import test_objects.TSPtestnode;
import test_objects.TSPtestpath;
import test_objects.node;
import test_objects.object1;
import yifan_toolkit.AlgoKit;
import yifan_toolkit.MathKit;
import yifan_toolkit.UtilityKit;


public class testground{
	
	public static void main(String[] args) throws IOException {		
		
		String data = UtilityKit.readfile("test.txt");
		
		List<String> rows = UtilityKit.stringtolistbydelimiter(data, "\n");
		List<TSPtestnode> nodes = new ArrayList<TSPtestnode>();
		List<TSPtestpath> paths = new ArrayList<TSPtestpath>();
		HashMap<Integer, TSPtestnode> nodemap = new HashMap<Integer, TSPtestnode>();
		
		int nodescount = 0;
		int totalnodes = 0;
		
		//Create nodes, add value to them and add nodes to list
		for(int i=0;i<rows.size();i++){
			List<String> dump = UtilityKit.stringtolistbydelimiter(rows.get(i), " ");
			TSPtestnode node = new TSPtestnode(i,Double.valueOf(dump.get(1)),Double.valueOf(dump.get(2)));
			nodes.add(node);
		}
		
		totalnodes = nodes.size();
		
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
		
		bruteforcecontrol(nodes);
		
		//Remaps nodes to hashmap for quick access via ID
		/*for(int i=0;i<nodes.size();i++){
			nodemap.put(nodes.get(i).getId(), nodes.get(i));
		}
		
		//Scans for all possible paths between 2 nodes
		for(int i=0;i<nodes.size();i++){
			for(int j=i+1;j<nodes.size();j++){
				TSPtestnode node1 = nodes.get(i);
				TSPtestnode node2 = nodes.get(j);
				TSPtestpath path = new TSPtestpath(node1.getId(), node2.getId(), MathKit.DoubleTwoDEuclideanDist(node1.getX(), node1.getY(), node2.getX(), node2.getY()));
				paths.add(path);
			}
		}
		
		//Sort paths in ascending order
		Collections.sort(paths, new Comparator<TSPtestpath>() {
			public int compare(TSPtestpath o1, TSPtestpath o2) {
				// TODO Auto-generated method stub
				if(o1.getDistance() > o2.getDistance()){
					return 1;
				}
				else if(o1.getDistance() == o2.getDistance()){
					return 0;
				}
				else{
					return -1;
				}
			}
	    });

		int lastnodeid = 0;
		
		//Scans and creates paths using shortest path, eventually creating a local closed circuit
		for(int i=0;i<paths.size();i++){
			int pathnodeID1 = paths.get(i).getNode1ID();
			int pathnodeID2 = paths.get(i).getNode2ID();
			if(CanConnect(nodemap.get(pathnodeID1)) && CanConnect(nodemap.get(pathnodeID2))){
				nodemap = ConnectNodes(pathnodeID1, pathnodeID2, nodemap);
				paths.remove(i);
				i--;	//Since current path was removed, next path takes the index of current path
				boolean stopsearch = circuitclosed(nodemap.get(pathnodeID1));
				if(stopsearch==true){
					i=paths.size();
					lastnodeid = pathnodeID1;
				}
			}
		}
		
		TSPtestnode n = nodemap.get(lastnodeid);
		List<Integer> circuitnodes = nodesincircuit(n);
		nodescount = circuitnodes.size();
		
		while(nodescount!=totalnodes){
		
			//Removes obsolete paths to save computing time
			for(int i=0;i<paths.size();i++){
				for(int k=0;k<circuitnodes.size();k++){
					for(int l=k+1;l<circuitnodes.size();l++){
						if((paths.get(i).getNode1ID()==circuitnodes.get(k) && paths.get(i).getNode2ID()==circuitnodes.get(l)) ||
								(paths.get(i).getNode2ID()==circuitnodes.get(k) && paths.get(i).getNode1ID()==circuitnodes.get(l))	){
							paths.remove(i);
							i--;
							if(i==-1){
								k = circuitnodes.size();
								l = circuitnodes.size();
							}
						}
					}
				}
			}
			
			int pathnature = 0;
			TSPtestnode closestnodetocircuit = new TSPtestnode();
			TSPtestnode closestpointoncircuit = new TSPtestnode();
			
			//Scans for shortest paths with open node closest to circuit
			for(int i=0;i<paths.size();i++){
				for(int k=0; k<circuitnodes.size();k++){
					if(paths.get(i).getNode1ID()==circuitnodes.get(k) || paths.get(i).getNode2ID()==circuitnodes.get(k)){
						if(paths.get(i).getNode1ID()==circuitnodes.get(k)){
							pathnature = PathNature(nodemap.get(paths.get(i).getNode2ID()));
							closestnodetocircuit = nodemap.get(paths.get(i).getNode2ID());
						}
						else if(paths.get(i).getNode2ID()==circuitnodes.get(k)){
							pathnature = PathNature(nodemap.get(paths.get(i).getNode1ID()));
							closestnodetocircuit = nodemap.get(paths.get(i).getNode1ID());
						}
						closestpointoncircuit = nodemap.get(circuitnodes.get(k));
						paths.remove(i);
						i = paths.size();
						k = circuitnodes.size();
					}
				}
			}
			
			//If path points to a node
			if(pathnature == 1){
				TSPtestnode leftnode = closestpointoncircuit.getLink1();
				TSPtestnode rightnode = closestpointoncircuit.getLink2();
				double leftnodedistance = MathKit.DoubleTwoDEuclideanDist(leftnode.getX(), leftnode.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY());
				double rightnodedistance = MathKit.DoubleTwoDEuclideanDist(rightnode.getX(), rightnode.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY());
				if(leftnodedistance<=rightnodedistance){
					nodemap = ResetNodesOnCircuitToNode(leftnode, closestpointoncircuit, closestnodetocircuit, nodemap);
					lastnodeid = closestnodetocircuit.getId();
				}
				else{
					nodemap = ResetNodesOnCircuitToNode(rightnode, closestpointoncircuit, closestnodetocircuit, nodemap);
					lastnodeid = closestnodetocircuit.getId();
				}
			}
			//If path points to a route
			else if(pathnature == 2){
				TSPtestnode routeend = TranverseToOppositeEnd(closestnodetocircuit);
				TSPtestnode leftnode = closestpointoncircuit.getLink1();
				TSPtestnode rightnode = closestpointoncircuit.getLink2();
				double leftnodedistance = MathKit.DoubleTwoDEuclideanDist(leftnode.getX(), leftnode.getY(), routeend.getX(), routeend.getY());
				double rightnodedistance = MathKit.DoubleTwoDEuclideanDist(rightnode.getX(), rightnode.getY(), routeend.getX(), routeend.getY());
				if(leftnodedistance<=rightnodedistance){
					nodemap = ResetNodesOnCircuitToRoute(closestpointoncircuit, leftnode, closestnodetocircuit, routeend, nodemap);
					lastnodeid = closestnodetocircuit.getId();
				}
				else{
					nodemap = ResetNodesOnCircuitToRoute(closestpointoncircuit, rightnode, closestnodetocircuit, routeend, nodemap);
					lastnodeid = closestnodetocircuit.getId();
				}
			}
			
			n = nodemap.get(lastnodeid);
			circuitnodes = nodesincircuit(n);
			nodescount = circuitnodes.size();
		}
		LogCircuit(n);
		System.out.println(CircuitDist(n));*/
	}
	
	/**
	 * Logs nodes in circuit sequentially
	 * @param o
	 * @return
	 */
	public static void LogCircuit(TSPtestnode o){
		int originid = o.getId();
		TSPtestnode previousnode = o.getLink1();
		while(o.TranverseNode(previousnode)!=null){
			
			//Logs node
			System.out.println(o);
			
			TSPtestnode dump = o;	//Saves this node to be assigned to previous node
			o = o.TranverseNode(previousnode);	//Main node moves on to next node
			previousnode = dump;	//Saves previous node, which is needed for main node to proceed to next node in the right direction
			
			int nextid = o.getId();
			if(nextid==originid){
				return;
				
			}
		}
		return;
	}
	
	/**
	 * Resets 2 points on a circuit to a route closest to them
	 * @param cnode1
	 * @param cnode2
	 * @param routestart
	 * @param routeend
	 * @param nodemap
	 * @return
	 */
	public static HashMap<Integer, TSPtestnode> ResetNodesOnCircuitToRoute(TSPtestnode cnode1, TSPtestnode cnode2, TSPtestnode routestart, TSPtestnode routeend, HashMap<Integer, TSPtestnode> nodemap){
		//Set connections for closest node in circuit
		if(cnode2.getLink1().getId() == cnode1.getId()){
			nodemap.get(cnode2.getId()).setLink1(routeend);
		}
		else if(cnode2.getLink2().getId() == cnode1.getId()){
			nodemap.get(cnode2.getId()).setLink2(routeend);
		}
		//Set connections for closest neighbouring node in circuit
		if(cnode1.getLink1().getId() == cnode2.getId()){
			nodemap.get(cnode1.getId()).setLink1(routestart);
		}
		else if(cnode1.getLink2().getId() == cnode2.getId()){
			nodemap.get(cnode1.getId()).setLink2(routestart);
		}
		//Set connections for start node in route
		if(routestart.getLink1() == null){
			nodemap.get(routestart.getId()).setLink1(cnode1);
		}
		else if(routestart.getLink2() == null){
			nodemap.get(routestart.getId()).setLink2(cnode1);
		}
		//Set connections for end node in route
		if(routeend.getLink1() == null){
			nodemap.get(routeend.getId()).setLink1(cnode2);
		}
		else if(routeend.getLink2() == null){
			nodemap.get(routeend.getId()).setLink2(cnode2);
		}
		return nodemap;
	}
	
	/**
	 * Resets 2 points on a circuit to a node closest to them
	 * @param cnode1
	 * @param cnode2
	 * @param newnode
	 * @param nodemap
	 * @return
	 */
	public static HashMap<Integer, TSPtestnode> ResetNodesOnCircuitToNode(TSPtestnode cnode1, TSPtestnode cnode2, TSPtestnode newnode, HashMap<Integer, TSPtestnode> nodemap){
		//Set connections for closest node in circuit
		if(cnode2.getLink1().getId() == cnode1.getId()){
			nodemap.get(cnode2.getId()).setLink1(newnode);
		}
		else if(cnode2.getLink2().getId() == cnode1.getId()){
			nodemap.get(cnode2.getId()).setLink2(newnode);
		}
		//Set connections for closest neighbouring node in circuit
		if(cnode1.getLink1().getId() == cnode2.getId()){
			nodemap.get(cnode1.getId()).setLink1(newnode);
		}
		else if(cnode1.getLink2().getId() == cnode2.getId()){
			nodemap.get(cnode1.getId()).setLink2(newnode);
		}
		//Set connections for new node
		nodemap.get(newnode.getId()).setLink1(cnode1);
		nodemap.get(newnode.getId()).setLink2(cnode2);
		return nodemap;
	}
	
	/**
	 * Travels from one end to another end of a route
	 * @param o
	 * @return
	 */
	public static TSPtestnode TranverseToOppositeEnd(TSPtestnode o){
		if(o.getLink1() == null){
			TSPtestnode previousnode = o.getLink1();
			while(o!=null){
				TSPtestnode dump = o;	//Saves this node to be assigned to previous node
				o = o.TranverseNode(previousnode);	//Main node moves on to next node
				previousnode = dump;	//Saves previous node, which is needed for main node to proceed to next node in the right direction
			}
			return previousnode;
		}
		else{
			TSPtestnode previousnode = o.getLink2();
			while(o!=null){
				TSPtestnode dump = o;	//Saves this node to be assigned to previous node
				o = o.TranverseNode(previousnode);	//Main node moves on to next node
				previousnode = dump;	//Saves previous node, which is needed for main node to proceed to next node in the right direction
			}
			return previousnode;
		}
	}
	
	/**
	 * Checks whether path points towards a node or a route (list of nodes)
	 * 1 means node and 2 means route
	 * @param o
	 * @return
	 */
	public static int PathNature(TSPtestnode o){
		//If points to node, return 1
		if(o.getLink1()==null && o.getLink2()==null){
			return 1;
		}
		//Else is assumed to be route for now for the current implementation. Condition would be one link is null and the other is not.
		else{
			return 2;
		}
	}
	
	/**
	 * Scans a closed circuit and returns a list of nodes that belong to the circuit in the form of node IDs
	 * @param o
	 * @return
	 */
	public static List<Integer> nodesincircuit(TSPtestnode o){
		List<Integer> nodes = new ArrayList<Integer>();
		int originid = o.getId();
		TSPtestnode previousnode = o.getLink1();
		while(o.TranverseNode(previousnode)!=null){
			
			TSPtestnode dump = o;	//Saves this node to be assigned to previous node
			o = o.TranverseNode(previousnode);	//Main node moves on to next node
			previousnode = dump;	//Saves previous node, which is needed for main node to proceed to next node in the right direction
			int nextid = o.getId();
			
			nodes.add(nextid);
			if(nextid==originid){
				return nodes;
			}
		}
		return null;
	}
	
	/**
	 * Calculate distance of circuit
	 * @param o
	 * @return
	 */
	public static double CircuitDist(TSPtestnode o){
		int originid = o.getId();
		TSPtestnode previousnode = o.getLink1();
		double totaldist = 0;
		totaldist= totaldist + MathKit.DoubleTwoDEuclideanDist(o.getX(), o.getY(), previousnode.getX(), previousnode.getY());
		while(o.TranverseNode(previousnode).getId()!=originid){
			totaldist= totaldist + MathKit.DoubleTwoDEuclideanDist(o.getX(), o.getY(), o.TranverseNode(previousnode).getX(), o.TranverseNode(previousnode).getY());
			
			TSPtestnode dump = o;	//Saves this node to be assigned to previous node
			o = o.TranverseNode(previousnode);	//Main node moves on to next node
			previousnode = dump;	//Saves previous node, which is needed for main node to proceed to next node in the right direction
		}
		return totaldist;
	}
	
	/**
	 * Checks if circuit is closed
	 * @param o
	 * @return
	 */
	public static boolean circuitclosed(TSPtestnode o){
		int originid = o.getId();
		TSPtestnode previousnode = o.getLink1();
		while(o.TranverseNode(previousnode)!=null){
			
			TSPtestnode dump = o;	//Saves this node to be assigned to previous node
			o = o.TranverseNode(previousnode);	//Main node moves on to next node
			previousnode = dump;	//Saves previous node, which is needed for main node to proceed to next node in the right direction
			
			if(o==null){
				return false;
			}
			else{
				int nextid = o.getId();
				if(nextid==originid){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks if node can be connected
	 * @param o
	 * @return
	 */
	public static boolean CanConnect(TSPtestnode o){
		if(o.getLink1() == null || o.getLink2() == null){
			return true;
		}
		return false;
	}
	
	/**
	 * Connects 2 node in hashmap
	 * @param o
	 * @return
	 */
	public static HashMap<Integer, TSPtestnode> ConnectNodes(int pathnodeID1, int pathnodeID2, HashMap<Integer, TSPtestnode> nodemap){
		if(nodemap.get(pathnodeID1).getLink1() == null && nodemap.get(pathnodeID2).getLink1() == null){
			nodemap.get(pathnodeID1).setLink1((nodemap.get(pathnodeID2)));
			nodemap.get(pathnodeID2).setLink1((nodemap.get(pathnodeID1)));
		}
		else if(nodemap.get(pathnodeID1).getLink1() == null && nodemap.get(pathnodeID2).getLink2() == null){
			nodemap.get(pathnodeID1).setLink1((nodemap.get(pathnodeID2)));
			nodemap.get(pathnodeID2).setLink2((nodemap.get(pathnodeID1)));
		}
		else if(nodemap.get(pathnodeID1).getLink2() == null && nodemap.get(pathnodeID2).getLink1() == null){
			nodemap.get(pathnodeID1).setLink2((nodemap.get(pathnodeID2)));
			nodemap.get(pathnodeID2).setLink1((nodemap.get(pathnodeID1)));
		}
		else if(nodemap.get(pathnodeID1).getLink2() == null && nodemap.get(pathnodeID2).getLink2() == null){
			nodemap.get(pathnodeID1).setLink2((nodemap.get(pathnodeID2)));
			nodemap.get(pathnodeID2).setLink2((nodemap.get(pathnodeID1)));
		}
		return nodemap;
	}
	
	/**
	 * Control method to provide the definite right answer
	 * Still need to verify if this function is actually producing the right answer
	 * @param nodes
	 */
	public static void bruteforcecontrol(List<TSPtestnode> nodes){
		
		List<List<TSPtestnode>> allpossibleroutes = AlgoKit.permutations(nodes.size(), nodes);
		double mindist = 999999999;
		List bestroute = new ArrayList();
		
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
	
