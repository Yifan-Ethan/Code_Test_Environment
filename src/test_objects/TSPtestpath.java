package test_objects;

public class TSPtestpath {

	double distance = 0;
	int node1ID = 0;
	int node2ID = 0;
	
	public TSPtestpath(int node1,int node2){
		this.node1ID = node1;
		this.node2ID = node2;
	}
	
	public TSPtestpath(int node1,int node2,double distance){
		this.node1ID = node1;
		this.node2ID = node2;
		this.distance = distance;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getNode1ID() {
		return node1ID;
	}

	public void setNode1ID(int node1id) {
		node1ID = node1id;
	}

	public int getNode2ID() {
		return node2ID;
	}

	public void setNode2ID(int node2id) {
		node2ID = node2id;
	}
	
	public String toString(){
		String content = "Distance: "+this.distance+", Node1: "+this.node1ID+", Node2: "+this.node2ID;
		return content;
	}
}
