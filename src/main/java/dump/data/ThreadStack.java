package dump.data;

import java.util.ArrayList;
import java.util.List;

public class ThreadStack {

	public String name;
	public String headLine;
	public ThreadState state;
	
	List<String> callstack = new ArrayList<String>();
	List<String> lockedObjects = new ArrayList<String>();
	List<String> waitingObjects = new ArrayList<String>();
	
	public void setThreadName(String threadName) {
	    name = threadName;
	}
	
	public List<String> getCallStack() {
		return callstack;
	}

	public void addCall(String methodCall) {
		callstack.add(methodCall);
	}
	
	public void addLock(String lock) {
		lockedObjects.add(lock);
	}
	
	public void addWait(String lock) {
		waitingObjects.add(lock);
	}

}
