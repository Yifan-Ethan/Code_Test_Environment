package test_programs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import test_objects.TSPtestnode;
import test_objects.TSPtestpath;
import yifan_toolkit.MathKit;
import yifan_toolkit.UtilityKit;

public class TSPSuppFunc {
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
		System.out.println(o);
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
	 * Checks if node belongs to a closed circuit
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
	 * Creates a list of edges belonging to a circuit. Each edge is represented as a path.
	 * @param lastnodeid
	 * @param nodemap
	 * @return
	 */
	public static List<TSPtestpath> CircuitEdges(TSPtestnode o) {
		
		List<TSPtestpath> edges = new ArrayList<TSPtestpath>();
		
		int originid = o.getId();
		TSPtestnode previousnode = o.getLink1();
		edges.add(new TSPtestpath(o.getId(),o.getLink1().getId()));
		while(o.TranverseNode(previousnode).getId()!=originid){
			
			edges.add(new TSPtestpath(o.getId(),o.TranverseNode(previousnode).getId()));
			
			TSPtestnode dump = o;	//Saves this node to be assigned to previous node
			o = o.TranverseNode(previousnode);	//Main node moves on to next node
			previousnode = dump;	//Saves previous node, which is needed for main node to proceed to next node in the right direction
			
		}
		
		return edges;
	}

	/**
	 * Removes paths that are no longer needed
	 * @param paths
	 * @return
	 */
	public static List<TSPtestpath> RemoveObsoletePaths(List<TSPtestpath> paths,List<Integer> circuitnodes) {
		for(int i=0;i<paths.size();i++){
			for(int k=0;k<circuitnodes.size();k++){
				for(int l=k+1;l<circuitnodes.size();l++){
					if((paths.get(i).getNode1ID()==circuitnodes.get(k) && paths.get(i).getNode2ID()==circuitnodes.get(l)) ||
							(paths.get(i).getNode2ID()==circuitnodes.get(k) && paths.get(i).getNode1ID()==circuitnodes.get(l))	){
						paths.remove(i);
						i--;
						if(i==-1){
							k=circuitnodes.size();
							l=circuitnodes.size();
						}
					}
				}
			}
		}
		return paths;
	}

	/**
	 * Returns a list of paths from each circuit node to new node
	 * @param circuitnodes
	 * @param nodemap
	 * @param closestnodetocircuit 
	 * @return
	 */
	public static List<TSPtestpath> DistFromCircuitNodesToNewNode(List<Integer> circuitnodes, HashMap<Integer, TSPtestnode> nodemap, TSPtestnode n) {
		
		List<TSPtestpath> paths = new ArrayList<TSPtestpath>();
		
		for(int i=0;i<circuitnodes.size();i++){
			TSPtestnode nc = nodemap.get(circuitnodes.get(i));
			paths.add(new TSPtestpath(n.getId(), nc.getId(), MathKit.DoubleTwoDEuclideanDist(n.getX(), n.getY(), nc.getX(), nc.getY())));
		}
		
		return paths;
	}

	/**
	 * Scans all edges in circuit to check if new edge will result in an interception
	 * @param CircuitEdges
	 * @param nodemap
	 * @param newnode
	 * @param circuitnode
	 * @return
	 */
	public static boolean ScanEdgesForInterception(List<TSPtestpath> CircuitEdges, HashMap<Integer, TSPtestnode> nodemap, TSPtestnode newnode, TSPtestnode circuitnode) {
		for(int k=0;k<CircuitEdges.size();k++){
			TSPtestnode cep1 = nodemap.get(CircuitEdges.get(k).getNode1ID());
			TSPtestnode cep2 = nodemap.get(CircuitEdges.get(k).getNode2ID());
			if(MathKit.IfEdgesIntersect(newnode.getX(), newnode.getY(), circuitnode.getX(), circuitnode.getY(), cep1.getX(), cep1.getY(), cep2.getX(), cep2.getY())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if 2 edges are parallel
	 * @return
	 */
	public static boolean IsNotParallel(double e1x1, double e1y1, double e1x2, double e1y2, double e2x1, double e2y1, double e2x2, double e2y2){
		List<List<Double>> e1MNC = MathKit.GraphLinearEquationMNCDouble(e1x1, e1y1, e1x2, e1y2);
		List<List<Double>> e2MNC = MathKit.GraphLinearEquationMNCDouble(e2x1, e2y1, e2x2, e2y2);
		
		//If edges are parallel, return false
		if(
		  (e1MNC.get(0).get(0).equals(e2MNC.get(0).get(0))) &&
		  (e1MNC.get(0).get(1).equals(e2MNC.get(0).get(1)))
		  ){
			return false;
		}
		return true;
	}

	/**
	 * Create nodes, add value to them and add nodes to list
	 * Code within this loop largely depend on format at which node information is kept in file
	 * Current implement assumes format to be "1 385 204", where first number represent node index (useless), 2nd number is x coordinate, 3rd number is y coordinate
	 * @param rows 
	 * @return
	 */
	public static List<TSPtestnode> CreateNodes(List<String> rows) {
		List<TSPtestnode> nodes = new ArrayList<TSPtestnode>();
		for(int i=0;i<rows.size();i++){
			List<String> dump = UtilityKit.stringtolistbydelimiter(rows.get(i), " ");
			nodes.add(new TSPtestnode(i,Double.valueOf(dump.get(1)),Double.valueOf(dump.get(2))));
		}
		return nodes;
	}

	/**
	 * Remove repeated nodes that are kept in list
	 * @param nodes 
	 * @return
	 */
	public static List<TSPtestnode> RemoveDupNodes(List<TSPtestnode> nodes) {
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
		return nodes;
	}

	/**
	 * Create all possible paths from all available nodes
	 * @param nodes 
	 * @return
	 */
	public static List<TSPtestpath> GenerateAllPaths(List<TSPtestnode> nodes) {
		List<TSPtestpath> paths = new ArrayList<TSPtestpath>();
		for(int i=0;i<nodes.size();i++){
			for(int j=i+1;j<nodes.size();j++){
				TSPtestpath path = new TSPtestpath(nodes.get(i).getId(), nodes.get(j).getId(), MathKit.DoubleTwoDEuclideanDist(nodes.get(i).getX(), nodes.get(i).getY(), nodes.get(j).getX(), nodes.get(j).getY()));
				paths.add(path);
			}
		}
		return paths;
	}

	/**
	 * Sort paths in ascending order
	 * @param paths
	 * @return
	 */
	public static List<TSPtestpath> SortPathsAscending(List<TSPtestpath> paths) {
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
		return paths;
	}
}
