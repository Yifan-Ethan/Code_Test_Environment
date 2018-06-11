package test_objects;

import java.math.BigInteger;

public class object1 {
	
	BigInteger i = BigInteger.ONE;
	
	public object1(BigInteger i_input){
		i = i_input;
	}

	public BigInteger geti() {
		return i;
	}

	public void seti(BigInteger i) {
		this.i = i;
	}
	
	public String toString(){
		return geti().toString();
	}
}
