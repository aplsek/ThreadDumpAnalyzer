package dump;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import calltree.CallTreeBuilder;
import calltree.Node;
import dump.data.ThreadDump;
import dump.data.ThreadStack;
import dump.data.ThreadState;

public class Analyzer {

	public void analyze(List<ThreadDump> dumps) {
		for (ThreadDump dump : dumps) {
			dump.analyzeHotspots();
			dump.analyzeThreadPools();
			dump.analyzeThreadStates();
		}
	}
	
	public void print(List<ThreadDump> dumps) {
		for (ThreadDump dump : dumps) {
			dump.print();
		}
	}
	
	public static final void pln(String str) {
		System.out.println(str);
	}
}
