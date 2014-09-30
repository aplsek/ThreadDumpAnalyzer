package calltree;

import java.util.List;

import dump.data.ThreadDump;
import dump.data.ThreadStack;

public class CallTreeBuilder {

	public void processThreadStack(List<String> stack, int idx, Node node) {
		if (idx < 0)
			return;
		
		String str = stack.get(idx);
		Node child = node.addChild(str);
		processThreadStack(stack,--idx,child);
	}
	
	public Node buildCallTree(List<ThreadDump> dumps) {
		Node root = Node.getRoot();
		root.samples = dumps.size();
		
		for (ThreadDump td : dumps) {
			for (ThreadStack st : td.getThreads()) {
				List<String> stack = st.getCallStack();
				processThreadStack(stack, stack.size()-1, root);
			}

		}
		
		return root;
	}
}
