package dump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dump.data.ThreadDump;

public class ThreadDumpExperiment {
	List<ThreadDump> dumps = new ArrayList<ThreadDump>();
	
	Map<String,Integer> hotspots = new HashMap<String,Integer>();
}
