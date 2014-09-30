package calltree;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable {
	public String name;
	public int samples;
	int percent;
	SortedArrayList<Node> children = new SortedArrayList<Node>();
	
	Node() {
		
	}
	
	public Node(String n) {
		name = n;
		samples = 1;
		percent = 0;
	}
	
	public static Node getRoot() {
		Node root = new Node();
		root.name = "/";
		root.percent = 100;
		return root;
	}
	
	public void addChild(Node n) {
		children.insertSorted(n);
	}

	public Node addChild(String str) {
		for (Node n : children) {
			if (n.name.equals(str) ) {
				n.samples++;
				return n;
			}
		}
		Node newNode = new Node(str);
		addChild(newNode);
		return newNode;
	}

	@Override
	public int compareTo(Object o) {
		Node n = (Node) o;
		if (n.samples < this.samples)
			return -1;
		return 0;
	}
}
