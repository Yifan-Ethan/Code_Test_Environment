package test_programs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import test_objects.TSPtestnode;
import test_objects.TSPtestpath;
import yifan_toolkit.MathKit;
import yifan_toolkit.UtilityKit;

public class TSPOptimizer {

	public static void run() throws FileNotFoundException{
		
		//Read values of coordinates
		String data = UtilityKit.readfile("test.txt");
		
		//Create a list of node values. Each entry represents information regarding one node
		List<String> rows = UtilityKit.stringtolistbydelimiter(data, "\n");
		
		//A list of all nodes in existence
		List<TSPtestnode> nodes = new ArrayList<TSPtestnode>();
		
		//A list of all available paths
		List<TSPtestpath> paths = new ArrayList<TSPtestpath>();
		
		//A map with all available nodes. Allows for quick retrieval of a specific node using nodeID
		HashMap<Integer, TSPtestnode> nodemap = new HashMap<Integer, TSPtestnode>();
		
		//Create nodes, add value to them and add nodes to list
		nodes = TSPSuppFunc.CreateNodes(rows);
		
		//Remove dups
		nodes = TSPSuppFunc.RemoveDupNodes(nodes);
		
		//Remaps nodes to hashmap for quick access via ID
		for(int i=0;i<nodes.size();i++){
			nodemap.put(nodes.get(i).getId(), nodes.get(i));
		}
		
		//Scans for all possible paths between 2 nodes
		paths = TSPSuppFunc.GenerateAllPaths(nodes);
		
		//Sort paths in ascending order
		paths = TSPSuppFunc.SortPathsAscending(paths);
		
		//Tracks the number of nodes that have been joined
		int joinednodes = 1;
		
		//Contains a list of edges. Each edge is joined by 2 nodes
		List<TSPtestpath> edges = new ArrayList<TSPtestpath>();
		
		//TEST ScanEdgesForInterception FUNCTION THOROUGHLY BEFORE PROCEEDING
		//Scans and joins all nodes using shortest path, and stops when all nodes have been joined with one or two other nodes
		for(int i=0;i<paths.size();i++){
			if(TSPSuppFunc.CanConnect(nodemap.get(paths.get(i).getNode1ID())) && TSPSuppFunc.CanConnect(nodemap.get(paths.get(i).getNode2ID())) &&
			   TSPSuppFunc.ScanEdgesForInterception(edges, nodemap, nodemap.get(paths.get(i).getNode1ID()), nodemap.get(paths.get(i).getNode2ID())))
			{
				edges.add(new TSPtestpath(paths.get(i).getNode1ID(), paths.get(i).getNode2ID()));
				nodemap = TSPSuppFunc.ConnectNodes(paths.get(i).getNode1ID(), paths.get(i).getNode2ID(), nodemap);
				paths.remove(i);
				i--;	//Since current path was removed, next path takes the index of current path
				joinednodes++;
				if(joinednodes==nodes.size()){
					i=paths.size();
				}
			}
		}
		
		//Contains a map of nodes that are not in circuit
		HashMap<Integer, TSPtestnode> nodesnotincircuit = new HashMap<Integer, TSPtestnode>();
		
		//Obtains a list of nodes that are within a circuit
		List<Integer> nodesincircuit = TSPSuppFunc.NodesInCircuit(nodemap.get(nodemap.keySet().toArray()[0]));

		//If node lies in a route and not a circuit
		if(nodesincircuit == null){
			
		}
		else{
			
		}
		
		//DANGER ZONE: LOGIC FROM HERE ON NEEDS OVERHAUL. DO NOT VENTURE UNLESS CERTAIN
		
		//Stores the last node that was added to route/circuit
		int lastnodeid = 0;
		
		List<TSPtestpath> CircuitEdges = TSPSuppFunc.CircuitEdges(nodemap.get(lastnodeid));
		
		TSPtestnode n = nodemap.get(lastnodeid);
		List<Integer> circuitnodes = TSPSuppFunc.nodesincircuit(n);
		
		while(circuitnodes.size()!=nodes.size()){
		
			//Removes obsolete paths
			paths = TSPSuppFunc.RemoveObsoletePaths(paths,circuitnodes);
			
			//Is the next path a single node or a route of nodes
			int pathnature = 0;
			
			//Node that is closest to a certain node in circuit
			TSPtestnode closestnodetocircuit = new TSPtestnode();
			
			//Scans for shortest paths with open node closest to circuit
			for(int i=0;i<paths.size();i++){
				for(int k=0; k<circuitnodes.size();k++){
					if(paths.get(i).getNode1ID()==circuitnodes.get(k) || paths.get(i).getNode2ID()==circuitnodes.get(k)){
						if(paths.get(i).getNode1ID()==circuitnodes.get(k)){
							pathnature = TSPSuppFunc.PathNature(nodemap.get(paths.get(i).getNode2ID()));
							closestnodetocircuit = nodemap.get(paths.get(i).getNode2ID());
						}
						else if(paths.get(i).getNode2ID()==circuitnodes.get(k)){
							pathnature = TSPSuppFunc.PathNature(nodemap.get(paths.get(i).getNode1ID()));
							closestnodetocircuit = nodemap.get(paths.get(i).getNode1ID());
						}
						paths.remove(i);
						i = paths.size();
						k = circuitnodes.size();
					}
				}
			}
			
			//List of paths from new node to all nodes in circuit
			List<TSPtestpath> circuitnodetonewnode = TSPSuppFunc.DistFromCircuitNodesToNewNode(circuitnodes, nodemap, closestnodetocircuit);
			
			//Sort paths in ascending order
			circuitnodetonewnode = TSPSuppFunc.SortPathsAscending(circuitnodetonewnode);
			
			//Node on circuit that is closest to node not in circuit
			TSPtestnode closestpointoncircuit = new TSPtestnode();
			
			//Scans for node in circuit that joins new node via the shortest distance and not cut across existing edges
			//Note that the new node is always stored as ID 1
			for(int i=0;i<circuitnodetonewnode.size();i++){
				boolean intercepted = false;
				TSPtestnode circuitnode = nodemap.get(circuitnodetonewnode.get(i).getNode2ID());
				intercepted = TSPSuppFunc.ScanEdgesForInterception(CircuitEdges,nodemap,closestnodetocircuit,circuitnode);
				if(intercepted == false){
					i = circuitnodetonewnode.size();
					closestpointoncircuit = circuitnode;
				}
			}
			
			//If path points to a node
			if(pathnature == 1){
				TSPtestnode leftnode = closestpointoncircuit.getLink1();
				TSPtestnode rightnode = closestpointoncircuit.getLink2();
				double newleftedge = MathKit.DoubleTwoDEuclideanDist(leftnode.getX(), leftnode.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY());
				double newrightedge = MathKit.DoubleTwoDEuclideanDist(rightnode.getX(), rightnode.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY());
				double existingleftedge = MathKit.DoubleTwoDEuclideanDist(leftnode.getX(), leftnode.getY(), closestpointoncircuit.getX(), closestpointoncircuit.getY());
				double existingrightedge = MathKit.DoubleTwoDEuclideanDist(rightnode.getX(), rightnode.getY(), closestpointoncircuit.getX(), closestpointoncircuit.getY());
				CircuitEdges.add(new TSPtestpath(closestpointoncircuit.getId(),closestnodetocircuit.getId()));
				//This condition means if (left node is shorter AND pass the test) OR (right node fails the test) 
				//Test consists of 2 elements: Check for interception with edges in circuit and whether edge will be parallel to other new edge
				if(((newleftedge+existingrightedge)<=(newrightedge+existingleftedge) &&
				   TSPSuppFunc.ScanEdgesForInterception(CircuitEdges,nodemap,leftnode,closestnodetocircuit)==false && 
				   TSPSuppFunc.IsNotParallel(leftnode.getX(), leftnode.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY(), closestpointoncircuit.getX(), closestpointoncircuit.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY()))
				   ||
				   TSPSuppFunc.ScanEdgesForInterception(CircuitEdges,nodemap,rightnode,closestnodetocircuit)==true || 
				   TSPSuppFunc.IsNotParallel(rightnode.getX(), rightnode.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY(), closestpointoncircuit.getX(), closestpointoncircuit.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY()) == false
				){
					nodemap = TSPSuppFunc.ResetNodesOnCircuitToNode(leftnode, closestpointoncircuit, closestnodetocircuit, nodemap);
					lastnodeid = closestnodetocircuit.getId();
					//Circuit edges need to be reset after each run. This is because the previous connection of nearest node with its left or right needs to be removed
					CircuitEdges = TSPSuppFunc.CircuitEdges(nodemap.get(lastnodeid));
				}
				else{
					nodemap = TSPSuppFunc.ResetNodesOnCircuitToNode(rightnode, closestpointoncircuit, closestnodetocircuit, nodemap);
					lastnodeid = closestnodetocircuit.getId();
					//Circuit edges need to be reset after each run. This is because the previous connection of nearest node with its left or right needs to be removed
					CircuitEdges = TSPSuppFunc.CircuitEdges(nodemap.get(lastnodeid));
				}
			}
			//If path points to a route
			else if(pathnature == 2){
				TSPtestnode routeend = TSPSuppFunc.TranverseToOppositeEnd(closestnodetocircuit);
				TSPtestnode leftnode = closestpointoncircuit.getLink1();
				TSPtestnode rightnode = closestpointoncircuit.getLink2();
				double newleftedge = MathKit.DoubleTwoDEuclideanDist(leftnode.getX(), leftnode.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY());
				double newrightedge = MathKit.DoubleTwoDEuclideanDist(rightnode.getX(), rightnode.getY(), closestnodetocircuit.getX(), closestnodetocircuit.getY());
				double existingleftedge = MathKit.DoubleTwoDEuclideanDist(leftnode.getX(), leftnode.getY(), closestpointoncircuit.getX(), closestpointoncircuit.getY());
				double existingrightedge = MathKit.DoubleTwoDEuclideanDist(rightnode.getX(), rightnode.getY(), closestpointoncircuit.getX(), closestpointoncircuit.getY());
				CircuitEdges.add(new TSPtestpath(closestpointoncircuit.getId(),closestnodetocircuit.getId()));
				//This condition means if (left node is shorter AND pass the test) OR (right node fails the test) 
				//Test consists of 2 elements: Check for interception with edges in circuit and whether edge will be parallel to other new edge
				if(((newleftedge+existingrightedge)<=(newrightedge+existingleftedge) && 
				   TSPSuppFunc.ScanEdgesForInterception(CircuitEdges,nodemap,leftnode,routeend)==false && 
				   TSPSuppFunc.IsNotParallel(leftnode.getX(), leftnode.getY(), routeend.getX(), routeend.getY(), closestpointoncircuit.getX(), closestpointoncircuit.getY(), routeend.getX(), routeend.getY()))
				   ||
				   TSPSuppFunc.ScanEdgesForInterception(CircuitEdges,nodemap,rightnode,routeend)==true || 
				   TSPSuppFunc.IsNotParallel(rightnode.getX(), rightnode.getY(), routeend.getX(), routeend.getY(), closestpointoncircuit.getX(), closestpointoncircuit.getY(), routeend.getX(), routeend.getY()) == false
				){
					nodemap = TSPSuppFunc.ResetNodesOnCircuitToRoute(closestpointoncircuit, leftnode, closestnodetocircuit, routeend, nodemap);
					lastnodeid = closestnodetocircuit.getId();
					//Circuit edges need to be reset after each run. This is because the previous connection of nearest node with its left or right needs to be removed
					CircuitEdges = TSPSuppFunc.CircuitEdges(nodemap.get(lastnodeid));
				}
				else{
					nodemap = TSPSuppFunc.ResetNodesOnCircuitToRoute(closestpointoncircuit, rightnode, closestnodetocircuit, routeend, nodemap);
					lastnodeid = closestnodetocircuit.getId();
					//Circuit edges need to be reset after each run. This is because the previous connection of nearest node with its left or right needs to be removed
					CircuitEdges = TSPSuppFunc.CircuitEdges(nodemap.get(lastnodeid));
				}
			}
			
			n = nodemap.get(lastnodeid);
			circuitnodes = TSPSuppFunc.nodesincircuit(n);
		}
		//TSPSuppFunc.LogCircuit(n);
		System.out.println(TSPSuppFunc.CircuitDist(n));
	}
	
}
