package test_objects;

import java.util.HashMap;

/**
 * This object is used for memoizing (Dynamic programming)
 * @author weiyifan
 *
 */
public class memoize {

	@SuppressWarnings("rawtypes")
	HashMap memoizelist = new HashMap();
	
	public memoize(){
	}
	
	public memoize(@SuppressWarnings("rawtypes") HashMap m){
		this.memoizelist = m;
	}

	@SuppressWarnings("rawtypes")
	public HashMap getMemoizelist() {
		return memoizelist;
	}

	@SuppressWarnings("rawtypes")
	public void setMemoizelist(HashMap memoizelist) {
		this.memoizelist = memoizelist;
	}
	
	@SuppressWarnings("unchecked")
	public void addkeyvaluepair (Object key, Object value){
		this.memoizelist.put(key, value);
	}
	
	public void clearmemoize(){
		this.memoizelist.clear();
	}
}
