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

public class Parser {

	
	//strings:
	public static final String DUMP_START = "Full thread dump";
	public static final String THREAD_STACK_START = "\"";
	public static final String STACK_LINE_START = "at ";
	public static final String LOCK_STATE = "- ";
	public static final String THREAD_STATE = "java.lang.Thread.State:";
	public static final String EMPTY_LINE = "";		
	
	List<ThreadDump> dumps = new ArrayList<ThreadDump>();
	
	ThreadDump currentDump = null;
	public ThreadStack curStack;
	ParserState state = ParserState.NEW;
	String previousLine = "";
	String line;

	private int parsedThreads = 0;
	
	public List<ThreadDump> parse(String inputFile) {
		try {
			FileReader fileReader = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fileReader);
			
			while ((line = br.readLine()) != null) {
			   line = formatLine(line);	
			   switch (state) {
			   		case NEW:
			   			parseNEW();
			   			break;
			   		case THREAD_START:
			   			parseTHREAD_START();
			   			break;
			   		case THREAD_STACK:
			   			parseTHREAD_STACK();
			   			break;
			   }
			   previousLine = line;
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		pln("done");
		pln("dumps : " + dumps.size());
		pln("threads : " + parsedThreads);
		
		return dumps;
	}
	
	
	public void print() {
		for (ThreadDump dump : dumps) {
			dump.print();
		}
	}
	
	private String formatLine(String line2) {
		return line.trim();
	}

	public static final void pln(String str) {
		System.out.println(str);
	}

	private void parseTHREAD_STACK() {
		if (line.startsWith(STACK_LINE_START)) {
			String methodCall = line.substring(2, line.length()).trim();
			curStack.addCall(methodCall);
		} else if (line.startsWith(LOCK_STATE)) {
			//TODO: unify this with the previous case:
			String methodCall = line.substring(2, line.length()).trim();
			curStack.addCall(methodCall);
		} else if (line.startsWith(EMPTY_LINE)) {
			state = ParserState.NEW;
		}
		
	}

	private void parseNEW() {
		   if (line.startsWith(DUMP_START)) {
			   currentDump = new ThreadDump();
			   currentDump.timestamp = previousLine;
			   dumps.add(currentDump);
			   state = ParserState.NEW;
		   } 
		   
		   if (line.startsWith(THREAD_STACK_START)) {
			   curStack = new ThreadStack();
			   curStack.setThreadName(getThreadName(line));
			   curStack.headLine = line;
			   currentDump.addThread(curStack);
			   state = ParserState.THREAD_START;
			   
			   parsedThreads ++;
		   }
	}
	
	private void parseTHREAD_START() {
		String st = getThreadState(line);
		curStack.state = ThreadState.valueOf(st);
		state  = ParserState.THREAD_STACK;
	}

	private String getThreadState(String line2) {
		if (line.startsWith(THREAD_STATE)) {
			String state = line.substring(THREAD_STATE.length(),line.length());
			if (line.contains("(")) {
				state = state.substring(0,state.indexOf("("));
			}
			return state.trim();
		}
		return null;
	}

	private String getThreadName(String str) {
		String s = str.substring(1,str.length());
		String res = s.substring(0,s.indexOf("\""));
		return res.trim();
	}
	
	
	
	public static void main(String[] args) {
		//Parser a = new Parser();
		//a.parse();
		//a.analyze();
		//a.print();
		//
		//CallTreeBuilder c = new CallTreeBuilder();
		//Node root = c.buildCallTree(a.dumps);
		//
		//pln("Calltree done : " + root);
	}
}
