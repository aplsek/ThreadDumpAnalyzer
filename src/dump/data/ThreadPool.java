package dump.data;

public class ThreadPool {
	String name;
	int threads;
	
	
	
	public static final String getThreadPoolName(final String threadName) {
		return threadName.replaceAll("[0-9]", "");
	}
}
