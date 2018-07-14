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
		
		//Remaps nodes to hashmap for quick access via ID
		for(int i=0;i<nodes.size();i++){
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
			if(TSPSuppFunc.CanConnect(nodemap.get(pathnodeID1)) && TSPSuppFunc.CanConnect(nodemap.get(pathnodeID2))){
				nodemap = TSPSuppFunc.ConnectNodes(pathnodeID1, pathnodeID2, nodemap);
				paths.remove(i);
				i--;	//Since current path was removed, next path takes the index of current path
				boolean stopsearch = TSPSuppFunc.circuitclosed(nodemap.get(pathnodeID1));
				if(stopsearch==true){
					i=paths.size();
					lastnodeid = pathnodeID1;
				}
			}
		}
		
		TSPtestnode n = nodemap.get(lastnodeid);
		List<Integer> circuitnodes = TSPSuppFunc.nodesincircuit(n);
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
							pathnature = TSPSuppFunc.PathNature(nodemap.get(paths.get(i).getNode2ID()));
							closestnodetocircuit = nodemap.get(paths.get(i).getNode2ID());
						}
						else if(paths.get(i).getNode2ID()==circuitnodes.get(k)){
							pathnature = TSPSuppFunc.PathNature(nodemap.get(paths.get(i).getNode1ID()));
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
					nodemap = TSPSuppFunc.ResetNodesOnCircuitToNode(leftnode, closestpointoncircuit, closestnodetocircuit, nodemap);
					lastnodeid = closestnodetocircuit.getId();
				}
				else{
					nodemap = TSPSuppFunc.ResetNodesOnCircuitToNode(rightnode, closestpointoncircuit, closestnodetocircuit, nodemap);
					lastnodeid = closestnodetocircuit.getId();
				}
			}
			//If path points to a route
			else if(pathnature == 2){
				TSPtestnode routeend = TSPSuppFunc.TranverseToOppositeEnd(closestnodetocircuit);
				TSPtestnode leftnode = closestpointoncircuit.getLink1();
				TSPtestnode rightnode = closestpointoncircuit.getLink2();
				double leftnodedistance = MathKit.DoubleTwoDEuclideanDist(leftnode.getX(), leftnode.getY(), routeend.getX(), routeend.getY());
				double rightnodedistance = MathKit.DoubleTwoDEuclideanDist(rightnode.getX(), rightnode.getY(), routeend.getX(), routeend.getY());
				if(leftnodedistance<=rightnodedistance){
					nodemap = TSPSuppFunc.ResetNodesOnCircuitToRoute(closestpointoncircuit, leftnode, closestnodetocircuit, routeend, nodemap);
					lastnodeid = closestnodetocircuit.getId();
				}
				else{
					nodemap = TSPSuppFunc.ResetNodesOnCircuitToRoute(closestpointoncircuit, rightnode, closestnodetocircuit, routeend, nodemap);
					lastnodeid = closestnodetocircuit.getId();
				}
			}
			
			n = nodemap.get(lastnodeid);
			circuitnodes = TSPSuppFunc.nodesincircuit(n);
			nodescount = circuitnodes.size();
		}
		TSPSuppFunc.LogCircuit(n);
		System.out.println(TSPSuppFunc.CircuitDist(n));
	}
	
}