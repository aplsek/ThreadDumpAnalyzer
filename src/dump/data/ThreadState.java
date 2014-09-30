package dump.data;

public enum ThreadState {
	RUNNABLE("RUNNABLE"), 
	BLOCKED("BLOCKED"), 
	TIMED_WAITING("TIMED_WAITING"), 
	WAITING("WAITING");

	private String text;

	ThreadState(String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static ThreadState fromString(String text) {
		if (text != null) {
			for (ThreadState b : ThreadState.values()) {
				if (text.equalsIgnoreCase(b.text)) {
					return b;
				}
			}
		}
		return null;
	}
}
