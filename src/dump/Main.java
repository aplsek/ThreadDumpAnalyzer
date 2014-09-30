package dump;

import java.util.List;

import calltree.CallTreeBuilder;
import calltree.Node;
import dump.data.ThreadDump;

public class Main {

	public static void main(String[] args) {
		String inputFile = "./input/onedumps.txt";
		//String inputFile = "./input/threadDumps.txt";
		//String inputFile = "./input/dumps.txt";
		
		Parser p = new Parser();
		List<ThreadDump> dumps = p.parse(inputFile);
		
		
		Analyzer a = new Analyzer();
		a.analyze(dumps);
		a.print(dumps);
		
		CallTreeBuilder c = new CallTreeBuilder();
		Node root = c.buildCallTree(dumps);
		
		pln("Calltree done : " + root);
		

	}
	
	public static final void pln(String str) {
		System.out.println(str);
	}

}
