package test_programs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import test_objects.TSPtestnode;
import yifan_toolkit.MathKit;

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
}
