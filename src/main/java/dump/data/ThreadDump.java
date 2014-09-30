package dump.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dump.data.utils.MapUtil;

public class ThreadDump {
	public String timestamp;
	List<ThreadStack> threads =  new ArrayList<ThreadStack>();	
	Map<String,Integer> hotspots = new HashMap<String,Integer>();
	Map<String,Integer> threadPools = new HashMap<String,Integer>();
	Map<ThreadState,Integer> states = new HashMap<ThreadState,Integer>();
	

	public void addThread(ThreadStack curStack) {
	    threads.add(curStack);
	}
	
	public List<ThreadStack> getThreads() {
		return threads;
	}
	
	public void analyzeHotspots() {
		HashMap<String,Integer> h = new HashMap<String,Integer>();
		for (ThreadStack th : threads) {
			if (th.callstack.isEmpty()) 
				continue;
			String str = th.callstack.get(0);
		 	
			if (h.containsKey(str)) {
		 		Integer cnt = h.get(str);
		 		int c = cnt.intValue() +1 ;
		 		h.put(str,new Integer(c));
		 	} else {
		 		h.put(str,1);
		 	}
		}
		
		hotspots = MapUtil.sortByValue(h);
	}
	
	public void analyzeThreadPools() {
		Map<String,Integer> res = new HashMap<String,Integer>();
		for (ThreadStack th : threads) {
			String name = th.name;
		 	String pool = ThreadPool.getThreadPoolName(name);
			
			if (res.containsKey(pool)) {
		 		Integer cnt = res.get(pool);
		 		int c = cnt.intValue() +1 ;
		 		res.put(pool,new Integer(c));
		 	} else {
		 		res.put(pool,1);
		 	}
		}
		
		//todo remove pools with only one thread
		
		threadPools = MapUtil.sortByValue(res);
	}
	
	public void analyzeThreadStates() {
		Map<ThreadState,Integer> res = new HashMap<ThreadState,Integer>();
		res.put(ThreadState.BLOCKED,Integer.valueOf(0));
		res.put(ThreadState.RUNNABLE,Integer.valueOf(0));
		res.put(ThreadState.TIMED_WAITING,Integer.valueOf(0));
		res.put(ThreadState.WAITING,Integer.valueOf(0));
		
		for (ThreadStack th : threads) {
			ThreadState state = th.state;	
			if (res.containsKey(state)) {
		 		Integer cnt = res.get(state);
		 		int c = cnt.intValue() +1 ;
		 		res.put(state,new Integer(c));
		 	} else {
		 		res.put(state,1);
		 	}
		}
		
		states = MapUtil.sortByValue(res);
	}

	public void print() {
		pln("Hotposts:");
		for (Map.Entry entry : hotspots.entrySet()) {
		    System.out.println("  " + entry.getKey() + " : " + entry.getValue());
		}
		pln("Hotposts:");

		pln("Pools:");
		for (Map.Entry entry : threadPools.entrySet()) {
		    System.out.println("  " + entry.getKey() + " : " + entry.getValue());
		}

		pln("states:");
		for (Map.Entry entry : states.entrySet()) {
		    System.out.println("  " + entry.getKey() + " : " + entry.getValue());
		}
	}
	
	//private void printMap(Map<K,V> map) {
	//	for (Map.Entry entry : map.entrySet()) {
	//	    System.out.println(entry.getKey() + " : " + entry.getValue());
	//	}
	//}
		
	public static final void pln(String str) {
		System.out.println(str);
	}
}
