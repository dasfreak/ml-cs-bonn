package ml_ex1;

public class Tree {
	public Node   root;
	
	
	public Tree(Node root)
	{
		this.root = root;
		Node.nodeCounter++;
	}
	
	
	public String toString()
	{
		String tree = "";
		Node parent = root;
		tree += root.toString();
		return tree;
	}
	
}

