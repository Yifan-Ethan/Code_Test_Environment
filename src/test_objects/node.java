package test_objects;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds an object that represents a point on map or point on graph.
 * It is used for path-finding algorithms
 * @author weiyifan
 *
 */
public class node {
	
	BigInteger x = BigInteger.ZERO;
	BigInteger y = BigInteger.ZERO;
	//Holds an array of coordinates that this coordinate can proceed to
	List<node> neighbournodes = new ArrayList<node>();
	
	public node(){
		this.x = null;
		this.y = null;
	}
	
	public node(BigInteger inputx, BigInteger inputy){
		this.x = inputx;
		this.y = inputy;
	}
	
	//This function adds a single neighbor to the coordinate
	public void addneighbour(node n){
		this.neighbournodes.add(n);
	}
	
	public BigInteger getX() {
		return x;
	}

	public List<node> getNeighbournode() {
		return neighbournodes;
	}

	public void setNeighbournode(List<node> neighbournodes) {
		this.neighbournodes = neighbournodes;
	}

	public void setX(BigInteger x) {
		this.x = x;
	}

	public BigInteger getY() {
		return y;
	}

	public void setY(BigInteger y) {
		this.y = y;
	}

	public String toString(){
		return "("+x+","+y+")";
	}
}
