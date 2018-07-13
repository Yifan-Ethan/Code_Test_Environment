package test_objects;

public class TSPtestnode {

	boolean connected = false;
	int id = 0;
	double x = 0;
	double y = 0;
	TSPtestnode link1 = null;
	TSPtestnode link2 = null;
	
	public TSPtestnode(){
		
	}
	
	public TSPtestnode(int ID, double x, double y){
		this.id = ID;
		this.x = x;
		this.y = y;
	}

	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public String toString(){
		String content = this.x + " " + this.y;
		return content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TSPtestnode getLink1() {
		return link1;
	}

	public void setLink1(TSPtestnode link1) {
		this.link1 = link1;
	}

	public TSPtestnode getLink2() {
		return link2;
	}

	public void setLink2(TSPtestnode link2) {
		this.link2 = link2;
	}

	public TSPtestnode TranverseNode(TSPtestnode o){
		if(o.getId() == link1.getId()){
			return link2;
		}
		else if(o.getId() == link2.getId()){
			return link1;
		}
		return null;
	}
}
